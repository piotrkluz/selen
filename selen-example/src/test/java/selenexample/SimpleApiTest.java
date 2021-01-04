package selenexample;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import selen.SelenApi;

public class SimpleApiTest {
    @Test
    public void baseSelector() {
        SelenApi.$("html").find();
    }

    @AfterAll
    public static void close() {
        SelenApi.closeBrowser();
    }
}
