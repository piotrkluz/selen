package selen;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selen.util.BrowserContent;

import static org.junit.jupiter.api.Assertions.*;
import static selen.SApi.$;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ElementTest {
    private BrowserContent content;

    @BeforeAll
    public void init() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = DriverSource.getDriver();
        content = new BrowserContent(driver);
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
    @Disabled("Need to investigate")
    public void dragAndDrop() {
        throw new RuntimeException();
        //given
//        final String CONTENT = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//        SElement input1 = $("input").type(CONTENT);
//        SElement input2 = $("#secondInput").clear();

        //when
//        $("input").dragAndDrop($("input").find(1));
//        input1.dragAndDrop(input2);
//
//        //then
//        assertEquals(CONTENT, input2.value());
//        assertEquals("", input1.value());
    }

    @Test
    public void type() {
        content.setBody("<input/>");
        String value = $("input")
                .clear()
                .type("123{backspace}{backspace}")
                .value();
        assertEquals("1", value);
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
        SElement el1 = $("form").find();// driver.findElement(By.cssSelector("form"));
        SElement el2 = $("form").find();// driver.findElement(By.cssSelector("form"));

        assertTrue(el1.equals(el2));
    }
}
