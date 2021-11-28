package models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutCompletedPage {

    private final WebDriver driver;
    private final By pageTitleSel = By.cssSelector(".page-title");
    private CheckoutDataComponent checkoutDataComponent;

    public CheckoutCompletedPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.urlContains("/checkout/completed/"));
    }

    public WebElement pageTitle() {
        return driver.findElement(pageTitleSel);
    }

    public CheckoutDataComponent getCheckoutDataComponent() {
        return new CheckoutDataComponent(driver);
    }

    public static class CheckoutDataComponent{
        private final WebDriver driver;
        private By titleSel = By.cssSelector(".title");
        private By detailsSel = By.cssSelector(".details li");
        private By pageBodySel = By.cssSelector(".checkout-data");
        private By continueBtnSel = By.cssSelector(".order-completed-continue-button");
        public WebElement checkOutData;

        public CheckoutDataComponent(WebDriver driver) {
            this.driver = driver;
            checkOutData = driver.findElement(pageBodySel);
        }

        public WebElement title() {
            return checkOutData.findElement(titleSel);
        }

        public WebElement continueBtn() {
            return checkOutData.findElement(continueBtnSel);
        }
        public String orderTextId() {
            return checkOutData.findElements(detailsSel).get(0).getText().split(":")[1].trim();
        }

        public String orderDetailsLink() {
            return checkOutData.findElements(detailsSel).get(1).findElement(By.tagName("a")).getAttribute("href");
        }
    }
}
