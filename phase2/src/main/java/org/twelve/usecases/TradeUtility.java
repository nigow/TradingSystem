package org.twelve.usecases;

import org.twelve.entities.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// TODO javadoc is fucked

/**
 * Utility class for accessing certain types of trades and information on trades.
 *
 * @author Isaac
 */
abstract public class TradeUtility {

    protected ItemManager itemManager;
    protected AccountRepository accountRepository;

    /**
     * List of all trades in the system
     */
    protected List<Trade> trades;

    /**
     * List of all meetings times and places in the system
     */
    protected List<TimePlace> timePlaces;

    protected TimePlace getTimePlaceByID(int timePlaceID) {
        for (TimePlace timePlace : timePlaces) {
            if (timePlaceID == timePlace.getId())
                return timePlace;
        }
        return null;
    }

    protected Trade getTradeByID(int tradeID) {
        for (Trade trade : trades) {
            if (tradeID == trade.getId())
                return trade;
        }
        return null;
    }

    protected List<Integer> itemsTraderGives(int accountID, Trade trade) {
        List<Integer> items = new ArrayList<>();
        for (int id : trade.getItemsIds()) {
            if (itemManager.getOwnerId(id) == accountID)
                items.add(id);
        }
        return items;
    }

    protected List<Integer> itemsTraderGets(int accountID, Trade trade) {
        return itemsTraderGives(trade.getNextTraderID(accountID), trade);
    }

    /**
     * Retrieves all the trades the current account has.
     *
     * @return List of all of the trades the current account has done
     */
    protected List<Trade> getAllTradesAccount(int accountID) {
        List<Trade> accountTrades = new ArrayList<>();
        for (Trade trade : trades) {
            if (trade.getTraderIds().contains(accountID))
                accountTrades.add(trade);
        }
        return accountTrades;
    }

    /**
     * Returns a user-friendly string representation of a trade.
     *
     * @param trade The Trade whose representation is being returned
     * @return An user-friendly representation of a trade
     */
    public String tradeAsString(Trade trade) {
        TimePlace timePlace = getTimePlaceByID(trade.getTimePlaceID());
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < trade.getTraderIds().size(); i++) {
            int id = trade.getTraderIds().get(i);
            ans.append("\nUser " + i + ": " + accountRepository.getUsernameFromID(id));
            int j = (i + 1) % trade.getTraderIds().size();
            ans.append("\nitems being given to user " + j + ": ");
            for (int itemID : itemsTraderGives(id, trade)) {
                ans.append("\n").append(itemManager.findItemByIdString(itemID));
            }
        }

        ans.append("\nStatus: ").append(trade.getStatus().toString()).append(" ");
        ans.append("\nType: ");
        ans.append(trade.isPermanent() ? "Permanent " : "Temporary ");
        ans.append("\nLocation: ").append(timePlace.getPlace()).append(" ");
        ans.append("\nTime: ").append(timePlace.getTime()).append(" ");

        return ans.toString();
    }

    /**
     * Retrieves all the trades the current account has done in string format.
     *
     * @return List of the trades the current account has done in string format
     */
    public List<String> getAllTradesAccountString(int accountID) {
        List<String> accountTrades = new ArrayList<>();
        for (Trade trade : getAllTradesAccount(accountID)) {
            accountTrades.add(tradeAsString(trade));
        }
        return accountTrades;
    }

    public List<Integer> getAllTradesAccountID(int accountID) {
        List<Integer> accountTrades = new ArrayList<>();
        for (Trade trade : getAllTradesAccount(accountID)) {
            accountTrades.add(trade.getId());
        }
        return accountTrades;
    }

    /**
     * Retrieves the Ids of the top three trade partners of the current account.
     *
     * @return List of top three trade partners, if less than three list size is
     * adjusted
     */
    public List<Integer> getTopThreePartnersIds(int accountID, Restrictions restrictions) {
        Map<Integer, Integer> tradeFrequency = new HashMap<>();
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!trade.isTwoPersonTrade())
                continue;
            tradeFrequency.compute(trade.getNextTraderID(accountID), (k, v) -> v == null ? 1 : v + 1);
        }
        Map<Integer, Integer> sorted = tradeFrequency.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<Integer> partnerIDs = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {
            if (count >= restrictions.getNumberOfStats()) break;
            partnerIDs.add(entry.getKey());
            count++;
        }
        return partnerIDs;
    }

    /**
     * Retrieves the three most recent one-way trades the current account
     * has made.
     *
     * @return List of three most recent one-way trades the current account
     * has made, if less than three list size is adjusted
     */
    public List<Integer> getRecentOneWay(int accountID, Restrictions restrictions) {
        List<TimePlace> allOneWay = new ArrayList<>();
        List<Integer> threeRecent = new ArrayList<>();
        List<Integer> allOneWayItems = new ArrayList<>();
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!trade.isTwoPersonTrade())
                continue;
            if (!itemsTraderGives(accountID, trade).isEmpty() && itemsTraderGets(accountID, trade).isEmpty()) {
                TimePlace timePlace = getTimePlaceByID(trade.getId());
                allOneWay.add(timePlace);
            }
        }
        Collections.sort(allOneWay);
        for (TimePlace tp : allOneWay) {
            Trade trade = getTradeByID(tp.getId());
            allOneWayItems.addAll(itemsTraderGives(accountID, trade));
        }
        int count = 0;
        for (int tradeId : allOneWayItems) {
            if (count >= restrictions.getNumberOfStats()) break;
            threeRecent.add(tradeId);
            count++;
        }
        return threeRecent;
    }

    /**
     * Retrieves the three most recent two-way trades the current account has
     * made.
     *
     * @return List of three most recent two-way trades the current account
     * has made, if less than three list size is adjusted
     */
    public List<Integer> getRecentTwoWay(int accountID, Restrictions restrictions) {
        List<TimePlace> allTwoWay = new ArrayList<>();
        List<Integer> threeRecent = new ArrayList<>();
        List<Integer> allTwoWayItems = new ArrayList<>();
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!trade.isTwoPersonTrade())
                continue;
            if (!itemsTraderGives(accountID, trade).isEmpty() && !itemsTraderGets(accountID, trade).isEmpty()) {
                TimePlace timePlace = getTimePlaceByID(trade.getId());
                allTwoWay.add(timePlace);
            }
        }
        Collections.sort(allTwoWay);
        for (TimePlace tp : allTwoWay) {
            Trade trade = getTradeByID(tp.getId());
            allTwoWayItems.addAll(itemsTraderGives(accountID, trade));
        }
        int count = 0;
        for (int tradeId : allTwoWayItems) {
            if (count >= restrictions.getNumberOfStats()) break;
            threeRecent.add(tradeId);
            count++;
        }
        return threeRecent;
    }

    // TODO: fix the logic behind this
    /**
     * Retrieves the number of trades the current account has made in the past week.
     *
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
     * Retrieves the number of times the current user has failed to complete
     * a trade.
     *
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

    // TODO only checks trades with two people
    /**
     * Retrieves the number of times the current user has borrowed items.
     *
     * @return Number of times the current user has borrowed items
     */
    public int getTimesBorrowed(int accountID) {
        int timesBorrowed = 0;
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!trade.isTwoPersonTrade())
                continue;
            if (!itemsTraderGets(accountID, trade).isEmpty() && itemsTraderGives(accountID, trade).isEmpty())
                timesBorrowed++;
        }
        return timesBorrowed;
    }

    /**
     * Retrieves the number of times the current user has lent items.
     *
     * @return Number of times the current user has lent items
     */
    public int getTimesLent(int accountID) {
        int timesLent = 0;
        for (Trade trade : getAllTradesAccount(accountID)) {
            if (trade.getStatus() != TradeStatus.CONFIRMED && trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!trade.isTwoPersonTrade())
                continue;
            if (itemsTraderGets(accountID, trade).isEmpty() && !itemsTraderGives(accountID, trade).isEmpty())
                timesLent++;
        }
        return timesLent;
    }

    /**
     * Returns the date and time of this Trade.
     *
     * @return Date and time of this Trade
     */
    public LocalDateTime getDateTime(int tradeID) {
        return getTimePlaceByID(getTradeByID(tradeID).getTimePlaceID()).getTime();
    }

    /**
     * Gets the number of times this Trade has been edited.
     *
     * @return The number of times this Trade has been edited.
     */
    public int getEditedCounter(int tradeID) {
        return getTradeByID(tradeID).getEditedCounter();
    }

    /**
     * Returns whether this Trade is temporary or permanent.
     *
     * @return Whether this Trade is temporary or permanent
     */
    public boolean isPermanent(int tradeID) {
        return getTradeByID(tradeID).isPermanent();
    }

    /**
     * Returns if Trade is rejected.
     *
     * @return Whether the Trade is rejected
     */
    public boolean isRejected(int tradeID) {
        return getTradeByID(tradeID).getStatus().equals(TradeStatus.REJECTED);
    }

    /**
     * Returns if Trade is confirmed.
     *
     * @return Whether Trade is confirmed
     */
    public boolean isConfirmed(int tradeID) {
        return getTradeByID(tradeID).getStatus().equals(TradeStatus.CONFIRMED);
    }

    /**
     * Returns if Trade is unconfirmed.
     *
     * @return Whether Trade is unconfirmed
     */
    public boolean isUnconfirmed(int tradeID) {
        return getTradeByID(tradeID).getStatus().equals(TradeStatus.UNCONFIRMED);
    }

    /**
     * Returns if Trade is completed.
     *
     * @return Whether Trade is completed.
     */
    public boolean isCompleted(int tradeID) {
        return getTradeByID(tradeID).getStatus().equals(TradeStatus.COMPLETED);
    }

    /**
     * Returns if it is a accounts turn to edit.
     *
     * @return Whether it's the account's turn to edit.
     */
    public boolean isEditTurn(int accountID, int tradeID) {
        return getTradeByID(tradeID).isEditTurn(accountID);
    }

    /**
     * Retrieves all trades stored in persistent storage in string format.
     *
     * @return List of trades in string format
     */
    public List<String> getAllTradesString() {
        List<String> stringTrade = new ArrayList<>();
        for (Trade trade : trades) {
            stringTrade.add(tradeAsString(trade));
        }
        return stringTrade;
    }
}
