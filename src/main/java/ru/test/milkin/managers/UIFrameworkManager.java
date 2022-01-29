package ru.test.milkin.managers;

import java.util.concurrent.TimeUnit;

import static ru.test.milkin.constants.PropertyKey.*;
import static ru.test.milkin.managers.DriverManager.getDriver;
import static ru.test.milkin.managers.DriverManager.quitDriver;

public class UIFrameworkManager {

    public static void initFramework() {
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(PropertyManager.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(PropertyManager.getProperty(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
        getDriver().get(PropertyManager.getProperty(TEST_UI_ADMIN_LOGIN_URL));
    }

    public static void quitFramework() {
        quitDriver();
    }
}
