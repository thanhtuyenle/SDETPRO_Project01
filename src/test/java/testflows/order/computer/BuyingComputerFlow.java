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
import testdata.purchasing.UserDataObject;

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

        //TODO: need to contiue modifying footer component
        // Print all price list for the items in Cart Footer Component
//        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(subTotal));
//        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(shipping));
//        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(tax));
//        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(total));

        shoppingCartPage.cartFooterComponent().getCartTotalComponent().getTermOfServiceSel().click();
        shoppingCartPage.cartFooterComponent().getCartTotalComponent().getCheckoutSel().click();
    }

    public void checkout(UserDataObject userDataObject) {
        CheckOutOptionPage checkOutOptionPage = new CheckOutOptionPage(driver);
        checkOutOptionPage.asGuestOrRegisteredUserComp().checkoutAsGuestBtn().click();

        CheckOutPage checkOutPage = new CheckOutPage(driver);
        checkOutPage.getBillingAddressComponent().firstName().sendKeys(userDataObject.getFirstName());
        checkOutPage.getBillingAddressComponent().lastName().sendKeys(userDataObject.getLastName());
        checkOutPage.getBillingAddressComponent().email().sendKeys(userDataObject.getEmail());
        checkOutPage.getBillingAddressComponent().selectCountry(userDataObject.getCountry());
        checkOutPage.getBillingAddressComponent().selectState(userDataObject.getState());
        checkOutPage.getBillingAddressComponent().city().sendKeys(userDataObject.getCity());
        checkOutPage.getBillingAddressComponent().address1().sendKeys(userDataObject.getAddress1());
        checkOutPage.getBillingAddressComponent().phoneNumber().sendKeys(userDataObject.getPhone());
        checkOutPage.getBillingAddressComponent().zipCode().sendKeys(userDataObject.getZipcode());
        checkOutPage.getBillingAddressComponent().continueBtn().click();

        checkOutPage.getShippingAddressComponent().continueBtn().click();
        checkOutPage.getShippingMethodComponent().continueBtn().click();
        checkOutPage.getPaymentMethodComponent().continueBtn().click();

        checkOutPage.getPaymentInformationComponent().continueBtn().click();
        checkOutPage.getConfirmOrderComponent().confirmBtn().click();

        //Verify Information display in ConfirmOrderConponent
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
        String displayName = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().name().getText();
        String displayFirstName = displayName.split("\\s")[0].trim();
        String displayLastName = displayName.split("\\s")[1].trim();
        Assert.assertEquals(displayFirstName, userDataObject.getFirstName(), "[ERR] ConfirmOrderComponent - First Name is not correct");
        Assert.assertEquals(displayLastName, userDataObject.getLastName(), "[ERR] ConfirmOrderComponent - Last Name is not correct");

        // TODO: Add verification for the rest below..., not only printing...
        String displayEmail = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().email().getText().replace("Email: ", "");
        String displayPhone = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().phone().getText().replace("Phone: ", "");
        String displayAddress1 = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().address1().getText();
        String displayCityStateZip = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().cityStateZip().getText();
        String displayCity = displayCityStateZip.split(",")[0].trim();
        String displayStateZip = displayCityStateZip.split(",")[1].trim();
        String displayState = displayStateZip.split("\\s")[0].trim();
        String dispalyZip = displayStateZip.split("\\s")[1].trim();
        String displayCountry = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().country().getText();
        String displayPaymentMethod = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().paymentMethod().getText();


        Assert.assertEquals(displayEmail, userDataObject.getEmail(), "[ERR] ConfirmOrderComponent - Email is not correct");
        Assert.assertEquals(displayPhone, userDataObject.getPhone(), "[ERR] ConfirmOrderComponent - Phone is not correct");
        Assert.assertEquals(displayAddress1, userDataObject.getAddress1(), "[ERR] Address 1 is not correct");
        Assert.assertEquals(displayCity, userDataObject.getCity(), "[ERR] ConfirmOrderComponent - City is not correct");
        Assert.assertEquals(displayState, userDataObject.getState(), "[ERR] ConfirmOrderComponent - State is not correct");
        Assert.assertEquals(dispalyZip, userDataObject.getZipcode(), "[ERR] ConfirmOrderComponent - Zipcode is not correct");
        Assert.assertEquals(displayCountry, userDataObject.getCountry(), "[ERR] ConfirmOrderComponent - Country is not correct");
        Assert.assertEquals(displayPaymentMethod, "Cash On Delivery (COD)", "[ERR]ConfirmOrderComponent - PaymentMethod is not correct");

        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().email().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().phone().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().fax().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().address1().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().cityStateZip().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().country().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().paymentMethod().getText());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        // TODO: Verify the Shipping Info as well!!

        checkOutPage.getConfirmOrderComponent().completedCheckoutBtn().click();


    }
}
