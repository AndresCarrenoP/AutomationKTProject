package com.stackoverflow.seleniumutils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


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


	public void assertLogURL(WebDriver driver, ExtentTest test, String expectedURL, String currentURL, String screenshotName) throws Exception {
		if (expectedURL.equals(currentURL)) {
			test.log(LogStatus.PASS, "User was navigated to the right URL" + test.addScreenCapture(getScreenshot(driver, screenshotName)));
		} else {
			test.log(LogStatus.FAIL, "User was navigated to the wrong URL" + test.addScreenCapture(getScreenshot(driver, screenshotName)));
		}
		assertEquals(expectedURL, currentURL);
	}
	
	
	public void log(WebDriver driver, ExtentTest test, String STATUS, String resultComment, String screenshotName) throws Exception {
		test.log(LogStatus.valueOf(STATUS), resultComment + test.addScreenCapture(getScreenshot(driver, screenshotName)));
	}


	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/ExtentReports/" + screenshotName + "_" + dateName + ".png";
		File finalDestination = new File (destination);
		FileUtils.copyFile(source, finalDestination);
		Thread.sleep(1000);
		return destination;
	}


	public String getMethodName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String currentMethod = stackTrace[2].getMethodName();
		return currentMethod;
	}

}
