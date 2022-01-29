package ru.test.milkin.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.Ordering;

import io.qameta.allure.Step;

public class PlayerManagementPage extends BasePage {

    private int columnIndex = -1;

    @FindBy(xpath = "//div[@class='page']/div/div[@class='panel-heading']/strong[contains(.,'Player management')]")
    private WebElement tableHeader;

    @FindBy(xpath = "//table/thead/tr/th[contains(@id, 'payment-system-transaction-grid_')]/a")
    private List<WebElement> columnNames;

    @FindBy(xpath = "//table/tbody/tr[@class='odd' or @class='even']/td")
    private List<WebElement> cellValues;

    private By loadSpinner = By.xpath("//*[contains(@class,'loading')]");


    @Step("проверяем, что страница успешно загрузилась")
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(tableHeader);
    }

    public PlayerManagementPage sortTableBy(String columnName) {
        WebElement columnNameElement = columnNames.stream()
                .filter(e -> e.getText().equalsIgnoreCase(columnName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Столбец с именем " + columnName + " не найден"));
        columnIndex = Integer.parseInt(columnNameElement.findElement(By.xpath("./..")).getAttribute("cellIndex"));
        clickOnElement(columnNameElement);
        return this;
    }

    public boolean isTableSorted(boolean isReverseOrder) {
        waitForNoSpinner();
        List<String> values = cellValues.stream()
                .filter(e -> Integer.parseInt(e.getAttribute("cellIndex")) == columnIndex)
                .map(e -> e.getAttribute("textContent").toLowerCase())
                .collect(Collectors.toList());
        return isReverseOrder ?
                Ordering.natural().reverse().isOrdered(values) :
                Ordering.natural().isOrdered(values);
    }

    private void waitForNoSpinner() {
        wait.until(ExpectedConditions.invisibilityOf(findElement(loadSpinner)));
    }
}
