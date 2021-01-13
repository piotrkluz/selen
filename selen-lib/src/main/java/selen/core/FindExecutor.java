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
    private final WebDriver driver = DriverSource.getDriver();

    public PowerList<WebElement> findAll() {
        PowerList<By> findList = PowerList.of(chain.getAll());
        By lastBy = findList.last();
        findList.remove(findList.size() - 1);
        List<WebElement> foundList = findList.isEmpty()
                ? driver.findElements(lastBy)
                : find(findList).findElements(lastBy);
        return PowerList.of(foundList);
    }

    public WebElement find() {
        return find(chain.getAll());
    }

    private WebElement find(List<By> byChain) {
        WebElement el = null;
        for (By by : byChain) {
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
