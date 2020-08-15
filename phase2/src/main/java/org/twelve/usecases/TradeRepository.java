package org.twelve.usecases;

import org.twelve.entities.TimePlace;
import org.twelve.entities.Trade;
import org.twelve.entities.TradeStatus;
import org.twelve.gateways.TradeGateway;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * A class to store all trades and timeplaces
 */
public class TradeRepository {
    private Map<Integer, Trade> trades;
    private Map<Integer, TimePlace> timePlaces;
    private TradeGateway tradeGateway;
    private final ItemManager itemManager;

    private final AccountRepository accountRepository;

    /**
     * sets up the trade repository
     *
     * @param tradeGateway an instance of TradeGateway
     * @param accountRepository an instance of AccounRepository
     * @param itemManager and instance of ItemManager
     */
    public TradeRepository(TradeGateway tradeGateway, AccountRepository accountRepository, ItemManager itemManager) {
        this.tradeGateway = tradeGateway;
        trades = new HashMap<>();
        timePlaces = new HashMap<>();
        this.accountRepository = accountRepository;
        this.itemManager = itemManager;
        tradeGateway.populate(this);
    }

    /**
     * Creates a new Trade object to be edited.
     *
     * @param time          Time of the Trade
     * @param place         Location of the Trade
     * @param isPermanent   Whether the Trade is permanent or not
     * @param tradersIds    An ordered list of each trader participating
     * @param itemsIds      The items involved in this trade.
     */
    public int createTrade(LocalDateTime time, String place, boolean isPermanent,
                           List<Integer> tradersIds, List< List<Integer> > itemsIds) {
        int id = (trades.isEmpty() ? 1 : Collections.max(trades.keySet()) + 1);
        TimePlace timePlace = new TimePlace(id, time, place);
        Trade trade = new Trade(id, isPermanent, tradersIds, itemsIds);
        trades.put(id, trade);
        timePlaces.put(id, timePlace);

        updateToGateway(trade, true);
        return id;
    }

    //TODO : remove this
    /**
     * Adds a new trade and its timePlace to local storage
     *
     * @param id id of trade and timePlace
     * @param isPermanent if the trade is permanent
     * @param traderIDs list of all ids of the traders
     * @param itemIDs list of all the ids of the items
     * @param editedCounter counter for how many edits were made
     * @param tradeStatus status of the trade
     * @param tradeCompletions The Completion status of the trades
     * @param time the time of the trade
     * @param location the location of the trade
     */
    public void addToTrades(int id, boolean isPermanent, List<Integer> traderIDs, List< List<Integer> > itemIDs,
                            int editedCounter, String tradeStatus, List<Boolean> tradeCompletions,
                            String time, String location) {
        Trade trade = new Trade(id, isPermanent, traderIDs, itemIDs, editedCounter,
                TradeStatus.valueOf(tradeStatus), tradeCompletions);
        TimePlace timePlace = new TimePlace(id, LocalDateTime.parse(time), location);
        trades.put(id, trade);
        timePlaces.put(id, timePlace);
    }

    // TODO difference between this and above?  --maryam
    /**
     * Adds a new trade and its timePlace that exist in the database
     *
     * @param id id of trade and timePlace
     * @param isPermanent if the trade is permanent
     * @param traderIDs list of all ids of the traders
     * @param itemIDs list of all the ids of the items
     * @param editedCounter counter for how many edits were made
     * @param tradeStatus status of the trade
     * @param tradeCompletions The Completion status of the trades
     * @param time the time of the trade
     * @param location the location of the trade
     */
    public void createTrade(int id, boolean isPermanent, List<Integer> traderIDs, List< List<Integer> > itemIDs,
                            int editedCounter, String tradeStatus, List<Boolean> tradeCompletions,
                            String time, String location) {
        Trade trade = new Trade(id, isPermanent, traderIDs, itemIDs, editedCounter,
                TradeStatus.valueOf(tradeStatus), tradeCompletions);
        TimePlace timePlace = new TimePlace(id, LocalDateTime.parse(time), location);
        trades.put(id, trade);
        timePlaces.put(id, timePlace);
    }

    /**
     * Updates the given trade and if it is a new trade to the tradeGateway
     *
     * @param trade the object representing a trade
     * @param newTrade if the trade is a new trade
     */
    public void updateToGateway(Trade trade, boolean newTrade) {
        TimePlace timePlace = getTimePlaceByID(trade.getId());
        tradeGateway.save(trade.getId(), trade.isPermanent(), trade.getTraderIds(), trade.getItemsIds(),
                trade.getEditedCounter(), trade.getStatus().toString(), trade.getTradeCompletions(),
                timePlace.getTime().toString(), timePlace.getPlace(), newTrade);
    }

    /**
     * Switch trade changes to reflect in demo mode.
     * @param tradeGateway An instance of TradeGateway
     */
    void switchToDemoMode(TradeGateway tradeGateway) {
        this.tradeGateway = tradeGateway;
        for (Trade trade : trades.values()) {
            updateToGateway(trade, true);
        }
    }

    /**
     * Switch trade changes to reflect in normal mode.
     * @param tradeGateway An instance of TradeGateway
     */
    void switchToNormalMode(TradeGateway tradeGateway) {
        this.tradeGateway = tradeGateway;
        trades.clear();
        timePlaces.clear();
        tradeGateway.populate(this);
    }

    /**
     * Return the timePlace object with the given id
     *
     * @param timePlaceID the id of the timePlace object
     * @return timePlace object with id given id
     */
    public TimePlace getTimePlaceByID(int timePlaceID) {
        return timePlaces.get(timePlaceID);
    }

    /**
     * Return the trade object with the given id
     *
     * @param tradeID the id of the trade object
     * @return trade object with given id
     */
    protected Trade getTradeByID(int tradeID) {
        return trades.get(tradeID);
    }

    /**
     * Retrieves all the trades the current account has.
     *
     * @return List of all of the trades the current account has done
     */
     List<Trade> getAllTradesAccount(int accountID) {
        List<Trade> accountTrades = new ArrayList<>();
        for (Trade trade : trades.values()) {
            if (trade.getTraderIds().contains(accountID) && trade.getStatus() != TradeStatus.ADMIN_CANCELLED)
                accountTrades.add(trade);
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
     * Retrieves all trades stored in persistent storage in string format.
     *
     * @return List of trades in string format
     */
    public List<String> getAllTradesString() {
        List<String> stringTrade = new ArrayList<>();
        for (Trade trade : trades.values()) {
            if (trade.getStatus() != TradeStatus.ADMIN_CANCELLED)
                stringTrade.add(tradeAsString(trade));
        }
        return stringTrade;
    }

    public List<Integer> getAllTradesIds(){
        List<Integer> tradeIds = new ArrayList<>();
        for(Trade trade: trades.values()){
            if (trade.getStatus() != TradeStatus.ADMIN_CANCELLED)
                tradeIds.add(trade.getId());
        }
        return tradeIds;
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
     * Returns the location of this Trade.
     *
     * @return location of this trade
     */
    public String getLocation(int tradeID) {
        return getTimePlaceByID(getTradeByID(tradeID).getTimePlaceID()).getPlace();
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
     * Returns the status of the trade.
     *
     * @param tradeID id of the trade
     * @return status of the trade
     */
    public TradeStatus getTradeStatus(int tradeID) {
        return getTradeByID(tradeID).getStatus();
    }

    /**
     * Returns the next trader in trade after account.
     *
     * @param tradeID id of the trade
     * @param accountID id of the account
     * @return the next trader after account
     */
    public int getNextTraderID(int tradeID, int accountID) {
        return getTradeByID(tradeID).getNextTraderID(accountID);
    }

    /**
     * finds items account traded in this trade
     *
     * @param accountID id of account
     * @param tradeID id of trade
     * @return list of id of items
     */
    public List<Integer> itemsTraderGives(int accountID, int tradeID) {
        Trade trade = getTradeByID(tradeID);
        int ind = trade.getTraderIds().indexOf(accountID);
        return trade.getItemsIds().get(ind);
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

        boolean space = false;
        for (int id : trade.getTraderIds()) {
            if (space)
                ans.append("\n");
            else
                space = true;
            ans.append("Items being traded by ").append(accountRepository.getUsernameFromID(id)).append(": ");
            boolean hasItem = false;
            for (int itemID : itemsTraderGives(id, trade.getId())) {
                ans.append(itemManager.getItemNameById(itemID));
                hasItem = true;
            }
            if (!hasItem)
                ans.append("-");
        }

        ans.append("\nStatus: ").append(trade.getStatus().toString().toLowerCase());
        ans.append("\nType: ");
        ans.append(trade.isPermanent() ? "permanent" : "temporary");
        ans.append("\nLocation: ").append(timePlace.getPlace());
        ans.append("\nTime: ").append(timePlace.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")));

        return ans.toString();
    }
}
