package entities;

import java.util.ArrayList;
import java.util.List;

// TODO jacadoc should be fixed

/**
 * Represents a one-way or two-way transaction between two users.
 *
 * @author Maryam
 */
public class Trade {

    private final int id;

    private boolean isPermanent;

    private final List<Integer> tradersIds;

    private final List<Integer> itemsIds;

    private TradeStatus tradeStatus;

    private int editedCounter;

    private final List<Boolean> tradeCompletions;

    /**
     * Creates a new Trade object. The status is set to unconfirmed.
     * The trade is uncompleted.
     *
     * @param id            ID of this trade
     * @param isPermanent   Whether this is a permanent or temporary trade
     * @param tradersIds    A collection of integer storing the ids of all traders.
     * @param itemsIds      A collection of ids for the items in this trade.
     */
    public Trade(int id, boolean isPermanent, List<Integer> tradersIds, List<Integer> itemsIds) {
        this.id = id;
        this.isPermanent = isPermanent;
        this.tradersIds = tradersIds;
        this.itemsIds = itemsIds;
        tradeStatus = TradeStatus.UNCONFIRMED;
        editedCounter = 0;
        tradeCompletions = new ArrayList<>();
        for(int i = 0; i < tradersIds.size(); i++) {
            tradeCompletions.add(false);
        }
    }

    /**
     * Initializes a Trade object which already exists in the system.
     *
     * @param id            ID of this trade
     * @param isPermanent   Whether this is a permanent or temporary trade
     * @param tradersIds    A collection of integer storing the ids of all traders.
     * @param itemsIds      A collection of ids for the items in this trade.
     * @param editedCounter Number of times this trade's TimePlace has been edited
     * @param tradeStatus   The status of the trade.
     * @param tradeCompletions The completions of this trade.
     */
    public Trade(int id, boolean isPermanent, List<Integer> tradersIds,
                 List<Integer> itemsIds, int editedCounter, TradeStatus tradeStatus,
                 List<Boolean> tradeCompletions) {
        this.id = id;
        this.isPermanent = isPermanent;
        this.tradersIds = tradersIds;
        this.itemsIds = itemsIds;
        this.editedCounter = editedCounter;
        this.tradeCompletions = tradeCompletions;
        this.tradeStatus = tradeStatus;
    }


    /**
     * Returns the ID of this trade.
     *
     * @return The id of this trade
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the ID of a TimePlace object denoting the meetup for this trade.
     *
     * @return ID of a TimePlace object denoting the meetup for this trade
     */
    public int getTimePlaceID() {
        return id;
    }

    /**
     * Returns whether this is a permanent or temporary trade.
     *
     * @return Whether this is a permanent or temporary trade
     */
    public boolean isPermanent() {
        return isPermanent;
    }


    /**
     * Get all the item ids.
     *
     * @return A collection of item ids for this trade.
     */
    public List<Integer> getItemsIds() {
        return itemsIds;
    }

    /**
     * Returns the status of this trade.
     *
     * @return Status of this trade
     */
    public TradeStatus getStatus() {
        return tradeStatus;
    }

    /**
     * Returns the number of times a meetup has been suggested.
     *
     * @return Number of times a meetup has been suggested
     */
    public int getEditedCounter() {
        return editedCounter;
    }

    /**
     * Sets whether this trade is temporary or permanent.
     *
     * @param permanent Whether this is a permanent or temporary trade
     */
    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    /**
     * Changes the status of this trade.
     *
     * @param tradeStatus New status of this trade
     */
    public void setStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    /**
     * Increments the number of times a meetup has been suggested.
     */
    public void incrementEditedCounter() {
        editedCounter++;
    }


    /**
     * @return The ids of all traders.
     */
    public List<Integer> getTraderIds() {
        return tradersIds;
    }

    /**
     * @return An array for whether each user reported trade completion.
     */
    public List<Boolean> getTradeCompletions() {
        return tradeCompletions;
    }

    public int getNextTraderID(int accountID) {
        int index = (tradersIds.indexOf(accountID) + 1) % tradersIds.size();
        return tradersIds.get(index);
    }

    public void setCompletedOfTrader(int accountID, boolean isCompleted) {
        int index = tradersIds.indexOf(accountID);
        tradeCompletions.set(index, isCompleted);
        if (!tradeCompletions.contains(false))
            setStatus(TradeStatus.COMPLETED);
    }

    public boolean isEditTurn(int accountID) {
        return editedCounter % tradersIds.size() == tradersIds.indexOf(accountID);
    }

    public boolean isTwoPersonTrade() {
        return tradersIds.size() == 2;
    }
}
