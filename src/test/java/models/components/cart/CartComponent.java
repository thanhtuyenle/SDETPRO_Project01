package models.components.cart;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartComponent extends AbstractCartComponent{

    public CartComponent(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By productPriceSel() {
        return By.className("qty-input");
    }

    @Override
    protected boolean isSummaryCartComponent() {
        return false;
    }
}
