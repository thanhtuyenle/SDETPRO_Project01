package testflows.order.computer;

import models.components.cart.AbstractCartComponent;
import models.components.cart.CartComponent;
import models.components.checkout.BillingAddressComponent;
import models.components.product.ComputerEssentialComponent;
import models.pages.CheckOutOptionPage;
import models.pages.CheckOutPage;
import models.pages.ItemDetailsPage;
import models.pages.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.ComputerSpec;

import java.lang.reflect.InvocationTargetException;

public class BuyingComputerFlow<T extends ComputerEssentialComponent> implements ComputerPriceType {

    private final WebDriver driver;
    private T essentialCompGeneric;

    public BuyingComputerFlow(WebDriver driver) {
        this.driver = driver;
    }

    public BuyingComputerFlow<T> withComputerEssentialComp(Class<T> computerType) {
        try {
            essentialCompGeneric = computerType.getConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return this;
    }

    public void buildComputer(ComputerDataObject compData) {
        if (essentialCompGeneric == null) {
            throw new RuntimeException("Please call withComputerType to specify computer type!");
        }
        essentialCompGeneric.unSelectAllDefaultOptions();
        ItemDetailsPage detailsPage = new ItemDetailsPage(driver);

        // Build Comp specs
        essentialCompGeneric.selectProcessorType(compData.getProcessorType());
        essentialCompGeneric.selectRAM(compData.getRam());
        essentialCompGeneric.selectHDD(compData.getHdd());

        // Add To cart
        essentialCompGeneric.clickOnAddToCartBtn();
        try {
            detailsPage.waitUntilItemAddedToCart();
        } catch (Exception e) {
            throw new Error("[ERR] Item is not added after 15s!");
        }
    }

    public void verifyComputerAdded(ComputerDataObject computerDataObject, double startPrice) {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

        // Get additional fee
        double additionalFees = 0.0;
        additionalFees += ComputerSpec.valueOf(computerDataObject.getProcessorType()).additionPrice();
        additionalFees += ComputerSpec.valueOf(computerDataObject.getRam()).additionPrice();
        additionalFees += ComputerSpec.valueOf(computerDataObject.getHdd()).additionPrice();

        // Get Total current price for computer
        double expectedSubTotalPrice = startPrice + additionalFees;
        double subTotalPrice = shoppingCartPage.shoppingCartItemComp().itemTotalPrice();
        Assert.assertEquals(subTotalPrice, expectedSubTotalPrice, "[ERR] Total price is not correct!");

        // Verify item details in cart
        for(CartComponent.CartItemRowData cartItemRowData: shoppingCartPage.shoppingCartItemComp().cartItemRowDataList()) {
            Assert.assertTrue(cartItemRowData.getProductAttributes().contains(ComputerSpec.valueOf(computerDataObject.getProcessorType()).value()),
                    "[ERR] Processor Type info is not in item details");

            Assert.assertTrue(cartItemRowData.getProductAttributes().contains(ComputerSpec.valueOf(computerDataObject.getHdd()).value()),
                    "[ERR] HDD Type info is not in item details");

            Assert.assertEquals(cartItemRowData.getPrice(), expectedSubTotalPrice, "[ERR] Display price is not correct!");

            String productName = cartItemRowData.getProductName();
            String productLink = cartItemRowData.getProductEditLink();
            Assert.assertNotNull(productName, "[ERR] Product name is empty");
            Assert.assertNotNull(productLink, "[ERR] Product link is empty");
        }

        // Print all price list for the items in Cart Footer Component
//        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(subTotal));
//        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(shipping));
//        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(tax));
//        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(total));

        shoppingCartPage.cartFooterComponent().getCartTotalComponent().getTermOfServiceSel().click();
        shoppingCartPage.cartFooterComponent().getCartTotalComponent().getCheckoutSel().click();
    }

    public void checkout() {
        CheckOutOptionPage checkOutOptionPage = new CheckOutOptionPage(driver);
        checkOutOptionPage.asGuestOrRegisteredUserComp().checkoutAsGuestBtn().click();

        CheckOutPage checkOutPage = new CheckOutPage(driver);
        checkOutPage.getBillingAddressComponent().firstName().sendKeys("firstname");
        checkOutPage.getBillingAddressComponent().lastName().sendKeys("lastname");
        checkOutPage.getBillingAddressComponent().email().sendKeys("email@email.com");
        checkOutPage.getBillingAddressComponent().selectCountry("United States");
        checkOutPage.getBillingAddressComponent().selectState("Alabama");
        checkOutPage.getBillingAddressComponent().city().sendKeys("city");
        checkOutPage.getBillingAddressComponent().address1().sendKeys("Address 1");
        checkOutPage.getBillingAddressComponent().zipCode().sendKeys("12345");
        checkOutPage.getBillingAddressComponent().phoneNumber().sendKeys("9999999999");
        checkOutPage.getBillingAddressComponent().continueBtn().click();

        checkOutPage.getShippingAddressComponent().continueBtn().click();
        checkOutPage.getShippingMethodComponent().continueBtn().click();
        checkOutPage.getPaymentMethodComponent().continueBtn().click();
        checkOutPage.getPaymentInformationComponent().continueBtn().click();
        checkOutPage.getConfirmOrderComponent().confirmBtn().click();
        checkOutPage.getConfirmOrderComponent().completedCheckoutBtn().click();

    }
}
