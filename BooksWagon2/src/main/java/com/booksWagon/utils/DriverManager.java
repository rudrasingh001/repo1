package com.booksWagon.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.edge.EdgeDriver; // keep if you plan to support multiple browsers

public class DriverManager {

    // Remove static singleton driver (no longer shared globally)
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Create and return a new driver instance
    public static WebDriver initializeDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver newDriver = new ChromeDriver();
        newDriver.manage().window().maximize();
        driver.set(newDriver);
        return newDriver;
    }

    // Get current thread's driver instance
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Quit and remove current thread's driver
    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }
}
