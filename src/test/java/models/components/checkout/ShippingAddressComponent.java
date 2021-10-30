package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShippingAddressComponent {
    private final WebDriver driver;
    private By continueBtnSel = By.cssSelector(".new-address-next-step-button");

    private final WebElement shippingAddressComp;
    public ShippingAddressComponent(WebDriver driver) {

        this.driver = driver;
        shippingAddressComp = driver.findElement(By.cssSelector("#checkout-step-shipping"));
    }

    public WebElement continueBtn() {
        return shippingAddressComp.findElement(continueBtnSel);
    }
}
