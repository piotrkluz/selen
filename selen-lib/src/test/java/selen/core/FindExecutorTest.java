package selen.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import selen.driver.DriverSource;
import selen.util.BrowserContent;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FindExecutorTest {
    private BrowserContent content;

    @BeforeAll
    public void init() {
        content = new BrowserContent(DriverSource.getDriver());
    }

    @Test
    public void text() {
       //todo
    }
}
