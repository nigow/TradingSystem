package org.twelve.presenters.experimental;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for storing itemInfo and giving it to the view when needed
 * @author Ethan
 */
public class TradeCreatorPresenter {

    /**
     * All your items that the other user does not have on their wishlist
     */
    private List <String> yourItems;

    /**
     * All your items that the other user has on their wishlist
     */
    private List <String> yourRecommendedItems;

    /**
     * All the items that the other user has to trade
     */
    private List <String> peerItems;

    /**
     * Constructor to initialize yourItems, yourRecommendedItems, and peerItems as empty lists
     */
    public TradeCreatorPresenter() {
        this.yourItems = new ArrayList<>();
        this.yourRecommendedItems = new ArrayList<>();
        this.peerItems = new ArrayList<>();

    }

    /**
     * Getter for yourItems
     * @return yourItems all your items that the other user does not have on their wishlist
     */
    public List<String> getYourItems() {
        return yourItems;
    }

    /**
     * Getter for yourRecommendedItems
     * @return yourRecommendedItems All your items that the other user has on their wishlist
     */
    public List<String> getYourRecommendedItems() {
        return yourRecommendedItems;
    }

    /**
     * Getter for peerItems
     * @return peerItems All the items that the other user has to trade
     */
    public List<String> getPeerItems() {
        return peerItems;
    }

    /**
     * Setter for yourItems
     * @param yourItems all your items that the other user does not have on their wishlist
     */
    public void setYourItems(List<String> yourItems) {
        this.yourItems = yourItems;
    }

    /**
     * Setter for yourRecommendedItems
     * @param yourRecommendedItems All your items that the other user has on their wishlist
     */
    public void setYourRecommendedItems(List<String> yourRecommendedItems) {
        this.yourRecommendedItems = yourRecommendedItems;
    }

    /**
     * Setter for peerItems
     * @param peerItems All the items that the other user has to trade
     */
    public void setPeerItems(List<String> peerItems) {
        this.peerItems = peerItems;
    }

}
