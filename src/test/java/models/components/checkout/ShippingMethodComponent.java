package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShippingMethodComponent {
    private final WebDriver driver;
    private final By groundOptionSel = By.cssSelector("[value='Ground___Shipping.FixedRate']");
    private final By nextDayAirOptionSel = By.cssSelector("[value='Next Day Air___Shipping.FixedRate']");
    private final By secondDayAirSel = By.cssSelector("[value='2nd Day Air___Shipping.FixedRate']");
    private By continueBtnSel = By.cssSelector(".shipping-method-next-step-button");

    private WebElement shippingMethodComp;
    public ShippingMethodComponent(WebDriver driver) {
        this.driver = driver;
        shippingMethodComp = driver.findElement(By.cssSelector("#checkout-step-shipping-method"));
    }

    public WebElement groundOption() {
        return driver.findElement(groundOptionSel);
    }

    public WebElement nextDayAirOption() {
        return driver.findElement(nextDayAirOptionSel);
    }

    public WebElement secondDayAir() {
        return driver.findElement(secondDayAirSel);
    }

    public WebElement continueBtn() {
        return shippingMethodComp.findElement(continueBtnSel);
    }
}
