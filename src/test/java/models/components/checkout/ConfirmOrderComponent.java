package models.components.checkout;

import models.components.cart.CartComponent;
import models.components.cart.CartFooterComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmOrderComponent {
    private final WebDriver driver;
    private final By confirmBtnSel = By.cssSelector(".confirm-order-next-step-button");

    private final By completedCheckoutBtnSel = By.cssSelector(".order-completed-continue-button");
    private BillingAddressComponent billingAddressComponent;
    private ShippingAddressComponent shippingAddressComponent;
    private CartComponent cartComponent;
    private CartFooterComponent cartFooterComponent;

    public ConfirmOrderComponent(WebDriver driver) {
        this.driver = driver;
    }

    public BillingAddressComponent getBillingAddressComponent() {
        return new BillingAddressComponent(driver);
    }

    public ShippingAddressComponent getShippingAddressComponent() {
        return new ShippingAddressComponent(driver);
    }

    public CartComponent getCartComponent() {
        return new CartComponent(driver);
    }

    public CartFooterComponent getCartFooterComponent() {
        return new CartFooterComponent(driver);
    }

    public WebElement confirmBtn() {
        return driver.findElement(confirmBtnSel);
    }

    public WebElement completedCheckoutBtn() {
        return driver.findElement(completedCheckoutBtnSel   );
    }

    //inner class
    public abstract class InfoComponent {
        private final WebDriver driver;
        private final By nameSel = By.cssSelector(".name");
        private final By emailSel = By.cssSelector(".email");
        private final By phoneSel = By.cssSelector(".phone");
        private final By faxSel = By.cssSelector(".fax");
        private final By address1Sel = By.cssSelector(".address1");
        private final By citySel = By.cssSelector(".city-state-zip");
        private final By countrySel = By.cssSelector(".country");

        protected WebElement component;

        abstract By componentSel();
        protected InfoComponent(WebDriver driver) {
            this.driver = driver;
            this.component = driver.findElement(componentSel());
        }

        public WebElement name() {
            return component.findElement(nameSel);
        }

        public WebElement email() {
            return component.findElement(emailSel);
        }

        public WebElement phone() {
            return component.findElement(phoneSel);
        }

        public WebElement fax() {
            return component.findElement(faxSel);
        }

        public WebElement address1() {
            return component.findElement(address1Sel);
        }

        public WebElement city() {
            return component.findElement(citySel);
        }

        public WebElement country() {
            return component.findElement(countrySel);
        }
    }

    public class BillingAddressComponent extends InfoComponent{
        private final By paymentMethodSel = By.cssSelector(".payment-method");

        @Override
        By componentSel() {
            return By.cssSelector(".billing-info");
        }

        protected BillingAddressComponent(WebDriver driver) {
            super(driver);
        }

        public WebElement paymentMethod() {
            return component.findElement(paymentMethodSel);
        }
    }

    public class ShippingAddressComponent extends InfoComponent{
        private final By shippingMethodSel = By.cssSelector(".shipping-method");

        @Override
        By componentSel() {
            return By.cssSelector(".shipping-info");
        }

        public ShippingAddressComponent(WebDriver driver) {
            super(driver);
        }

        public WebElement shippingMethod() {
            return driver.findElement(shippingMethodSel);
        }
    }
}
