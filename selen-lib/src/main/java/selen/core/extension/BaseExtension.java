package selen.core.extension;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface BaseExtension extends WebElementProvider {
    default boolean isExist() {
        try {
            return getWebElement() != null;
        } catch (Exception e) {
            return false;
        }
    }
    default String getValue() {
        return getAttribute("value");
    }
    default boolean hasAttribute(String name) {
        return getWebElement().getAttribute(name) != null;
    }
    default boolean hasClass(String name) {
        return classes().contains(name);
    }
    default List<String> classes() {
        String attribute = getAttribute("class");
        return attribute == null ? new ArrayList<>() : Arrays.asList(attribute.split(" "));
    }

    default void dbClick() {
        actions().doubleClick(getWebElement()).perform();
    }

    default Actions actions() {
        return new Actions(getDriver());
    }

    default void dragAndDrop(WebElement targetElement) {
        actions().dragAndDrop(getWebElement(), targetElement).perform();
    }
}
