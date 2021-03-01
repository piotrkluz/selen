package selen.core2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import selen.driver.DriverSource;
import selen.util.BrowserContent;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SelenElementTest {
    private static final String HTML = "<b>test</b>";
    private static final String BASE_HTML = "<div id='test'>"+HTML+"</div>";
    private BrowserContent content;

    @BeforeAll
    public void init() {
        content = new BrowserContent(DriverSource.getDriver()).setBody(BASE_HTML);
    }

    @Test
    public void baseFind() {
        assertEquals(HTML, new SelenElement("#test").innerHTML());
    }
}