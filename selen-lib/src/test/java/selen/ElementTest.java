package selen;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import selen.core.SElement;
import selen.driver.DriverSource;
import selen.util.BrowserContent;

import static org.junit.jupiter.api.Assertions.*;
import static selen.SelenApi.$;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ElementTest {
    private BrowserContent content;

    @BeforeAll
    public void init() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = DriverSource.getDriver();
        content = new BrowserContent(driver);
    }

    @AfterAll
    public void exit() {
        DriverSource.close();
    }

    @Test
    public void text() {
        content.setBody("<p>Hello <span>World</span></p>");

        assertEquals("Hello World", $("p").text());
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

    // is exist policy

    @Test
    public void attribute() {
        content.setBody("<input type='text' value='' required>");

        assertEquals("text", $("input").attribute("type"));
        assertEquals("true", $("input").attribute("required"));
        assertEquals("", $("input").attribute("value"));
        assertNull($("input").attribute("notattribute"));
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

        assertEquals("abc", $("input").value());
        assertNull($("span").value());
    }

    @Test
    public void dbClick() {
        content.setBody("<input value='00000000000000000000'>");
        final String LETTER = "A";

        // when db click - should mark entire text, and replace with letter
        SElement input = $("input");
        input.dbClick().sendKeys(LETTER);

        //then
        assertEquals(LETTER, input.value());
    }

    @Test
    public void click() {
        content.setBody("<input value='first'/><input value='second'/>");
        SElement input = $("input").find();
        SElement secondInput = $("input").find(1);

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
        SElement input = $("input").find();
        SElement secondInput = $("input").find(1);

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
        SElement input1 = $("input").type("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        SElement input2 = $("#secondInput");

//        when
        input1.dragAndDrop(input2);

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
        SElement input = $("input");

        input.clear().type(typeArgument);

        assertEquals(expectedResult,  input.value());
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
        SElement input = $("input").clear();
        input.sendKeys("123{backspace}{backspace}");

        assertEquals("123{backspace}{backspace}", input.value());
    }

    @Test
    public void equalsTest() {
        content.setBody("<form></form>");
        WebDriver driver = $("form").getDriver();
        SElement el1 = $("form").find();
        SElement el2 = $("form").find();

        assertTrue(el1.equals(el2));
    }
}
