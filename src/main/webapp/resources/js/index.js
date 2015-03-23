/**
 * @author Dmitry Shnurenko
 */

if (window.addEventListener)
    window.addEventListener("load", init);
else if (window.attachEvent)
    window.attachEvent("onload", init);

function init() {
    var mainWidget = new MainWidget();

    function getAllEmployees() {
        $.get("get/all", function (data) {
            mainWidget.addEmployees(data)
        }, "json").fail();
    }

    getAllEmployees();

    //----form dialog object---
    function FormDialog() {
        this.isAddBtnActive = true;
        this.isUpdateBtnActive = true;
        this.isAddFormOpened = true;

        this.id = "";
        this.name = "";

        $("#formAddBtn").click(function () {
            if (formDialog.isAddBtnActive) {
                formDialog.onAddClicked();
            }
        });

        $("#cancel").click(function () {
            formDialog.hideDialog();
        });

        $("#number").keyup(function () {
            onValuesChanged();
        });

        function onValuesChanged() {
            if (formDialog.isAddFormOpened) {
                formDialog.onAddFormValuesChanged()
            } else {
                formDialog.onEditFormValuesChanged();
            }
        }

        $("#name").keyup(function () {
            onValuesChanged();
        });
    }

    var formDialog = new FormDialog();
    formDialog.form = $("#form")[0];

    FormDialog.prototype.onAddClicked = function () {
        var id = $("#number").val();
        var name = $("#name").val();

        var data = {id: id, name: name};

        $.post("add/employee", data, "json")
            .done(mainWidget.addEmployees([data])).fail(function () {
                throw new Error("Can't save...")
            });

        formDialog.form.style.display = "none";
    };

    FormDialog.prototype.onAddFormValuesChanged = function () {
        var number = $("#number").val();
        var name = $("#name").val();

        this.isAddBtnActive = number.length > 0 && name.length > 0;

        $("#formAddBtn").css("opacity", this.isAddBtnActive ? "1" : "0.4");
    };

    FormDialog.prototype.onEditFormValuesChanged = function () {
        var selectedId = mainWidget.selectedElement.getId();
        var selectedName = mainWidget.selectedElement.getName();

        var id = $("#number").val();
        var name = $("#name").val();

        this.isUpdateBtnActive = id !== selectedId || name !== selectedName;

        $("#formAddBtn").css("opacity", this.isUpdateBtnActive ? "1" : "0.4");
    };

    FormDialog.prototype.showDialog = function () {
        formDialog.form.style.display = "block";
    };

    FormDialog.prototype.hideDialog = function () {
        formDialog.form.style.display = "none";
    };

    //----main widget object------
    function MainWidget() {
        $("#add").click(function () {
            mainWidget.onAddBtnClicked();
        });

        $("#edit").click(function () {
            mainWidget.onEditBtnClicked();
        });

        $("#delete").click(function () {
            mainWidget.onDeleteClicked();
        });

        this.selectedElement = null;

        this.id = null;
        this.name = null;
        this.elements = $.makeArray();
    }

    MainWidget.prototype.onAddBtnClicked = function () {
        $("#number").val("");
        $("#name").val("");

        $("#formAddBtn").text("Add Employee");

        formDialog.onAddFormValuesChanged();

        formDialog.isAddFormOpened = true;

        formDialog.showDialog();
    };

    MainWidget.prototype.onEditBtnClicked = function () {
        formDialog.form.style.display = "block";

        $("#formAddBtn").text("Update");

        $("#number").val(mainWidget.selectedElement.getId());
        $("#name").val(mainWidget.selectedElement.getName());

        formDialog.isAddFormOpened = false;

        formDialog.onEditFormValuesChanged();
    };

    MainWidget.prototype.onDeleteClicked = function () {
        var id = mainWidget.selectedElement.getId();

        $.get("delete/employee", mainWidget.selectedElement.getId(), "json")
            .done(function () {
                mainWidget.elements.splice(id);

                mainWidget.addEmployees(mainWidget.elements);
            }).fail();
    };

    MainWidget.prototype.addEmployees = function (data) {
        for (var i in data) {
            var employee = data[i];

            var elementWidget = new ElementWidget(employee);

            elementWidget.setId(employee.id);
            elementWidget.setName(employee.name);

            mainWidget.elements.push(elementWidget);
        }
    };

    MainWidget.prototype.onElementSelected = function (id) {
        for (var i in mainWidget.elements) {
            var element = mainWidget.elements[i];

            if (element.getId() == id) {
                mainWidget.selectedElement = element;

                element.select(id);

                continue;
            }

            element.unSelect(element.getId());
        }
    };

    //---- employee entity---
    function Employee(id, name) {
        this.id = id;
        this.name = name;
    }

    //---element widget----
    function ElementWidget(employee) {
        this.employee = employee;

        $("#table").append('<div id=' + employee.id + ' class="employee">');

        $("#" + employee.id)
            .append('<div id="id' + employee.id + '" class="employeeId">')
            .append('<div id="name' + employee.id + '" class="employeeName">')
            .click(function () {
                ElementWidget.prototype.select(employee.id);

                MainWidget.prototype.onElementSelected(employee.id);
            });
    }

    ElementWidget.prototype.setId = function (id) {
        $('#id' + this.employee.id).text(id);
    };

    ElementWidget.prototype.setName = function (name) {
        $('#name' + this.employee.id).text(name);
    };

    ElementWidget.prototype.getId = function () {
        return $('#id' + this.employee.id).text();
    };

    ElementWidget.prototype.getName = function () {
        return $('#name' + this.employee.id).text();
    };

    ElementWidget.prototype.select = function (id) {
        $("#" + id).addClass('selected');
    };

    ElementWidget.prototype.unSelect = function (id) {
        $("#" + id).removeClass('selected');
    };

}


