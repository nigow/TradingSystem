package controllers;

import gateways.ManualConfig;
import presenters.ConsoleHomePresenter;
import presenters.HomePresenter;
import presenters.RestrictionPresenter;
import presenters.TradePresenter;
import usecases.AuthManager;
import usecases.FreezingUtility;

import java.util.ArrayList;
import java.util.List;

public class RestrictionController {

    private ManualConfig mc;

    private FreezingUtility freezingUtility;

    private AuthManager authManager;

    private RestrictionPresenter restrictionPresenter;

    private ControllerInputValidator controllerInputValidator;

    public RestrictionController(ManualConfig mc, RestrictionPresenter restrictionPresenter) {
        this.mc = mc;
        freezingUtility = mc.getFreezingUtility();
        authManager = mc.getAuthManager();
        this.restrictionPresenter = restrictionPresenter;
        controllerInputValidator = new ControllerInputValidator();
    }

    public void run() {
        List<String> options = new ArrayList<>();
        options.add("Lend vs. borrow limit");
        options.add("Maximum number of incomplete trades");
        options.add("Maximum number of weekly trades");
        options.add("Return to main menu");
        boolean isValidInput = false;
        while (!isValidInput) {
            isValidInput = true;
            String action =  restrictionPresenter.displayRestrictionOptions(options);
            if (action.equals("0")) {
                lendMoreThanBorrow();
            }
            else if (action.equals("1")) {
                maxIncompleteTrades();
            }
            else if (action.equals("2")) {
                maxIncompleteTrades();
            }
            else if (action.equals("3")) {
                //TODO go back to main
            }
            else {
                restrictionPresenter.invalidInput();
                isValidInput = false;
            }
        }

    }

    protected void lendMoreThanBorrow() {
        boolean isValid = false;
        String newNumber;
        while (!isValid) {
            isValid = true;
            newNumber = restrictionPresenter.changeLendMoreThanBorrow(freezingUtility.getLendMoreThanBorrow());
            if (!controllerInputValidator.isNum(newNumber)) {
                isValid = false;
                restrictionPresenter.invalidInput();
            }
            else {
                freezingUtility.setLendMoreThanBorrow(Integer.parseInt(newNumber));
            }
        }
    }

    protected void maxIncompleteTrades() {
        boolean isValid = false;
        String newNumber;
        while (!isValid) {
            isValid = true;
            newNumber = restrictionPresenter.changeMaxIncompleteTrades(freezingUtility.getMaxIncompleteTrade());
            if (!controllerInputValidator.isNum(newNumber)) {
                isValid = false;
                restrictionPresenter.invalidInput();
            }
            else {
                freezingUtility.setMaxIncompleteTrade(Integer.parseInt(newNumber));
            }
        }
    }

    protected void maxWeeklyTrades() {
        boolean isValid = false;
        String newNumber;
        while (!isValid) {
            isValid = true;
            newNumber = restrictionPresenter.changeMaxWeeklyTrades(freezingUtility.getMaxWeeklyTrade());
            if (!controllerInputValidator.isNum(newNumber)) {
                isValid = false;
                restrictionPresenter.invalidInput();
            }
            else {
                freezingUtility.setMaxWeeklyTrade(Integer.parseInt(newNumber));
            }
        }
    }
}
