package com.stackoverflow.pageobjects;

import org.openqa.selenium.By;

public class QuestionsPage {
	
	public By questionFilterButton = By.xpath("//*[@class='svg-icon iconFilter']/parent::*");
	public By questionNoAnswersCheckBox = By.xpath("//input[(@type='checkbox') and (@value='NoAnswers')]");
	public By questionRecActRadioButton = By.xpath("//input[(@type='radio') and (@value='RecentActivity')]");
	public By questionFollTagsRadioButton = By.xpath("//input[(@type='radio') and (@value='Specified')]");	
	public By questionTagsField = By.xpath("//input[(@type='text') and (@class='s-input js-tageditor-replacing')]");
	public By questionApplyFilterButton = By.xpath("//button[(@type='submit') and (normalize-space(text()) = 'Apply filter')]");
	public By questionFilterTotalFound = By.xpath("//div[contains(text(),'questions with no answers')]");
	public By questionFilterResults = By.xpath("//div[@id='questions']/child::*");
	public By questionFltrRsltsTitle = By.xpath(".//h3/a");
	public By questionFltrRsltsTags = By.xpath(".//div[@class='summary']//a[@rel='tag']");
	
}
