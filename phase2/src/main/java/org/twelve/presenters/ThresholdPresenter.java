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

    String getLendMoreThanBorrow();
    String getMaxIncompleteTrade();
    String getMaxWeeklyTrade();
    String getNumberOfDays();
    String getNumberOfStats();
    String getNumberOfEdits();

}