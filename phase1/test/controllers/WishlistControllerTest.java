package controllers;

import entities.TimePlace;
import entities.Trade;
import gateways.InMemoryManualConfig;
import gateways.ManualConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import presenters.TradeCreatorPresenter;
import presenters.WishlistPresenter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

import static org.junit.Assert.*;

public class WishlistControllerTest {

    private ManualConfig manualConfig;

    @Before
    public void setUp() throws Exception {

        manualConfig = new InMemoryManualConfig();
        manualConfig.getAccountManager().createStandardAccount("akari", "akaza"); // 1


        manualConfig.getAccountManager().createStandardAccount("kyouko", "toshinou"); // 2
        manualConfig.getItemManager().createItem("中学デビュー!", " ", 2); // 0

        manualConfig.getItemManager().updateApproval(manualConfig.getItemManager().getItemById(0), true);

        manualConfig.getItemManager().createItem("私とあなたと生徒会", " ", 1); // 1

        manualConfig.getItemManager().updateApproval(manualConfig.getItemManager().getItemById(1), true);


        manualConfig.getAccountManager().setCurrAccount("akari");
        manualConfig.getAccountManager().addItemToWishlist(0);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void startTrade() {

        WishlistPresenter wishlistPresenter = new WishlistPresenter() {

            private boolean acted;

            @Override
            public String displayWishlistOptions(List<String> wishlistOptions) {
                if (acted) return "2";
                acted = true;
                return "0";
            }

            @Override
            public void displayWishlist(List<String> wishlist) {

            }

            @Override
            public String startTrade() {
                return "0";
            }

            @Override
            public String removeFromWishlist() {
                return null;
            }

            @Override
            public void invalidInput() {

            }
        };

        TradeCreatorPresenter tradeCreatorPresenter = new TradeCreatorPresenter() {
            @Override
            public void invalidInput() {

            }

            @Override
            public String getTwoWayTrade() {
                return "y"; // exit creator immediately
            }

            @Override
            public void showInventory(String username, List<String> inventory) {

            }

            @Override
            public String getItem() {
                return "0";
            }

            @Override
            public String getLocation() {
                return "nms";
            }

            @Override
            public String getDate() {
                return LocalDate.now().toString();
            }

            @Override
            public String getTime() {
                return LocalTime.now().plusMinutes(1).truncatedTo(ChronoUnit.MINUTES).toString();
            }

            @Override
            public String getIsPerm() {
                return "y";
            }

            @Override
            public void successMessage() {

            }
        };

        WishlistController wishlistController = new WishlistController(wishlistPresenter, tradeCreatorPresenter,
                                                                       manualConfig);
        wishlistController.run();

        Trade trade = manualConfig.getTradeManager().getTrade();
        assertEquals(trade.getTraderOneID(), 1);
        assertEquals(trade.getTraderTwoID(), 2);
        assertTrue(trade.isPermanent());
        assertTrue(trade.getItemOneIDs().contains(1));
        assertTrue(trade.getItemTwoIDs().contains(0));

        TimePlace timePlace = manualConfig.getTradeManager().getTimePlace();
        assertEquals(timePlace.getPlace(), "nms");
        assertEquals(timePlace.getTime(), LocalDateTime.parse(tradeCreatorPresenter.getDate() + "T" +
                                                              tradeCreatorPresenter.getTime()));

    }

    @Test
    public void removeFromWishlist() {

        WishlistPresenter wishlistPresenter = new WishlistPresenter() {

            private boolean acted;

            @Override
            public String displayWishlistOptions(List<String> wishlistOptions) {
                if (acted) return "2";
                acted = true;
                return "1";
            }

            @Override
            public void displayWishlist(List<String> wishlist) {

            }

            @Override
            public String startTrade() {
                return null;
            }

            @Override
            public String removeFromWishlist() {
                return "0";
            }

            @Override
            public void invalidInput() {

            }
        };

        WishlistController wishlistController = new WishlistController(wishlistPresenter, null, manualConfig);
        wishlistController.run();

        assertEquals(manualConfig.getWishlistUtility().wishlistItems(1).size(), 0);

    }
}