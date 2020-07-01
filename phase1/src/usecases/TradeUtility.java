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

    private final AccountManager accountManager;

    /**
     * Manager responsible for creating and editing trades
     */
    private final TradeManager tradeManager;

    /**
     * The account which info on its trades are being retrieved
     */
    private final Account account;

    public TradeUtility(AccountManager accountManager, TradeManager tradeManager) {
        this.accountManager = accountManager;
        this.tradeManager = tradeManager;
        this.account = accountManager.getCurrAccount();
    }

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

    public List<Trade> getRecentOneWay() {
        List<TimePlace> AllOneWay = new ArrayList<>();
        List<Trade> AllOneWayTrades = new ArrayList<>();
        for (Trade trade : getAllTradesAccount()) {
            if (!trade.getItemOneID().isEmpty() && trade.getItemTwoID().isEmpty() ||
                    trade.getItemOneID().isEmpty() && !trade.getItemTwoID().isEmpty()) {
                TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
                AllOneWay.add(timePlace);
            }
        }
        Collections.sort(AllOneWay);
        for (int i = 0; i < 3; i++) {
            Trade trade = tradeManager.getTradeGateway().findTradeById(AllOneWay.get(i).getId());
            AllOneWayTrades.add(trade);
        }
        return AllOneWayTrades;
    }

    public List<Trade> getRecentTwoWay() {
        List<TimePlace> AllTwoWay = new ArrayList<>();
        List<Trade> AllTwoWayTrades = new ArrayList<>();
        for (Trade trade : getAllTradesAccount()) {
            if (!trade.getItemOneID().isEmpty() && !trade.getItemTwoID().isEmpty()) {
                TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
                AllTwoWay.add(timePlace);
            }
        }
        Collections.sort(AllTwoWay);
        for (int i = 0; i < 3; i++) {
            Trade trade = tradeManager.getTradeGateway().findTradeById(AllTwoWay.get(i).getId());
            AllTwoWayTrades.add(trade);
        }
        return AllTwoWayTrades;
    }

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

    public Integer getTimesIncomplete() {
        Integer timesIncomplete = 0;
        TradeStatus status = TradeStatus.UNCONFIRMED;
        for (Trade trade : getAllTradesAccount()) {
            if (trade.getStatus().equals(status)) {
                timesIncomplete++;
            }
        }
        return timesIncomplete;
    }

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
