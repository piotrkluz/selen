package selen;

import org.openqa.selenium.WebElement;

public interface SSelector extends SElement {
    SSelector $(String cssOrXpath);
    SElement find();
    ElementList<SElement> findAll();

    default WebElement getWebElement() {
        return find().getWebElement();
    }
    default boolean isExist() {
        return tryFind() != null;
    }
    default SElement tryFind() {
        try {
            return find();
        } catch (Exception e) {
            return null;
        }
    }

    default SElement find(int index) {
        return findAll().get(index);
    }

    default int count() {
        return findAll().size();
    }
}
