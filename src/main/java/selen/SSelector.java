package selen;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface SSelector {
    WebElement find(WebDriver driver);

    class SXpath implements SSelector {
        @Override
        public WebElement find(WebDriver driver) {
            return null;
        }
    }
    class SCss implements SSelector {
        @Override
        public WebElement find(WebDriver driver) {
            return null;
        }
    }
    class SJs implements SSelector {
        @Override
        public WebElement find(WebDriver driver) {
            return null;
        }
    }
}
