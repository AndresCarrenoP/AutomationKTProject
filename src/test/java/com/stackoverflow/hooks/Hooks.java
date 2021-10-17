package com.stackoverflow.hooks;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.stackoverflow.seleniumutils.SeleniumUtils;

public class Hooks {

	public static WebDriver driver;
	private final String DRIVERSPATH = "src/test/resources/Drivers";
	private String browserName;


	@Before
	public void getBrowser() throws IOException{
		
		SeleniumUtils utils = new SeleniumUtils();
		browserName = utils.readProperty("browser");

		System.out.println("--------- starting " + browserName + " browser -------------------");

		if (browserName.equals("chrome")) {
			getChromeDriver();

		} else if (browserName.equals("firefox")) {
			getFirefoxDriver();

		}else {
			throw new InvalidArgumentException("Invalid browser option");
		}
	}



	@After
	public void tearDown() {
		System.out.println("--------- quitting " + browserName + " browser -------------------");
		if (driver != null) {
			driver.quit();
		}
	}



	private void getChromeDriver() {
		File chromeFile = new File(DRIVERSPATH, "chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", chromeFile.getPath());
		driver = new ChromeDriver();
	}


	private void getFirefoxDriver() {
		File geckoFile = new File(DRIVERSPATH, "geckodriver.exe");
		System.setProperty("webdriver.gecko.driver", geckoFile.getPath());
		driver = new FirefoxDriver();
	}


}
