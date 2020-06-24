package entities;

import java.util.ArrayList;

/**
 * Represents a one-way or two-way transaction between two users.
 * @author Maryam
 */
public class Trade {
    /**
     * The ID of this Trade.
     */
    private int id;
    /**
     * The ID of a TimePlace object denoting the meetup for this transaction.
     */
    private int timePlaceID;
    /**
     * Stores whether this is a permanent or temporary transaction.
     */
    private boolean isPermanent;
    /**
     * The ID of the Account of the first person in this trade.
     */
    private int traderOneID;
    /**
     * The ID of the Account of the second person in this trade.
     */
    private int traderTwoID;
    /**
     * List of IDs of items the first person is trading away.
     */
    private ArrayList<Integer> itemOneID;
    /**
     * List of IDs of items the second person is trading away.
     */
    private ArrayList<Integer> itemTwoID;

    /**
     * Different possible statuses of a trade.
     */
    enum Status {
        REJECTED,
        UNCONFIRMED,
        CONFIRMED,
        COMPLETED
    }

    /**
     * The status of this transaction (i.e. if it's rejected, unconfirmed, confirmed, or completed)
     */
    private Status status;
    /**
     * Keeps track of the last person who suggested a meetup.
     */
    private int lastEditorID;
    /**
     * Keeps track of number of times a new meetup has been suggested.
     */
    private int editedCounter;
}
