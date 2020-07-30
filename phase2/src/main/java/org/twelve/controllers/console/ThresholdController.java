package org.twelve.controllers.console;

import org.twelve.controllers.InputHandler;
import org.twelve.presenters.ThresholdPresenter;
import org.twelve.usecases.ThresholdRepository;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with changing restrictions.
 */
public class ThresholdController {

    private final ThresholdRepository thresholdRepository;

    private final ThresholdPresenter thresholdPresenter;

    private final InputHandler inputHandler;

    public ThresholdController(UseCasePool useCasePool, ThresholdPresenter thresholdPresenter) {
        thresholdRepository = useCasePool.getThresholdRepository();
        this.thresholdPresenter = thresholdPresenter;
        inputHandler = new InputHandler();
    }

    public void run() {
        List<String> options = new ArrayList<>();
        options.add(thresholdPresenter.lendBorrowLimit());
        options.add(thresholdPresenter.maxIncompleteTrades());
        options.add(thresholdPresenter.maxWeeklyTrades());
        options.add(thresholdPresenter.returnToMainMenu());
        while (true) {
            String action = thresholdPresenter.displayThresholdOptions(options);
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
                    thresholdPresenter.invalidInput();
                    break;
            }
        }

    }

    private void lendMoreThanBorrow() {
        while (true) {
            String newNumber = thresholdPresenter.changeLendMoreThanBorrow(thresholdRepository.getLendMoreThanBorrow());
            if (inputHandler.isExitStr(newNumber))
                return;
            if (!inputHandler.isNum(newNumber))
                thresholdPresenter.invalidInput();
            else {
                thresholdRepository.setLendMoreThanBorrow(Integer.parseInt(newNumber));
                thresholdPresenter.displayChangedThresholds();
                return;
            }
        }
    }

    private void maxIncompleteTrades() {
        while (true) {
            String newNumber = thresholdPresenter.changeMaxIncompleteTrades(thresholdRepository.getMaxIncompleteTrade());
            if (inputHandler.isExitStr(newNumber))
                return;
            if (!inputHandler.isNum(newNumber))
                thresholdPresenter.invalidInput();
            else {
                thresholdRepository.setMaxIncompleteTrade(Integer.parseInt(newNumber));
                thresholdPresenter.displayChangedThresholds();
                return;
            }
        }
    }

    private void maxWeeklyTrades() {
        while (true) {
            String newNumber = thresholdPresenter.changeMaxWeeklyTrades(thresholdRepository.getMaxWeeklyTrade());
            if (inputHandler.isExitStr(newNumber))
                return;
            if (!inputHandler.isNum(newNumber))
                thresholdPresenter.invalidInput();
            else {
                thresholdRepository.setMaxWeeklyTrade(Integer.parseInt(newNumber));
                thresholdPresenter.displayChangedThresholds();
                return;
            }
        }
    }

    private void numberOfDays() {
        while (true) {
            String newNumber = thresholdPresenter.changeNumberOfDays(thresholdRepository.getNumberOfDays());
            if (inputHandler.isExitStr(newNumber))
                return;
            if (!inputHandler.isNum(newNumber))
                thresholdPresenter.invalidInput();
            else {
                thresholdRepository.setNumberOfDays(Integer.parseInt(newNumber));
                thresholdPresenter.displayChangedThresholds();
                return;
            }
        }
    }

    private void numberOfEdits() {
        while (true) {
            String newNumber = thresholdPresenter.changeNumberOfEdits(thresholdRepository.getNumberOfEdits());
            if (inputHandler.isExitStr(newNumber))
                return;
            if (!inputHandler.isNum(newNumber))
                thresholdPresenter.invalidInput();
            else {
                thresholdRepository.setNumberOfEdits(Integer.parseInt(newNumber));
                thresholdPresenter.displayChangedThresholds();
                return;
            }
        }
    }

    private void numberOfStats() {
        while (true) {
            String newNumber = thresholdPresenter.changeNumberOfStats(thresholdRepository.getNumberOfStats());
            if (inputHandler.isExitStr(newNumber))
                return;
            if (!inputHandler.isNum(newNumber))
                thresholdPresenter.invalidInput();
            else {
                thresholdRepository.setNumberOfStats(Integer.parseInt(newNumber));
                thresholdPresenter.displayChangedThresholds();
                return;
            }
        }
    }
}
