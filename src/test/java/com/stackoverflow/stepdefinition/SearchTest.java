package com.stackoverflow.stepdefinition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.stackoverflow.hooks.Hooks;
import com.stackoverflow.pageobjects.HomePage;
import com.stackoverflow.pageobjects.JobsPage;
import com.stackoverflow.pageobjects.QuestionsPage;
import com.stackoverflow.seleniumutils.SeleniumUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchTest {

	WebDriver driver = Hooks.driver;
	ExtentReports report = Hooks.report;
	ExtentTest test = Hooks.test;
	SeleniumUtils utils = new SeleniumUtils();
	HomePage homeP = new HomePage();
	JobsPage jobsP = new JobsPage();
	String searchCriteria;
	QuestionsPage questionsP = new QuestionsPage();
	String qTags;



	@Given("user navigates to home page")
	public void user_navigates_to_home_page() throws Exception {
		String home = utils.readProperty("stackoverflowhome");
		utils.navigateTo(driver, home);
	}

	@Given("user clicks on the hamburger menu")
	public void user_clicks_on_the_hamburger_menu() throws Exception {
		utils.clickElement(driver, homeP.hamburgerMenu);
	}

	@When("user clicks on the Jobs link")
	public void user_clicks_on_the_jobs_link() throws Exception {
		utils.clickElement(driver, homeP.jobsLink);
	}

	@Then("user is navigated to the jobs screen")
	public void user_is_navigated_to_the_jobs_screen() throws Exception {
		String currentURL = driver.getCurrentUrl();
		String expectedURL = utils.readProperty("stackoverflowhome") + "jobs";
		utils.assertLogURL(driver, test, expectedURL, currentURL, utils.getMethodName());
	}

	@When("user enters <searchCriteria>")
	public void user_enters_search_criteria() throws Exception {
		searchCriteria = utils.readProperty("search");
		utils.enterText(driver, jobsP.jobSearchField, searchCriteria);
	}

	@When("user clicks on Search button")
	public void user_clicks_on_search_button() throws Exception {
		utils.clickElement(driver, jobsP.jobSearchButton);
	}

	@Then("system displays jobs related to the search criteria")
	public void system_displays_jobs_related_to_the_search_criteria() throws Exception {

		String totalFoundS = utils.getTotalItemsFound(driver, jobsP.jobSearchTotalFound, " jobs", "", "jobs"); 
		if (totalFoundS.equals("0")) {
			utils.log(driver,test, "FAIL", "No jobs found", utils.getMethodName());
			fail("No jobs found");
		} else {
			boolean isRelated = utils.getJobsTitle(driver, jobsP.jobSrchRsltsTitle);
			if (isRelated == false) {
				utils.log(driver,test, "FAIL", "Some jobs are not related to the search criteria", utils.getMethodName());
				fail("Job is not related to search criteria '" + searchCriteria + "': " + utils.titleValue);
			} else {
				utils.log(driver,test, "PASS", "Jobs found for the search criteria", utils.getMethodName());
			}
		}
		Thread.sleep(2000);
	}



	@When("user clicks on the Questions link")
	public void user_clicks_on_the_questions_link() throws Exception {
		utils.clickElement(driver, homeP.questionsLink);
	}

	@Then("user is navigated to the questions screen")
	public void user_is_navigated_to_the_questions_screen() throws Exception {
		String currentURL = driver.getCurrentUrl();
		String expectedURL = utils.readProperty("stackoverflowhome") + "questions";
		utils.assertLogURL(driver, test, expectedURL, currentURL, utils.getMethodName());
	}

	@When("user clicks on filter button")
	public void user_clicks_on_filter_button() throws Exception {
		utils.clickElement(driver, questionsP.questionFilterButton);
	}

	@When("user selects Filter by as No answers")
	public void user_selects_filter_by_as_no_answers() throws Exception {
		utils.clickElement(driver, questionsP.questionNoAnswersCheckBox);
	}

	@When("user selects Sorted by as Recent activity")
	public void user_selects_sorted_by_as_recent_activity() throws Exception {
		utils.clickElement(driver, questionsP.questionRecActRadioButton);
	}

	@When("user selects Tagged with The following tags:")
	public void user_selects_tagged_with_the_following_tags() throws Exception {
		utils.clickElement(driver, questionsP.questionFollTagsRadioButton);
	}

	@When("user enters <tags>")
	public void user_enters_tags() throws Exception {
		qTags = utils.readProperty("tags");
		utils.enterText(driver, questionsP.questionTagsField, qTags);
	}

	@When("user clicks on Apply filter button")
	public void user_clicks_on_apply_filter_button() throws Exception {
		utils.clickElement(driver, questionsP.questionApplyFilterButton);
	}

	@Then("system displays questions based on the selection")
	public void system_displays_questions_based_on_the_selection() throws Exception {

		String totalFoundS = utils.getTotalItemsFound(driver, questionsP.questionFilterTotalFound, " questions with no answers", "", "questions"); 
		if (totalFoundS.equals("0")) {
			utils.log(driver,test, "FAIL", "No questions found", utils.getMethodName());
			fail("No questions found");
		} else {
			boolean containsTag = utils.getQuestionsTags (driver, questionsP.questionFilterResults, questionsP.questionFltrRsltsTitle, questionsP.questionFltrRsltsTags);
			if (containsTag == false) {
				utils.log(driver,test, "FAIL", "some questions do not contain the search tag", utils.getMethodName());
				fail("Question does not contain search tag '" + qTags + "': " + utils.titleText);
			} else {
				utils.log(driver,test, "PASS", "Questions found for the search tag", utils.getMethodName());
			}
		}
		Thread.sleep(2000);
	}

}
