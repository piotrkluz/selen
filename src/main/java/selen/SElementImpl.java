package selen;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;

import java.util.List;

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
