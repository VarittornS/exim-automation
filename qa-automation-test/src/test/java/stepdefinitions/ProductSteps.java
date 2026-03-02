package test.java.stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Assert;
import java.util.List;

public class ProductSteps {
    WebDriver driver = LoginSteps.driver;

    @When("I search and add {string} to cart by clicking Next")
    public void searchAndAddWithNext(String productName) throws InterruptedException {
        boolean found = false;
        while (!found) {
            LoginSteps.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shop-item-title")));
            List<WebElement> titles = driver.findElements(By.className("shop-item-title"));
            for (WebElement title : titles) {
                if (title.getText().trim().equalsIgnoreCase(productName)) {
                    WebElement parent = title.findElement(By.xpath("./.."));
                    WebElement addBtn = parent.findElement(By.className("shop-item-button"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                    found = true;
                    break;
                }
            }
            if (!found) {
                // Navigate to next page if item not found
                WebElement nextBtn = driver.findElement(By.xpath("//button[contains(text(), 'Next')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
                Thread.sleep(800);
            }
        }
    }

    @And("I adjust quantity of {string} to {int}")
    public void adjustQuantity(String productName, int quantity) throws InterruptedException {
        String xpath = "//div[@class='cart-row' and .//span[contains(text(), '" + productName + "')]]";
        WebElement productRow = LoginSteps.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        WebElement qtyInput = productRow.findElement(By.className("cart-quantity-input"));
        // Clear existing value and enter new quantity
        qtyInput.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        qtyInput.sendKeys(String.valueOf(quantity), Keys.TAB);
        Thread.sleep(500);
    }

    @And("I validate the Total cost of items in cart")
    public void validateTotalCost() {
        List<WebElement> cartRows = driver.findElements(By.cssSelector(".cart-items .cart-row"));
        double calculatedTotal = 0.0;
        
        // Print calculation details to console once
        System.out.println("\n========== CART CALCULATION LOG ==========");
        for (WebElement row : cartRows) {
            String name = row.findElement(By.className("cart-item-title")).getText();
            double price = Double.parseDouble(row.findElement(By.className("cart-price")).getText().replace("$", ""));
            int qty = Integer.parseInt(row.findElement(By.className("cart-quantity-input")).getAttribute("value"));
            calculatedTotal += (price * qty);
            System.out.println(String.format("Product: %-25s | Price: %7.2f | Qty: %d", name, price, qty));
        }
        
        double uiTotal = Double.parseDouble(driver.findElement(By.className("cart-total-price")).getText().replace("$", ""));
        System.out.println("Calculated Total: $" + calculatedTotal + " | UI Display Total: $" + uiTotal);
        System.out.println("==========================================\n");
        Assert.assertEquals("Cart total mismatch!", calculatedTotal, uiTotal, 0.01);
    }

    @And("I proceed to checkout")
    public void proceed() {
        // Validation call removed here to avoid double logging
        WebElement btn = LoginSteps.wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'PROCEED TO CHECKOUT')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }
}