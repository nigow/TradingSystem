package org.twelve.presenters;

import org.twelve.entities.Thresholds;

import java.util.List;

/**
 * Interface for managing thresholds.
 *
 * @author Catherine
 */
public interface ThresholdPresenter {
    List<String> getThresholds();
    void setThresholds(List<String> thresholdValues);

}