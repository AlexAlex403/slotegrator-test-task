package ru.test.milkin.ui.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.test.milkin.managers.DriverManager;
import ru.test.milkin.managers.UIFrameworkManager;
import ru.test.milkin.utils.AllureLogger;

public class Hooks {

    @Before
    public void setUp() {
        AllureLogger.setDriver(DriverManager.getDriver());
        UIFrameworkManager.initFramework();
    }

    @After
    public void tearDown() {
        AllureLogger.attachScreenshot();
        UIFrameworkManager.quitFramework();
    }
}
