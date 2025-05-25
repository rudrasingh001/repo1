package com.booksWagon.hooks;

import com.booksWagon.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class HookClass {

    public static WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
