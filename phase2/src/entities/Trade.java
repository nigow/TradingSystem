package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a one-way or two-way transaction between two users.
 *
 * @author Maryam
 */
public class Trade {

    private final int id;

    private boolean isPermanent;

    private final int[] tradersIds;

    private final int[] itemsIds;

    private TradeStatus tradeStatus;

    private int lastEditorID;

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
    public Trade(int id, boolean isPermanent, int[] tradersIds, int[] itemsIds) {
        this.id = id;
        this.isPermanent = isPermanent;
        this.tradersIds = tradersIds;
        this.itemsIds = itemsIds;
        tradeStatus = TradeStatus.UNCONFIRMED;
        editedCounter = 0;
        lastEditorID = tradersIds[0];
        tradeCompletions = new ArrayList<>();
        for(int i = 1; i <= tradersIds.length; i++) {
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
    public Trade(int id, boolean isPermanent, int[] tradersIds,
                 int[] itemsIds, int editedCounter, TradeStatus tradeStatus,
                 List<Boolean> tradeCompletions) {
        this.id = id;
        this.isPermanent = isPermanent;
        this.tradersIds = tradersIds;
        this.itemsIds = itemsIds;
        this.editedCounter = editedCounter;
        this.tradeCompletions = tradeCompletions;
        this.tradeStatus = tradeStatus;
        lastEditorID = tradersIds[0];
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
    public int[] getItemsIds() {
        return itemsIds;
    }

    /**
     * Returns the status of this trade.
     *
     * @return Status of this trade
     */
    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    /**
     * Returns the ID of the person who last suggested a meetup.
     *
     * @return ID of the person who last suggested a meetup
     */
    public int getLastEditorID() {
        return lastEditorID;
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
    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    /**
     * Changes the ID of the last person who suggested a meetup.
     *
     * @param lastEditorID ID of the last person who suggested a meetup
     */
    public void setLastEditorID(int lastEditorID) {
        this.lastEditorID = lastEditorID;
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
    public int[] getTraderIds() {
        return tradersIds;
    }


    /**
     * @return An array for whether each user reported trade completion.
     */
    public List<Boolean> getTradeCompletions() {
        return tradeCompletions;
    }

    /**
     * Creates a string representation of this trade.
     *
     * @return String representation of a OldTrade object
     */
    @Override
    public String toString() {
        return "OldTrade{" +
                "id=" + id +
                ", timePlaceID=" + id +
                ", isPermanent=" + isPermanent +
                ", status=" + tradeStatus +
                ", lastEditorID=" + lastEditorID +
                ", editedCounter=" + editedCounter +
                '}';
    }


}
