package usecases;

import entities.Account;
import entities.TimePlace;
import entities.Trade;
import entities.TradeStatus;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Utility class for trades to access certain types of trades for an account
 * @author Isaac
 */
public class TradeUtility {

    /**
     * Manager responsible for creating and editing trades
     */
    private final TradeManager tradeManager;

    /**
     * The account which info on its trades are being retrieved
     */
    private Account account;

    /**
     * Constructor for TradeUtility which stores an account and TradeManager
     * @param tradeManager Manager for creating and editing trades
     */
    public TradeUtility(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    /** Sets the current account to be edited
     * @param account the current account to be edited
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Retrieves all the trades the current account has done
     * @return List of all of the trades the current account has done
     */
    public List<Trade> getAllTradesAccount() {
        List<Trade> accountTrades = new ArrayList<>();
        for (Trade trade : tradeManager.getAllTrades()) {
            if (trade.getTraderOneID() == account.getAccountID()) {
                accountTrades.add(trade);
            }
            else if (trade.getTraderTwoID() == account.getAccountID()) {
                accountTrades.add(trade);
            }
        }
        return accountTrades;
    }

    /**
     * Retrieves all the trades the current account has done in string format
     * @return List of the trades the current account has done in string format
     */
    public List<String> getAllTradesAccountString() {
        List<String> accountTrades = new ArrayList<>();
        for (Trade trade : tradeManager.getAllTrades()) {
            if (trade.getTraderOneID() == account.getAccountID()) {
                accountTrades.add(trade.toString());
            }
            else if (trade.getTraderTwoID() == account.getAccountID()) {
                accountTrades.add(trade.toString());
            }
        }
        return accountTrades;
    }

    /**
     * Retrieves the Ids of the top three trade partners of the current account
     * @return List of top three trade partners, if less than three list size is
     * adjusted
     */
    public List<Integer> getTopThreePartnersIds() {
        Map<Integer, Integer> tradeFrequency = new HashMap<>();
        for (Trade trade : getAllTradesAccount()) {
            if (account.getAccountID() == trade.getTraderOneID()) {
                tradeFrequency.compute(trade.getTraderTwoID(), (k, v) -> v == null ? 1 : v + 1);
            }
            else {
                tradeFrequency.compute(trade.getTraderOneID(), (k, v) -> v == null ? 1 : v + 1);
            }
        }
        SortedMap<Integer, Integer> sortedFrequency = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Integer> entry : tradeFrequency.entrySet()) {
            sortedFrequency.put(entry.getValue(), entry.getKey());
        }
        List<Integer> tradeIds = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : sortedFrequency.entrySet()) {
            if (count >= 3) break;
            tradeIds.add(entry.getValue());
            count++;
        }
        return tradeIds;
    }

    /**
     * Retrieves the three most recent one-way trades the current account
     * has made
     * @return List of three most recent one-way trades the current account
     * has made, if less than three list size is adjusted
     */
    public List<Trade> getRecentOneWay() {
        List<TimePlace> AllOneWay = new ArrayList<>();
        List<Trade> AllOneWayTrades = new ArrayList<>();
        for (Trade trade : getAllTradesAccount()) {

            if (trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (trade.getTraderOneID() == account.getAccountID()) {
                if (!trade.getItemOneID().isEmpty() && trade.getItemTwoID().isEmpty()) {
                    TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
                    AllOneWay.add(timePlace);
                }
            } else if (trade.getTraderTwoID() == account.getAccountID()) {
                if (trade.getItemOneID().isEmpty() && !trade.getItemTwoID().isEmpty()) {
                    TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
                    AllOneWay.add(timePlace);
                }
            }
            // i changed this  -maryam

            /*
            if (!trade.getItemOneID().isEmpty() && trade.getItemTwoID().isEmpty() ||
                    trade.getItemOneID().isEmpty() && !trade.getItemTwoID().isEmpty()) {
                TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
                AllOneWay.add(timePlace);
            }
             */
        }
        Collections.sort(AllOneWay);
        int count = 0;
        for (TimePlace tp: AllOneWay) {
            if (count >= 3) break;
            Trade trade = tradeManager.getTradeGateway().findTradeById(tp.getId());
            AllOneWayTrades.add(trade);
            count++;
        }
        return AllOneWayTrades;
    }

    /**
     * Retrieves the three most recent two-way trades the current account has
     * made
     * @return List of three most recent two-way trades the current account
     * has made, if less than three list size is adjusted
     */
    public List<Trade> getRecentTwoWay() {
        List<TimePlace> AllTwoWay = new ArrayList<>();
        List<Trade> AllTwoWayTrades = new ArrayList<>();
        for (Trade trade : getAllTradesAccount()) {

            if (trade.getStatus() != TradeStatus.COMPLETED)
                continue;
            // i added this  -maryam

            if (!trade.getItemOneID().isEmpty() && !trade.getItemTwoID().isEmpty()) {
                TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
                AllTwoWay.add(timePlace);
            }
        }
        Collections.sort(AllTwoWay);
        int count = 0;
        for (TimePlace tp: AllTwoWay) {
            if (count >= 3) break;
            Trade trade = tradeManager.getTradeGateway().findTradeById(tp.getId());
            AllTwoWayTrades.add(trade);
            count++;
        }
        return AllTwoWayTrades;
    }

    /**
     * Retrieves the number of trades the current account has made in the past week
     * @return number of trades user has made in the past week
     */
    public Integer getNumWeeklyTrades() {
        Integer weeklyTrades = 0;
        LocalDateTime currDate = LocalDateTime.now();
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        for (Trade trade : getAllTradesAccount()) {
            TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
            if (timePlace.getTime().isBefore(currDate) && timePlace.getTime().isAfter(weekAgo)) {
                weeklyTrades++;
            }
        }
        return weeklyTrades;
    }

    /**
     * Retrieves the number of times the current user has failed to complete
     * a trade
     * @return number of times the current user has failed to complete a trade
     */
    public Integer getTimesIncomplete() {
        Integer timesIncomplete = 0;
        for (Trade trade : getAllTradesAccount()) {
            LocalDateTime tradeTime = tradeManager.getTradeGateway().findTimePlaceById(trade.getId()).getTime();
            if (tradeTime.isBefore(LocalDateTime.now()) && !trade.getStatus().equals(TradeStatus.COMPLETED)) {
                timesIncomplete++;
            }
        }
        // i changed this  -maryam
        return timesIncomplete;
    }

    /**
     * Retrieves the number of times the current user has borrowed items
     * @return number of times the current user has borrowed items
     */
    public Integer getTimesBorrowed() {
        Integer timesBorrowed = 0;
        for (Trade trade : getAllTradesAccount()) {
            if (account.getAccountID() == trade.getTraderTwoID()) {
                if (!trade.getItemOneID().isEmpty() && trade.getItemTwoID().isEmpty()) {
                    timesBorrowed++;
                }
            } else {
                if (trade.getItemOneID().isEmpty() && !trade.getItemTwoID().isEmpty()) {
                    timesBorrowed++;
                }
            }
        }
        return timesBorrowed;
    }

    /**
     * Retrieves the number of times the current user has lent items
     * @return number of times the current user has lent items
     */
    public Integer getTimesLent() {
        Integer timesLent = 0;
        for (Trade trade : getAllTradesAccount()) {
            if (account.getAccountID() == trade.getTraderTwoID()) {
                if (trade.getItemOneID().isEmpty() && !trade.getItemTwoID().isEmpty()) {
                    timesLent++;
                }
            } else {
                if (!trade.getItemOneID().isEmpty() && trade.getItemTwoID().isEmpty()) {
                    timesLent++;
                }
            }
        }
        return timesLent;
    }

}
