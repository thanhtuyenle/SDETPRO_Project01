package models.components.global.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MenuInformationComponent extends MenuColumnComponent{
    public MenuInformationComponent(WebDriver driver) {
        super(driver);
    }

    @Override
    public By componentSel() {
        return By.cssSelector(".information");
    }

}
