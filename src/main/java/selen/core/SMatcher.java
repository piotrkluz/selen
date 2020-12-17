package selen.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selen.utils.PowerList;

public interface SMatcher extends SElement {
    SMatcher $(String cssOrXpath);
    SMatcher $(By by);

    SElement find();
    PowerList<SElement> findAll();

    default SMatcher $parent() { return $(".." ); }
//    default SSelector $contains() { return $(".." ); }
//    default SSelector $withText() { return $(".." ); }
//    default SSelector $startsWith() { return $(".." ); }
//    default SSelector $endsWith() { return $(".." ); }
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
