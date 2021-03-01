package selen.core2;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import selen.core2.extension.BaseExtension;
import selen.core2.extension.JsExtension;
import selen.core2.extension.WebElementProvider;
import selen.driver.DriverSource;

@RequiredArgsConstructor
public class SelenElement implements WebElement, WebElementProvider, JsExtension, BaseExtension {
    final String selector;

    public SelenElement $(String cssOrXpath) {
        return new SelenElement(selector + " >>> " + cssOrXpath);
    }

    @Override
    public WebElement getWebElement() {
        return DriverSource.getDriver().findElement(By.cssSelector(selector.toString()));
    }

    @Override
    public WebDriver getDriver() {
        return DriverSource.getDriver();
    }
}
