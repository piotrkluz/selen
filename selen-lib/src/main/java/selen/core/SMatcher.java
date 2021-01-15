package selen.core;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selen.exception.SelenCriticalError;
import selen.utils.PowerList;

@AllArgsConstructor
public class SMatcher implements SElement, IMatcher {
    private final ByChain chain;
    private WebElement webElement;
    @Setter
    private WebElement parentElement;

    public SMatcher(String cssOrXpath) {
        chain = new ByChain(cssOrXpath);
    }
    private SMatcher(ByChain chain) {
        this.chain = chain;
    }
    private SMatcher(ByChain chain, WebElement webElement) {
        this.chain = chain;
        this.webElement = webElement;
    }

    @Override
    public WebElement getWebElement() {
        return webElement != null
                ? webElement
                : find().getWebElement();
    }

    public SMatcher $(By by) {
        return childMatcher(this.chain.add(by));
    }

    public SMatcher $(String cssOrXpath) {
        return childMatcher(this.chain.add(cssOrXpath));
    }

    private SMatcher childMatcher(ByChain newChain) {
        SMatcher child = new SMatcher(newChain);
        if(this.webElement != null) child.setParentElement(this.webElement);
        return child;
    }

    public SMatcher $parent() {
        return $("..");
    }

    public <T extends SModule> T as(Class<T> clazz) {
        try {
            return clazz.getConstructor(SMatcher.class).newInstance(this);
        } catch (Exception ex) {
            String name = clazz.getSimpleName();
            String msg = String.format("Module class: %s should have constructor: %s(SMatcher matcher){super(matcher)}", name, name);
            throw new SelenCriticalError(msg, ex);
        }
    }

    public SMatcher find() {
        webElement = new FindExecutor(chain , parentElement).find();
        return this;
    }

    public SMatcher find(int nth) {
        return findAll().get(nth);
    }

    public <T extends SMatcher> PowerList<T> findAll() {

        return new FindExecutor(chain, parentElement)
                .findAll()
                .stream()
                .map(el -> (T)new SMatcher(chain, el))
                .collect(PowerList.collector());
    }

    public SMatcher tryFind() {
        try {
            return find();
        } catch (Exception ex) {
            return null;
        }
    }

    public int count() {
        return findAll().size();
    }
}

