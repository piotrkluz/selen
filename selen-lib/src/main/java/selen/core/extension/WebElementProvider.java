package selen.core.extension;

import org.openqa.selenium.*;

import java.util.List;

public interface WebElementProvider extends WebElement {
    WebElement getWebElement();
    WebDriver getDriver();

    /**
     * Below is base implementation of each WebDriver method.
     */
    @Override
    default void click() {
        getWebElement().click();
    }

    @Override
    default void submit() {
        getWebElement().submit();
    }

    @Override
    default void sendKeys(CharSequence... keysToSend) {
        getWebElement().sendKeys(keysToSend);
    }

    @Override
    default void clear() {
        getWebElement().clear();
    }

    @Override
    default String getTagName() {
        return getWebElement().getTagName();
    }

    @Override
    default String getAttribute(String name) {
        return getWebElement().getAttribute(name);
    }

    @Override
    default boolean isSelected() {
        return getWebElement().isSelected();
    }

    @Override
    default boolean isEnabled() {
        return getWebElement().isEnabled();
    }

    @Override
    default String getText() {
        return getWebElement().getText();
    }

    @Override
    default List<WebElement> findElements(By by) {
        return getWebElement().findElements(by);
    }

    @Override
    default WebElement findElement(By by) {
        return getWebElement().findElement(by);
    }

    @Override
    default boolean isDisplayed() {
        return getWebElement().isDisplayed();
    }

    @Override
    default Point getLocation() {
        return getWebElement().getLocation();
    }

    @Override
    default Dimension getSize() {
        return getWebElement().getSize();
    }

    @Override
    default Rectangle getRect() {
        return getWebElement().getRect();
    }

    @Override
    default String getCssValue(String propertyName) {
        return getWebElement().getCssValue(propertyName);
    }

    @Override
    default <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return getWebElement().getScreenshotAs(target);
    }
}
