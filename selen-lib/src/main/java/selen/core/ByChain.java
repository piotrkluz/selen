package selen.core;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ByChain {
    private final List<By> list;

    public ByChain(String cssOrXpath) {
        list = new ArrayList<>();
        list.add(byGen(cssOrXpath, false));
    }

    private ByChain(List<By> list) {
        this.list = list;
    }

    public ByChain add(By by) {
        List<By> newList = new ArrayList<>(this.list);
        newList.add(by);
        return new ByChain(newList);
    }

    public ByChain add(String cssOrXpath) {
        List<By> newList = new ArrayList<>(this.list);

        By by = byGen(cssOrXpath, true);

        newList.add(by);
        return new ByChain(newList);

    }

    public By getLast() {
        return list.get(list.size() - 1);
    }

    public List<By> getAll() {
        return this.list;
    }

    private By byGen(String cssOrXpath, boolean haveParent) {
        return isXpath(cssOrXpath)
                ? By.xpath(normalizeXpath(cssOrXpath, haveParent))
                : By.cssSelector(cssOrXpath);
    }

    /**
     * Dot is required while call from parent element, and restricted when call from root element.
     * This method Add / remove this dot
     *
     * @return ".//selector" OR "//selector"
     */
    private String normalizeXpath(String xpath, boolean addDot) {
        return addDot
                ? xpath.startsWith(".") ? xpath : "." + xpath
                : xpath.startsWith(".") ? xpath.replaceFirst("^\\.+", "") : xpath;
    }

    private boolean isXpath(String cssOrXpath) {
        return cssOrXpath.startsWith("//") || cssOrXpath.startsWith("./") || cssOrXpath.startsWith("..");
    }
}
