package selen.modules;

import selen.core.SelenElement;

import java.util.List;

public class Table extends SelenElement {
    public Table(String s) {
        super(s);
    }

    public TableModel getTable() {
        return new TableModel(
                getColumns(),
                getRows()
        );
    }

    public List<List<String>> getRows() {
        return $("tr")
                .findAll().map(tr -> tr.$("td")
                        .findAll().map(SelenElement::getText)
                );
    }

    public List<String> getColumns() {
        return $("th")
                .findAll().map(SelenElement::getText);
    }
}