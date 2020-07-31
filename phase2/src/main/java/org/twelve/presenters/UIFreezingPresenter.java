package org.twelve.presenters;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UIFreezingPresenter implements FreezingPresenter {
    private List<String> allAccounts;
    private List<String> allFrozenAccounts;
    private List<String> allBannedAccounts;

    private String selectedAccountName;

    private final ResourceBundle localizedResources;
    private final PropertyChangeSupport propertyChangeSupport;

    public UIFreezingPresenter(ResourceBundle localizedResources) {
        this.localizedResources = localizedResources;
        propertyChangeSupport = new PropertyChangeSupport(this);
        allAccounts = new ArrayList<>();
    }

    @Override
    public void setAllAccounts(List<String> allAccounts) {
        List<String> oldAllAccounts = this.allAccounts;
        this.allAccounts = allAccounts;
        propertyChangeSupport.firePropertyChange("allAccounts", oldAllAccounts, allAccounts);
    }

    @Override
    public List<String> getAllAccounts() {
        return allAccounts;
    }

    @Override
    public void setAllFrozenAccounts(List<String> allFrozenAccounts) {
        List<String> oldAllFrozenAccounts = this.allFrozenAccounts;
        this.allFrozenAccounts = allFrozenAccounts;
        propertyChangeSupport.firePropertyChange("allFrozenAccounts", oldAllFrozenAccounts, allFrozenAccounts);
    }

    @Override
    public List<String> getAllFrozenAccounts() {
        return allFrozenAccounts;
    }

    @Override
    public void setAllBannedAccounts(List<String> allBannedAccounts) {
        List<String> oldAllBannedAccounts = this.allBannedAccounts;
        this.allBannedAccounts = allBannedAccounts;
        propertyChangeSupport.firePropertyChange("allBannedAccounts", oldAllBannedAccounts, allBannedAccounts);
    }

    @Override
    public List<String> getAllBannedAccounts() {
        return allBannedAccounts;
    }

    @Override
    public void setSelectedAccountName(String name) {
        String oldSelectedAccountName = name;
        selectedAccountName = MessageFormat.format(localizedResources.getString("accountName"), name);
        propertyChangeSupport.firePropertyChange("selectedAccountName", oldSelectedAccountName, selectedAccountName);
    }

    @Override
    public String getSelectedAccountName() {return selectedAccountName;}


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
