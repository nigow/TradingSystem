package org.twelve.presenters.ui;

import org.twelve.presenters.FreezingPresenter;

import java.util.*;

public class UIFreezingPresenter extends ObservablePresenter implements FreezingPresenter {
    // private List<String> allAccounts;

    private List<Map<String, String>> bannedAccounts;
    private List<Map<String, String>> frozenAccounts;
    private List<Map<String, String>> unfreezeAccounts;
    private List<Map<String, String>> toFreezeAccounts;
    private List<Map<String, String>> trustedAccounts;

    private List<Map<String, String>> vacationingAccounts;

    private List<Map<String, String>> adminAccounts;

    private final ResourceBundle localizedResources;

    public UIFreezingPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;

        setBannedAccounts(new ArrayList<>());
        setFrozenAccounts(new ArrayList<>());
        setUnfreezeAccounts(new ArrayList<>());
        setToFreezeAccounts(new ArrayList<>());

        setVacationingAccounts(new ArrayList<>());


        setAdminAccounts(new ArrayList<>());
        setTrustedAccounts(new ArrayList<>());
    }

    @Override
    public void setBannedAccounts(List<String> bannedAccounts) {
        List<Map<String, String>> oldBannedAccounts = this.bannedAccounts;
        this.bannedAccounts = new ArrayList<>();
        for (String bannedAccount : bannedAccounts) {

            this.bannedAccounts.add(Map.of("username", bannedAccount, "role", localizedResources.getString("banned")));

        }
        propertyChangeSupport.firePropertyChange("bannedAccounts", oldBannedAccounts, this.bannedAccounts);
    }

    @Override
    public List<Map<String, String>> getBannedAccounts() {
        return bannedAccounts;
    }

    @Override
    public void setFrozenAccounts(List<String> frozenAccounts) {
        List<Map<String, String>> oldFrozenAccounts = this.frozenAccounts;
        this.frozenAccounts = new ArrayList<>();
        for (String frozenAccount : frozenAccounts) {

            this.frozenAccounts.add(Map.of("username", frozenAccount, "role", localizedResources.getString("frozen")));

        }
        propertyChangeSupport.firePropertyChange("frozenAccounts", oldFrozenAccounts, this.frozenAccounts);
    }

    @Override
    public List<Map<String, String>> getFrozenAccounts() {
        return frozenAccounts;
    }

    @Override
    public void setUnfreezeAccounts(List<String> unfreezeAccounts) {
        List<Map<String, String>> oldUnfreezeAccounts = this.unfreezeAccounts;
        this.unfreezeAccounts = new ArrayList<>();
        for (String unfreezeAccount : unfreezeAccounts) {

            this.unfreezeAccounts.add(Map.of("username", unfreezeAccount, "role",
                    localizedResources.getString("requested")));

        }
        propertyChangeSupport.firePropertyChange("unfreezeAccounts", oldUnfreezeAccounts, this.unfreezeAccounts);
    }

    @Override
    public List<Map<String, String>> getUnfreezeAccounts() {
        return unfreezeAccounts;
    }

    @Override
    public void setToFreezeAccounts(List<String> toFreezeAccounts) {
        List<Map<String, String>> oldToFreezeAccounts = this.toFreezeAccounts;
        this.toFreezeAccounts = new ArrayList<>();
        for (String toFreezeAccount: toFreezeAccounts) {

            this.toFreezeAccounts.add(Map.of("username", toFreezeAccount, "role", localizedResources.getString("pending")));

        }
        propertyChangeSupport.firePropertyChange("toFreezeAccounts", oldToFreezeAccounts, this.toFreezeAccounts);
    }

    @Override
    public List<Map<String, String>> getToFreezeAccounts() {
        return toFreezeAccounts;
    }

    @Override
    public void setVacationingAccounts(List<String> vacationingAccounts) {
        List<Map<String, String>> oldVacationingAccounts = this.vacationingAccounts;
        this.vacationingAccounts = new ArrayList<>();
        for (String vacationingAccount: vacationingAccounts) {

            this.vacationingAccounts.add(Map.of("username", vacationingAccount, "role", localizedResources.getString("vacationing")));

        }
        propertyChangeSupport.firePropertyChange("vacationingAccounts", oldVacationingAccounts, this.vacationingAccounts);
    }

    @Override
    public List<Map<String, String>> getVacationingAccounts() {
        return vacationingAccounts;
    }

    /*
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
    */

    @Override
    public void setAdminAccounts(List<String> adminAccounts) {
        List<Map<String, String>> oldAdminAccounts = this.adminAccounts;
        this.adminAccounts = new ArrayList<>();
        for (String adminAccount : adminAccounts) {

            this.adminAccounts.add(Map.of("username", adminAccount, "role", localizedResources.getString("admin")));

        }
        propertyChangeSupport.firePropertyChange("adminAccounts", oldAdminAccounts, this.adminAccounts);
    }

    @Override
    public List<Map<String, String>> getAdminAccounts() {
        return adminAccounts;
    }

    @Override
    public void setTrustedAccounts(List<String> trustedAccounts) {
        List<Map<String, String>> oldTrustedAccounts = this.trustedAccounts;
        this.trustedAccounts = new ArrayList<>();
        for (String trustedAccount: trustedAccounts) {
            this.vacationingAccounts.add(Map.of("username", trustedAccount, "role", localizedResources.getString("Trusted")));

        }
        propertyChangeSupport.firePropertyChange("vacationingAccounts", oldTrustedAccounts, this.trustedAccounts);
    }

    @Override
    public List<Map<String, String>> getTrustedAccounts() {
        return trustedAccounts;
    }
}
