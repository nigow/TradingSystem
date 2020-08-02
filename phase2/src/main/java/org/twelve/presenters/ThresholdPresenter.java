package org.twelve.presenters;

import org.twelve.entities.Thresholds;

import java.util.List;

/**
 * Interface for managing thresholds.
 *
 * @author Catherine
 */
public interface ThresholdPresenter {

    void setThresholds(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays,
                       int numberOfStats, int numberOfEdits);
    int getLendMoreThanBorrow();
    int getMaxIncompleteTrade();
    int getMaxWeeklyTrade();
    int getNumberOfDays();
    int getNumberOfStats();
    int getNumberOfEdits();
}