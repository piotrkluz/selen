package selen.core2.modules;

import selen.core2.SelenElement;

public class MyPage extends SelenElement {
    public MyForm form1 = $("#form1").as(MyForm.class);
    public MyForm form2 = $("#form2").as(MyForm.class);

    public MyPage(String s) {
        super(s);
    }
}
