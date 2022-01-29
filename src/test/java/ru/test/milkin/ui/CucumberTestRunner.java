package ru.test.milkin.ui;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty",
                "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm"
        },
        tags = "@ui",
        glue = "ru.test.milkin.ui",
        features = "classpath:ru.test.milkin.features"
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
