package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShippingAddressComponent {
    private final WebDriver driver;
    private By firstNameSel = By.cssSelector("#ShippingNewAddress_FirstName");
    private By lastNameSel = By.cssSelector("#ShippingNewAddress_LastName");
    private By emailSel = By.cssSelector("#ShippingNewAddress_Email");
    private By countryDropdownSel = By.cssSelector("#ShippingNewAddress_CountryId");
    private By stateSel = By.cssSelector("#ShippingNewAddress_StateProvinceId");
    private By citySel = By.cssSelector("#ShippingNewAddress_City");
    private By address1Sel = By.cssSelector("#ShippingNewAddress_Address1");
    private By zipCodeSel = By.cssSelector("#ShippingNewAddress_ZipPostalCode");
    private By phoneNumberSel = By.cssSelector("#ShippingNewAddress_PhoneNumber");
    private By continueBtnSel = By.cssSelector(".new-address-next-step-button");

    private final WebElement shippingAddressComp;
    public ShippingAddressComponent(WebDriver driver) {
        this.driver = driver;
        shippingAddressComp = driver.findElement(By.cssSelector("#checkout-step-shipping"));
    }

    public WebElement firstName() {
        return shippingAddressComp.findElement(firstNameSel);
    }

    public WebElement lastName() {
        return shippingAddressComp.findElement(lastNameSel);
    }

    public WebElement email() {
        return shippingAddressComp.findElement(emailSel);
    }

    public void selectCountry(String country) {
        Select countryDropdown = new Select(shippingAddressComp.findElement(countryDropdownSel));
        countryDropdown.selectByVisibleText(country);
    }

    public void selectState(String state) {
        Select stateDropdown = new Select(shippingAddressComp.findElement(stateSel));
        stateDropdown.selectByVisibleText(state);
    }

    public WebElement city() {
        return shippingAddressComp.findElement(citySel);
    }

    public WebElement address1() {
        return shippingAddressComp.findElement(address1Sel);
    }

    public WebElement zipCode() {
        return shippingAddressComp.findElement(zipCodeSel);
    }

    public WebElement phoneNumber() {
        return shippingAddressComp.findElement(phoneNumberSel);
    }

    public WebElement continueBtn() {
        return shippingAddressComp.findElement(continueBtnSel);
    }
}
