/**
 * author Shnurenko Dmitry
 */

function SearchingElement(id, valueToSet, mainWidget) {
    this.id = id;

    var searchedElements = $("#searchedElements");

    searchedElements.append('<div id="_' + id + '" class="foundedElement font">');

    var searchingElement = $('#_' + id);

    searchingElement.click(function () {
        searchedElements.css("display", "none");

        for (var index in mainWidget.elements) {
            var employeeWidget = mainWidget.elements[index];

            if (isNaN(valueToSet)) {
                var name = employeeWidget.employee.firstName;

                if (valueToSet === name) {
                    mainWidget.onElementSelected(employeeWidget.employee.id);

                    $("#searchField").val(employeeWidget.employee.id);

                    return;
                }
            }

            var id = employeeWidget.employee.id;

            if (valueToSet == id) {
                mainWidget.onElementSelected(id);

                $("#searchField").val(employeeWidget.employee.id);
            }
        }
    });

    searchingElement.text(valueToSet);
}
