package ru.test.milkin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;
import ru.test.milkin.constants.PropertyKey;

import static ru.test.milkin.managers.PropertyManager.getProperty;

public class AuthorizationPage extends BasePage {
    private static final By verifyCodeInput = By.id("UserLogin_verifyCode");

    @FindBy(xpath = "//section/a[text()='Casino']")
    private WebElement pageLogo;

    @FindBy(xpath = "//input[@id='UserLogin_username']")
    private WebElement loginInput;

    @FindBy(xpath = "//input[@id='UserLogin_password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[contains(@class, 'btn') and @value='Sign in']")
    private WebElement submitButton;


    @Step("проверяем, что страница успешно загрузилась")
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(pageLogo);
    }

    public AdminPanelPage loginAsAdmin() {
        clickOnElementAndSendKeys(loginInput, getProperty(PropertyKey.ADMIN_LOGIN_NAME));
        clickOnElementAndSendKeys(passwordInput, getProperty(PropertyKey.ADMIN_LOGIN_PASSWORD));
        clickOnElement(submitButton);
        if (isElementDisplayed(verifyCodeInput)) {
            throw new RuntimeException("Не удалось авторизоваться, отобразилась капча");
        }
        return app.getAdminPanelPage();
    }
}
