package test.java.testrunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "test.java.stepdefinitions",
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    tags = "@UI or @API"

)
public class TestRunner {
}