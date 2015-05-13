/**
 * @author Dmitry Shnurenko
 */
function FilterListBox(id, parent, allElements) {
    this.id = id;
    this.entries = [];
    this.allElemnts = allElements;

    $(parent).append('<div id="filter' + id + '" class="filterLBMain">')
        .append('<div id="label' + id + '" class="labelLB font">')
        .append('<input id="input' + id + '" type="text" class="filterInput">');

    $("#input" + id).click(function () {
        $("#filter" + id).css("display", "block");
    });

    $("#filter" + id).css("display", "none");
}

FilterListBox.prototype.setLabel = function (label) {
    $("#label" + this.id).text(label);
};

FilterListBox.prototype.addEntry = function (entry) {
    var id = this.id;
    this.entries[id] = entry;

    $("#filter" + id).append('<div id="entry' + entry + id + '" class="filterEntry font">');

    $("#entry" + entry + id).click(function (event) {
        $("#input" + id).val(entry);

        $("#filter" + id).css("display", "none");

        event.preventDefault();
    }).text(entry);
};
