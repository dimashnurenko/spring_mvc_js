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
            var isAddBtn = $("#formAddBtn").text() === "Add Employee";

            if (formDialog.isAddBtnActive && isAddBtn) {
                formDialog.onAddClicked();
            }

            if (formDialog.isUpdateBtnActive && !isAddBtn) {
                formDialog.onUpdateClicked();
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

    FormDialog.prototype.onUpdateClicked = function () {
        var id = $("#number").val();
        var name = $("#name").val();

        var element = {id: id, name: name};

        $.ajax({
            type: 'GET',
            url: 'update/employee',
            data: element,
            dataType: 'json',
            success: success(element)
        });

        function success(element) {
            mainWidget.elements[element.id].employee.name = element.name;

            $("#elements").empty();

            mainWidget.addEmployees(mainWidget.elements);

            formDialog.hideDialog();
        }
    };

    FormDialog.prototype.onAddFormValuesChanged = function () {
        var number = $("#number").val();
        var name = $("#name").val();

        this.isAddBtnActive = number.length > 0 && name.length > 0;

        $("#formAddBtn").css("opacity", this.isAddBtnActive ? "1" : "0.4");
    };

    FormDialog.prototype.onEditFormValuesChanged = function () {
        var selectedId = mainWidget.selectedWidget.getId();
        var selectedName = mainWidget.selectedWidget.getName();

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

        this.selectedWidget = null;

        this.id = null;
        this.name = null;
        this.elements = {};
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

        var number = $("#number");

        number.prop("disabled", true);

        $("#formAddBtn").text("Update");

        number.val(mainWidget.selectedWidget.getId());
        $("#name").val(mainWidget.selectedWidget.getName());

        formDialog.isAddFormOpened = false;

        formDialog.onEditFormValuesChanged();
    };

    MainWidget.prototype.onDeleteClicked = function () {
        var id = mainWidget.selectedWidget.getId();
        var name = mainWidget.selectedWidget.getName();

        var element = {id: id, name: name};

        $.ajax({
            type: 'GET',
            url: 'delete/employee',
            data: {id: id, name: name},
            dataType: 'json',
            success: success(element)
        });

        function success(element) {
            for (var i in mainWidget.elements) {
                var elementWidget = mainWidget.elements[i];

                if (elementWidget.getId() == element.id) {
                    delete mainWidget.elements[element.id];

                    break;
                }
            }

            $("#elements").empty();

            mainWidget.addEmployees(mainWidget.elements);
        }
    };

    MainWidget.prototype.addEmployees = function (data) {
        for (var i in data) {
            var entity = data[i];

            var hasEmployee = entity.employee;

            var elementWidget = new ElementWidget(hasEmployee ? hasEmployee : entity);

            var id = hasEmployee ? hasEmployee.id : entity.id;
            var name = hasEmployee ? hasEmployee.name : entity.name;

            $("#id" + id).text(id);
            $("#name" + id).text(name);

            mainWidget.elements[id] = elementWidget;

            mainWidget.onElementSelected(id);
        }
    };

    MainWidget.prototype.onElementSelected = function (id) {
        var elements = mainWidget.elements;

        for (var i in elements) {
            var employeeWidget = elements[i];

            var employeeId = employeeWidget.employee.id;

            if (employeeId == id) {
                mainWidget.selectedWidget = employeeWidget;

                employeeWidget.select(id);

                continue;
            }

            employeeWidget.unSelect(employeeId);
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

        $("#elements").append('<div id=' + employee.id + ' class="employee">');

        $("#" + employee.id)
            .append('<div id="id' + employee.id + '" class="employeeId">')
            .append('<div id="name' + employee.id + '" class="employeeName">')
            .click(function () {
                ElementWidget.prototype.select(employee.id);

                MainWidget.prototype.onElementSelected(employee.id);
            });
    }

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


