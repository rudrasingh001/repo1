package com.booksWagon.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Rough {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.bookswagon.com");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		Actions act = new Actions(driver);
		WebElement ele1 = driver.findElement(By.id("ctl00_lblUser"));
		WebElement ele2 = driver.findElement(By.xpath("//a[@class='themecolor headersignuplink']"));
		act.moveToElement(ele1).moveToElement(ele2).click().perform();

	}

}
