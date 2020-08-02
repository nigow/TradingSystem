package org.twelve.controllers;

import org.twelve.presenters.ThresholdPresenter;
import org.twelve.usecases.ThresholdRepository;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with changing restrictions
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class ThresholdController {

    /**
     * An instance of ThresholdRepository to set new limits
     */
    private final ThresholdRepository thresholdRepository;

    private ThresholdPresenter thresholdPresenter;

    /**
     * Initializes the class with thresholdRepository from useCasePool
     * @param useCasePool the useCasePool for getting thresholdRepository
     */
    public ThresholdController(UseCasePool useCasePool) {
        thresholdRepository = useCasePool.getThresholdRepository();
    }

    /**
     * Calls thresholdRepository and sets LendMoreThanBorrow to the newNumber
     * @param newNumber the new number of items you need to lend more than borrow
     */
    public void lendMoreThanBorrow(int newNumber) {
        thresholdRepository.setLendMoreThanBorrow(newNumber);
    }

    /**
     * Calls thresholdRepository and sets maxIncompleteTrades to the newNumber
     * @param newNumber the new number of max incomplete trades you can have
     */
    public void maxIncompleteTrades(int newNumber) {
        thresholdRepository.setMaxIncompleteTrade(newNumber);
    }

    /**
     * Calls thresholdRepository and sets maxWeeklyTrades to the newNumber
     * @param newNumber the new number of max weekly trades you can have
     */
    public void maxWeeklyTrades(int newNumber) {
        thresholdRepository.setMaxWeeklyTrade(newNumber);
    }

    public void numberOfDays(int newNumber) {
        thresholdRepository.setNumberOfDays(newNumber);
    }

    public void numberOfStats(int newNumber) {
       thresholdRepository.setNumberOfStats(newNumber);
    }

    public void numberOfEdits(int newNumber) {
       thresholdRepository.setNumberOfEdits(newNumber);
    }

    public void setThresholdPresenter(ThresholdPresenter thresholdPresenter) {
        this.thresholdPresenter = thresholdPresenter;
    }

    public void displayThresholds() {

        thresholdPresenter.setThresholds(thresholdRepository.getLendMoreThanBorrow(),
                thresholdRepository.getMaxIncompleteTrade(), thresholdRepository.getMaxWeeklyTrade(),
                thresholdRepository.getNumberOfDays(), thresholdRepository.getNumberOfStats(),
                thresholdRepository.getNumberOfEdits());

    }
}
