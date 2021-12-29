package models.components.global.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.awt.*;

public class MenuFollowUsComponent extends MenuColumnComponent {
    public MenuFollowUsComponent(WebDriver driver) {
        super(driver);
    }

    @Override
    public By componentSel() {
        return By.cssSelector(".follow-us");
    }

}
