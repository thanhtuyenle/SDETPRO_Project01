package tests.order;

import io.qameta.allure.Description;
import models.components.product.CheapComputerEssentialComponent;
import models.components.product.StandardEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.ComputerType;
import testdata.url.URL;
import testdata.user.UserDataObject;
import testflows.order.computer.BuyingComputerFlow;
import testflows.order.computer.BuyingComputerFlowExtend;
import testflows.order.computer.ComputerPriceType;
import tests.BaseTest;
import utils.data.CommonData;
import utils.data.ComputerTestDataGenerator;

import java.security.SecureRandom;

public class BuyMultipleTypeComputerTest extends BaseTest implements ComputerPriceType{
    @Test(description = "Buying multiple types of computer")
    @Description(value = "Build 2 computers with different types")
    public void testBuildingCheapComputer() {
        WebDriver driver = getDriver();
        BuyingComputerFlowExtend orderingComputerFlow = new BuyingComputerFlowExtend(driver);

        //Get list standard computer from json
        ComputerDataObject[] standardComputerTestData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/StandardComputerDataList.json");

        //get a standard computer randomly
        ComputerDataObject standardComputerData = standardComputerTestData[new SecureRandom().nextInt(standardComputerTestData.length)];

        //Get list cheap computer from json
        ComputerDataObject[] cheapComputerTestData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/CheapComputerDataList.json");

        //Get a cheap computer randomly
        ComputerDataObject cheapComputerData = cheapComputerTestData[new SecureRandom().nextInt(cheapComputerTestData.length)];

        // Go to standard computer item page
        goTo(URL.STANDARD_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(StandardEssentialComponent.class);
        int standardComputerQuantity = 3;
        orderingComputerFlow.buildComputerAndAddToCart(ComputerType.STANDARD_COMPUTER, standardComputerData, standardComputerQuantity);

        // Go to cheap computer item page
        goTo(URL.CHEAP_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(CheapComputerEssentialComponent.class);
        int cheapComputerQuantity = 2;
        orderingComputerFlow.buildComputerAndAddToCart(ComputerType.CHEAP_COMPUTER, cheapComputerData, cheapComputerQuantity);

        // Go to Shopping cart Page
        goTo(URL.CART);
        orderingComputerFlow.verifyComputerAdded();
        orderingComputerFlow.agreeAndCheckout();

        UserDataObject userDataObject = CommonData.buildUserDataObject("/src/test/java/testdata/user/DefaultCheckoutUser.json");
        orderingComputerFlow.checkout(userDataObject);
    }
}
