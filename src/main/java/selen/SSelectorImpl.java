package selen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SSelectorImpl implements SSelector {
    private final ByChain chain;

    SSelectorImpl(String cssOrXpath) {
        chain = new ByChain(cssOrXpath);
    }

    private SSelectorImpl(ByChain chain) {
        this.chain = chain;
    }

    @Override
    public SSelector $(String cssOrXpath) {
        return new SSelectorImpl(this.chain.add(cssOrXpath));
    }

    @Override
    public WebDriver getDriver() {
        return DriverSource.getDriver();
    }

    public SElement tryFind() {
        try {
            return find();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public SElement find() {
        WebElement el = null;
        for (By by : chain.getAll()) {
            el = this.findOne(by, el);
        }
        return new SElementImpl(el, getDriver());
    }

    @Override
    public ElementList<SElement> findAll() {
        List<By> byList = chain.getAll();
        WebDriver driver = getDriver();

        List<WebElement> list = driver.findElements(byList.get(0));
        for(int i = 1; i < byList.size(); i++) {
            List<WebElement> newList = new ArrayList<>();
            for(WebElement el : list) newList.addAll(el.findElements(byList.get(i)));
            list = newList;
        }

        return list.stream()
                .map(el -> new SElementImpl(el, driver))
                .collect(Collectors.toCollection(ElementList::new));
    }

    private WebElement findOne(By by, WebElement parent) {
        return parent == null
                ? getDriver().findElement(by)
                : parent.findElement(by);
    }
}

