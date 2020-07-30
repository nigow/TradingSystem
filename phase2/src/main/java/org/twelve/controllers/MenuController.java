package org.twelve.controllers;

import org.twelve.usecases.SessionManager;
import org.twelve.usecases.UseCasePool;

public class MenuController {

    private SessionManager sessionManager;

    public MenuController(UseCasePool useCasePool) {

        this.sessionManager = useCasePool.getSessionManager();

    }

    public void logout() {
        sessionManager.logout();
    }

}
