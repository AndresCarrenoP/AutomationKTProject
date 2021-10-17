package com.stackoverflow.pageobjects;

import org.openqa.selenium.By;

public class JobsPage {
	
	public By jobSearchField = By.xpath("//input[@id='q']");
	public By jobSearchButton = By.xpath("//button[normalize-space(text()) = 'Search']");
	public By jobSearchTotalFound = By.xpath("//span[contains(text(),'jobs')]");
	public By jobSrchRsltsTitle = By.xpath("//div[@class='listResults']/div[@data-jobid]//h2/a");
			
}
