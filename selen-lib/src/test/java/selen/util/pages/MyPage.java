package selen.util.pages;

import selen.core.SMatcher;
import selen.core.SModule;

public class MyPage extends SModule {
    public MyForm form1 = $("#form1").as(MyForm.class);
    public MyForm form2 = $("#form2").as(MyForm.class);

    public MyPage(SMatcher m) {
        super(m);
    }
}
