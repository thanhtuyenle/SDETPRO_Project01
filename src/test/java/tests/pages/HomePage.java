package tests.pages;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import testdata.url.URL;
import testflows.global.FooterFlow;
import tests.BaseTest;

public class HomePage extends BaseTest {
    @Test
    public void testFooterComponent() {
        WebDriver driver = getDriver();
        goTo(URL.HOMEPAGE);
        FooterFlow footerFlow = new FooterFlow(driver);
        footerFlow.verifyFooterComponent();
    }
}
