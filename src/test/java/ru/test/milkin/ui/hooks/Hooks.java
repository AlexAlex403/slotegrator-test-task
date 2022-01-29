package ru.test.milkin.ui.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.test.milkin.managers.UIFrameworkManager;
import ru.test.milkin.utils.AllureLogger;

public class Hooks {

    @Before
    public void setUp() {
        UIFrameworkManager.initFramework();
    }

    @After
    public void tearDown() {
        AllureLogger.attachScreenshot();
        UIFrameworkManager.quitFramework();
    }
}
