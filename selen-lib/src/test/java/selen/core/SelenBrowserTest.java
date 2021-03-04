package selen.core;

import org.junit.jupiter.api.Test;
import selen.SelenApi;

public class SelenBrowserTest {

    @Test
    public void useBrowser() {
        SelenApi.getBrowser().get("data:,");
    }
}
