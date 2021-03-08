package example;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import selen.SelenApi;
import selen.modules.Table;
import selen.modules.TableModel;

import static selen.SelenApi.$;

public class Grabber {

    @Test
    public void getIndexes() {
        WebDriver driver = SelenApi.getDriver();
        driver.get("");

        $("table.sortTable")
                .$("tr")
                .findAll()
                .get(1)
                .$("td")
                .findAll();

        TableModel model = $("table").as(Table.class).getTable();
    }

    @Test
    public void xx() {
        $("table.sortTable")
                .$("tr")
                .findAll()
                .get(1)
                .$("td").findAll();
    }
}
