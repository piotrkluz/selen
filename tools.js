function $(css, parent = document) {
    return parent.querySelector(css)
}

function $$(css, parent = document) {
    return Array.from(parent.querySelectorAll(css))
}

/** Get HTML table contents */
function getTable(selector, columnNames = null) {
    const columns = columnNames ? columnNames : $$("th").map(th => th.innerText)
    const rows = $$("tr").map(tr => $$("td", tr).map(td => td.innerText))
    return {columns, rows}
}
