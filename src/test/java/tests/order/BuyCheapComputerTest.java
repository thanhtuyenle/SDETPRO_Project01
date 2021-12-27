package tests.order;

import io.qameta.allure.Description;
import models.components.product.CheapComputerEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.ComputerType;
import testdata.user.UserDataObject;
import testdata.url.URL;
import testflows.order.computer.BuyingComputerFlow;
import testflows.order.computer.BuyingComputerFlowExtend;
import testflows.order.computer.ComputerPriceType;
import tests.BaseTest;
import utils.data.CommonData;
import utils.data.ComputerTestDataGenerator;

public class BuyCheapComputerTest extends BaseTest implements ComputerPriceType {

    @Test(dataProvider = "cheapCompsDataSet", description = "Buying a cheap computer")
    @Description(value = "Using a set of utils.data with different computer specs and check total price in cart")
    public void testBuildingCheapComputer(ComputerDataObject computerDataObject) {
        WebDriver driver = getDriver();
        BuyingComputerFlowExtend<CheapComputerEssentialComponent> orderingComputerFlow = new BuyingComputerFlowExtend<>(driver);

        // Go to cheap computer item page
        goTo(URL.CHEAP_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(CheapComputerEssentialComponent.class);
        int quantity = 1;
        orderingComputerFlow.buildComputerAndAddToCart(ComputerType.CHEAP_COMPUTER, computerDataObject, 1);

        // Go to Shopping cart Page
        goTo(URL.CART);
        orderingComputerFlow.verifyComputerAdded();
        orderingComputerFlow.agreeAndCheckout();

        // checkout
        UserDataObject userDataObject = CommonData.buildUserDataObject("/src/test/java/testdata/user/DefaultCheckoutUser.json");
        orderingComputerFlow.checkout(userDataObject);
    }

    @DataProvider()
    public ComputerDataObject[] cheapCompsDataSet() {
        ComputerDataObject[] computerTestData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/CheapComputerDataList.json");
        return computerTestData;
    }

}
