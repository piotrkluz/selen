package selen.modules;

import selen.core.SElement;
import selen.core.SMatcher;
import selen.core.SModule;

import java.util.List;

public class Table extends SModule {
    public Table(SMatcher matcher) {
        super(matcher);
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
                        .findAll().map(SElement::text)
                );
    }

    public List<String> getColumns() {
        return $("th")
                .findAll().map(SElement::text);
    }
}