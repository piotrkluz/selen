package selen.core;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selen.utils.PowerList;

@NoArgsConstructor
@AllArgsConstructor
public class SModule implements SElement, IMatcher {
    private SMatcher matcher;

    @Override
    public WebElement getWebElement() {
        return matcher.getWebElement();
    }
    public SMatcher $(By by) {
        return matcher.$(by);
    }
    public SMatcher $(String cssOrXpath) {
        return matcher.$(cssOrXpath);
    }
    public SMatcher $parent() {
        return matcher.$("..");
    }
    public SModule find() {
        return this;
    }
    public PowerList<SModule> findAll() {
        return null;
    }
}

