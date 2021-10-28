package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BillingAddressComponent {
    private final WebDriver driver;

    private By firstNameSel = By.cssSelector("#BillingNewAddress_FirstName");
    private By lastNameSel = By.cssSelector("#BillingNewAddress_LastName");
    private By emailSel = By.cssSelector("#BillingNewAddress_Email");
    private By countryDropdownSel = By.cssSelector("#BillingNewAddress_CountryId");
    private By stateSel = By.cssSelector("#BillingNewAddress_StateProvinceId");
    private By citySel = By.cssSelector("#BillingNewAddress_City");
    private By address1Sel = By.cssSelector("#BillingNewAddress_Address1");
    private By zipCodeSel = By.cssSelector("#BillingNewAddress_ZipPostalCode");
    private By phoneNumberSel = By.cssSelector("#BillingNewAddress_PhoneNumber");

    public BillingAddressComponent(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement firstName() {
        return driver.findElement(firstNameSel);
    }

    public WebElement lastName() {
        return driver.findElement(lastNameSel);
    }

    public WebElement email() {
        return driver.findElement(emailSel);
    }

    public void selectCountry(String country) {
        Select countryDropdown = new Select(driver.findElement(countryDropdownSel));
        countryDropdown.selectByVisibleText(country);
    }

    public void selectState(String state) {
        Select stateDropdown = new Select(driver.findElement(stateSel));
        stateDropdown.selectByVisibleText(state);
    }

    public WebElement city() {
        return driver.findElement(citySel);
    }

    public WebElement address1() {
        return driver.findElement(address1Sel);
    }

    public WebElement zipCode() {
        return driver.findElement(zipCodeSel);
    }

    public WebElement phoneNumber() {
        return driver.findElement(phoneNumberSel);
    }
}
