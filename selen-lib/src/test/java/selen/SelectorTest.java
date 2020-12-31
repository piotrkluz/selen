package selen;

import org.junit.jupiter.api.*;
import selen.core.SMatcher;
import selen.driver.DriverSource;
import selen.util.BrowserContent;

import static org.junit.jupiter.api.Assertions.*;
import static selen.SelenApi.$;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SelectorTest {
    private static final String INPUT_VALUE = "some input";
    private static final String OPTION_VALUE = "some option";

    @BeforeAll
    public void init() {
        new BrowserContent(DriverSource.getDriver()).setBody(
                "<form id='inputForm'><input value='" + INPUT_VALUE + "'/></form>" +
                        "<form id='selectForm'><select><option value='" + OPTION_VALUE +
                        "'>some select</option></select></form>" +
                        "<form id='thirdForm'><input/></form>");
    }

    @AfterAll
    public void exit() {
        DriverSource.close();
    }

    @Test
    public void findCss() {
        shouldFind($("input"), INPUT_VALUE);
        shouldFind($("form > input"), INPUT_VALUE);
        shouldFind($("html input"), INPUT_VALUE);
    }

    @Test
    public void findXpath() {
        shouldFind($("//input"), INPUT_VALUE);
        shouldFind($("//form/input"), INPUT_VALUE);
        shouldFind($("//html//input"), INPUT_VALUE);
    }

    @Test
    public void nestedCss() {
        shouldFind($("html").$("form").$("input"), INPUT_VALUE);
    }

    @Test
    public void nestedXpath() {
        shouldFind($("html").$("form").$("input"), INPUT_VALUE);
    }

    @Test
    public void nestedMixedXpathAndCss() {
        shouldFind($("html").$("//form[1]").$("input"), INPUT_VALUE);
        shouldFind($("html").$("//form[2]").$("option"), OPTION_VALUE);
        shouldFind($("//body").$("#inputForm").$("//input"), INPUT_VALUE);
        shouldFind($("//body").$("#selectForm").$("//option"), OPTION_VALUE);
    }

    @Test
    public void trickyXpath() {
        shouldFind($("#inputForm").$("//input"), INPUT_VALUE);
        shouldFind($("#inputForm").$(".//input"), INPUT_VALUE);
        shouldFind($("#selectForm").$("//option"), OPTION_VALUE);
        shouldFind($("#selectForm").$(".//option"), OPTION_VALUE);
        shouldNotFind($("#selectForm").$("//input"));
        shouldNotFind($("#selectForm").$(".//input"));
        shouldNotFind($("#inputForm").$("//option"));
        shouldNotFind($("#inputForm").$(".//option"));
    }

    @Test
    public void isExist() {
        assertTrue($("//body").$("input").isExist());
        assertFalse($("//body").$("article").isExist());
    }

    @Test
    public void tryFind() {
        assertTrue($("//body").$("input").tryFind().isDisplayed());
        assertNull($("//body").$("article").tryFind());
    }

    @Test
    public void findNth() {
        assertEquals("thirdForm",
                $("form").find(2).attribute("id"));
    }

    @Test
    public void findAll() {
        assertEquals(2, $("form input").findAll().size());
        assertEquals(2, $("form").$("input").findAll().size());

        assertEquals(0, $("form").$("article").findAll().size());
    }

    @Test
    public void count() {
        assertEquals(3, $("form").count());
        assertEquals(2, $("form input").count());
        assertEquals(2, $("//form//input").count());
        assertEquals(2, $("form").$("input").count());
        assertEquals(2, $("form").$("//input").count());

        assertEquals(0, $("form").$("article").count());
    }

    @Test
    public void parentXpath() {
        assertEquals("form", $("select").$("..").tagName());
        assertEquals("form", $("select").$parent().tagName());
    }

    @Test
//    @Disabled("Not implemented yet")
    public void selectorFromElementScope() {
//        $("abc").find().$parent();
//        $("abc").find().$("other");
    }

    @Test
    @Disabled("Not implemented yet")
    public void textSelector() {
//        $("select")
//        $("abc").$contains(String text, boolean matchCase = false);
//        $("abc").$withText(String text);
//        $("abc").$startsWith(String text);
//        $("abc").$endsWith(String text);
    }

    private void shouldFind(SMatcher selector, String text) {
        assertEquals(text, selector.value());
    }

    private void shouldNotFind(SMatcher selector) {
        assertNull(selector.tryFind());
    }
}