package models.components.global.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.awt.*;

public class MenuCustomerServiceComponent extends MenuColumnComponent {
    public MenuCustomerServiceComponent(WebDriver driver) {
        super(driver);
    }

    @Override
    public By componentSel() {
        return By.cssSelector(".customer-service");
    }
}
