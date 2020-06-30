package usecases;

import entities.Account;
import entities.TimePlace;
import entities.Trade;
import entities.TradeStatus;

import java.time.LocalDateTime;
import java.util.*;

public class TradeUtility {
    private final AccountManager accountManager;
    private final TradeManager tradeManager;
    private final Account account;

    public TradeUtility(AccountManager accountManager, TradeManager tradeManager) {
        this.accountManager = accountManager;
        this.tradeManager = tradeManager;
        this.account = accountManager.getCurrAccount();
    }

    public List<Trade> getAllTradesAccount() {
        List<Trade> accountTrades = new ArrayList<>();
        for (Trade trade : tradeManager.getAllItems()) {
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
        for (Trade trade : tradeManager.getAllItems()) {
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
    public List<Integer> getTopThreePartners() {
        Hashtable tradeFrequency = new Hashtable();
        for (Trade trade : getAllTradesAccount()) {
            if (account.getAccountID() == trade.getTraderOneID()) {
                if (tradeFrequency.containsKey(trade.getTraderTwoID())){

                }
                else  {
                    tradeFrequency.put(trade.getTraderTwoID(), 1);
                }
            }
            else {

            }
        }
    }

    public List<Integer> getRecentOneWay() {
        List<Trade> AllOneWay = new ArrayList<>();
        for (Trade trade : getAllTradesAccount()) {
            if (!trade.getItemOneID().isEmpty() && !trade.getItemTwoID().isEmpty()) {
                AllOneWay.add(trade);
            }
        }
        Collections.sort(AllOneWay);
    }

    public List<Trade> getRecentTwoWay() {
        List<Trade> AllTwoWay = new ArrayList<>();
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
        LocalDateTime first = LocalDateTime.MIN, second = LocalDateTime.MIN, third = LocalDateTime.MIN;
        if (getAllTradesAccount().size() <= 3) {
            return getAllTradesAccount();
        }
        for (Trade trade : getAllTradesAccount()) {
            TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
            if (timePlace.getTime().isAfter(first)) {
                third = second;
                second = first;
                first = timePlace.getTime();
            }
            else if (timePlace.getTime().isAfter(second)) {
                third = second;
                second = timePlace.getTime();
            }
            else if (timePlace.getTime().isAfter(third)) {
                third = timePlace.getTime();
            }
        }
    }
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
