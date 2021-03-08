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
class SelectorTest {
    private BrowserContent content;

    @BeforeAll
    public void init() {
        content = new BrowserContent(DriverSource.getDriver());
    }

    @Test
    public void decomposeSelector() {
        $("//ul/li");
        $("//ul/li[1][text='lol']"); //fail
        
        $("//ul/li [1]");
        $("//ul/li [text='lol']");
        $("//ul/li [text='lol'][1]");
        $("//ul/li[1] [text='lol'][1]");
        $("//ul/li [text='lol'][1]");

        $("ul > li[1] [text=lol]"); //fail - wrong css
        $("ul > li[1] [noFilterWithThisName=lol]");
        $("ul > li[1] [text='lol']");
        $("ul > li[1] [text=\"lol\"]");
        $("ul > li[1] [text=' lol\'\"lol']");
        $("ul > li [1][text='lol[[]]lol']");
        $("ul > li [1][text=\"lol[[]]lol\"]");
        $("ul > li[1] [text=lol]");

        $("ul > li [1][text=lol]");
        $("ul > li [1][text='lol']");
        $("ul > li [1][text=\"lol\"]");
        $("ul > li [1][text='lol\"]"); //fail
        $("ul > li [1][text=\"lol\"]");
        $("ul > li [1][text=\"lol\"]");
        $("ul > li [1][text=\"lol\"]");
    }
}