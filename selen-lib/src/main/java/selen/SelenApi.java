package selen;

import selen.core.SMatcher;
import selen.core.SMatcherImpl;

public class SelenApi {
    public static SMatcher $(String cssOrXpath) {
        return new SMatcherImpl(cssOrXpath);
    }
}
