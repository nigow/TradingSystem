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

/**
 * controller that displays and adjusts restrictions
 * @auth Catherine
 */
public class RestrictionController {
    /**
     * an instance of ManualConfig
     */
    private ManualConfig mc;
    /**
     * an instance of FreezingUtility to get and change the restrictions
     */
    private FreezingUtility freezingUtility;
    /**
     * an instance of authManager
     */
    private AuthManager authManager;
    /**
     * an instance of restrictionPresenter to communicate with user
     */
    private RestrictionPresenter restrictionPresenter;
    /**
     * an instance of ControllerInputValidator to checkvalidity of input
     */
    private ControllerInputValidator controllerInputValidator;

    /**
     * instantiate constructor for RestrictionController
     * @param mc an instance of ManualConfig
     * @param restrictionPresenter an instance of RestrictionPresenter
     */
    public RestrictionController(ManualConfig mc, RestrictionPresenter restrictionPresenter) {
        this.mc = mc;
        freezingUtility = mc.getFreezingUtility();
        authManager = mc.getAuthManager();
        this.restrictionPresenter = restrictionPresenter;
        controllerInputValidator = new ControllerInputValidator();
    }

    /**
     * lets user to see possible estriction related actions and directs them to action
     */
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
                //TODO go back to home
            }
            else {
                restrictionPresenter.invalidInput();
                isValidInput = false;
            }
        }

    }

    /**
     * lets user change how many more objects they can lend than borrow
     */
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

    /**
     * lets user change how many incomplete trades are allowed
     */
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

    /**
     * lets user change how many trdes they're allowed weekly
     */
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
