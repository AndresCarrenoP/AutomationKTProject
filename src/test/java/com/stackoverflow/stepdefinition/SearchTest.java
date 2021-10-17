package com.stackoverflow.stepdefinition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

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
	public void user_is_navigated_to_the_jobs_screen() throws IOException {
		String currentURL = driver.getCurrentUrl();
		String expectedURL = utils.readProperty("stackoverflowhome") + "jobs";
		assertEquals(expectedURL, currentURL);
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

		String totalFoundS = utils.getTotalItemsFound(driver, jobsP.jobSearchTotalFound, " Jobs", "", "jobs"); 
		if (totalFoundS.equals("0")) {
			fail("No jobs found");
		} else {
			boolean isRelated = utils.getJobsTitle(driver, jobsP.jobSrchRsltsTitle);
			if (isRelated == false) {
				fail("Job is not related to search criteria '" + searchCriteria + "': " + utils.titleValue);
			}
		}
		Thread.sleep(2000);
	}



	@When("user clicks on the Questions link")
	public void user_clicks_on_the_questions_link() throws Exception {
		utils.clickElement(driver, homeP.questionsLink);
	}

	@Then("user is navigated to the questions screen")
	public void user_is_navigated_to_the_questions_screen() throws IOException {
		String currentURL = driver.getCurrentUrl();
		String expectedURL = utils.readProperty("stackoverflowhome") + "questions";
		assertEquals(expectedURL, currentURL);
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
			fail("No questions found");
		} else {
			boolean containsTag = utils.getQuestionsTags (driver, questionsP.questionFilterResults, questionsP.questionFltrRsltsTitle, questionsP.questionFltrRsltsTags);
			if (containsTag == false) {
				fail("Question does not contain search tag '" + qTags + "': " + utils.titleText);
			}
		}
		Thread.sleep(2000);
	}

}
