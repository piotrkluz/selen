package selen.core;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selen.exception.SelenCriticalError;
import selen.utils.PowerList;

@AllArgsConstructor
public class SMatcher implements SElement, IMatcher {
    private final ByChain chain;
    private WebElement webElement;

    public SMatcher(String cssOrXpath) {
        chain = new ByChain(cssOrXpath);
    }
    private SMatcher(ByChain chain) {
        this.chain = chain;
    }

    @Override
    public WebElement getWebElement() {
        return webElement != null
                ? webElement
                : find().getWebElement();
    }

    public SMatcher $(By by) {
        return new SMatcher(this.chain.add(by));
    }

    public SMatcher $(String cssOrXpath) {
        return new SMatcher(this.chain.add(cssOrXpath));
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
        webElement = new FindExecutor(chain).find();
        return this;
    }

    public SMatcher find(int nth) {
        return findAll().get(nth);
    }

    public <T extends SMatcher> PowerList<T> findAll() {
        return new FindExecutor(chain)
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

