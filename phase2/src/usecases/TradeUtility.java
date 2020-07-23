package usecases;

import entities.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for accessing certain types of trades and information on trades.
 *
 * @author Isaac
 */
public class TradeUtility {

    private final int NUMBER_OF_NEEDED_STATS = 3;

    /**
     * Manager responsible for creating and editing trades.
     */
    private final TradeManager tradeManager;

    /**
     * The account which info on its trades are being retrieved.
     */
    private Account account;

    /**
     * Constructor for TradeUtility which stores an account and TradeManager.
     *
     * @param tradeManager Manager for creating and editing trades
     */
    public TradeUtility(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    /**
     * Sets the current account to be edited.
     *
     * @param account Current account to be edited
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Retrieves all the trades the current account has.
     *
     * @return List of all of the trades the current account has done
     */
    public List<OldTrade> getAllTradesAccount() {
        List<OldTrade> accountOldTrades = new ArrayList<>();
        for (OldTrade oldTrade : tradeManager.getAllTrades()) {
            if (oldTrade.getTraderOneID() == account.getAccountID()) {
                accountOldTrades.add(oldTrade);
            } else if (oldTrade.getTraderTwoID() == account.getAccountID()) {
                accountOldTrades.add(oldTrade);
            }
        }
        return accountOldTrades;
    }

    /**
     * Retrieves all the trades the current account has done in string format.
     *
     * @return List of the trades the current account has done in string format
     */
    public List<String> getAllTradesAccountString() {
        List<String> accountTrades = new ArrayList<>();
        for (OldTrade oldTrade : tradeManager.getAllTrades()) {
            if (oldTrade.getTraderOneID() == account.getAccountID()) {
                accountTrades.add(oldTrade.toString());
            } else if (oldTrade.getTraderTwoID() == account.getAccountID()) {
                accountTrades.add(oldTrade.toString());
            }
        }
        return accountTrades;
    }

    /**
     * Retrieves the Ids of the top three trade partners of the current account.
     *
     * @return List of top three trade partners, if less than three list size is
     * adjusted
     */
    public List<Integer> getTopThreePartnersIds() {
        Map<Integer, Integer> tradeFrequency = new HashMap<>();
        for (OldTrade oldTrade : getAllTradesAccount()) {
            if (oldTrade.getStatus() != TradeStatus.CONFIRMED && oldTrade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (account.getAccountID() == oldTrade.getTraderOneID()) {
                tradeFrequency.compute(oldTrade.getTraderTwoID(), (k, v) -> v == null ? 1 : v + 1);
            } else {
                tradeFrequency.compute(oldTrade.getTraderOneID(), (k, v) -> v == null ? 1 : v + 1);
            }
        }
        Map<Integer, Integer> sorted = tradeFrequency.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<Integer> tradeIds = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {
            if (count >= NUMBER_OF_NEEDED_STATS) break;
            tradeIds.add(entry.getKey());
            count++;
        }
        return tradeIds;
    }

    /**
     * Retrieves the three most recent one-way trades the current account
     * has made.
     *
     * @return List of three most recent one-way trades the current account
     * has made, if less than three list size is adjusted
     */
    public List<Integer> getRecentOneWay() {
        List<TimePlace> allOneWay = new ArrayList<>();
        List<Integer> threeRecent = new ArrayList<>();
        List<Integer> allOneWayItems = new ArrayList<>();
        for (OldTrade oldTrade : getAllTradesAccount()) {
            if (oldTrade.getStatus() != TradeStatus.CONFIRMED && oldTrade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (oldTrade.getTraderOneID() == account.getAccountID()) {
                if (!oldTrade.getItemOneIDs().isEmpty() && oldTrade.getItemTwoIDs().isEmpty()) {
                    TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(oldTrade.getId());
                    allOneWay.add(timePlace);
                }
            } else if (oldTrade.getTraderTwoID() == account.getAccountID()) {
                if (oldTrade.getItemOneIDs().isEmpty() && !oldTrade.getItemTwoIDs().isEmpty()) {
                    TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(oldTrade.getId());
                    allOneWay.add(timePlace);
                }
            }
        }
        Collections.sort(allOneWay);
        for (TimePlace tp : allOneWay) {
            OldTrade oldTrade = tradeManager.getTradeGateway().findTradeById(tp.getId());
            allOneWayItems.addAll(oldTrade.getItemOneIDs());
            allOneWayItems.addAll(oldTrade.getItemTwoIDs());
        }
        int count = 0;
        for (Integer tradeId : allOneWayItems) {
            if (count >= NUMBER_OF_NEEDED_STATS) break;
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
    public List<Integer> getRecentTwoWay() {
        List<TimePlace> allTwoWay = new ArrayList<>();
        List<Integer> threeRecent = new ArrayList<>();
        List<Integer> allTwoWayItems = new ArrayList<>();
        for (OldTrade oldTrade : getAllTradesAccount()) {
            if (oldTrade.getStatus() != TradeStatus.CONFIRMED && oldTrade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!oldTrade.getItemOneIDs().isEmpty() && !oldTrade.getItemTwoIDs().isEmpty()) {
                TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(oldTrade.getId());
                allTwoWay.add(timePlace);
            }
        }
        Collections.sort(allTwoWay);
        for (TimePlace tp : allTwoWay) {
            OldTrade oldTrade = tradeManager.getTradeGateway().findTradeById(tp.getId());
            if (account.getAccountID() == oldTrade.getTraderOneID()) {
                allTwoWayItems.addAll(oldTrade.getItemOneIDs());
            } else {
                allTwoWayItems.addAll(oldTrade.getItemTwoIDs());
            }
        }
        int count = 0;
        for (Integer tradeId : allTwoWayItems) {
            if (count >= NUMBER_OF_NEEDED_STATS) break;
            threeRecent.add(tradeId);
            count++;
        }
        return threeRecent;
    }

    /**
     * Retrieves the number of trades the current account has made in the past week.
     *
     * @return Number of trades user has made in the past week
     */
    public Integer getNumWeeklyTrades() {
        Integer weeklyTrades = 0;
        LocalDateTime currDate = LocalDateTime.now();
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        for (OldTrade oldTrade : getAllTradesAccount()) {
            TimePlace timePlace = tradeManager.getTradeGateway().findTimePlaceById(oldTrade.getId());
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
    public Integer getTimesIncomplete() {
        Integer timesIncomplete = 0;
        for (OldTrade oldTrade : getAllTradesAccount()) {
            LocalDateTime tradeTime = tradeManager.getTradeGateway().findTimePlaceById(oldTrade.getId()).getTime();
            if (tradeTime.isBefore(LocalDateTime.now()) && oldTrade.getStatus().equals(TradeStatus.CONFIRMED)) {
                timesIncomplete++;
            }
        }
        return timesIncomplete;
    }

    /**
     * Retrieves the number of times the current user has borrowed items.
     *
     * @return Number of times the current user has borrowed items
     */
    public Integer getTimesBorrowed() {
        Integer timesBorrowed = 0;
        for (OldTrade oldTrade : getAllTradesAccount()) {
            if (account.getAccountID() == oldTrade.getTraderTwoID()) {
                if (!oldTrade.getItemOneIDs().isEmpty() && oldTrade.getItemTwoIDs().isEmpty()) {
                    timesBorrowed++;
                }
            } else {
                if (oldTrade.getItemOneIDs().isEmpty() && !oldTrade.getItemTwoIDs().isEmpty()) {
                    timesBorrowed++;
                }
            }
        }
        return timesBorrowed;
    }

    /**
     * Retrieves the number of times the current user has lent items.
     *
     * @return Number of times the current user has lent items
     */
    public Integer getTimesLent() {
        Integer timesLent = 0;
        for (OldTrade oldTrade : getAllTradesAccount()) {
            if (account.getAccountID() == oldTrade.getTraderTwoID()) {
                if (oldTrade.getItemOneIDs().isEmpty() && !oldTrade.getItemTwoIDs().isEmpty()) {
                    timesLent++;
                }
            } else {
                if (!oldTrade.getItemOneIDs().isEmpty() && oldTrade.getItemTwoIDs().isEmpty()) {
                    timesLent++;
                }
            }
        }
        return timesLent;
    }

    /**
     * Completes the action of making a oldTrade.
     *
     * @param oldTrade          OldTrade object representing the oldTrade about to be made
     * @param accountManager Manager for accounts
     * @param itemManager    Manager for items
     * @param itemUtility    Utility for accessing certain types of items
     */
    public void makeTrade(OldTrade oldTrade, AccountManager accountManager, ItemManager itemManager,
                          ItemUtility itemUtility) {
        Account account = accountManager.getCurrAccount();
        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderTwoID()).getUsername());
        for (Integer itemId : oldTrade.getItemOneIDs()) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
            if (itemUtility.getApprovedInventoryOfAccount(oldTrade.getTraderOneID()).contains(itemManager.getItemById(itemId))) {
                itemManager.updateOwner(itemManager.getItemById(itemId), oldTrade.getTraderTwoID());
            }
        }
        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderOneID()).getUsername());
        for (Integer itemId : oldTrade.getItemTwoIDs()) {
            if (accountManager.getCurrWishlist().contains(itemId)) {
                accountManager.removeItemFromWishlist(itemId);
            }
            if (itemUtility.getApprovedInventoryOfAccount(oldTrade.getTraderTwoID()).contains(itemManager.getItemById(itemId))) {
                itemManager.updateOwner(itemManager.getItemById(itemId), oldTrade.getTraderOneID());
            }
        }
        accountManager.setCurrAccount(account.getUsername());
    }

    /**
     * Completes the action of reversing a oldTrade which was rejected.
     *
     * @param oldTrade          OldTrade object representing the oldTrade about to be rejected
     * @param accountManager Object for managing accounts
     * @param itemManager    Object for managing items
     */
    public void rejectedTrade(OldTrade oldTrade, AccountManager accountManager, ItemManager itemManager) {
        Account account = accountManager.getCurrAccount();
        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderTwoID()).getUsername());
        for (Integer itemId : oldTrade.getItemOneIDs()) {
            accountManager.addItemToWishlist(itemId);
            itemManager.updateOwner(itemManager.getItemById(itemId), oldTrade.getTraderOneID());
        }
        accountManager.setCurrAccount(accountManager.getAccountFromID(oldTrade.getTraderOneID()).getUsername());
        for (Integer itemId : oldTrade.getItemTwoIDs()) {
            accountManager.addItemToWishlist(itemId);
            itemManager.updateOwner(itemManager.getItemById(itemId), oldTrade.getTraderTwoID());
        }
        accountManager.setCurrAccount(account.getUsername());
    }
}
