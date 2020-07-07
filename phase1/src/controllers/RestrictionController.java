package controllers;

import gateways.ManualConfig;
import presenters.RestrictionPresenter;
import usecases.FreezingUtility;

import java.util.ArrayList;
import java.util.List;

public class RestrictionController {

    private final FreezingUtility freezingUtility;

    private final RestrictionPresenter restrictionPresenter;

    private final ControllerInputValidator controllerInputValidator;

    public RestrictionController(ManualConfig mc, RestrictionPresenter restrictionPresenter) {
        freezingUtility = mc.getFreezingUtility();
        this.restrictionPresenter = restrictionPresenter;
        controllerInputValidator = new ControllerInputValidator();
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
            if (controllerInputValidator.isExitStr(newNumber))
                return;
            if (!controllerInputValidator.isNum(newNumber))
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
            if (controllerInputValidator.isExitStr(newNumber))
                return;
            if (!controllerInputValidator.isNum(newNumber))
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
            if (controllerInputValidator.isExitStr(newNumber))
                return;
            if (!controllerInputValidator.isNum(newNumber))
                restrictionPresenter.invalidInput();
            else {
                freezingUtility.setMaxWeeklyTrade(Integer.parseInt(newNumber));
                restrictionPresenter.showMessage("You changed the restriction threshold.");
                return;
            }
        }
    }
}
