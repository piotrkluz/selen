package selen.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import selen.core.modules.MyForm;
import selen.core.modules.MyPage;
import selen.driver.DriverSource;
import selen.util.BrowserContent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static selen.SelenApi.$;

public class ModuleTest {

    @BeforeAll
    public static void init() {
        final String FORM_PATTERN =
                "<div id='%s'>" +
                "   <input name='firstName'/>" +
                "   <ul>" +
                "       <li>%s item1</li>" +
                "       <li>%s item2</li>" +
                "   </ul>" +
                "   <button onclick='$(`#result`, this.parentNode).innerText = $(`input[name=firstName]`, this.parentNode).value '>%s</button>" +
                "   <p id='result'></p>" +
                "</div>";
        new BrowserContent(DriverSource.getDriver()).setBody(
                        String.format(FORM_PATTERN, "form1", "list1", "list1", "Send1") +
                        String.format(FORM_PATTERN, "form2", "list2", "list2", "Send2") +
                        "<input id='outsideOfForm' name='firstName' />"
        ).addJsTools();
    }

    @Test
    public void useModule() {
        MyForm myform = $("div").as(MyForm.class);
        assertNotNull(myform);
        assertEquals(2, myform.getList().size());
    }

    @Test
    public void useNestedModules() {
        $("body").as(MyPage.class).form1.getList();
    }
}
