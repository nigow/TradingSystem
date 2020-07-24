package usecases;

import entities.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// TODO javadoc is fucked

/**
 * Utility class for accessing certain types of trades and information on trades.
 *
 * @author Isaac
 */
public class TradeUtility {

    private final int NUMBER_OF_NEEDED_STATS = 3;

    /**
     * List of all trades in the system
     */
    protected List<OldTrade> trades;

    /**
     * List of all meetings times and places in the system
     */
    protected List<TimePlace> timePlaces;

    public TimePlace getTimePlaceByID(int timePlaceID) {
        for (TimePlace timePlace : timePlaces) {
            if (timePlaceID == timePlace.getId())
                return timePlace;
        }
        return null;
    }

    public OldTrade getTradeByID(int tradeID) {
        for (OldTrade oldTrade : trades) {
            if (tradeID == oldTrade.getId())
                return oldTrade;
        }
        return null;
    }

    /**
     * Retrieves all the trades the current account has.
     *
     * @return List of all of the trades the current account has done
     */
    public List<OldTrade> getAllTradesAccount(int accountID) {
        List<OldTrade> accountOldTrades = new ArrayList<>();
        for (OldTrade oldTrade : trades) {
            if (oldTrade.getTraderOneID() == accountID) {
                accountOldTrades.add(oldTrade);
            } else if (oldTrade.getTraderTwoID() == accountID) {
                accountOldTrades.add(oldTrade);
            }
        }
        return accountOldTrades;
    }

    /**
     * Returns a user-friendly string representation of a oldTrade.
     *
     * @param oldTrade The Trade whose representation is being returned
     * @param accountManager Manager for manipulating accounts
     * @param itemUtility    Manager for manipulating items
     * @return An user-friendly representation of a oldTrade
     */
    public String tradeAsString(OldTrade oldTrade, AccountManager accountManager, ItemUtility itemUtility) {
        TimePlace timePlace = getTimePlaceByID(oldTrade.getTimePlaceID());
        StringBuilder ans = new StringBuilder();
        String username1 = accountManager.getAccountFromID(
                oldTrade.getTraderOneID()).getUsername();
        String username2 = accountManager.getAccountFromID(
                oldTrade.getTraderTwoID()).getUsername();

        if (oldTrade.getItemOneIDs().size() > 0 && oldTrade.getItemTwoIDs().size() > 0) {
            ans.append("Type: Two-way ");
            ans.append("\nAccount 1: ").append(username1).append("\nAccount 2: ").append(username2);
        } else {
            ans.append("Type: One-way ");
            if (oldTrade.getItemOneIDs().size() > 0) {
                ans.append("\nBorrower: ").append(username2).append("\nLender: ").append(username1);
            } else {
                ans.append("\nBorrower: ").append(username1).append("\nLender: ").append(username2);
            }

        }
        ans.append("\nStatus: ").append(oldTrade.getStatus().toString()).append(" ");
        ans.append("\nType: ");
        ans.append(oldTrade.isPermanent() ? "Permanent " : "Temporary ");
        ans.append("\nLocation: ").append(timePlace.getPlace()).append(" ");
        ans.append("\nTime: ").append(timePlace.getTime()).append(" ");

        if (oldTrade.getItemOneIDs().size() > 0 && oldTrade.getItemTwoIDs().size() > 0) {
            ans.append("\nTrader 1 Items: ");
            String separator = "";
            for (Integer tradeId : oldTrade.getItemOneIDs()) {
                ans.append(separator).append(itemUtility.getItemById(tradeId).toString());
                separator = ", ";
            }
            separator = "";
            ans.append("\nTrader 2 Items: ");
            for (Integer tradeId : oldTrade.getItemTwoIDs()) {
                ans.append(separator).append(itemUtility.getItemById(tradeId).toString());
                separator = ", ";
            }
        } else {
            ans.append("\nItem being borrowed/lent: ");
            String separator = "";
            for (Integer tradeId : oldTrade.getItemOneIDs()) {
                ans.append(separator).append(itemUtility.getItemById(tradeId).toString());
                separator = ", ";
            }
            for (Integer tradeId : oldTrade.getItemTwoIDs()) {
                ans.append(separator).append(itemUtility.getItemById(tradeId).toString());
                separator = ", ";
            }
        }

        return ans.toString();
    }

    /**
     * Retrieves all the trades the current account has done in string format.
     *
     * @return List of the trades the current account has done in string format
     */
    public List<String> getAllTradesAccountString(AccountManager accountManager, ItemManager itemManager, int accountID) {
        List<String> accountTrades = new ArrayList<>();
        for (OldTrade oldTrade : getAllTradesAccount(accountID)) {
            accountTrades.add(tradeAsString(oldTrade, accountManager, itemManager));
        }
        return accountTrades;
    }

    /**
     * Retrieves the Ids of the top three trade partners of the current account.
     *
     * @return List of top three trade partners, if less than three list size is
     * adjusted
     */
    public List<Integer> getTopThreePartnersIds(AccountManager accountManager, int accountID) {
        Account account = accountManager.getAccountFromID(accountID);
        Map<Integer, Integer> tradeFrequency = new HashMap<>();
        for (OldTrade oldTrade : getAllTradesAccount(accountID)) {
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
    public List<Integer> getRecentOneWay(AccountManager accountManager, int accountID) {
        Account account = accountManager.getAccountFromID(accountID);
        List<TimePlace> allOneWay = new ArrayList<>();
        List<Integer> threeRecent = new ArrayList<>();
        List<Integer> allOneWayItems = new ArrayList<>();
        for (OldTrade oldTrade : getAllTradesAccount(accountID)) {
            if (oldTrade.getStatus() != TradeStatus.CONFIRMED && oldTrade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (oldTrade.getTraderOneID() == account.getAccountID()) {
                if (!oldTrade.getItemOneIDs().isEmpty() && oldTrade.getItemTwoIDs().isEmpty()) {
                    TimePlace timePlace = getTimePlaceByID(oldTrade.getId());
                    allOneWay.add(timePlace);
                }
            } else if (oldTrade.getTraderTwoID() == account.getAccountID()) {
                if (oldTrade.getItemOneIDs().isEmpty() && !oldTrade.getItemTwoIDs().isEmpty()) {
                    TimePlace timePlace = getTimePlaceByID(oldTrade.getId());
                    allOneWay.add(timePlace);
                }
            }
        }
        Collections.sort(allOneWay);
        for (TimePlace tp : allOneWay) {
            OldTrade oldTrade = getTradeByID(tp.getId());
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
    public List<Integer> getRecentTwoWay(int accountID) {
        List<TimePlace> allTwoWay = new ArrayList<>();
        List<Integer> threeRecent = new ArrayList<>();
        List<Integer> allTwoWayItems = new ArrayList<>();
        for (OldTrade oldTrade : getAllTradesAccount(accountID)) {
            if (oldTrade.getStatus() != TradeStatus.CONFIRMED && oldTrade.getStatus() != TradeStatus.COMPLETED)
                continue;
            if (!oldTrade.getItemOneIDs().isEmpty() && !oldTrade.getItemTwoIDs().isEmpty()) {
                TimePlace timePlace = getTimePlaceByID(oldTrade.getId());
                allTwoWay.add(timePlace);
            }
        }
        Collections.sort(allTwoWay);
        for (TimePlace tp : allTwoWay) {
            OldTrade oldTrade = getTradeByID(tp.getId());
            if (accountID == oldTrade.getTraderOneID()) {
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
    public Integer getNumWeeklyTrades(int accountID) {
        Integer weeklyTrades = 0;
        LocalDateTime currDate = LocalDateTime.now();
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        for (OldTrade oldTrade : getAllTradesAccount(accountID)) {
            TimePlace timePlace = getTimePlaceByID(oldTrade.getId());
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
    public Integer getTimesIncomplete(int accountID) {
        Integer timesIncomplete = 0;
        for (OldTrade oldTrade : getAllTradesAccount(accountID)) {
            LocalDateTime tradeTime = getTimePlaceByID(oldTrade.getId()).getTime();
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
    public Integer getTimesBorrowed(AccountManager accountManager, int accountID) {
        Account account = accountManager.getAccountFromID(accountID);
        Integer timesBorrowed = 0;
        for (OldTrade oldTrade : getAllTradesAccount(accountID)) {
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
    public Integer getTimesLent(AccountManager accountManager, int accountID) {
        Account account = accountManager.getAccountFromID(accountID);
        Integer timesLent = 0;
        for (OldTrade oldTrade : getAllTradesAccount(accountID)) {
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
     * Returns the date and time of this oldTrade.
     *
     * @return Date and time of this oldTrade
     */
    public LocalDateTime getDateTime(int tradeID) {
        return getTimePlaceByID(getTradeByID(tradeID).getTimePlaceID()).getTime();
    }

    /**
     * Gets the number of times this oldTrade has been edited.
     *
     * @return The number of times this oldTrade has been edited.
     */
    public int getEditedCounter(int tradeID) {
        return getTradeByID(tradeID).getEditedCounter();
    }

    /**
     * Returns whether this oldTrade is temporary or permanent.
     *
     * @return Whether this oldTrade is temporary or permanent
     */
    public boolean isPermanent(int tradeID) {
        return getTradeByID(tradeID).isPermanent();
    }

    /**
     * Returns if oldTrade is rejected.
     *
     * @return Whether the oldTrade is rejected
     */
    public boolean isRejected(int tradeID) {
        return getTradeByID(tradeID).getStatus().equals(TradeStatus.REJECTED);
    }

    /**
     * Returns if oldTrade is confirmed.
     *
     * @return Whether oldTrade is confirmed
     */
    public boolean isConfirmed(int tradeID) {
        return getTradeByID(tradeID).getStatus().equals(TradeStatus.CONFIRMED);
    }

    /**
     * Returns if oldTrade is unconfirmed.
     *
     * @return Whether oldTrade is unconfirmed
     */
    public boolean isUnconfirmed(int tradeID) {
        return getTradeByID(tradeID).getStatus().equals(TradeStatus.UNCONFIRMED);
    }

    /**
     * Returns if oldTrade is completed.
     *
     * @return Whether oldTrade is completed.
     */
    public boolean isCompleted(int tradeID) {
        return getTradeByID(tradeID).getStatus().equals(TradeStatus.COMPLETED);
    }

    /**
     * Gets the status of the oldTrade.
     *
     * @return Current status of the oldTrade
     */
    public TradeStatus getTradeStatus(int tradeID) {
        return getTradeByID(tradeID).getStatus();
    }

    /**
     * Retrieves all trades stored in persistent storage in string format.
     *
     * @return List of trades in string format
     */
    public List<String> getAllTradesString(AccountManager accountManager, ItemUtility itemUtility) {
        List<String> StringTrade = new ArrayList<>();
        for (OldTrade oldTrade : trades) {
            StringTrade.add(tradeAsString(oldTrade, accountManager, itemUtility));
        }
        return StringTrade;
    }
}
