package usecases;

import entities.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for trades to access certain types of trades and information on trades
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
     * Retrieves all the trades the current account has
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
            if (trade.getStatus() != TradeStatus.CONFIRMED)
                continue;
            if (account.getAccountID() == trade.getTraderOneID()) {
                tradeFrequency.compute(trade.getTraderTwoID(), (k, v) -> v == null ? 1 : v + 1);
            }
            else {
                tradeFrequency.compute(trade.getTraderOneID(), (k, v) -> v == null ? 1 : v + 1);
            }
        }
        Map<Integer, Integer> sorted = tradeFrequency.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<Integer> tradeIds = new ArrayList<>();
        int counter = 0;
        for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {
            if (counter > sorted.size()-4) {
                tradeIds.add(entry.getKey());
            }
            counter++;
        }
        return tradeIds;
    }

    /**
     * Retrieves the three most recent one-way trades the current account
     * has made
     * @return List of three most recent one-way trades the current account
     * has made, if less than three list size is adjusted
     */
    public List<Integer> getRecentOneWay() {
        List<TimePlace> AllOneWay = new ArrayList<>();
        List<Integer> ThreeRecent = new ArrayList<>();
        List<Integer> allOneWayItems = new ArrayList<>();
        for (Trade trade : getAllTradesAccount()) {
            if (trade.getStatus() != TradeStatus.CONFIRMED)
                continue;
            if (trade.getTraderOneID() == account.getAccountID()) {
                if (!trade.getItemOneIDs().isEmpty() && trade.getItemTwoIDs().isEmpty()) {
                    TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
                    AllOneWay.add(timePlace);
                }
            } else if (trade.getTraderTwoID() == account.getAccountID()) {
                if (trade.getItemOneIDs().isEmpty() && !trade.getItemTwoIDs().isEmpty()) {
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
        for (TimePlace tp: AllOneWay) {
            Trade trade = tradeManager.getTradeGateway().findTradeById(tp.getId());
            allOneWayItems.addAll(trade.getItemOneIDs());
            allOneWayItems.addAll(trade.getItemTwoIDs());
        }
        int count = 0;
        for (Integer tradeId : allOneWayItems) {
            if (count >= 3) break;
            ThreeRecent.add(tradeId);
            count++;
        }
        return ThreeRecent;
        /*
        int count = 0;
        int i = 0;
        while (count < 3 && i < allOneWayItems.size()) {
            ThreeRecent.add(allOneWayItems.get(i));
            count++;
            i++;
        }

        return ThreeRecent;
         */
    }

    /**
     * Retrieves the three most recent two-way trades the current account has
     * made
     * @return List of three most recent two-way trades the current account
     * has made, if less than three list size is adjusted
     */
    public List<Integer> getRecentTwoWay() {
        List<TimePlace> AllTwoWay = new ArrayList<>();
        List<Integer> ThreeRecent = new ArrayList<>();
        List<Integer> allTwoWayItems = new ArrayList<>();
        for (Trade trade : getAllTradesAccount()) {
            if (trade.getStatus() != TradeStatus.CONFIRMED)
                continue;
            if (!trade.getItemOneIDs().isEmpty() && !trade.getItemTwoIDs().isEmpty()) {
                TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(trade.getId());
                AllTwoWay.add(timePlace);
            }
        }
        Collections.sort(AllTwoWay);
        for (TimePlace tp: AllTwoWay) {
            Trade trade = tradeManager.getTradeGateway().findTradeById(tp.getId());
            allTwoWayItems.addAll(trade.getItemOneIDs());
            allTwoWayItems.addAll(trade.getItemTwoIDs());
        }
        int count = 0;
        for (Integer tradeId : allTwoWayItems) {
            if (count >= 3) break;
            ThreeRecent.add(tradeId);
            count++;
        }
        return ThreeRecent;
        /*
        int count = 0;
        int i = 0;
        while (count < 3 && i < allTwoWayItems.size()) {
            ThreeRecent.add(allTwoWayItems.get(i));
            count++;
            i++;
        }

        return ThreeRecent;
         */
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
            if (timePlace.getTime().isEqual(currDate) || timePlace.getTime().isEqual(weekAgo)) {
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
            if (tradeTime.isBefore(LocalDateTime.now()) && trade.getStatus().equals(TradeStatus.CONFIRMED)) {
                timesIncomplete++;
            }
        }
        if (account.getUsername().equals("user25"))
           System.out.println(account.getUsername() + " " + timesIncomplete);
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
                if (!trade.getItemOneIDs().isEmpty() && trade.getItemTwoIDs().isEmpty()) {
                    timesBorrowed++;
                }
            } else {
                if (trade.getItemOneIDs().isEmpty() && !trade.getItemTwoIDs().isEmpty()) {
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
                if (trade.getItemOneIDs().isEmpty() && !trade.getItemTwoIDs().isEmpty()) {
                    timesLent++;
                }
            } else {
                if (!trade.getItemOneIDs().isEmpty() && trade.getItemTwoIDs().isEmpty()) {
                    timesLent++;
                }
            }
        }
        return timesLent;
    }

    /**
     * Completes the action of making a trade
     * @param trade the trade object representing the trade about to be made
     * @param accountManager an object for managing accounts
     * @param itemManager an object for managing items
     * @param itemUtility an object to access certain types of items
     */
    public void makeTrade (Trade trade, AccountManager accountManager, ItemManager itemManager,
                           ItemUtility itemUtility) {
        accountManager.setCurrAccount(accountManager.getAccountFromID(trade.getTraderTwoID()).getUsername());
        for (Integer itemId : trade.getItemOneIDs()) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
            if (itemUtility.getApprovedInventoryOfAccount(trade.getTraderOneID()).contains(itemManager.getItemById(itemId))) {
                itemManager.updateOwner(itemManager.getItemById(itemId), trade.getTraderTwoID());
            }
        }
        accountManager.setCurrAccount(accountManager.getAccountFromID(trade.getTraderOneID()).getUsername());
        for (Integer itemId : trade.getItemTwoIDs()) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
            if (itemUtility.getApprovedInventoryOfAccount(trade.getTraderTwoID()).contains(itemManager.getItemById(itemId))) {
                itemManager.updateOwner(itemManager.getItemById(itemId), trade.getTraderOneID());
            }
        }
    }

    /**
     * Completes the action of reversing a trade which was rejected
     * @param trade he trade object representing the trade about to be rejected
     * @param accountManager an object for managing accounts
     * @param itemManager an object for managing items
     */
    public void rejectedTrade(Trade trade, AccountManager accountManager, ItemManager itemManager) {
        accountManager.setCurrAccount(accountManager.getAccountFromID(trade.getTraderTwoID()).getUsername());
        for (Integer itemId : trade.getItemOneIDs()) {
            accountManager.addItemToWishlist(itemId);
            itemManager.updateOwner(itemManager.getItemById(itemId), trade.getTraderOneID());
        }
        accountManager.setCurrAccount(accountManager.getAccountFromID(trade.getTraderOneID()).getUsername());
        for (Integer itemId : trade.getItemTwoIDs()) {
            accountManager.addItemToWishlist(itemId);
            itemManager.updateOwner(itemManager.getItemById(itemId), trade.getTraderTwoID());
        }
    }
}
