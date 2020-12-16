package selen;

public class SApi {
    public static SMatcher $(String cssOrXpath) {
        return new SMatcherImpl(cssOrXpath);
    }
}
