package models.components.global;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HeaderMenuComponent {
    private final WebElement component;
    private final WebDriver driver;

    public HeaderMenuComponent(WebDriver driver) {
        this.driver = driver;
        this.component = driver.findElement(By.cssSelector(".header-menu"));
    }
    public List<WebElement> topMenuHeaderLinks() {
        return component.findElements(By.cssSelector(".top-menu > li > a"));
    }

    public void waitUntilCategoryPageLoad(String categoryName) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.urlContains(
                categoryName.toLowerCase().replaceAll(" ","-").replace("&", "-")));
    }
}
