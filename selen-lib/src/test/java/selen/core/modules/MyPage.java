package selen.core.modules;

import selen.core.SelenElement;

public class MyPage extends SelenElement {
    public MyForm form1 = $("#form1").as(MyForm.class);
    public MyForm form2 = $("#form2").as(MyForm.class);

    public MyPage(String s) {
        super(s);
    }
}
