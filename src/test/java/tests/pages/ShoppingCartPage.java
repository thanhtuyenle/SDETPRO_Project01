package tests.pages;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import testdata.url.URL;
import testflows.global.FooterFlow;
import tests.BaseTest;

public class ShoppingCartPage extends BaseTest {
    @Test
    public void verifyFooterComponent() {
        WebDriver driver = getDriver();
        goTo(URL.CART);
        FooterFlow footerFlow = new FooterFlow(driver);
        footerFlow.verifyFooterComponent();
    }

}
