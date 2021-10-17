package com.stackoverflow.pageobjects;

import org.openqa.selenium.By;

public class HomePage {

	public By hamburgerMenu = By.xpath("//span[@class='ps-relative']");
	public By jobsLink = By.xpath("//div[(@class='flex--item truncate') and (normalize-space(text()) = 'Jobs')]");
	public By questionsLink = By.xpath("//span[(@class='-link--channel-name') and (normalize-space(text()) = 'Questions')]");  

}
