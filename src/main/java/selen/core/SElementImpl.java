package selen.core;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;

@RequiredArgsConstructor
public class SElementImpl implements SElement {
    private final WebElement el;
    private final WebDriver driver;

    public WebDriver getDriver() {
        return this.driver;
    }

    public WebElement getWebElement() {
        return this.el;
    }
}
