package org.twelve.presenters.ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ObservablePresenter {

    protected PropertyChangeSupport propertyChangeSupport;

    public ObservablePresenter() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.addPropertyChangeListener(listener);

    }
}
