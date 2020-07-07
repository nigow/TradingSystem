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
        boolean isValid = false;
        String newNumber;
        while (!isValid) {
            isValid = true;
            newNumber = restrictionPresenter.changeLendMoreThanBorrow(freezingUtility.getLendMoreThanBorrow());
            if (newNumber.equals("-1")) {
                return;
            } else if (!controllerInputValidator.isNum(newNumber)) {
                isValid = false;
                restrictionPresenter.invalidInput();
            } else {
                freezingUtility.setLendMoreThanBorrow(Integer.parseInt(newNumber));
            }
        }
    }

    private void maxIncompleteTrades() {
        boolean isValid = false;
        String newNumber;
        while (!isValid) {
            isValid = true;
            newNumber = restrictionPresenter.changeMaxIncompleteTrades(freezingUtility.getMaxIncompleteTrade());
            if (newNumber.equals("-1")) {
                return;
            } else if (!controllerInputValidator.isNum(newNumber)) {
                isValid = false;
                restrictionPresenter.invalidInput();
            } else {
                freezingUtility.setMaxIncompleteTrade(Integer.parseInt(newNumber));
            }
        }
    }

    private void maxWeeklyTrades() {
        boolean isValid = false;
        String newNumber;
        while (!isValid) {
            isValid = true;
            newNumber = restrictionPresenter.changeMaxWeeklyTrades(freezingUtility.getMaxWeeklyTrade());
            if (newNumber.equals("-1")) {
                return;
            } else if (!controllerInputValidator.isNum(newNumber)) {
                isValid = false;
                restrictionPresenter.invalidInput();
            } else {
                freezingUtility.setMaxWeeklyTrade(Integer.parseInt(newNumber));
            }
        }
    }
}
