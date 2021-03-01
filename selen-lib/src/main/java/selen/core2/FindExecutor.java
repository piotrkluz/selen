package selen.core2;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class FindExecutor {
    private final String selector;
    private final WebElement parentElement;
    private final WebDriver driver;


    public WebElement find() {
        return parentElement != null
                ? findOne(chain.getLast(), parentElement)
                : find(chain.getAll());
    }
}
