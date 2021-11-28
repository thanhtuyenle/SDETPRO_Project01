package tests.order;

import io.qameta.allure.Description;
import models.components.product.CheapComputerEssentialComponent;
import models.components.product.StandardEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.url.URL;
import testdata.user.UserDataObject;
import testflows.order.computer.BuyingComputerFlow;
import testflows.order.computer.ComputerPriceType;
import tests.BaseTest;
import utils.data.ComputerTestDataGenerator;

import java.security.SecureRandom;

public class BuildMultipleTypeComputerTest extends BaseTest implements ComputerPriceType{
    @Test(description = "Buying multiple types of computer")
    @Description(value = "Build 2 computers with different types")
    public void testBuildingCheapComputer() {
        WebDriver driver = getDriver();
        BuyingComputerFlow orderingComputerFlow = new BuyingComputerFlow(driver);

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

        // Go to cheap computer item page
        goTo(URL.STANDARD_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(StandardEssentialComponent.class);
        orderingComputerFlow.buildComputerAndAddToCart(standardComputerData);

        //goTo(URL.CART);
        //orderingComputerFlow.verifyComputerAdded(standardComputerData, ComputerPriceType.standardComputerStartPrice);

        // Go to cheap computer item page
        goTo(URL.CHEAP_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(CheapComputerEssentialComponent.class);
        orderingComputerFlow.buildComputerAndAddToCart(cheapComputerData);

        // Go to Shopping cart Page
        goTo(URL.CART);
        //orderingComputerFlow.verifyComputerAdded(cheapComputerData, ComputerPriceType.cheapComputerStartPrice);

        // checkout
//        UserDataObject userDataObject = new UserDataObject();
//        userDataObject.setFirstName("Tester");
//        userDataObject.setLastName("Lee");
//        userDataObject.setEmail("test@sdet.com");
//        userDataObject.setCountry("United States");
//        userDataObject.setState("Alaska");
//        userDataObject.setCity("Dallas");
//        userDataObject.setAddress1("123 Do Not Send");
//        userDataObject.setZipcode("75000");
//        userDataObject.setPhone("9999999999");
//        orderingComputerFlow.checkout(userDataObject);
    }
}
