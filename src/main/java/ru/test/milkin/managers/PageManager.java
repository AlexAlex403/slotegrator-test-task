package ru.test.milkin.managers;

import ru.test.milkin.pages.AdminPanelPage;
import ru.test.milkin.pages.AuthorizationPage;
import ru.test.milkin.pages.PlayerManagementPage;

public class PageManager {
    private static PageManager pageManager;

    private AuthorizationPage authorizationPage;
    private AdminPanelPage adminPanelPage;
    private PlayerManagementPage playerPage;

    private PageManager() {
    }

    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public AuthorizationPage getAuthorizationPage() {
        if (authorizationPage == null) {
            authorizationPage = new AuthorizationPage();
        }
        return authorizationPage;
    }

    public AdminPanelPage getAdminPanelPage() {
        if (adminPanelPage == null) {
            adminPanelPage = new AdminPanelPage();
        }
        return adminPanelPage;
    }

    public PlayerManagementPage getPlayerManagementPage() {
        if (playerPage == null) {
            playerPage = new PlayerManagementPage();
        }
        return playerPage;
    }
}
