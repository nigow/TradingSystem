package org.twelve.presenters;

public interface PresenterPool {

    RegistrationPresenter getRegistrationPresenter();
    WarehousePresenter getWarehousePresenter();
    WishlistPresenter getWishlistPresenter();

}
