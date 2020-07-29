package org.twelve.presenters;

import java.util.Locale;

public class UIPresenterPool implements PresenterPool {

    private final RegistrationPresenter registrationPresenter;
    private final WarehousePresenter warehousePresenter;

    public UIPresenterPool(Locale locale) {

        registrationPresenter = new UIRegistrationPresenter(locale);
        warehousePresenter = new UIWarehousePresenter(locale);

    }

    @Override
    public RegistrationPresenter getRegistrationPresenter() {
        return registrationPresenter;
    }

    @Override
    public WarehousePresenter getWarehousePresenter() {
        return warehousePresenter;
    }
}
