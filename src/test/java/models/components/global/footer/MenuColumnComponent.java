package models.components.global.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class MenuColumnComponent {
    private final By componentSel;
    private final WebElement component;

    protected MenuColumnComponent(WebDriver driver) {
        this.componentSel = componentSel();
        component = driver.findElement(componentSel);
    }

    public abstract By componentSel();
    public String columnTitle() {
        return component.findElement(By.tagName("h3")).getText();
    }
    public List<WebElement> getLinks() {
        return component.findElements(By.cssSelector("li a"));
    }

}
