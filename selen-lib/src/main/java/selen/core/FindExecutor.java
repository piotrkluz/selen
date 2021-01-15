package selen.core;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selen.driver.DriverSource;
import selen.utils.PowerList;

import java.util.List;

@RequiredArgsConstructor
public class FindExecutor {
    private final ByChain chain;
    private final WebElement parentElement;
    private final WebDriver driver = DriverSource.getDriver();

    public PowerList<WebElement> findAll() {
        PowerList<By> byList = PowerList.of(chain.getAll());
        By lastBy = byList.last();
        byList.remove(byList.size() - 1);

        List<WebElement> foundList = parentElement != null
                ? parentElement.findElements(lastBy)
                : byList.isEmpty()
                ? driver.findElements(lastBy)
                : find(byList).findElements(lastBy);

        return PowerList.of(foundList);
    }

    public WebElement find() {
        return parentElement != null
                ? findOne(chain.getLast(), parentElement)
                : find(chain.getAll());
    }

    private WebElement find(List<By> bys) {
        WebElement el = null;
        for (By by : bys) {
            el = this.findOne(by, el);
        }
        return el;
    }

    private WebElement findOne(By by, WebElement parent) {
        return parent == null
                ? driver.findElement(by)
                : parent.findElement(by);
    }
}
