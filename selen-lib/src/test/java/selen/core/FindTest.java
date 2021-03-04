package selen.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import selen.driver.DriverSource;
import selen.util.BrowserContent;

import static org.junit.jupiter.api.Assertions.*;
import static selen.SelenApi.$;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FindTest {
    private static final String INPUT_VALUE = "some input";
    private static final String OPTION_VALUE = "some option";
    private static final String BASE_FORM = "<form id='inputForm'><input value='" + INPUT_VALUE + "'/></form>" +
            "<form id='selectForm'><select><option value='" + OPTION_VALUE +
            "'>some select</option></select></form>" +
            "<form id='thirdForm'><input/></form>";
    private BrowserContent content;

    @BeforeAll
    public void init() {
        content = new BrowserContent(DriverSource.getDriver());
    }

    @Test
    public void findCss() {
        content.setBody(BASE_FORM);

        shouldFind($("input"), INPUT_VALUE);
        shouldFind($("form > input"), INPUT_VALUE);
        shouldFind($("html input"), INPUT_VALUE);
    }

    @Test
    public void findXpath() {
        content.setBody(BASE_FORM);

        shouldFind($("//input"), INPUT_VALUE);
        shouldFind($("//form/input"), INPUT_VALUE);
        shouldFind($("//html//input"), INPUT_VALUE);
    }

    @Test
    public void nestedCss() {
        content.setBody(BASE_FORM);

        shouldFind($("html").$("form").$("input"), INPUT_VALUE);
    }

    @Test
    public void nestedXpath() {
        content.setBody(BASE_FORM);

        shouldFind($("html").$("form").$("input"), INPUT_VALUE);
    }

    @Test
    public void nestedMixedXpathAndCss() {
        content.setBody(BASE_FORM);

        shouldFind($("html").$("//form[1]").$("input"), INPUT_VALUE);
        shouldFind($("html").$("//form[2]").$("option"), OPTION_VALUE);
        shouldFind($("//body").$("#inputForm").$("//input"), INPUT_VALUE);
        shouldFind($("//body").$("#selectForm").$("//option"), OPTION_VALUE);
    }

    @Test
    public void trickyXpath() {
        content.setBody(BASE_FORM);

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
        content.setBody(BASE_FORM);

        assertTrue($("//body").$("input").isExist());
        assertFalse($("//body").$("article").isExist());
    }

    @Test
    @Disabled("Not implemented yet")
    public void findNth() {
//        content.setBody(BASE_FORM);
//
//        assertEquals("thirdForm",
//                $("form").find(2).attribute("id"));
    }

    @Test
    public void findAll() {
        content.setBody(BASE_FORM);

        assertEquals(2, $("form input").findAll().size());
        assertEquals(1, $("form").$("input").findAll().size());

        assertEquals(0, $("form").$("article").findAll().size());
    }

    @Test
    public void count() {
        content.setBody(BASE_FORM);

        assertEquals(3, $("form").count());
        assertEquals(2, $("form input").count());
        assertEquals(2, $("//form//input").count());
        assertEquals(1, $("form").$("input").count());
        assertEquals(1, $("form").$("//input").count());

        assertEquals(0, $("form").$("article").count());
    }

    @Test
    public void parentXpath() {
        content.setBody(BASE_FORM);

        assertEquals("form", $("select").$("..").getTagName());
        assertEquals("form", $("select").$parent().getTagName());
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

    @Test
    @Disabled("Not implemented")
    public void attributeSelector() {
//        $("");
    }

    @Test
    public void splitIntoModules() {
        content.setBody(
                "<ul><li>first</li></ul>" +
                "<ul><li>second</li></ul>" +
                "<ul><li>third</li></ul>");

        SelenElement li = $("ul").findAll().get(1).$("li");
        assertEquals(1, li.count());
        assertEquals("second", li.getText());
    }

    private void shouldFind(SelenElement matcher, String text) {
        assertEquals(text, matcher.getValue());
    }

    private void shouldNotFind(SelenElement matcher) {
        assertFalse(matcher.isExist());
    }
}