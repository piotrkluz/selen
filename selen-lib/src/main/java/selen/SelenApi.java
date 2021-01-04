package selen;

import selen.core.SMatcher;
import selen.core.SMatcherImpl;
import selen.driver.DriverSource;

public class SelenApi {
    public static SMatcher $(String cssOrXpath) {
        return new SMatcherImpl(cssOrXpath);
    }

    public static void closeBrowser() {
        DriverSource.close();
    }
}
