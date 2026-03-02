package test.java.stepdefinitions;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.junit.Assert;
import java.time.Duration;

public class LoginSteps {
    // Shared WebDriver and WebDriverWait instances for cross-class access
    public static WebDriver driver;
    public static WebDriverWait wait;

    @Given("I open the e-commerce page")
    public void openPage() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--proxy-server='direct://'");
            options.addArguments("--proxy-bypass-list=*");

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://qa-practice.razvanvancea.ro/auth_ecommerce.html");
    }

    @When("I login with {string} and {string}")
    public void login(String user, String pass) {
        // Wait until email input is ready and fill it
        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        email.clear();
        email.sendKeys(user);
        
        // Find and fill the password field
        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys(pass);

        // Locate the login button and click it via JavaScript to prevent potential UI freezing
        WebElement loginBtn = driver.findElement(By.id("submitLoginBtn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedMsg) {
        // Wait for the error message element to become visible
        WebElement errorElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        // Assert that the displayed text contains the expected error message
        Assert.assertTrue("Error message mismatch!", errorElem.getText().contains(expectedMsg));
    }

    @Then("I should see the logout button")
    public void verifyLogoutVisible() {
        // Verify successful login by locating the Log Out button with ID "logout"
        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
        Assert.assertTrue("Login failed: Logout button is not displayed", logoutBtn.isDisplayed());
    }
}