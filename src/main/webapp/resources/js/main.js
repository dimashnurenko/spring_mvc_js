/**
 * @author Dmitry Shnurenko
 */

if (window.addEventListener)
    window.addEventListener("load", init);
else if (window.attachEvent)
    window.attachEvent("onload", init);

function init() {
    var notification = new Notification();
    var moreInfo = new MoreInfo(notification);
    var mainWidget = new MainWidget();

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

    //----main widget object------
    function MainWidget() {
        $("#searchField").keyup(function () {
            mainWidget.searchElement();
        });

        this.id = null;
        this.name = null;
        this.elements = {};
    }

    MainWidget.prototype.addEmployees = function (data) {
        for (var i in data) {
            var entity = data[i];

            var hasEmployee = entity.employee;

            var employee = new Employee(hasEmployee ? hasEmployee.id : entity.id,
                hasEmployee ? hasEmployee.firstName : entity.firstName,
                hasEmployee ? hasEmployee.lastName : entity.lastName);

            var elementWidget = new ElementWidget(employee);

            var id = employee.id;

            mainWidget.elements[id] = elementWidget;
        }

        var firstEntity = data[0];
        var hasFirst = firstEntity.hasOwnProperty(elementWidget.employee) ? firstEntity.employee : firstEntity;

        mainWidget.onElementSelected(hasFirst ? hasFirst.id : firstEntity.id);
    };

    MainWidget.prototype.onElementSelected = function (id) {
        $("#employeeId").text(id);
        var elements = mainWidget.elements;

        for (var i in elements) {
            var employeeWidget = elements[i];

            var employeeId = employeeWidget.employee.id;

            if (employeeId == id) {
                mainWidget.selectedWidget = employeeWidget;

                employeeWidget.select(id);

                moreInfo.showAddress(id);
                moreInfo.showEmployeeInfo(employeeWidget.employee);

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

        $("#elements").append('<div id=' + employeeId + ' class="element">');

        var fLNamesId = "flNames" + employeeId;
        var imageId = "image" + employeeId;
        var hiringDate = "hiringDate" + employeeId;

        $("#" + employeeId)
            .append('<div id=' + imageId + ' class="image">')
            .append('<div id=' + fLNamesId + ' class="firstLastName font">')
            .append('<div id=' + hiringDate + ' class="firstLastName font">')
            .click(function () {
                MainWidget.prototype.onElementSelected(employeeId);
            });

        $("#" + fLNamesId).append('<div id=label' + fLNamesId + ' class="fLNames">');
        $("#label" + fLNamesId).text(employee.firstName + " " + employee.lastName);

        $("#" + imageId).append('<svg id=image' + imageId + ' class="imageIcon">');
        $("#image" + imageId).html(createSVG(40, 40, "resources/images/postDeveloper.svg"));

        function createSVG(height, width, location) {
            var image = document.createElementNS('http://www.w3.org/2000/svg', 'image');
            image.setAttributeNS(null, 'height', height.toString());
            image.setAttributeNS(null, 'width', width.toString());
            image.setAttributeNS('http://www.w3.org/1999/xlink', 'href', location);
            image.setAttributeNS(null, 'visibility', 'visible');

            return image;
        }
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
        $("#image" + id).addClass('selected');
        $("#flNames" + id).addClass('selected');
        $("#hiringDate" + id).addClass('selected');
        $("#" + id).addClass('rightShadow');
    };

    ElementWidget.prototype.unSelect = function (id) {
        $("#image" + id).removeClass('selected');
        $("#flNames" + id).removeClass('selected');
        $("#hiringDate" + id).removeClass('selected');
        $("#" + id).removeClass('rightShadow');
    };

//    ----------------- add employee -----------------
    $("#addEmployeeBtn").click(function () {
        var employee = new Employee("no negative number value", "enter first name", "enter last name");

        moreInfo.showEmployeeInfo(employee);
        moreInfo.setAddress("", "", "", "", "");
        moreInfo.disableAddressForm(true);

        mainWidget.onElementSelected(-1);
    });

    $("#deleteEmployeeBtn").click(function () {
        var element = mainWidget.elements[$("#employeeId").text()].employee;

        $.ajax({
            url: "delete/employee",
            method: 'GET',
            data: element,
            success: function (element) {
                for (var i in mainWidget.elements) {
                    var elementWidget = mainWidget.elements[i];

                    if (elementWidget.employee.id == element.id) {
                        delete mainWidget.elements[element.id];

                        break;
                    }
                }

                $("#elements").empty();

                mainWidget.addEmployees(mainWidget.elements);

                notification.showInfo(element.firstName + " was deleted...");

                MainWidget.prototype.deleteAddress();
            },
            error: function () {
                notification.showError("Can't delete employee...")
            }
        });
    });

    MainWidget.prototype.deleteAddress = function () {
        var employeeId = $("#employeeId").text();

        $.ajax({
            method: "POST",
            url: "/address/delete",
            data: {employeeId: employeeId},
            success: function () {
                new Notification().showInfo("Deleted address with id" + employeeId);

                $("#country").val("");
                $("#city").val("");
                $("#street").val("");
                $("#house").val("");
                $("#flat").val("");
            },
            error: function (x, y, error) {
                new Notification().showError(error);
            }
        });
    };

    $("#saveMoreInfo").click(function () {
        var employeeId = $("#infoEmployeeId").val();
        var firstName = $("#infoEmployeeName").val();
        var lastName = $("#infoEmployeeLName").val();

        if (isNaN(employeeId)) {
            notification.showError("Required number...");
            return;
        }

        if (employeeId < 0) {
            notification.showError("Must be above zero...");
            return;
        }

        if (firstName.length == 0 || lastName.length == 0) {
            notification.showError("Enter first and last name");
            return;
        }

        var data = {id: employeeId, firstName: firstName, lastName: lastName};

        $.ajax({
            url: "add/employee",
            method: 'POST',
            data: data,
            success: function (data) {
                mainWidget.addEmployees([data]);

                moreInfo.disableAddressForm(false);
                notification.showInfo(firstName + " is added...");
            },
            error: function () {
                notification.showError("Can't add employee...")
            }
        });
    });

    $("#saveAddress").click(function () {
        var country = $("#country").val();
        var city = $("#city").val();
        var street = $("#street").val();
        var house = $("#house").val();
        var flat = $("#flat").val();

        if (country.length == 0 || city.length == 0 || street.length == 0) {
            notification.showError("Country, City, Street are required");
            return;
        }

        if (isNaN(house) || isNaN(flat)) {
            notification.showError("Incorrect input house or flat number");
            return;
        }

        var address = {country: country, city: city, street: street, house: house, flat: flat};

        $.ajax({
            method: "POST",
            url: "/address/save",
            data: {employeeId: $("#employeeId").text(), address: address},
            success: function () {
                new Notification().showInfo("Address saved...");
            },
            error: function () {
                new Notification().showError("Can't save address...")
            }
        });
    });

    $("#deleteAddress").click(function () {
        MainWidget.prototype.deleteAddress();
    });


}


