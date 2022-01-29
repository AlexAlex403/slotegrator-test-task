package ru.test.milkin.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.qameta.allure.Step;

public class AdminPanelPage extends BasePage {
    @FindBy(xpath = "//div[@class='logo']/a/img[@alt='Casino']")
    private WebElement pageLogo;

    @FindBy(xpath = "//section[@id='header']//a[@class='dropdown-toggle']/i[contains(@class, 'fa-user')]/following-sibling::span")
    private WebElement authorizedAdmin;

    @FindBy(xpath = "//li/a[@data-target]/span")
    private List<WebElement> menuList;

    @FindBy(xpath = "//li/ul[contains(@id, 's-menu-')]/li/a[text()]")
    private List<WebElement> subMenuList;


    @Step("проверяем, что страница успешно загрузилась")
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(pageLogo);
    }

    public String getAuthorizedAdminName() {
        return authorizedAdmin.getText();
    }

    public AdminPanelPage selectMenuItem(String menuItemName) {
        clickOnElement(
                menuList.stream()
                        .filter(e -> menuItemName.equalsIgnoreCase(e.getText()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Элемент меню [" + menuItemName + "] не найден на странице"))
        );
        return this;
    }

    public PlayerManagementPage selectSubMenuItem(String subMenuItemName) {
        WebElement subMenuItem = subMenuList.stream()
                .filter(e -> subMenuItemName.equalsIgnoreCase(e.getText()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Элемент подменю [" + subMenuItemName + "] не найден на странице"));
        wait.until(ExpectedConditions.visibilityOf(subMenuItem));
        clickOnElement(subMenuItem);
        return app.getPlayerManagementPage();
    }
}
