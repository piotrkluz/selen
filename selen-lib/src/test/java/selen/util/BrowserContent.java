package selen.util;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class BrowserContent {
    private final WebDriver driver;
    private final String JS_TOOLS =
            "const $$ = (css, parent = document) => Array.from(parent.querySelectorAll(css))\n" +
            "const $ =  (css, parent = document) => parent.querySelector(css)";

    public BrowserContent setBody(String html) {
        WebElement bodyEl = driver.findElement(By.cssSelector("html>body"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = arguments[1]", bodyEl,
                html);
        return this;
    }

    public BrowserContent addJsTools() {
        setScriptTag(JS_TOOLS);

        return this;
    }

    public BrowserContent setScriptTag(String scriptContent) {
        String escapedScriptContent = scriptContent.replaceAll("`", "\\`");
        final String ADD_SCRIPT =
                "const s = document.createElement('script')\n" +
                "s.innerHTML = `" + escapedScriptContent + "`;\n" +
                " document.querySelector('head').append(s);";

        ((JavascriptExecutor) driver).executeScript(ADD_SCRIPT);
        return this;
    }
}
