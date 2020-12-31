package selen.keyboard;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class SelenKeyboard {
    private final WebDriver driver;

    public void type(String text) {
        for (String part : splitText(text)) {
            if (isKeyCombination(part)) {
                typeKeyCombination(part);
            } else {
                typeText(part);
            }
        }
    }

    /**
     * Splits text to fragments, example:
     * "text{Shift}other{Ctrl+S}"
     * -> will split into array: "text", "{Shift}", "other", "{Ctrl+S}"
     */
    private List<String> splitText(String text) {
        Matcher m = Pattern.compile("(?:\\{.*?}|[^{}]+)").matcher(text);

        List<String> allMatches = new ArrayList<>();
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }

    /**
     * Checks is string is in braces like: "{Something}"
     */
    private boolean isKeyCombination(String text) {
        return text.matches("^\\{.*?}$");
    }

    private void typeText(String text) {
        new Actions(driver).sendKeys(text).perform();
    }

    private void typeKeyCombination(String text) {
        new KeyCombination(text, driver).type();
    }
}
