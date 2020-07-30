package org.twelve.presenters;

import org.twelve.entities.AccountType;

import java.util.List;

public interface RegistrationPresenter {

    List<String> getAvailableTypes();
    void setAvailableTypes(List<AccountType> types);

}
