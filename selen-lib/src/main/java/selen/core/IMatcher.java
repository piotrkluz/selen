package selen.core;

import org.openqa.selenium.By;

public interface IMatcher {
    SMatcher $(By by);
    SMatcher $(String cssOrXpath);
    SMatcher $parent();
}
