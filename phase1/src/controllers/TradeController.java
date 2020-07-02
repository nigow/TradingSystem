package controllers;

import gateways.ManualConfig;
import presenters.ConsoleTradePresenter;
import presenters.TradePresenter;

// TODO: variable and method javadoc

/**
 * Controller that deals with user's past trades and making changes to them.
 * @author Maryam
 */
public class TradeController {
    private TradePresenter tradePresenter;

    public TradeController(ManualConfig mc) {
        tradePresenter = new ConsoleTradePresenter();
    }

    public void run() {

    }
}
