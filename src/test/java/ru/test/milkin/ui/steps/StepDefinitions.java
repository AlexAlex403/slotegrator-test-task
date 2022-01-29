package ru.test.milkin.ui.steps;

import io.cucumber.java.ru.И;
import ru.test.milkin.constants.PropertyKey;
import ru.test.milkin.managers.PageManager;
import ru.test.milkin.pages.AdminPanelPage;
import ru.test.milkin.pages.AuthorizationPage;
import ru.test.milkin.pages.PlayerManagementPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.test.milkin.managers.PropertyManager.getProperty;

public class StepDefinitions {
    AuthorizationPage authPage = PageManager.getPageManager().getAuthorizationPage();
    AdminPanelPage adminPage;
    PlayerManagementPage playerPage;

    @И("Отображается страница авторизации")
    public void assertAuthorizationPageIsLoaded() {
        assertTrue(authPage.isPageLoaded(), "Страница авторизации не загрузилась");
    }

    @И("Я, как администратор, авторизуюсь на странице авторизации")
    public void authByAdmin() {
        adminPage = authPage.loginAsAdmin();
    }

    @И("^Пользователь \\(админ. успешно авторизован$")
    public void assertAdminUserIsAuthorized() {
        assertEquals(
                adminPage.getAuthorizedAdminName(),
                getProperty(PropertyKey.ADMIN_LOGIN_NAME),
                "Имя авторизованного пользователя не равно ожидаемому"
        );
    }

    @И("Отображается админ-панель")
    public void assertAdminPanelPageIsLoaded() {
        assertTrue(adminPage.isPageLoaded(), "Админ-панель не загрузилась");
    }

    @И("Выбираю раздел {string}")
    public void selectMenuItem(String menuItemName) {
        adminPage.selectMenuItem(menuItemName);
    }

    @И("Выбираю подраздел {string}")
    public void selectSubMenuItem(String subMenuItemName) {
        playerPage = adminPage.selectSubMenuItem(subMenuItemName);
    }

    @И("Сортирую таблицу по столбцу {string}")
    public void sortTableByColumnName(String columnName) {
        playerPage.sortTableBy(columnName);
    }

    @И("^Таблица отсортирована по выбранному столбцу( в обратном порядке)?$")
    public void assertTableIsSorted(String isReverseOrder) {
        assertTrue(playerPage.isTableSorted(isReverseOrder != null), "Таблица игроков отсортирована неверно");
    }

    @И("Отображается таблица с игроками")
    public void assertPlayerManagementPageIsLoaded() {
        assertTrue(playerPage.isPageLoaded(), "Таблица игроков не загрузилась");
    }
}
