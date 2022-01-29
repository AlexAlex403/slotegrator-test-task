package ru.test.milkin.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.ContainerLifecycleListener;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.model.TestResult;
import ru.test.milkin.managers.DriverManager;

public class AllureLogger implements StepLifecycleListener, TestLifecycleListener, ContainerLifecycleListener {

    @Override
    public void beforeStepStop(StepResult result) {
        if (Character.isUpperCase(result.getName().charAt(0))) {
            attachScreenshot();
        }
    }

    @Override
    public void beforeTestSchedule(TestResult result) {
        if (result.getName().equalsIgnoreCase("Runs Cucumber Scenarios")) {
            result.setUuid("");
            result = null;
        }
    }

    public static void attachScreenshot() {
        if (!DriverManager.isDriverNull()) {
            Allure.getLifecycle().addAttachment(
                    "screenshot",
                    "image/png",
                    "png",
                    ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES)
            );
        }
    }
}
