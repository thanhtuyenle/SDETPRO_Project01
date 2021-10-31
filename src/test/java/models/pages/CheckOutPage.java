package models.pages;

import models.components.checkout.*;
import org.openqa.selenium.WebDriver;

public class CheckOutPage {
    private final WebDriver driver;
    private BillingAddressComponent billingAddressComponent;
    private ShippingAddressComponent shippingAddressComponent;
    private ShippingMethodComponent shippingMethodComponent;
    private PaymentMethodComponent paymentMethodComponent;
    private PaymentInformationComponent paymentInformationComponent;
    private ConfirmOrderComponent confirmOrderComponent;

    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
    }

    public BillingAddressComponent getBillingAddressComponent() {
        return new BillingAddressComponent(driver);
    }

    public ShippingAddressComponent getShippingAddressComponent() {
        return new ShippingAddressComponent(driver);
    }

    public ShippingMethodComponent getShippingMethodComponent() {
        return new ShippingMethodComponent(driver);
    }

    public PaymentMethodComponent getPaymentMethodComponent() {
        return new PaymentMethodComponent(driver);
    }

    public PaymentInformationComponent getPaymentInformationComponent() {
        return new PaymentInformationComponent(driver);
    }

    public ConfirmOrderComponent getConfirmOrderComponent() {
        return new ConfirmOrderComponent(driver);
    }
}
