package com.stackoverflow.testrunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/FeatureFiles"},
		glue = {"com.stackoverflow.stepdefinition", "com.stackoverflow.hooks"},
		monochrome = true,
		tags = "@Search",
		dryRun = false,
		plugin = {"json:target/cucumber.json"}
		)


public class TestRunner {

}
