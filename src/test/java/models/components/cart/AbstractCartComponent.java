package models.components.cart;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCartComponent {
    private final WebDriver driver;
    private final By itemTotalPriceSel = By.className("product-subtotal");
    private final By cartItemRowSel = By.cssSelector("table[class='cart'] .cart-item-row");
    private final By productPictureSel = By.cssSelector(".product-picture img");
    private final By productNameSel = By.cssSelector(".product-name");
    private final By productAttributesSel = By.cssSelector(".attributes");
    private final By productEditSel = By.cssSelector(".edit-item a");
    private final By productUnitPriceSel = By.cssSelector(".product-unit-price");
    private final By quantityInputSel = By.cssSelector(".qty-input");
    private final By productSubTotalPriceSel = By.cssSelector(".product-subtotal");

    public AbstractCartComponent(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Get current total price")
    public Double itemTotalPrice() {
        String itemTotalPriceStr = driver.findElement(itemTotalPriceSel).getText();
        return Double.parseDouble(itemTotalPriceStr);
    }

    protected abstract By productPriceSel();

    protected abstract boolean isSummaryCartComponent();

    public List<CartItemRowData> cartItemRowDataList() {
        List<CartItemRowData> cartItemRowDataList = new ArrayList<>();
        List<WebElement> cartItemRowElems = driver.findElements(cartItemRowSel);
        for(WebElement cartItemRowElem : cartItemRowElems) {
            String imgSrc = cartItemRowElem.findElement(productPictureSel).getAttribute("src");
            String productName = cartItemRowElem.findElement(productNameSel).getText();
            String productNameLink = cartItemRowElem.findElement(productNameSel).getAttribute("href");

            List<WebElement> productAttributesElems = cartItemRowElem.findElements(productAttributesSel);
            String productAttributes = productAttributesElems.isEmpty() ? null : productAttributesElems.get(0).getText();

            List<WebElement> productEditLinkElems = cartItemRowElem.findElements(productEditSel);
            String productEditLink = productEditLinkElems.isEmpty()? null: productEditLinkElems.get(0).getText();

            double price = Double.parseDouble(cartItemRowElem.findElement(productUnitPriceSel).getText());
            double quanlity = isSummaryCartComponent()
                    ? Double.parseDouble((cartItemRowElem.findElement(quantityInputSel).getText()))
                    : Double.parseDouble(cartItemRowElem.findElement(quantityInputSel).getAttribute("value"));
            double subTotal = Double.parseDouble(cartItemRowElem.findElement(productSubTotalPriceSel).getText());

            CartItemRowData cartItemRowData = new CartItemRowData(imgSrc, productName, productNameLink,
                    productAttributes, productEditLink, price, quanlity, subTotal);
            cartItemRowDataList.add(cartItemRowData);
        }
        return cartItemRowDataList;
    }
    public static class CartItemRowData {
        private final String imgSrc;
        private final String productName;
        private final String productNameLink;
        private final String productAttributes;
        private final String productEditLink;
        private final double price;
        private final double quantity;
        private final double subTotal;

        public CartItemRowData(String imgSrc, String productName, String productNameLink,
                               String productAttributes, String productEditLink,
                               double price, double quantity, double subTotal) {
            this.imgSrc = imgSrc;
            this.productName = productName;
            this.productNameLink = productNameLink;
            this.productAttributes = productAttributes;
            this.productEditLink = productEditLink;
            this.price = price;
            this.quantity = quantity;
            this.subTotal = subTotal;
        }

        public String getImgSrc() {
            return imgSrc;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductNameLink() {
            return productNameLink;
        }

        public String getProductAttributes() {
            return productAttributes;
        }

        public String getProductEditLink() {
            return productEditLink;
        }

        public double getPrice() {
            return price;
        }

        public double getQuantity() {
            return quantity;
        }

        public double getSubTotal() {
            return subTotal;
        }
    }

}
