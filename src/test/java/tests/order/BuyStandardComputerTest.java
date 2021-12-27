package tests.order;

import io.qameta.allure.Description;
import models.components.product.StandardEssentialComponent;
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

public class BuyStandardComputerTest extends BaseTest implements ComputerPriceType {

    @Test(dataProvider = "standardCompsDataSet", description = "Buying a standard computer")
    @Description(value = "Using a set of utils.data with different computer specs and check total price in cart")
    public void testBuildingStandardComputer(ComputerDataObject computerDataObject) {
        WebDriver driver = getDriver();
        BuyingComputerFlowExtend<StandardEssentialComponent> orderingComputerFlow = new BuyingComputerFlowExtend<>(driver);

        // Go to cheap computer item page
        goTo(URL.STANDARD_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(StandardEssentialComponent.class);
        int quantity = 4;
        orderingComputerFlow.buildComputerAndAddToCart(ComputerType.STANDARD_COMPUTER, computerDataObject, quantity);

        // Go to Shopping cart Page
        goTo(URL.CART);
        orderingComputerFlow.verifyComputerAdded();
        orderingComputerFlow.agreeAndCheckout();

        UserDataObject userDataObject = CommonData.buildUserDataObject("/src/test/java/testdata/user/DefaultCheckoutUser.json");
        orderingComputerFlow.checkout(userDataObject);
    }

    @DataProvider()
    public ComputerDataObject[] standardCompsDataSet() {
        ComputerDataObject[] computerTestData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/StandardComputerDataList.json");
        return computerTestData;
    }

}
