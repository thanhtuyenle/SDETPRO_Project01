package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentMethodComponent {
    private final WebDriver driver;
    private By cashOnDeliveryOptionSel = By.cssSelector("[value='Payments.CashOnDelivery']");
    private By checkMoneyOrderOptionSel = By.cssSelector("[value='Payments.CheckMoneyOrder']");
    private By creditCardOptionSel = By.cssSelector("[value='Payments.Manual']");
    private By purchaseOrderOptionSel = By.cssSelector("[value='Payments.PurchaseOrder']");
    private By continueBtnSel = By.cssSelector(".payment-method-next-step-button");

    public PaymentMethodComponent(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement cashOnDeliveryOption() {
        return driver.findElement(cashOnDeliveryOptionSel);
    }

    public WebElement checkMoneyOrderOption() {
        return driver.findElement(checkMoneyOrderOptionSel);
    }

    public WebElement creditCardOption() {
        return driver.findElement(creditCardOptionSel);
    }

    public WebElement purchaseOrderOption() {
        return driver.findElement(purchaseOrderOptionSel);
    }

    public WebElement continueBtn() {
        return driver.findElement(continueBtnSel);
    }
}
