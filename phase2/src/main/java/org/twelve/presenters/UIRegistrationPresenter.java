package org.twelve.presenters;

import org.twelve.entities.AccountType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class UIRegistrationPresenter implements RegistrationPresenter {

    private List<String> availableTypes;
    private final ResourceBundle localizedResources;

    public UIRegistrationPresenter(ResourceBundle localizedResources) {
        this.localizedResources = localizedResources;
    }

    @Override
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public void setAvailableTypes(List<AccountType> types) {
        availableTypes = new ArrayList<>();
        for (AccountType type : types) {
            String localizedName = localizedResources.getString(type.name());
            availableTypes.add(localizedName);
        }
    }
}
