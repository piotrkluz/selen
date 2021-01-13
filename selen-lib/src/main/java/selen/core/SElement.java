package selen.core;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import selen.driver.DriverSource;
import selen.keyboard.SelenKeyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface SElement {
    WebElement getWebElement();
    default WebDriver getDriver() { return DriverSource.getDriver(); }
    default String text() {
        return getWebElement().getText();
    }
    default String value() {
        return attribute("value");
    }
    default boolean isExist() {
        try {
            return getWebElement() != null;
        } catch (Exception e) {
            return false;
        }
    }
    default boolean isDisplayed() {
        return getWebElement().isDisplayed();
    }
    default boolean isSelected() {
        return getWebElement().isSelected();
    }
    default boolean isEnabled() {
        return getWebElement().isEnabled();
    }
    default SElement submit() {
        getWebElement().submit();
        return this;
    }

    default SElement sendKeys(CharSequence... charSequence) {
        getWebElement().sendKeys(charSequence);
        return this;
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
    default long selectionStart() {
        return (long) executeJs("return el.selectionStart");
    }
    default long selectionEnd() {
        return (long) executeJs("return el.selectionEnd");
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

    default SElement highlight() {
        String jsCode =
                "//Position parameters used for drawing the rectangle\n" +
                "var x = 100;\n" +
                "var y = 150;\n" +
                "var width = 200;\n" +
                "var height = 150;\n" +
                "\n" +
                "var canvas = document.createElement('canvas'); //Create a canvas element\n" +
                "//Set canvas width/height\n" +
                "canvas.style.width='100%';\n" +
                "canvas.style.height='100%';\n" +
                "//Set canvas drawing area width/height\n" +
                "canvas.width = window.innerWidth;\n" +
                "canvas.height = window.innerHeight;\n" +
                "//Position canvas\n" +
                "canvas.style.position='absolute';\n" +
                "canvas.style.left=0;\n" +
                "canvas.style.opacity = 0.4;\n" +
                "canvas.style.top=0;\n" +
                "canvas.style.zIndex=100000;\n" +
                "canvas.style.pointerEvents='none'; //Make sure you can click 'through' the canvas\n" +
                "document.body.appendChild(canvas); //Append canvas to body element\n" +
                "var context = canvas.getContext('2d');\n" +
                "//Draw rectangle\n" +
                "context.rect(x, y, width, height);\n" +
                "context.fillStyle = 'red';\n" +
                "context.fill();";

        executeJs(String.format("%s%s", "a", "b"));

        return this;
    }

    /**
     * Executes JavaScript inside browser.
     * Current element have "el" name in JS Scope:
     *
     * {@code
     *     myElement.executeJs("console.log(el.outerHTML)")
     *     myElement.executeJs("console.log(arguments[0])", argumentComesFromJava)
     * }
     */
    default Object executeJs(String jsCode, Object... arguments) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        // add WebElement as last argument
        Object[] allArgs = ArrayUtils.add(arguments, getWebElement());
        int lastArg = allArgs.length - 1;
        return executor.executeScript("let el = arguments[" + lastArg + "];\n" + jsCode, allArgs);
    }

    default boolean equals(SElement compareElement) {
        return getWebElement().equals(compareElement.getWebElement());
    }
}
