/**
 * Created by Шнуренко on 02.03.2015.
 */

if (window.addEventListener)
    window.addEventListener("load", init);
else if (window.attachEvent)
    window.attachEvent("onload", init);

var form;

function init() {
    form = $("#form")[0];

    function getAllEmployees() {
        $.get("get/all", function (data) {
            addEmployees(data)
        }, "json").fail();
    }

    getAllEmployees();

    $("#add").click(function () {
        form.style.display = "block";
    });

    $("#edit").click(function () {
        onEditButtonClicked()
    });

    $("#cancel").click(function () {
        form.style.display = "none";
    });

    $("#formAddBtn").click(function () {
        var id = $("#number").val();
        var name = $("#name").val();

        var data = {id: id, name: name};

        $.post("add/employee", data, "json")
            .done(addEmployees([data])).fail(function () {
                throw new Error("Can't save...")
            });

        form.style.display = "none";
    });

    var selectedElement;

    function onElementSelected(data, id) {
        for (var i in data) {
            var element = data[i];

            if (element[0].id == id) {
                selectedElement = element;

                element.addClass('selected');

                continue;
            }

            element.removeClass('selected');
        }
    }

    function onEditButtonClicked() {
        form.style.display = "block";

        $("#number").val(selectedElement.children().get(0).innerHTML);
        $("#name").val(selectedElement.children().get(1).innerHTML);
    }

    function addEmployees(data) {
        var table = $("#table");
        var elements = $.makeArray();

        for (var i in data) {
            var employee = data[i];
            var id = employee.id;

            table.append('<div id=' + id + ' class="employee">');

            var element = $("#" + id);

            element.append('<div id="id' + id + '" class="employeeId">')
                .append('<div id="name' + id + '" class="employeeName">');

            $('#id' + id).text(id);
            $('#name' + id).text(employee.name);

            element.click(function () {
                onElementSelected(elements, this.id);
            });

            elements.push(element);
        }
    }
}


