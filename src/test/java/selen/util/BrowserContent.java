package selen.util;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class BrowserContent {
    private final WebDriver driver;

    public void setBody(String html) {
        WebElement bodyEl = driver.findElement(By.cssSelector("html>body"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = arguments[1]", bodyEl,
                html);
    }
}
