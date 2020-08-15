package org.twelve.presenters.ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Abstract class that enables observability.
 */
public abstract class ObservablePresenter {

    /**
     * Observability support module used to fire events.
     */
    protected final PropertyChangeSupport propertyChangeSupport;

    /**
     * Constructor for abstract class that enables observability.
     */
    public ObservablePresenter() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Add listener to observable class.
     *
     * @param listener An instance of a class that implements {@link java.beans.PropertyChangeListener}.
     */
    @SuppressWarnings("unused") // required according to PropertyChangeSupport documentation
    public void addPropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.addPropertyChangeListener(listener);

    }

    /**
     * Remove listener from observable class.
     *
     * @param listener An instance of a class that implements {@link java.beans.PropertyChangeListener}.
     */
    @SuppressWarnings("unused") // required according to PropertyChangeSupport documentation
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
