package com.booksWagon.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features", // location of .feature files
        glue = {"com.booksWagon.stepDefinition", "com.booksWagon.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json"},
        monochrome = true,
        dryRun = false,
        tags = "@Signup" 
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
