package org.twelve.presenters.ui;

import org.twelve.entities.Thresholds;
import org.twelve.presenters.ThresholdPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UIThresholdsPresenter extends ObservablePresenter implements ThresholdPresenter {
    private ResourceBundle localizedResources;
    List<String> thresholdValues;

    public UIThresholdsPresenter(ResourceBundle localizedResources){
        super();
        this.localizedResources = localizedResources;
        thresholdValues = new ArrayList<String>();
        thresholdValues = Arrays.asList("1", "5", "10", "30", "6", "3");
    }
    @Override
    public List<String> getThresholds() {
        return thresholdValues;
    }

    @Override
    public void setThresholds(List<String> thresholdValues) {
        propertyChangeSupport.firePropertyChange("lendVsBorrow", thresholdValues.get(0), this.thresholdValues.get(0));
        propertyChangeSupport.firePropertyChange("maxIncomplete", thresholdValues.get(1), this.thresholdValues.get(1));
        propertyChangeSupport.firePropertyChange("maxWeekly", thresholdValues.get(2), this.thresholdValues.get(2));
        propertyChangeSupport.firePropertyChange("numOfDays", thresholdValues.get(3), this.thresholdValues.get(3));
        propertyChangeSupport.firePropertyChange("numOfEdits", thresholdValues.get(4), this.thresholdValues.get(4));
        propertyChangeSupport.firePropertyChange("numOfStats", thresholdValues.get(5), this.thresholdValues.get(5));
        this.thresholdValues = thresholdValues;
    }
}
