package selen;

import selen.core.SMatcher;
import selen.driver.DriverSource;

public class SelenApi {
    public static SMatcher $(String cssOrXpath) {
        return new SMatcher(cssOrXpath);
    }

    public static void closeBrowser() {
        DriverSource.close();
    }
}
