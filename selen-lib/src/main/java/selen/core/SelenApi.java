package selen.core;

import org.openqa.selenium.WebDriver;
import selen.driver.DriverSource;
import selen.driver.SelenBrowser;

public class SelenApi {
    public static SelenElement $(String cssOrXpath) {
        return new SelenElement(cssOrXpath);
    }

    public static WebDriver getDriver() {
        return DriverSource.getDriver();
    }
    public static SelenBrowser getBrowser() {
        return new SelenBrowser(getDriver());
    }
}
