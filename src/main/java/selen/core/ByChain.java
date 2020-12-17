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

        boolean doNormalize = true; // todo extract to configuration
        By by = byGen(cssOrXpath, true);
        By last = getLast();
        if (doNormalize
                && (by instanceof By.ByCssSelector || by instanceof By.ByXPath)
                && by.getClass().equals(last.getClass())) {
            newList.set(newList.size() - 1, mergeBys(last, by));
            return new ByChain(newList);
        } else {
            newList.add(by);
            return new ByChain(newList);
        }
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

    private By mergeBys(By first, By second) {
        if(first instanceof By.ByXPath) {
            return By.xpath(regenerateBy(first) + "//" + normalizeXpath(regenerateBy(second), false));
        }
        return By.cssSelector(regenerateBy(first) + " " + regenerateBy(second));
    }

    private String regenerateBy(By by) {
        return by.toString().replaceFirst("^By\\.\\w+: ", "");
    }

    private By getLast() {
        return list.get(list.size() - 1);
    }
}
