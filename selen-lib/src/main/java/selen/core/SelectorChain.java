package selen.core;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SelectorChain {
    public static final String SEPARATOR = " >>> ";
    private final String selector;
    private final By[] chain;

    public SelectorChain(String selector) {
        this.selector = selector;
        chain = splitSelector(selector);
    }

    @NotNull
    private By[] splitSelector(String selector) {
        final By[] chain;
        String[] parts = selector.split(SEPARATOR);
        chain = new By[parts.length];
        chain[0] = byGen(parts[0], false);
        for(int i = 1; i < parts.length; i++) chain[i] = byGen(parts[i], true);
        return chain;
    }

    public SelectorChain add(String cssOrXpath) {
        return new SelectorChain(selector + SEPARATOR + cssOrXpath);
    }

    public By getLast() {
        return chain[chain.length - 1];
    }

    public List<By> getAll() {
        return List.of(chain);
    }

    public List<By> getAllExceptLast() {
        return List.of(Arrays.copyOf(chain, chain.length - 1));
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

    public int size() {
        return chain.length;
    }

    @Override
    public String toString() {
        return selector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SelectorChain)) return false;
        SelectorChain that = (SelectorChain) o;
        return selector.equals(that.selector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selector);
    }
}
