package selen.pages;

import lombok.RequiredArgsConstructor;
import selen.core.SElement;
import selen.core.SMatcher;
import selen.core.SModule;

import java.util.List;

@RequiredArgsConstructor
public class MyForm extends SModule {
    public final SMatcher firstNameInput = $("name:firstName");
    public final SMatcher listItems = $("ul > li");
    public final SMatcher button = $("button");

    public MyForm(SMatcher parent) {
        super(parent);
    }

    public void sendForm(String name) {
        firstNameInput.type("I am " + name);
        button.click();
    }

    public List<String> getList() {
        return listItems.findAll().map(SElement::text);
    }
}
