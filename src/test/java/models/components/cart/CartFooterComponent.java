package models.components.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFooterComponent {
    private final WebDriver driver;
    private CartShippingComponent cartShippingComponent;
    private CartTotalComponent cartTotalComponent;

    public CartFooterComponent(WebDriver driver) {
        this.driver = driver;
    }

    public CartShippingComponent getCartShippingComponent() {
        return new CartShippingComponent(driver);
    }

    public CartTotalComponent getCartTotalComponent() {
        return new CartTotalComponent(driver);
    }

    //inner class
    public static class CartShippingComponent {

        private final WebDriver driver;
        private final By countrySel = By.cssSelector("#CountryId");
        private final By stateSel = By.cssSelector("#StateProvinceId");
        private final By zipCodeSel = By.cssSelector("#ZipPostalCode");

        public CartShippingComponent(WebDriver driver) {
            this.driver = driver;
        }

        public WebElement getCountrySel() {
            return driver.findElement(countrySel);
        }

        public WebElement getStateSel() {
            return driver.findElement(stateSel);
        }

        public WebElement getZipCodeSel() {
            return driver.findElement(zipCodeSel);
        }
    }

    public static class CartTotalComponent {
        private final WebDriver driver;
        private final By priceTableRowsSel = By.cssSelector("table[class='cart-total']");

        private final By termOfServiceSel = By.cssSelector("#termsofservice");
        private final By checkOutSel = By.cssSelector("#checkout");

        public CartTotalComponent(WebDriver driver) {
            this.driver = driver;
        }

        public Map<String, Double> getPriceMap() {
            Map<String, Double> priceMap = new HashMap<>();
            List<WebElement> priceTableRowsElems = getPriceTableRowsSel();
            for (WebElement priceRowElem : priceTableRowsElems) {
                WebElement priceTypeElem = priceRowElem.findElement(By.cssSelector(".cart-total-left"));
                String priceTypeText = priceTypeElem.getText().replace(":", "").trim();

                WebElement priceValueElem = priceRowElem.findElement(By.cssSelector(".cart-total-right"));
                Double priceValue = Double.parseDouble(priceValueElem.getText());

                priceMap.put(priceTypeText, priceValue);
            }
            return priceMap;

        }

        public List<WebElement> getPriceTableRowsSel() {
            return driver.findElements(priceTableRowsSel);
        }

        public WebElement getTermOfServiceSel() {
            return driver.findElement(termOfServiceSel);
        }

        public WebElement getCheckoutSel() {
            return driver.findElement(checkOutSel);
        }
    }
}
