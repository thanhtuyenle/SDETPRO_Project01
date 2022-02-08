package driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    private WebDriver webDriver;

    public WebDriver getDriver() {
        if (webDriver == null) {
            //webDriver = Driver.getChromeDriver(); //use this line to run locally
            webDriver = createWebDriver(); //use this line to run jenkins
        }
        return webDriver;
    }

    private WebDriver createWebDriver() {
        WebDriver driver;
        String browserName = System.getProperty("browser");
        if (browserName == null)
            browserName = System.getenv("browser");
        if (browserName == null) {
            throw new IllegalArgumentException("[ERR] Please supply 'browser' type value!");
        } else {
            switch (BrowserType.valueOf(browserName)) {
                case chrome:
                    driver = initWebdriver(BrowserType.chrome);
                    break;
                case firefox:
                    driver = initWebdriver(BrowserType.firefox);
                    break;
                case safari:
                    driver = initWebdriver(BrowserType.safari);
                    break;
                default:
                    throw new IllegalArgumentException("[ERR] " + browserName + " is not supported!");
            }
        }
        return driver;
    }

    private WebDriver initWebdriver(BrowserType browserType) {
        WebDriver driver;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setPlatform(Platform.ANY);
        switch (browserType) {
            case chrome:
                desiredCapabilities.setBrowserName("chrome");
                break;
            case firefox:
                desiredCapabilities.setBrowserName("firefox");
                break;
            case safari:
                desiredCapabilities.setBrowserName("safari");
                break;
            default:
                throw new IllegalArgumentException("[ERR] " + browserType + " is not supported!");
        }

        String seleniumHub = System.getenv("seleniumHub") == null
                ? System.getProperty("seleniumHub")
                : System.getenv("seleniumHub");
        if (seleniumHub == null)
            throw new IllegalArgumentException("[ERR] Please provide selenium Hub address!");
        try {
            driver = new RemoteWebDriver(new URL(seleniumHub + "/wd/hub"), desiredCapabilities);
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

        return driver;
    }

    public WebDriver getDriver(String browserName) {
        if(webDriver == null) {
            webDriver = Driver.getDriver(browserName);
        }
        return webDriver;
    }

    public void quitDriver() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }
}
