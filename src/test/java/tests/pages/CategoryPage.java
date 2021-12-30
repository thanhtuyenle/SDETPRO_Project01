package tests.pages;

import models.components.global.HeaderMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import testdata.url.URL;
import testflows.global.FooterFlow;
import tests.BaseTest;

import java.security.SecureRandom;
import java.util.List;

public class CategoryPage extends BaseTest {
    @Test
    public void TestFooterComponent() {
        WebDriver driver = getDriver();
        goTo(URL.HOMEPAGE);

        HeaderMenuComponent headerMenuComponent = new HeaderMenuComponent(driver);
        List<WebElement> topMenuHeaderLinks = headerMenuComponent.topMenuHeaderLinks();
        WebElement randomHeader = topMenuHeaderLinks.get(new SecureRandom().nextInt(topMenuHeaderLinks.size()));
        String categoryName = randomHeader.getText();
        randomHeader.click();
        headerMenuComponent.waitUntilCategoryPageLoad(categoryName);

        FooterFlow footerFlow = new FooterFlow(driver);
        footerFlow.verifyFooterComponent();
    }
}
