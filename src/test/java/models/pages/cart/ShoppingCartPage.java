package models.pages.cart;

import models.components.cart.CartComponent;
import models.components.cart.CartFooterComponent;
import org.openqa.selenium.WebDriver;

public class ShoppingCartPage {

    private final WebDriver driver;
    private CartComponent cartComponent;
    private CartFooterComponent cartFooterComponent;

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
    }

    public CartComponent shoppingCartItemComp(){
        if(cartComponent == null)
            cartComponent = new CartComponent(driver);
        return cartComponent;
    }

    public CartFooterComponent cartFooterComponent() {
        if (cartFooterComponent == null)
            cartFooterComponent = new CartFooterComponent(driver);
        return cartFooterComponent;
    }
}
