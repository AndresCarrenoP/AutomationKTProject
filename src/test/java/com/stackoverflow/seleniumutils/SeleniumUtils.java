package com.stackoverflow.seleniumutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

	WebDriver driver;
	private final String PROPERTIESPATH = "src\\test\\resources\\ExternalSources\\Config.properties";
	public String titleValue;
	public String titleText;


	public void navigateTo(WebDriver driver, String destination) throws InterruptedException {
		driver.get(destination);
		driver.manage().window().maximize();
		Thread.sleep(1500);
		System.out.println("-----------User navigates to " + destination + "----------------");
	}


	public void clickElement(WebDriver driver, By locator) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'border: 4px solid red;');", driver.findElement(locator));
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator))).click();
			Thread.sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception ocurred: " + e.getMessage());
		}
	}


	public void enterText(WebDriver driver, By locator, String text) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'border: 4px solid red;');", driver.findElement(locator));
			WebDriverWait wait = new WebDriverWait(driver, 10);
			driver.findElement(locator).clear();
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator))).sendKeys(text);
			Thread.sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception ocurred: " + e.getMessage());
		}
	}


	public String readProperty(String key) throws IOException{

		File file = new File (PROPERTIESPATH);
		FileInputStream fis = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fis);
		fis.close();
		String value = prop.getProperty(key);
		return value;
	}


	public List<WebElement> getListOfElements(WebDriver driver, By locator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'border: 4px solid red;');", driver.findElement(locator));
		List<WebElement> list = driver.findElements(locator);
		return list;
	}


	public String getElementText(WebDriver driver, By locator) throws Exception {
		String elementText;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'border: 4px solid red;');", driver.findElement(locator));
			WebDriverWait wait = new WebDriverWait(driver, 10);
			elementText = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
			Thread.sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception ocurred: " + e.getMessage());
			elementText = null;
		}
		return elementText;
	}


	public String getSubElementText(WebDriver driver, WebElement element, By childLocator) throws Exception {
		String subElementText;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'border: 4px solid red;');", driver.findElement(childLocator));
			WebDriverWait wait = new WebDriverWait(driver, 10);
			subElementText = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, childLocator)).get(0).getText();
			//Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("Exception ocurred: " + e.getMessage());
			subElementText = null;
		}
		return subElementText;
	}


	public List<WebElement> getListOfSubElements(WebDriver driver, WebElement element, By childLocator)  {
		List <WebElement> subElements;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'border: 4px solid red;');", driver.findElement(childLocator));
			WebDriverWait wait = new WebDriverWait(driver, 10);
			subElements = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, childLocator));
			//Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("Exception ocurred: " + e.getMessage());
			subElements = null;
		}
		return subElements;
	}


	public String getTotalItemsFound (WebDriver driver, By locator, String toReplace, String replaceWith, String lookingFor) throws Exception {
		String totalFoundS = getElementText(driver, locator).replace(toReplace, replaceWith); 
		System.out.println("\n---------- total " + lookingFor + " found: " + totalFoundS + " --------------");
		return totalFoundS;
	}


	public boolean getJobsTitle (WebDriver driver, By locator) throws IOException {
		List<WebElement> results = getListOfElements(driver, locator);
		String[] valids = readProperty("valids").split(",");
		boolean isRelated = false;

		for (WebElement result : results){
			titleValue = result.getAttribute("title").toLowerCase();
			System.out.println("\n---------- title: " + titleValue + "-------------");

			isRelated = Arrays.stream(valids).anyMatch(titleValue::contains);
			System.out.println("---------- is related to search criteria? " + isRelated + "-------------");
			if (isRelated == false) { break; }
		}
		return isRelated;
	}


	public boolean getQuestionsTags (WebDriver driver, By parent, By childTitle, By childTags) throws Exception {
		List<WebElement> results = getListOfElements(driver, parent);
		boolean containsTag = false;

		for (WebElement result : results){
			titleText = getSubElementText(driver, result, childTitle);
			System.out.println("\n---------- title: " + titleText + "-------------");

			List<WebElement> tags = getListOfSubElements(driver, result, childTags);
			ArrayList<String> ar = new ArrayList<String>();
			
			for (WebElement tag : tags){
				String currentTag = tag.getText();
				ar.add(currentTag);
			}

			System.out.println("---------- tags: " + ar.toString() + "-------------");

			containsTag = ar.stream().anyMatch(readProperty("tags")::contains);
			System.out.println("---------- contains tag '" + readProperty("tags") + "'? " + containsTag + "-------------");
			if (containsTag == false) { break; }
		}
		return containsTag;
	}

}
