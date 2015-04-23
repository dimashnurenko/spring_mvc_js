/**
 * @author Dmitry Shnurenko
 */

if (window.addEventListener)
    window.addEventListener("load", init);
else if (window.attachEvent)
    window.attachEvent("onload", init);

function init() {
    var mainWidget = new MainWidget();
    var notification = new Notification();

    function getAllEmployees() {
        $.ajax({
            url: "get/all",
            success: function (data) {
                mainWidget.addEmployees(data);
                notification.showInfo("Shown all employee...");
            },
            error: function () {
                notification.showError("Can't get all employee...")
            }
        });
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

        $("#firstName").keyup(function () {
            onValuesChanged();
        });

        $("#lastName").keyup(function () {
            onValuesChanged();
        });
    }

    var formDialog = new FormDialog();
    formDialog.form = $("#form")[0];

    FormDialog.prototype.onAddClicked = function () {
        var id = $("#number").val();
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();

        var data = {id: id, firstName: firstName, lastName: lastName};

        $.ajax({
            url: "add/employee",
            method: 'POST',
            data: data,
            success: function (data) {
                mainWidget.addEmployees([data]);
                notification.showInfo(firstName + " is added...");
            },
            error: function () {
                notification.showError("Can't add employee...")
            }
        });

        formDialog.form.style.display = "none";
    };

    FormDialog.prototype.onUpdateClicked = function () {
        var id = $("#number").val();
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();

        var element = {id: id, firstName: firstName, lastName: lastName};

        $.ajax({
            url: "update/employee",
            method: 'GET',
            data: element,
            success: function (element) {
                mainWidget.elements[element.id].employee.firstName = element.firstName;
                mainWidget.elements[element.id].employee.lastName = element.lastName;

                $("#elements").empty();

                mainWidget.addEmployees(mainWidget.elements);

                formDialog.hideDialog();

                notification.showInfo(firstName + " is updated...");
            },
            error: function () {
                notification.showError("Can't update employee...")
            }
        });
    };

    FormDialog.prototype.onAddFormValuesChanged = function () {
        var number = $("#number").val();
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();

        this.isAddBtnActive = number.length > 0 && firstName.length > 0 && lastName.length > 0;

        $("#formAddBtn").css("opacity", this.isAddBtnActive ? "1" : "0.4");
    };

    FormDialog.prototype.onEditFormValuesChanged = function () {
        var selectedId = mainWidget.selectedWidget.getId();
        var selectedFirstName = mainWidget.selectedWidget.getFirstName();
        var selectedLastName = mainWidget.selectedWidget.getLastName();

        var id = $("#number").val();
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();

        this.isUpdateBtnActive = id !== selectedId || firstName !== selectedFirstName || lastName !== selectedLastName;

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

        $("#searchField").keyup(function () {
            mainWidget.searchElement();
        });

        this.selectedWidget = null;

        this.id = null;
        this.name = null;
        this.elements = {};
    }

    MainWidget.prototype.onAddBtnClicked = function () {
        $("#number").val("");
        $("#firstName").val("");
        $("#lastName").val("");

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
        $("#firstName").val(mainWidget.selectedWidget.getFirstName());
        $("#lastName").val(mainWidget.selectedWidget.getLastName());

        formDialog.isAddFormOpened = false;

        formDialog.onEditFormValuesChanged();
    };

    MainWidget.prototype.onDeleteClicked = function () {
        var id = mainWidget.selectedWidget.getId();
        var firstName = mainWidget.selectedWidget.getFirstName();
        var lastName = mainWidget.selectedWidget.getLastName();

        var element = {id: id, firstName: firstName, lastName: lastName};

        $.ajax({
            url: "delete/employee",
            method: 'GET',
            data: element,
            success: function (element) {
                for (var i in mainWidget.elements) {
                    var elementWidget = mainWidget.elements[i];

                    if (elementWidget.getId() == element.id) {
                        delete mainWidget.elements[element.id];

                        break;
                    }
                }

                $("#elements").empty();

                mainWidget.addEmployees(mainWidget.elements);

                notification.showInfo(firstName + " was deleted...");
            },
            error: function () {
                notification.showError("Can't delete employee...")
            }
        });
    };

    MainWidget.prototype.addEmployees = function (data) {
        for (var i in data) {
            var entity = data[i];

            var hasEmployee = entity.employee;

            var elementWidget = new ElementWidget(hasEmployee ? hasEmployee : entity);

            var id = hasEmployee ? hasEmployee.id : entity.id;
            var firstName = hasEmployee ? hasEmployee.firstName : entity.firstName;
            var lastName = hasEmployee ? hasEmployee.lastName : entity.lastName;

            $("#id" + id).text(id);
            $("#firstName" + id).text(firstName);
            $("#lastName" + id).text(lastName);

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

    MainWidget.prototype.searchElement = function () {
        var searchResultPanel = $("#searchResult");

        searchResultPanel.empty();

        var value = $("#searchField").val();

        if (value === "") {
            return;
        }

        var divId = 0;

        for (var id in mainWidget.elements) {
            var employee = mainWidget.elements[id].employee;

            if (!isNaN(value)) {
                var employeeId = employee.id;

                if (isIdMatch(value.toString(), employeeId.toString())) {

                    new SearchingElement(++divId, employeeId);
                }
            } else {
                var employeeName = employee.firstName;

                if (isStringMatch(value.toString(), employeeName.toString())) {
                    new SearchingElement(++divId, employeeName)
                }
            }
        }

        function isStringMatch(inputString, generalString) {
            var length = inputString.length;

            var subString = generalString.substring(0, length);

            return inputString.indexOf(subString) + 1;
        }

        function isIdMatch(inputId, generalId) {
            return generalId.indexOf(inputId) + 1;
        }
    };

    function SearchingElement(id, valueToSet) {
        this.id = id;

        $("#searchResult").append('<div id="_' + id + '" class="foundedElement">');

        var searchingElement = $('#_' + id);

        searchingElement.click(function () {
            for (var index in mainWidget.elements) {
                var employeeWidget = mainWidget.elements[index];

                if (isNaN(valueToSet)) {
                    var name = employeeWidget.employee.firstName;

                    if (valueToSet === name) {
                        mainWidget.onElementSelected(employeeWidget.employee.id);

                        return;
                    }
                }

                var id = employeeWidget.employee.id;

                if (valueToSet == id) {
                    mainWidget.onElementSelected(id);
                }
            }
        });

        searchingElement.text(valueToSet);
    }

    //---- employee entity---
    function Employee(id, firstName, lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //---element widget----
    function ElementWidget(employee) {
        this.employee = employee;

        var employeeId = employee.id;

        $("#elements").append('<div id=' + employeeId + ' class="employee">');

        $("#" + employeeId)
            .append('<div id="id' + employeeId + '" class="employeeId">')
            .append('<div id="firstName' + employeeId + '" class="employeeName">')
            .append('<div id="lastName' + employeeId + '" class="employeeName">')
            .append('<div id="showInfo' + employeeId + '" class="employeeName">')
            .click(function () {
                MainWidget.prototype.onElementSelected(employeeId);
            });

        $("#showInfo" + employeeId).append('<div id="showInfoBtn' + employeeId + '" class="showInfoBtn">Show info</div>');

        $("#showInfoBtn" + employeeId).click(function () {
            $("#employeeId").text(employeeId);

            $.ajax({
                method: "GET",
                url: "/info",
                success: function (moreInfo) {
                    var elements = $("#elements");

                    var tableContent = elements.html();

                    elements.empty();

                    elements.html(moreInfo);

                    $.getScript("/resources/js/more_info.js", function () {
                        init();
                    });

                    $.ajax({
                        method: "GET",
                        url: "/address/get",
                        data: {employeeId: employeeId},
                        success: function (address) {
                            $("#country").val(address.country);
                            $("#city").val(address.city);
                            $("#street").val(address.street);
                            $("#house").val(address.house);
                            $("#flat").val(address.flat);

                            notification.showInfo("Info of " + employee.firstName + "...");
                        },
                        error: function () {
                            notification.showError("Can't get address")
                        }
                    });

                    $("#backBtn").click(function () {
                        elements.html(tableContent);
                        location.reload();
                    });
                },
                error: function () {
                    notification.showError("Can't show more info...")
                }
            });
        });
    }

    ElementWidget.prototype.getId = function () {
        return $('#id' + this.employee.id).text();
    };

    ElementWidget.prototype.getFirstName = function () {
        return $('#firstName' + this.employee.id).text();
    };

    ElementWidget.prototype.getLastName = function () {
        return $('#lastName' + this.employee.id).text();
    };

    ElementWidget.prototype.select = function (id) {
        $("#" + id).addClass('selected');
    };

    ElementWidget.prototype.unSelect = function (id) {
        $("#" + id).removeClass('selected');
    };

}


