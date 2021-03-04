package selen.core2.modules;

import selen.core2.SelenElement;

import java.util.List;

import static selen.core2.SelenApi.$;

public class MyForm extends SelenElement {
    public final SelenElement firstNameInput = $("name:firstName");
    public final SelenElement listItems = $("ul > li");
    public final SelenElement button = $("button");

    public MyForm(String s) {
        super(s);
    }

    public void sendForm(String name) {
        firstNameInput.type("I am " + name);
        button.click();
    }

    public List<String> getList() {
        return listItems.findAll().map(SelenElement::getText);
    }
}
