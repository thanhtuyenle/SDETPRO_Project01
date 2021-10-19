package models.pages.cart;

import models.components.cart.CartComponent;
import models.components.cart.CartFooterComponent;
import org.openqa.selenium.WebDriver;

public class ShoppingCartPage {

    private final WebDriver driver;

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
    }

    public CartComponent shoppingCartItemComp(){
        return new CartComponent(driver);
    }

    public CartFooterComponent cartFooterComponent() {return new CartFooterComponent(driver);}
}
