package selen.core;

import lombok.EqualsAndHashCode;
import org.openqa.selenium.*;
import selen.core.extension.BaseExtension;
import selen.core.extension.JsExtension;
import selen.core.extension.WebElementProvider;
import selen.driver.DriverSource;
import selen.exception.SelenCriticalError;
import selen.utils.PowerList;

import java.util.List;

@EqualsAndHashCode
public class SelenElement implements WebElement, WebElementProvider, JsExtension, BaseExtension {
    private final SelectorChain chain;
    private WebElement webElement;
    private WebElement parentElement;

    protected SelenElement(SelectorChain chain, WebElement webElement, WebElement parentElement) {
        this.chain = chain;
        this.webElement = webElement;
        this.parentElement = parentElement;
    }

    public SelenElement(String selector) {
        chain = new SelectorChain(selector);
    }

    protected SelenElement(SelectorChain chain) {
        this.chain = chain;
    }

    public SelenElement $(String cssOrXpath) {
        return new SelenElement(chain.add(cssOrXpath), null, webElement);
    }

    public SelenElement $parent() {
        return $("..");
    }

    public <T extends SelenElement> T as(Class<T> clazz) {
        try {
            return clazz.getConstructor(String.class).newInstance(chain.toString());
        } catch (Exception ex) {
            String name = clazz.getSimpleName();
            String msg = String.format("Module class: %s should have constructor: %s(String selector){super(selector)}", name, name);
            throw new SelenCriticalError(msg, ex);
        }
    }

    @Override
    public WebElement getWebElement() {
        if(webElement == null) find();
        return webElement;
    }

    @Override
    public WebDriver getDriver() {
        return DriverSource.getDriver();
    }

    public SelenElement find() {
        webElement = haveParent()
                ? getParent().findElement(chain.getLast())
                : getDriver().findElement(chain.getLast());
        return this;
    }

    public PowerList<SelenElement> findAll() {
        List<WebElement> all = haveParent()
                ? getParent().findElements(chain.getLast())
                : getDriver().findElements(chain.getLast());
        return all.stream().map(el -> new SelenElement(this.chain, el, parentElement))
                .collect(PowerList.collector());
    }

    public int count() {
        return haveParent()
                ? getParent().findElements(chain.getLast()).size()
                : getDriver().findElements(chain.getLast()).size();
    }

    private boolean haveParent() {
        return chain.size() > 1;
    }


    private WebElement getParent() {
        if(parentElement == null) parentElement = find(chain.getAllExceptLast());
        return parentElement;
    }

    private WebElement find(List<By> bys) {
        WebElement el = null;
        for (By by : bys) {
            el = this.findOne(by, el);
        }
        return el;
    }

    private WebElement findOne(By by, WebElement parent) {
        return parent == null
                ? getDriver().findElement(by)
                : parent.findElement(by);
    }
}
