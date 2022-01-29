package ru.test.milkin.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.test.milkin.constants.PropertyKey;

public class DriverManager {
    private static WebDriver driver;

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = PropertyManager.getProperty(PropertyKey.BROWSER, "chrome");
            if (!"chrome".equals(browser)) {
                throw new RuntimeException("Sorry, the framework supports only Chrome browser");
            }
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        return driver;
    }

    public static void quitDriver() {
        driver.quit();
        driver = null;
    }
}
