package controllers;

import gateways.ManualConfig;
import presenters.ConsoleTradePresenter;
import presenters.TradePresenter;

// TODO: 1. variable and method javadoc 2. code >_<

/**
 * Controller that deals with user's past trades and making changes to them.
 * @author Maryam
 */
public class TradeController {
    private final TradePresenter tradePresenter;

    public TradeController(ManualConfig mc) {
        tradePresenter = new ConsoleTradePresenter();
    }

    public void run() {

    }
}
