package testflows.global;

import models.components.global.FooterComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class FooterFlow {
    private final WebDriver driver;
    private FooterComponent footerComponent;
    public FooterFlow(WebDriver driver) {
        this.driver = driver;
        footerComponent = new FooterComponent(driver);
    }

    public void verifyFooterComponent() {
        verifyFooterInformationColumn();
        verifyCustomerServiceColumn();
        verifyMyAccountColumn();
        verifyFollowUsColumn();
    }

    private void verifyFooterInformationColumn() {
        boolean isColumnName = footerComponent.menuInformationComponent().columnTitle().equalsIgnoreCase("Information");
        Assert.assertTrue(isColumnName, "Information Title is not correct");

        String baseUrl = System.getProperty("baseUrl") != null? System.getProperty("baseUrl"): System.getenv("baseUrl");
        List<WebElement> actualLinks = footerComponent.menuInformationComponent().getLinks();

        List<String> expectedLinks = Arrays.asList(
                baseUrl.concat("/sitemap"),
                baseUrl.concat("/shipping-returns"),
                baseUrl.concat("/privacy-policy"),
                baseUrl.concat("/conditions-of-use"),
                baseUrl.concat("/about-us"),
                baseUrl.concat("/contactus")
        );

        actualLinks.forEach(link -> {
            String infoText = link.getText();
            String infoHref = link.getAttribute("href");
            Assert.assertNotNull(infoText, "Text is not null");
            Assert.assertTrue(expectedLinks.contains(infoHref), "Href link is not correct");
        });
    }

    private void verifyCustomerServiceColumn() {
        boolean isColumnName = footerComponent.menuCustomerServiceComponent().columnTitle().equalsIgnoreCase("Customer service");
        Assert.assertTrue(isColumnName, "Customer Service Title is not correct");

        String baseUrl = System.getProperty("baseUrl") != null? System.getProperty("baseUrl"): System.getenv("baseUrl");
        List<WebElement> actualLinks = footerComponent.menuCustomerServiceComponent().getLinks();

        List<String> expectedLinks = Arrays.asList(
                baseUrl.concat("/search"),
                baseUrl.concat("/news"),
                baseUrl.concat("/blog"),
                baseUrl.concat("/recentlyviewedproducts"),
                baseUrl.concat("/compareproducts"),
                baseUrl.concat("/newproducts")
        );

        actualLinks.forEach(link -> {
            String infoText = link.getText();
            System.out.println(infoText);
            String infoHref = link.getAttribute("href");
            System.out.println(infoHref);
            Assert.assertNotNull(infoText, "Text is not null");
            Assert.assertTrue(expectedLinks.contains(infoHref), "Href link is not correct");
        });
    }

    private void verifyMyAccountColumn() {
        boolean isColumnName = footerComponent.menuMyAccountComponent().columnTitle().equalsIgnoreCase("My account");
        Assert.assertTrue(isColumnName, "My Account Title is not correct");

        String baseUrl = System.getProperty("baseUrl") != null? System.getProperty("baseUrl"): System.getenv("baseUrl");
        List<WebElement> actualLinks = footerComponent.menuMyAccountComponent().getLinks();

        List<String> expectedLinks = Arrays.asList(
                baseUrl.concat("/customer/info"),
                baseUrl.concat("/customer/orders"),
                baseUrl.concat("/customer/addresses"),
                baseUrl.concat("/cart"),
                baseUrl.concat("/wishlist")
        );

        actualLinks.forEach(link -> {
            String infoText = link.getText();
            String infoHref = link.getAttribute("href");
            Assert.assertNotNull(infoText, "Text is not null");
            Assert.assertTrue(expectedLinks.contains(infoHref), "Href link is not correct");
        });
    }

    private void verifyFollowUsColumn() {
        boolean isColumnName = footerComponent.menuFollowUsComponent().columnTitle().equalsIgnoreCase("Follow us");
        Assert.assertTrue(isColumnName, "Follow Us Title is not correct");

        String baseUrl = System.getProperty("baseUrl") != null? System.getProperty("baseUrl"): System.getenv("baseUrl");
        List<WebElement> actualLinks = footerComponent.menuFollowUsComponent().getLinks();

        List<String> expectedLinks = Arrays.asList(
                "http://www.facebook.com/nopCommerce",
                "https://twitter.com/nopCommerce",
                baseUrl.concat("/news/rss/1"),
                "http://www.youtube.com/user/nopCommerce",
                "https://plus.google.com/+nopcommerce"
        );

        actualLinks.forEach(link -> {
            String infoText = link.getText();
            String infoHref = link.getAttribute("href");
            Assert.assertNotNull(infoText, "Text is not null");
            Assert.assertTrue(expectedLinks.contains(infoHref), "Href link is not correct");
        });
    }
}
