package ru.test.milkin.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import ru.test.milkin.managers.PageManager;

import static ru.test.milkin.managers.DriverManager.getDriver;

public abstract class BasePage {
    protected PageManager app = PageManager.getPageManager();

    protected Actions action = new Actions(getDriver());

    protected WebDriverWait wait = new WebDriverWait(getDriver(), 15, 1000);

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    public abstract boolean isPageLoaded();


    @Step("набор текста в поле ввода")
    public void clickOnElementAndSendKeys(WebElement field, String value) {
        clickOnElement(field);
        field.clear();
        field.sendKeys(value);
        wait.until(ExpectedConditions.attributeToBe(field, "value", value));
    }

    @Step("клик по веб-элементу")
    public void clickOnElement(WebElement element) {
        action.click(element).build().perform();
    }

    @Step("поиск элемента на странице")
    public WebElement findElement(By by) {
        return getDriver().findElement(by);
    }

    @Step("поиск элементов на странице")
    public List<WebElement> findElements(By by) {
        return getDriver().findElements(by);
    }

    @Step("определяем, виден ли элемент на странице")
    public boolean isElementDisplayed(By by) {
        return !findElements(by).isEmpty();
    }

    @Step("определяем, виден ли элемент на странице")
    public boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }
}
