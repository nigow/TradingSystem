package org.twelve.views;

import org.twelve.controllers.ControllerPool;
import org.twelve.presenters.ui.*;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Builder responsible for constructing views.
 */
public class ViewBuilder {

    private final WindowHandler windowHandler;
    private ControllerPool controllerPool;

    /**
     * Constructor of builder responsible for constructing views.
     * @param windowHandler An instance of {@link org.twelve.views.WindowHandler}.
     */
    public ViewBuilder(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

    /**
     * Builds controllers that views depend on.
     * @param selectedLanguage Language views should be in.
     * @param demoMode Whether demo mode is active.
     */
    public void buildControllers(Locale selectedLanguage, boolean demoMode) {

        controllerPool = new ControllerPool(selectedLanguage, demoMode);

    }

    /**
     * Builds individual views.
     * @param scene Scene the view belongs to.
     * @param localizedResources Any localized resources.
     * @return View for the given scene.
     */
    public SceneView getView(Scenes scene, ResourceBundle localizedResources) {

        switch (scene) {

            case LANDING:

                return new LandingView<>(windowHandler, controllerPool.getLandingController(),
                        new UILandingPresenter());

            case LOGIN:

                return new LoginView<>(windowHandler, controllerPool.getLoginController(),
                        new UILoginPresenter(localizedResources));

            case MENU:

                return new MenuView<>(windowHandler, controllerPool.getMenuController(),
                        new UIMenuPresenter());

            case PROFILE:

                return new ProfileView<>(windowHandler, controllerPool.getProfileController(),
                        new UIProfilePresenter(localizedResources));

            case RESTRICTIONS:

                return new RestrictionsView<>(windowHandler, controllerPool.getThresholdController(),
                        new UIThresholdsPresenter());

            case TRADE_CREATOR:

                return new TradeCreatorView<>(windowHandler, controllerPool.getTradeCreatorController(),
                        new UITradeCreatorPresenter(localizedResources));

            case WAREHOUSE:

                return new WarehouseView<>(windowHandler, controllerPool.getWarehouseController(),
                        new UIWarehousePresenter(localizedResources));

            case REGISTRATION:

                return new RegistrationView<>(windowHandler, controllerPool.getRegistrationController(),
                        new UIRegistrationPresenter(localizedResources));

            case WISHLIST:

                return new WishlistView<>(windowHandler, controllerPool.getWishlistController(),
                        new UIWishlistPresenter(localizedResources));

            case INVENTORY:

                return new InventoryView<>(windowHandler, controllerPool.getInventoryController(),
                        new UIInventoryPresenter(localizedResources));

            case ACCOUNTS:

                return new AccountsView<>(windowHandler, controllerPool.getFreezingController(),
                        new UIFreezingPresenter(localizedResources));

            case TRADE_COLLECTION:

                return new TradeCollectionView<>(windowHandler, controllerPool.getTradeCollectionController(),
                        new UITradeCollectionPresenter(localizedResources));

            case ADMIN_WISHLIST:

                return new AdminWishlistView<>(windowHandler, controllerPool.getAdminWishlistController(),
                        new UIAdminWishlistPresenter(localizedResources));
        }

        return null;
    }

}
