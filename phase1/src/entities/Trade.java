package entities;

import java.util.List;

/**
 * Represents a one-way or two-way transaction between two users.
 * @author Maryam
 */
public class Trade {
    /**
     * The ID of this trade.
     */
    private final int id;

    /**
     * The ID of a TimePlace object denoting the meetup for this trade.
     */
    private int timePlaceID;

    /**
     * Stores whether this is a permanent or temporary trade.
     */
    private boolean isPermanent;

    /**
     * The ID of the Account of the first person in this trade.
     */
    private final int traderOneID;

    /**
     * The ID of the Account of the second person in this trade.
     */
    private final int traderTwoID;

    /**
     * List of IDs of items the first person is trading away.
     * Can be an empty list.
     */
    private final List<Integer> itemOneIDs;

    /**
     * List of IDs of items the second person is trading away.
     * Can be an empty list.
     */
    private final List<Integer> itemTwoIDs;

    /**
     * The status of this trade. (i.e. if it's rejected, unconfirmed, confirmed, or completed)
     */
    private TradeStatus status;

    /**
     * The ID of the last person who suggested a meetup.
     */
    private int lastEditorID;

    /**
     * Keeps track of number of times a new meetup has been suggested.
     */
    private int editedCounter;

    // TODO javadoc
    private boolean traderOneCompleted;
    private boolean traderTwoCompleted;

    /**
     * Creates a Trade object based on input. The status is set to unconfirmed.
     * @param id The ID of this trade.
     * @param timePlaceID The ID of a TimePlace object denoting the meetup for this trade.
     * @param isPermanent Whether this is a permanent or temporary trade.
     * @param traderOneID The ID of the Account of the first person in this trade.
     * @param traderTwoID The ID of the Account of the second person in this trade.
     * @param itemOneIDs List of IDs of items the first person is trading away.
     * @param itemTwoIDs List of IDs of items the second person is trading away.
     */
    public Trade(int id, int timePlaceID, boolean isPermanent, int traderOneID, int traderTwoID,
                 List<Integer> itemOneIDs, List<Integer> itemTwoIDs, int editedCounter) {
        this.id = id;
        this.timePlaceID = timePlaceID;
        this.isPermanent = isPermanent;
        this.traderOneID = traderOneID;
        this.traderTwoID = traderTwoID;
        this.itemOneIDs = itemOneIDs;
        this.itemTwoIDs = itemTwoIDs;
        status = TradeStatus.UNCONFIRMED;
        this.editedCounter = editedCounter;
        lastEditorID = traderOneID;
        traderOneCompleted = false;
        traderTwoCompleted = false;
    }

    /**
     * Returns the ID of this trade.
     * @return The ID of this trade.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the ID of a TimePlace object denoting the meetup for this trade.
     * @return The ID of a TimePlace object denoting the meetup for this trade.
     */
    public int getTimePlaceID() {
        return timePlaceID;
    }

    /**
     * Returns whether this is a permanent or temporary trade.
     * @return Whether this is a permanent or temporary trade.
     */
    public boolean isPermanent() {
        return isPermanent;
    }

    /**
     * Returns the ID of the account of the first person in this trade.
     * @return The ID of the Account of the first person in this trade.
     */
    public int getTraderOneID() {
        return traderOneID;
    }

    /**
     * Returns the ID of the account of the second person in this trade.
     * @return The ID of the Account of the second person in this trade.
     */
    public int getTraderTwoID() {
        return traderTwoID;
    }

    /**
     * Returns the list of IDs of items the first person is trading away.
     * @return List of IDs of items the first person is trading away.
     */
    public List<Integer> getItemOneIDs() {
        return itemOneIDs;
    }

    /**
     * Returns the list of IDs of items the second person is trading away.
     * @return List of IDs of items the second person is trading away.
     */
    public List<Integer> getItemTwoIDs() {
        return itemTwoIDs;
    }

    /**
     * Returns the status of this trade.
     * @return The status of this trade.
     */
    public TradeStatus getStatus() {
        return status;
    }

    /**
     * Returns the ID of the person who last suggested a meetup.
     * @return The ID of the person who last suggested a meetup.
     */
    public int getLastEditorID() {
        return lastEditorID;
    }

    /**
     * Returns the number of times a meetup has been suggested.
     * @return The number of times a meetup has been suggested.
     */
    public int getEditedCounter() {
        return editedCounter;
    }

    /**
     * Changes the meetup time and location for this trade.
     * @param timePlaceID The new ID of a TimePlace object denoting the meetup for this trade.
     */
    public void setTimePlaceID(int timePlaceID) {
        this.timePlaceID = timePlaceID;
    }

    /**
     * Sets whether this trade is temporary or permanent.
     * @param permanent Whether this is a permanent or temporary trade.
     */
    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    /**
     * Changes the status of this trade.
     * @param status The new status of this trade.
     */
    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    /**
     * Changes the ID of the last person who suggested a meetup.
     * @param lastEditorID The ID of the last person who suggested a meetup.
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

    // TODO wherever this toString is used, use the better use case version
    /**
     * Creates a string representation of this trade.
     * @return A string representation of a Trade object.
     */
    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", timePlaceID=" + timePlaceID +
                ", isPermanent=" + isPermanent +
                ", traderOneID=" + traderOneID +
                ", traderTwoID=" + traderTwoID +
                ", itemOneIDs=" + itemOneIDs +
                ", itemTwoIDs=" + itemTwoIDs +
                ", status=" + status +
                ", lastEditorID=" + lastEditorID +
                ", editedCounter=" + editedCounter +
                '}';
    }

    // TODO java doc

    public boolean isTraderOneCompleted() {
        return traderOneCompleted;
    }

    public void setTraderOneCompleted(boolean traderOneCompleted) {
        this.traderOneCompleted = traderOneCompleted;
        if (this.traderOneCompleted && this.traderTwoCompleted) {
            this.status = TradeStatus.COMPLETED;
        }
    }

    public boolean isTraderTwoCompleted() {
        return traderTwoCompleted;
    }

    public void setTraderTwoCompleted(boolean traderTwoCompleted) {
        this.traderTwoCompleted = traderTwoCompleted;
        if (this.traderOneCompleted && this.traderTwoCompleted) {
            this.status = TradeStatus.COMPLETED;
        }
    }

}
