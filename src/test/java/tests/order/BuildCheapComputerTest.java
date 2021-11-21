package tests.order;

import io.qameta.allure.Description;
import models.components.product.CheapComputerEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.UserDataObject;
import testdata.url.URL;
import testflows.order.computer.BuyingComputerFlow;
import testflows.order.computer.ComputerPriceType;
import tests.BaseTest;
import utils.data.ComputerTestDataGenerator;

public class BuildCheapComputerTest extends BaseTest implements ComputerPriceType {

    @Test(dataProvider = "cheapCompsDataSet", description = "Buying a cheap computer")
    @Description(value = "Using a set of utils.data with different computer specs and check total price in cart")
    public void testBuildingCheapComputer(ComputerDataObject computerDataObject) {
        WebDriver driver = getDriver();
        BuyingComputerFlow<CheapComputerEssentialComponent> orderingComputerFlow = new BuyingComputerFlow<>(driver);

        // Go to cheap computer item page
        goTo(URL.CHEAP_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(CheapComputerEssentialComponent.class);
        orderingComputerFlow.buildComputer(computerDataObject);

        // Go to Shopping cart Page
        goTo(URL.CART);
        orderingComputerFlow.verifyComputerAdded(computerDataObject,ComputerPriceType.cheapComputerStartPrice);

        // checkout
        UserDataObject userDataObject = new UserDataObject();
        userDataObject.setFirstName("Tester");
        userDataObject.setLastName("Lee");
        userDataObject.setEmail("test@sdet.com");
        userDataObject.setCountry("United States");
        userDataObject.setState("Alaska");
        userDataObject.setCity("Dallas");
        userDataObject.setAddress1("123 Do Not Send");
        userDataObject.setZipcode("75000");
        userDataObject.setPhone("9999999999");
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
