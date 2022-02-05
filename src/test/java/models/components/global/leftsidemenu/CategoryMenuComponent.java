package models.components.global.leftsidemenu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryMenuComponent {
    private final WebDriver driver;
    private WebElement categoryMenuSel;
    public CategoryMenuComponent(WebDriver driver) {
        this.driver = driver;
        this.categoryMenuSel = driver.findElement(By.cssSelector(".block-category-navigation"));
    }

    public List<WebElement> categoryLinks() {
        List<WebElement> categoryLinks = new ArrayList<>();
       return categoryMenuSel.findElements(By.cssSelector(".block-category-navigation > li > a"));
    }

    public void waitUntilCategoryPageLoad(String categoryName) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.urlContains(categoryName.toLowerCase().replaceAll(" ", "-").replace("&", "-")));
    }
}
