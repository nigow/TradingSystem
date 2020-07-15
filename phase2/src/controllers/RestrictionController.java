package controllers;

import gateways.UseCasePool;
import presenters.RestrictionPresenter;
import usecases.FreezingUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with changing restrictions.
 */
public class RestrictionController {

    private final FreezingUtility freezingUtility;

    private final RestrictionPresenter restrictionPresenter;

    private final InputHandler inputHandler;

    public RestrictionController(UseCasePool useCasePool, RestrictionPresenter restrictionPresenter) {
        freezingUtility = useCasePool.getFreezingUtility();
        this.restrictionPresenter = restrictionPresenter;
        inputHandler = new InputHandler();
    }

    public void run() {
        List<String> options = new ArrayList<>();
        options.add("Lend vs. borrow limit");
        options.add("Maximum number of incomplete trades");
        options.add("Maximum number of weekly trades");
        options.add("Return to main menu");
        while (true) {
            String action = restrictionPresenter.displayRestrictionOptions(options);
            switch (action) {
                case "0":
                    lendMoreThanBorrow();
                    break;
                case "1":
                    maxIncompleteTrades();
                    break;
                case "2":
                    maxWeeklyTrades();
                    break;
                case "3":
                    return;
                default:
                    restrictionPresenter.invalidInput();
                    break;
            }
        }

    }

    private void lendMoreThanBorrow() {
        while (true) {
            String newNumber = restrictionPresenter.changeLendMoreThanBorrow(freezingUtility.getLendMoreThanBorrow());
            if (inputHandler.isExitStr(newNumber))
                return;
            if (!inputHandler.isNum(newNumber))
                restrictionPresenter.invalidInput();
            else {
                freezingUtility.setLendMoreThanBorrow(Integer.parseInt(newNumber));
                restrictionPresenter.showMessage("You changed the restriction threshold.");
                return;
            }
        }
    }

    private void maxIncompleteTrades() {
        while (true) {
            String newNumber = restrictionPresenter.changeMaxIncompleteTrades(freezingUtility.getMaxIncompleteTrade());
            if (inputHandler.isExitStr(newNumber))
                return;
            if (!inputHandler.isNum(newNumber))
                restrictionPresenter.invalidInput();
            else {
                freezingUtility.setMaxIncompleteTrade(Integer.parseInt(newNumber));
                restrictionPresenter.showMessage("You changed the restriction threshold.");
                return;
            }
        }
    }

    private void maxWeeklyTrades() {
        while (true) {
            String newNumber = restrictionPresenter.changeMaxWeeklyTrades(freezingUtility.getMaxWeeklyTrade());
            if (inputHandler.isExitStr(newNumber))
                return;
            if (!inputHandler.isNum(newNumber))
                restrictionPresenter.invalidInput();
            else {
                freezingUtility.setMaxWeeklyTrade(Integer.parseInt(newNumber));
                restrictionPresenter.showMessage("You changed the restriction threshold.");
                return;
            }
        }
    }
}
