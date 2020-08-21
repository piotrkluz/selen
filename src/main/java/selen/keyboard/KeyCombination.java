package selen.keyboard;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyCombination {
    private List<Keys> keyboardKeys;
    private String text;
    private WebDriver driver;

    /**
     * @param keyCombination examples:
     *                       {Enter}
     *                       {Ctrl+Shift+A}
     *                       {arrowLeft}
     *                       {Shift+random string}
     */
    KeyCombination(String keyCombination, WebDriver driver) {
        this.driver = driver;
        String[] parts = keyCombination
                .replaceAll("(^\\{+|}+$)", "") // trim braces
                .split("\\+");

        // only last element can be random string
        String lastElement = parts[parts.length - 1];
        if (!isKeyboardKey(lastElement.trim())) {
            this.text = lastElement;
            parts = Arrays.copyOf(parts, parts.length - 1);
        }

        keyboardKeys = Stream.of(parts)
                .map(String::trim)
                .map(this::convertToSeleniumKey)
                .collect(Collectors.toList());
    }

    /**
     * Type key combination in currently focused element.
     */
    public void type() {
        Actions actions = new Actions(driver);

        Stack<Keys> keysToRelease = new Stack<>();
        for (Keys key : keyboardKeys) {
            actions.keyDown(key);
            keysToRelease.push(key);
        }
        actions.sendKeys(text);
        while (!keysToRelease.empty()) {
            actions.keyUp(keysToRelease.pop());
        }
        actions.perform();
    }

    /**
     * Accepts formats:
     * arrowLeft, left, control, ...
     * ARROW_LEFT, LEFT, CONTROL, ... - need to check / test
     */
    private Keys convertToSeleniumKey(String key) {
        Keys found = findKey(key);
        if(found == null) {
            throw new SelenException(String.format("Key %s not found in enum: org.openqa.selenium.Keys", key));
        }

        return found;
    }

    private boolean isKeyboardKey(String key) {
        return findKey(key) != null;
    }

    private Keys findKey(String searchKey) {
        // potentially performance may be improved. Need test.
        //prepare search
        searchKey = searchKey.replaceAll("[_ \\-]", "").toUpperCase();
        searchKey = searchKey.replace("CTRL", "CONTROL"); //let use "CTRL" too

        for (Keys enumEl : Keys.class.getEnumConstants()) {
            if (enumEl.name().replace("_", "")
                    .compareToIgnoreCase(searchKey) == 0) {
                return enumEl;
            }
        }
        return null;
    }
}
