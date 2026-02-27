package test.java.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.junit.Assert;
import java.util.List;
import java.util.Map;

public class CheckoutSteps {
    WebDriver driver = LoginSteps.driver;

    @Then("I validate the following shipping scenarios:")
    public void validateMultipleShippingCases(DataTable dataTable) throws InterruptedException {
        List<Map<String, String>> scenarios = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> data : scenarios) {
            // Fill shipping details from DataTable
            WebElement phone = LoginSteps.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
            phone.clear(); phone.sendKeys(data.get("phone") == null ? "" : data.get("phone"));
            
            WebElement street = driver.findElement(By.name("street"));
            street.clear(); street.sendKeys(data.get("street") == null ? "" : data.get("street"));
            
            WebElement city = driver.findElement(By.name("city"));
            city.clear(); city.sendKeys(data.get("city") == null ? "" : data.get("city"));

            Select country = new Select(driver.findElement(By.id("countries_dropdown_menu")));
            if (data.get("country") != null && !data.get("country").isEmpty()) 
                country.selectByVisibleText(data.get("country"));
            else country.selectByIndex(0);

            // Submit form
            driver.findElement(By.id("submitOrderBtn")).click();
            Thread.sleep(600); // Wait for tooltip or page transition

            if (data.get("result").equalsIgnoreCase("SUCCESS")) {
                // Step 4: Validate "Street, City - Country" format
                WebElement body = LoginSteps.wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
                String actualText = body.getText();
                String expectedAddress = data.get("street") + ", " + data.get("city") + " - " + data.get("country");
                
                Assert.assertTrue("Success message not found!", actualText.contains("Congrats!"));
                Assert.assertTrue("Address format mismatch! Expected: " + expectedAddress, actualText.contains(expectedAddress));
                System.out.println(">>> SUCCESS Case: Address concatenation validated correctly.");
            } else {
                // Step 3 Validation: Check for HTML5 Tooltip or Red Text for Country
                JavascriptExecutor js = (JavascriptExecutor) driver;
                boolean isFormValid = (Boolean) js.executeScript("return document.querySelector('form').checkValidity();");
                String color = driver.findElement(By.id("countries_dropdown_menu")).getCssValue("color");

                // Check if either the tooltip is shown (!isFormValid) OR the country text is red
                // Using .contains("255, 0, 0") fixes the mismatch crash in image_357498.png
                boolean isBlocked = !isFormValid || color.contains("255, 0, 0");
                Assert.assertTrue("The system should block incomplete data!", isBlocked);
                System.out.println(">>> FAIL Case: Blocked by " + (isFormValid ? "Red Text" : "Tooltip"));
            }
        }
    }
}