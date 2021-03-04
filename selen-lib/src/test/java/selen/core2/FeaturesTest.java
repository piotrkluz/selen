package selen.core2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import selen.driver.DriverSource;
import selen.util.BrowserContent;

import static org.junit.jupiter.api.Assertions.*;
import static selen.core2.SelenApi.$;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeaturesTest {
    private BrowserContent content;

    @BeforeAll
    public void init() {
        content = new BrowserContent(DriverSource.getDriver());
    }

    @Test
    public void text() {
        content.setBody("<p>Hello <span>World</span></p>");

        assertEquals("Hello World", $("p").getText());
    }


    @Test
    public void innerHTMLTest() {
        content.setBody("<p>Hello <span>World</span></p>");

        assertEquals("Hello <span>World</span>", $("p").innerHTML());
    }

    @Test
    public void outerHTMLTest() {
        content.setBody("<p>Hello <span>World</span></p>");

        assertEquals("<p>Hello <span>World</span></p>", $("p").outerHTML());
    }

    @Test
    public void isDisplayed() {
        content.setBody("<p>a</p> <div style='display:none'></div>");

        assertTrue($("p").isDisplayed());
        assertFalse($("div").isDisplayed());
    }

    @Test
    public void attribute() {
        content.setBody("<input type='text' value='' required>");

        assertEquals("text", $("input").getAttribute("type"));
        assertEquals("true", $("input").getAttribute("required"));
        assertEquals("", $("input").getAttribute("value"));
        assertNull($("input").getAttribute("notattribute"));
    }

    @Test
    public void hasAttribute() {
        content.setBody("<input type='text' value='' required>");

        assertTrue($("input").hasAttribute("type"));
        assertFalse($("input").hasAttribute("notattribute"));
    }

    @Test
    public void value() {
        content.setBody("<input type='text' value='abc' required><span>abc</span>");

        assertEquals("abc", $("input").getValue());
        assertNull($("span").getValue());
    }

    @Test
    public void dbClick() {
        content.setBody("<input value='00000000000000000000'>");
        final String LETTER = "A";

        // when db click - should mark entire text, and replace with letter
        SelenElement input = $("input");
        input.dbClick();
        input.sendKeys(LETTER);

        //then
        assertEquals(LETTER, input.getValue());
    }

    @Test
    public void click() {
        content.setBody("<input value='first'/><input value='second'/>");
        SelenElement input = $("input").find();
        SelenElement secondInput = $("input").findAll().get(1);

        //when
        secondInput.click();
        //then
        assertTrue(secondInput.hasFocus());
        assertFalse(input.hasFocus());

        //when
        input.click();
        //then
        assertTrue(input.hasFocus());
        assertFalse(secondInput.hasFocus());
    }

    @Test
    public void focus() {
        content.setBody("<input value='first'/><input value='second'/>");
        SelenElement input = $("input").find();
        SelenElement secondInput = $("input").findAll().get(1);

        //when
        secondInput.focus();
        //then
        assertTrue(secondInput.hasFocus());
        assertFalse(input.hasFocus());

        //when
        input.focus();
        //then
        assertTrue(input.hasFocus());
        assertFalse(secondInput.hasFocus());
    }

    @Test
    public void dragAndDrop() {
//        given
        content.setBody("<input/><input id='secondInput'/>");
        SelenElement input1 = $("input");
        input1.type("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        SelenElement input2 = $("#secondInput");

//        when
        input1.dragAndDrop(input2.getWebElement());

//        then
        assertTrue(input1.selectionStart() > 1);
    }


    @ParameterizedTest
    @CsvSource({
            "123{backspace}{backspace},1",
            "{backspace},''",
            "{Shift+abc},ABC"
    })
    public void type(String typeArgument, String expectedResult) {
        content.setBody("<input/>");
        SelenElement input = $("input");

        input.clear();
        input.type(typeArgument);

        assertEquals(expectedResult,  input.getValue());
    }

    @Test
    public void typeException() {
        content.setBody("<input/>");
        assertThrows(IllegalArgumentException.class, () ->
                $("input").type("{backspace+ABC}")
        );
    }

    @Test
    public void sendKeys() {
        content.setBody("<input/>");
        SelenElement input = $("input");
        input.sendKeys("123{backspace}{backspace}");

        assertEquals("123{backspace}{backspace}", input.getValue());
    }

    @Test
    public void equalsTest() {
        content.setBody("<form></form>");
        SelenElement el1 = $("form").find();
        SelenElement el2 = $("form").find();

        assertEquals(el2, el1);
    }
}