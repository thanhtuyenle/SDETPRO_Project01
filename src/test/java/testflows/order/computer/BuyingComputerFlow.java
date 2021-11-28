package testflows.order.computer;

import models.components.cart.AbstractCartComponent;
import models.components.cart.CartComponent;
import models.components.checkout.BillingAddressComponent;
import models.components.checkout.ShippingAddressComponent;
import models.components.product.ComputerEssentialComponent;
import models.pages.CheckOutOptionPage;
import models.pages.CheckOutPage;
import models.pages.CheckoutCompletedPage;
import models.pages.ItemDetailsPage;
import models.pages.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.ComputerSpec;
import testdata.purchasing.UserDataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

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
        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(subTotal));
        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(shipping));
        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(tax));
        System.out.println(shoppingCartPage.cartFooterComponent().getCartTotalComponent().getPriceMap().get(total));

        shoppingCartPage.cartFooterComponent().getCartTotalComponent().getTermOfServiceSel().click();
        shoppingCartPage.cartFooterComponent().getCartTotalComponent().getCheckoutSel().click();
    }

    public void checkout(UserDataObject userDataObject) {
        CheckOutOptionPage checkOutOptionPage = new CheckOutOptionPage(driver);
        checkOutOptionPage.asGuestOrRegisteredUserComp().checkoutAsGuestBtn().click();

        CheckOutPage checkOutPage = new CheckOutPage(driver);
        BillingAddressComponent billingAddressComponent = checkOutPage.getBillingAddressComponent();
        billingAddressComponent.firstName().sendKeys(userDataObject.getFirstName());
        billingAddressComponent.lastName().sendKeys(userDataObject.getLastName());
        billingAddressComponent.email().sendKeys(userDataObject.getEmail());
        billingAddressComponent.selectCountry(userDataObject.getCountry());
        billingAddressComponent.selectState(userDataObject.getState());
        billingAddressComponent.city().sendKeys(userDataObject.getCity());
        billingAddressComponent.address1().sendKeys(userDataObject.getAddress1());
        billingAddressComponent.phoneNumber().sendKeys(userDataObject.getPhone());
        billingAddressComponent.zipCode().sendKeys(userDataObject.getZipcode());
        billingAddressComponent.continueBtn().click();

        //ShippingAddressComponent shippingAddressComponent = checkOutPage.getShippingAddressComponent();
//        shippingAddressComponent.firstName().sendKeys(userDataObject.getFirstName());
//        shippingAddressComponent.lastName().sendKeys(userDataObject.getLastName());
//        shippingAddressComponent.email().sendKeys(userDataObject.getEmail());
//        shippingAddressComponent.selectCountry(userDataObject.getCountry());
//        shippingAddressComponent.selectState(userDataObject.getState());
//        shippingAddressComponent.city().sendKeys(userDataObject.getCity());
//        shippingAddressComponent.address1().sendKeys(userDataObject.getAddress1());
//        shippingAddressComponent.zipCode().sendKeys(userDataObject.getZipcode());
//        shippingAddressComponent.phoneNumber().sendKeys(userDataObject.getPhone());
        checkOutPage.getShippingAddressComponent().continueBtn().click();

        checkOutPage.getShippingMethodComponent().continueBtn().click();
        checkOutPage.getPaymentMethodComponent().continueBtn().click();
        checkOutPage.getPaymentInformationComponent().continueBtn().click();


        //Verify Information display in ConfirmOrderConponent - Billing Address
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
        String displayBillingName = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().name().getText();
        String displayBillingFirstName = displayBillingName.split("\\s")[0].trim();
        String displayBillingLastName = displayBillingName.split("\\s")[1].trim();
        Assert.assertEquals(displayBillingFirstName, userDataObject.getFirstName(), "[ERR] ConfirmOrderComponent - First Name is not correct");
        Assert.assertEquals(displayBillingLastName, userDataObject.getLastName(), "[ERR] ConfirmOrderComponent - Last Name is not correct");

        String displayBillingEmail = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().email().getText().replace("Email: ", "");
        String displayBillingPhone = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().phone().getText().replace("Phone: ", "");
        String displayBillingAddress1 = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().address1().getText();
        String displayBillingCityStateZip = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().cityStateZip().getText();
        String displayBillingCity = displayBillingCityStateZip.split(",")[0].trim();
        String displayBillingStateZip = displayBillingCityStateZip.split(",")[1].trim();
        String displayBillingState = displayBillingStateZip.split("\\s")[0].trim();
        String dispalyBillingZip = displayBillingStateZip.split("\\s")[1].trim();
        String displayBillingCountry = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().country().getText();
        String displayBillingPaymentMethod = checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().paymentMethod().getText();


        Assert.assertEquals(displayBillingEmail, userDataObject.getEmail(), "[ERR] ConfirmOrderComponent - Email is not correct");
        Assert.assertEquals(displayBillingPhone, userDataObject.getPhone(), "[ERR] ConfirmOrderComponent - Phone is not correct");
        Assert.assertEquals(displayBillingAddress1, userDataObject.getAddress1(), "[ERR] Address 1 is not correct");
        Assert.assertEquals(displayBillingCity, userDataObject.getCity(), "[ERR] ConfirmOrderComponent - City is not correct");
        Assert.assertEquals(displayBillingState, userDataObject.getState(), "[ERR] ConfirmOrderComponent - State is not correct");
        Assert.assertEquals(dispalyBillingZip, userDataObject.getZipcode(), "[ERR] ConfirmOrderComponent - Zipcode is not correct");
        Assert.assertEquals(displayBillingCountry, userDataObject.getCountry(), "[ERR] ConfirmOrderComponent - Country is not correct");
        //TODO: should not hard code here
        Assert.assertEquals(displayBillingPaymentMethod, "Cash On Delivery (COD)", "[ERR]ConfirmOrderComponent - PaymentMethod is not correct");

        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().name().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().email().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().phone().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().fax().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().address1().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().cityStateZip().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().country().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getBillingAddressComponent().paymentMethod().getText());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        //Verify Information display in ConfirmOrderConponent - Shipping Address
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
        String displayShippingName = checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().name().getText();
        String displayShippingFirstName = displayShippingName.split("\\s")[0].trim();
        String displayShippingLastName = displayShippingName.split("\\s")[1].trim();
        Assert.assertEquals(displayShippingFirstName, userDataObject.getFirstName(), "[ERR] ConfirmOrderComponent - First Name is not correct");
        Assert.assertEquals(displayShippingLastName, userDataObject.getLastName(), "[ERR] ConfirmOrderComponent - Last Name is not correct");

        String displayShippingEmail = checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().email().getText().replace("Email: ", "");
        String displayShippingPhone = checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().phone().getText().replace("Phone: ", "");
        String displayShippingAddress1 = checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().address1().getText();
        String displayShippingCityStateZip = checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().cityStateZip().getText();
        String displayShippingCity = displayShippingCityStateZip.split(",")[0].trim();
        String displayShippingStateZip = displayShippingCityStateZip.split(",")[1].trim();
        String displayShippingState = displayShippingStateZip.split("\\s")[0].trim();
        String dispalyShippingZip = displayShippingStateZip.split("\\s")[1].trim();
        String displayShippingCountry = checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().country().getText();
        String displayShippingMethod = checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().shippingMethod().getText();


        Assert.assertEquals(displayShippingEmail, userDataObject.getEmail(), "[ERR] ConfirmOrderComponent - Email is not correct");
        Assert.assertEquals(displayShippingPhone, userDataObject.getPhone(), "[ERR] ConfirmOrderComponent - Phone is not correct");
        Assert.assertEquals(displayShippingAddress1, userDataObject.getAddress1(), "[ERR] Address 1 is not correct");
        Assert.assertEquals(displayShippingCity, userDataObject.getCity(), "[ERR] ConfirmOrderComponent - City is not correct");
        Assert.assertEquals(displayShippingState, userDataObject.getState(), "[ERR] ConfirmOrderComponent - State is not correct");
        Assert.assertEquals(dispalyShippingZip, userDataObject.getZipcode(), "[ERR] ConfirmOrderComponent - Zipcode is not correct");
        Assert.assertEquals(displayShippingCountry, userDataObject.getCountry(), "[ERR] ConfirmOrderComponent - Country is not correct");
        //TODO: should not hard code here
        Assert.assertEquals(displayShippingMethod, "Ground", "[ERR]ConfirmOrderComponent - ShippingMethod is not correct");

        System.out.println(checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().name().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().email().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().phone().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().fax().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().address1().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().cityStateZip().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().country().getText());
        System.out.println(checkOutPage.getConfirmOrderComponent().getShippingAddressComponent().shippingMethod().getText());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");


        //TODO : need to add verification points
        List<CartComponent.CartItemRowData> cartItemRowDataList = checkOutPage.getConfirmOrderComponent().getSummaryCartComponent().cartItemRowDataList();
        Double sumSubTotalPrice = 0.0;
        Double itemSubTotalPrice = 0.0;
        for(CartComponent.CartItemRowData cartItemDataReturn: cartItemRowDataList) {
            System.out.println(cartItemDataReturn.getImgSrc());
            System.out.println(cartItemDataReturn.getPrice());
            itemSubTotalPrice += cartItemDataReturn.getSubTotal();
            Assert.assertNotNull(cartItemDataReturn.getImgSrc());
        }

        Map<String, Double> priceMap = checkOutPage.getConfirmOrderComponent().getCartFooterComponent().getCartTotalComponent().getPriceMap();
        System.out.println(">>>>>>>>>>>>> CartFooterComponent: ");
        Double displayTotal = 0.0;
        for(String key: priceMap.keySet()) {
            System.out.println( key + ": " + priceMap.get(key));
            if(key.contains("Sub-Total"))
                Assert.assertEquals(itemSubTotalPrice, priceMap.get(key), "Sub-Total is not expected");
        }
        System.out.println("<<<<<<<<<<<<<<<");
        //Confirm Order
        checkOutPage.getConfirmOrderComponent().confirmBtn().click();

        //Checkout Completed page
        CheckoutCompletedPage checkoutCompletedPage = new CheckoutCompletedPage(driver);
        Assert.assertEquals(checkoutCompletedPage.pageTitle().getText(),"Thank you", "[ERR]: Checkout completed title is not correct");
        String orderId = checkoutCompletedPage.getCheckoutDataComponent().orderTextId();
        String orderDetailsLink = checkoutCompletedPage.getCheckoutDataComponent().orderDetailsLink();
        boolean isValidOrderDetailsLink = orderDetailsLink.endsWith(orderId);
        Assert.assertTrue(isValidOrderDetailsLink, "[ERR]: Order Details link is not correct");
        checkoutCompletedPage.getCheckoutDataComponent().continueBtn().click();


    }
}
