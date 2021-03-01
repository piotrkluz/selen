package selen.core2.extension;

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
}
