



package com.booksWagon.stepDefinition;

import com.booksWagon.pages.LoginPage;
import com.booksWagon.utils.DriverManager;
import com.booksWagon.utils.ExcelUtil;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class LoginStepDefinition {

    List<Map<String, String>> testData;
    WebDriver driver;
    LoginPage loginPage;
    String expectedResult;

    @Given("User is on the BooksWagon homepage")
    public void navigateToHomepage() {
        System.out.println("Starting login test using Excel data...");
    }

    @When("User fetches login data from Excel and performs login")
    public void fetchDataAndLogin() {
        testData = ExcelUtil.getData("src/test/resources/testingData/TestData.xlsx", "LoginData");

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> row = testData.get(i);

            
            driver = DriverManager.initializeDriver();
            loginPage = new LoginPage(driver);

            driver.get("https://www.bookswagon.com/");

            loginPage.clickLoginHeader();
            loginPage.enterMobileOrEmail(row.get("mobile"));

            String password = row.get("password");
            String otp = row.get("otp");
            expectedResult = row.get("expectedResult");

            if (password != null && !password.isEmpty()) {
                loginPage.enterPassword(password);
                loginPage.clickLoginButton();
            } else {
                loginPage.clickRequestOtp();
                loginPage.waitForOtpInput(); 
                loginPage.clickVerifyOtp();
            }

            
            boolean loginSuccess = loginPage.isLoginSuccessful();
            String actualResult = loginSuccess ? "success" : "failure";

            
            ExcelUtil.setCellData("src/test/resources/testingData/TestData.xlsx", "LoginData", i + 1, "actualResult", actualResult);

            String testResult = actualResult.equalsIgnoreCase(expectedResult) ? "Pass" : "Fail";
            ExcelUtil.setCellData("src/test/resources/testingData/TestData.xlsx", "LoginData", i + 1, "testResult", testResult);

           
            if ("success".equalsIgnoreCase(expectedResult)) {
                Assert.assertTrue(loginSuccess, "Expected login to succeed, but it failed");
            } else {
                Assert.assertFalse(loginSuccess, "Expected login to fail, but it succeeded");
            }

            
            driver.quit();
        }
    }

    @Then("User verifies login result")
    public void userVerifiesLoginResult() {
        System.out.println("Login scenarios executed successfully based on Excel data.");
        
    }
}
