package selen.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import selen.driver.DriverSource;
import selen.pages.MyForm;
import selen.pages.MyPage;
import selen.util.BrowserContent;

import static selen.SelenApi.$;
import static org.junit.jupiter.api.Assertions.*;

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
        MyForm myform = $("form").as(MyForm.class);
        assertNotNull(myform);
        assertEquals(2, myform.getList().size());
    }

//    @Test
//    public void useModuleList() {
//        $("form").as(MyForm.class).findAll();
//    }

    @Test
    public void useNestedModules() {
        $("body").as(MyPage.class).form1.getList();
    }
}
