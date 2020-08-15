package org.twelve.usecases.trade;

import org.twelve.entities.TimePlace;
import org.twelve.entities.Trade;
import org.twelve.entities.TradeStatus;
import org.twelve.gateways.TradeGateway;
import org.twelve.usecases.system.ThresholdRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for accessing certain types of trades and information on trades.
 *
 * @author Isaac
 */
abstract public class TradeUtility extends TradeRepository {

    final ThresholdRepository thresholdRepository;

    /**
     * Constructor for TradeUtility.
     *
     * @param thresholdRepository Repository for storing all accounts threshold values of the program
     * @param tradeGateway        the gateway dealing with trades
     */
    public TradeUtility(ThresholdRepository thresholdRepository, TradeGateway tradeGateway) {
        super(tradeGateway);
        this.thresholdRepository = thresholdRepository;
    }

    /**
     * Retrieves the Ids of the top three trade partners of the current account.
     *
     * @param accountID Unique identifier of the account
     * @return List of top three trade partners, if less than three list size is adjusted
     */
    public List<Integer> getTopThreePartnersIds(int accountID) {
        Map<Integer, Integer> tradeFrequency = new HashMap<>();
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            tradeFrequency.compute(trade.getNextTraderID(accountID), (k, v) -> v == null ? 1 : v + 1);
        }
        Map<Integer, Integer> sorted = tradeFrequency.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<Integer> partnerIDs = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {
            if (count >= thresholdRepository.getNumberOfStats()) break;
            partnerIDs.add(entry.getKey());
            count++;
        }
        return partnerIDs;
    }

    /**
     * Retrieves the three most recent items given in one-way trades the current account
     * has made.
     *
     * @param accountID Unique identifier of the account
     * @return List of three most recent one-way trades the current account
     * has made, if less than three list size is adjusted
     */
    public List<Integer> getRecentOneWay(int accountID) {
        List<TimePlace> allOneWay = new ArrayList<>();
        List<Integer> threeRecent = new ArrayList<>();
        List<Integer> allOneWayItems = new ArrayList<>();
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!itemsTraderGives(accountID, trade.getId()).isEmpty() &&
                    itemsTraderGives(trade.getNextTraderID(accountID), trade.getId()).isEmpty()) {
                TimePlace timePlace = getTimePlaceByID(trade.getId());
                allOneWay.add(timePlace);
            }
        }
        Collections.sort(allOneWay);
        for (TimePlace tp : allOneWay) {
            Trade trade = getTradeByID(tp.getId());
            allOneWayItems.addAll(itemsTraderGives(accountID, trade.getId()));
        }
        int count = 0;
        for (int itemID : allOneWayItems) {
            if (count >= thresholdRepository.getNumberOfStats()) break;
            threeRecent.add(itemID);
            count++;
        }
        return threeRecent;
    }

    /**
     * Retrieves the three most recent items given in two-way trades the current account has made.
     *
     * @param accountID Unique identifier of the account
     * @return List of three most recent two-way trades the current account
     * has made, if less than three list size is adjusted
     */
    public List<Integer> getRecentTwoWay(int accountID) {
        List<TimePlace> allTwoWay = new ArrayList<>();
        List<Integer> threeRecent = new ArrayList<>();
        List<Integer> allTwoWayItems = new ArrayList<>();
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!itemsTraderGives(accountID, trade.getId()).isEmpty() &&
                    !itemsTraderGives(trade.getNextTraderID(accountID), trade.getId()).isEmpty()) {
                TimePlace timePlace = getTimePlaceByID(trade.getId());
                allTwoWay.add(timePlace);
            }
        }
        Collections.sort(allTwoWay);
        for (TimePlace tp : allTwoWay) {
            Trade trade = getTradeByID(tp.getId());
            allTwoWayItems.addAll(itemsTraderGives(accountID, trade.getId()));
        }
        int count = 0;
        for (int itemID : allTwoWayItems) {
            if (count >= thresholdRepository.getNumberOfStats()) break;
            threeRecent.add(itemID);
            count++;
        }
        return threeRecent;
    }

    /**
     * Retrieves the number of trades the current account has made in the past week.
     *
     * @param accountID Unique identifier of the account
     * @return Number of trades user has made in the past week
     */
    public int getNumWeeklyTrades(int accountID) {
        int weeklyTrades = 0;
        LocalDateTime currDate = LocalDateTime.now();
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        for (Trade trade : getAllTradesAccount(accountID)) {
            TimePlace timePlace = getTimePlaceByID(trade.getId());
            if (timePlace.getTime().isBefore(currDate) && timePlace.getTime().isAfter(weekAgo)) {
                weeklyTrades++;
            }
            if (timePlace.getTime().isEqual(currDate) || timePlace.getTime().isEqual(weekAgo)) {
                weeklyTrades++;
            }
        }
        return weeklyTrades;
    }

    /**
     * Retrieves the number of times the current user has failed to complete a trade.
     *
     * @param accountID Unique identifier of the account
     * @return Number of times the current user has failed to complete a trade
     */
    public int getTimesIncomplete(int accountID) {
        int timesIncomplete = 0;
        for (Trade trade : getAllTradesAccount(accountID)) {
            LocalDateTime tradeTime = getTimePlaceByID(trade.getId()).getTime();
            if (tradeTime.isBefore(LocalDateTime.now()) && trade.getStatus().equals(TradeStatus.CONFIRMED)) {
                timesIncomplete++;
            }
        }
        return timesIncomplete;
    }

    /**
     * Retrieves the number of times the current user has borrowed items.
     *
     * @param accountID Unique identifier of the account
     * @return Number of times the current user has borrowed items
     */
    public int getTimesBorrowed(int accountID) {
        int timesBorrowed = 0;
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!itemsTraderGives(trade.getNextTraderID(accountID), trade.getId()).isEmpty() &&
                    itemsTraderGives(accountID, trade.getId()).isEmpty())
                timesBorrowed++;
        }
        return timesBorrowed;
    }

    /**
     * Retrieves the number of times the current user has lent items.
     *
     * @param accountID Unique identifier of the account
     * @return Number of times the current user has lent items
     */
    public int getTimesLent(int accountID) {
        int timesLent = 0;
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (itemsTraderGives(trade.getNextTraderID(accountID), trade.getId()).isEmpty() &&
                    !itemsTraderGives(accountID, trade.getId()).isEmpty())
                timesLent++;
        }
        return timesLent;
    }


    /**
     * Determines whether the current account has lent more than borrowed.
     *
     * @param accountID Unique identifier of the account
     * @return Whether the current account has lent more than borrowed
     */
    public boolean lentMoreThanBorrowed(int accountID) {
        return getTimesLent(accountID) - getTimesBorrowed(accountID) >=
                thresholdRepository.getLendMoreThanBorrow() || getTimesBorrowed(accountID) == 0;
    }

    /**
     * return whether this account can be trusted.
     *
     * @param accountID Unique identifier of the account
     * @return whether this account can be trusted
     */
    public boolean canBeTrusted(int accountID) {
        int counter = 0;
        for (Trade trade : getAllTradesAccount(accountID))
            if (trade.getStatus() == TradeStatus.COMPLETED)
                counter++;
        return counter >= thresholdRepository.getRequiredTradesForTrusted();
    }


    /**
     * Returns if it is a accounts turn to edit.
     *
     * @param accountID Unique identifier of the account
     * @param tradeID   Unique identifier of the trade
     * @return Whether it's the account's turn to edit
     */
    public boolean isEditTurn(int accountID, int tradeID) {
        return getTradeByID(tradeID).isEditTurn(accountID);
    }

    /**
     * return whether this account completed this trade or not.
     *
     * @param accountID id of account
     * @param tradeID   id of trade
     * @return whether this account completed trade or not
     */
    public boolean accountCompletedTrade(int accountID, int tradeID) {
        Trade trade = getTradeByID(tradeID);
        int ind = trade.getTraderIds().indexOf(accountID);
        return trade.getTradeCompletions().get(ind);
    }
}
