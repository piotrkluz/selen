package selen.modules;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TableModel {
    List<String> columns;
    List<List<String>> rows;
}
