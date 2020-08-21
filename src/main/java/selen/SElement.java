package selen;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import selen.keyboard.SelenKeyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface SElement {
    WebElement getWebElement();
    WebDriver getDriver();

    default String text() {
        return getWebElement().getText();
    }

    default String value() {
        return attribute("value");
    }
    default boolean isExist() { return getWebElement() != null; }
    default boolean isDisplayed() {
        return getWebElement().isDisplayed();
    }
    default boolean isSelected() {
        return getWebElement().isSelected();
    }
    default boolean isEnabled() {
        return getWebElement().isEnabled();
    }
    default void submit() {
        getWebElement().submit();
    }

    default void sendKeys(CharSequence... charSequence) {
        getWebElement().sendKeys(charSequence);
    }

    default String tagName() {
        return getWebElement().getTagName();
    }

    default Point location() {
        return getWebElement().getLocation();
    }

    default Dimension size() {
        return getWebElement().getSize();
    }

    default Rectangle rect() {
        return getWebElement().getRect();
    }

    default String cssValue(String propertyName) {
        return getWebElement().getCssValue(propertyName);
    }

    default String attribute(String name) {
        return getWebElement().getAttribute(name);
    }

    default boolean hasAttribute(String name) {
        return getWebElement().getAttribute(name) != null;
    }

    default List<String> classes() {
        String attribute = attribute("class");
        return attribute == null ? new ArrayList<>() : Arrays.asList(attribute.split(" "));
    }

    default boolean hasClass(String name) {
        return classes().contains(name);
    }

    default String innerHTML() {
        return (String) executeJs("return el.innerHTML");
    }

    default String outerHTML() {
        return (String) executeJs("return el.outerHTML");
    }

    // mouse actions
    default SElement click() {
        getWebElement().click();
        return this;
    }

    default SElement dbClick() {
        actions().doubleClick(getWebElement()).perform();
        return this;
    }

    default SElement rightClick() {
        new Actions(getDriver()).contextClick(getWebElement()).perform();
        return this;
    }

    default SElement dragAndDrop(SElement targetElement) {
        actions().dragAndDrop(getWebElement(), targetElement.getWebElement()).perform();
        return this;
    }

    default SElement dragAndDrop(int xOffset, int yOffset) {
        actions().dragAndDropBy(getWebElement(), xOffset, yOffset).perform();
        return this;
    }

    default Actions actions() {
        return new Actions(getDriver());
    }

    // keyboard actions
    default SElement type(String textWithKeys) {
        focus();
        new SelenKeyboard(getDriver()).type(textWithKeys);
        return this;
    }

    default SElement clear() {
        getWebElement().clear();
        return this;
    }

    default boolean hasFocus() {
        return (Boolean) executeJs("return el === document.activeElement");
    }

    default SElement focus() {
        executeJs("el.focus()");
        return this;
    }

    default SElement scrollIntoView() {
        return scrollIntoView(false, true);
    }

    /**
     * @param smooth Uses smooth scroll (EXPERIMENTAL)
     * @param center If set, the element will be scrolled to center of window.
     */
    default SElement scrollIntoView(boolean smooth, boolean center) {
        executeJs(String.format("el.scrollIntoView({behavior: '%s', block: '%s'})",
                smooth ? "smooth" : "auto", center ? "center" : "start"));
        return this;
    }

    /**
     * Executes JavaScript inside browser.
     * Current element have "el" name in JS Scope:
     *
     * @example - myElement.executeJs("console.log(el.outerHTML)")
     * - myElement.executeJs("console.log(arguments[0])", argumentComesFromJava)
     */
    default Object executeJs(String jsCode, Object... arguments) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        // add WebElement as last argument
        Object[] allArgs = ArrayUtils.add(arguments, getWebElement());
        int lastArg = allArgs.length - 1;
        return executor.executeScript("let el = arguments[" + lastArg + "];\n" + jsCode, allArgs);
    }
}
