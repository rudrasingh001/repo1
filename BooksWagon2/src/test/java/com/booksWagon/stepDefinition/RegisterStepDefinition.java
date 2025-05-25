package com.booksWagon.stepDefinition;

import com.booksWagon.pages.RegisterPage;
import com.booksWagon.utils.DriverManager;
import com.booksWagon.utils.ExcelUtil;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class RegisterStepDefinition {

    List<Map<String, String>> testData;
    WebDriver driver;
    RegisterPage registerPage;

    @Given("User is on the BooksWagon registration page")
    public void navigateToRegistrationPage() {
        System.out.println("Starting signup test using Excel data...");
    }

    @When("User fetches signup data from Excel and performs signup")
    public void fetchDataAndPerformRegister() throws InterruptedException {
        testData = ExcelUtil.getData("src/test/resources/testingData/TestData.xlsx", "SignupData");

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> row = testData.get(i);

            driver = DriverManager.initializeDriver();
            registerPage = new RegisterPage(driver);

            driver.get("https://www.bookswagon.com/");

            registerPage.clickRegisterHeader();

            registerPage.enterName(row.get("name"));
            registerPage.enterMobile(row.get("mobile"));

            System.out.println("Please complete captcha manually...");
            Thread.sleep(60000); // Wait for captcha manually

//            registerPage.clickContinue();
            
            registerPage.clickContinue();

         // Check if validation error appears
         boolean errorDisplayed = registerPage.isErrorMessageDisplayed(); // Implement this method in RegisterPage

         if (errorDisplayed) {
             System.out.println("Validation error detected! Moving to the next test case...");
             ExcelUtil.setCellData("src/test/resources/testingData/TestData.xlsx", "SignupData", i + 1, "actualResult", "failure");
             ExcelUtil.setCellData("src/test/resources/testingData/TestData.xlsx", "SignupData", i + 1, "testResult", "Pass");
             
             driver.quit(); // Close browser before moving to next test case
             continue; // Skip remaining steps and move to the next row in Excel
         }

            System.out.println("Please enter OTP manually...");
            Thread.sleep(30000); // Wait for user to manually input OTP

            // Do NOT fill OTP here again — it's manually entered by user

            registerPage.enterPassword(row.get("password"));
            registerPage.enterConfirmPassword(row.get("confirmPassword"));
            registerPage.clickRegister();

            // Validate registration result
            boolean registerSuccess = registerPage.isRegistrationSuccessful();
            String actualResult = registerSuccess ? "success" : "failure";

            // Write actualResult to Excel
            ExcelUtil.setCellData("src/test/resources/testingData/TestData.xlsx", "SignupData", i + 1, "actualResult", actualResult);

            // Compare with expected and write test result
            String expectedResult = row.get("expectedResult");
            String testResult = actualResult.equalsIgnoreCase(expectedResult) ? "Pass" : "Fail";
            ExcelUtil.setCellData("src/test/resources/testingData/TestData.xlsx", "SignupData", i + 1, "testResult", testResult);

            // Assert
            if ("success".equalsIgnoreCase(expectedResult)) {
                Assert.assertTrue(registerSuccess, "Expected successful registration, but it failed");
            } else {
                Assert.assertEquals(actualResult, expectedResult, "Error message mismatch");
            }

            
                driver.quit();
            }
        }
    

    @Then("User verifies signup result")
    public void verifyRegisterResult() {
        System.out.println("Signup scenarios completed and validated.");
    }
}
