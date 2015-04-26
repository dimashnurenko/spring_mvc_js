/**
 * @author Dmitry Shnurenko
 */
function Address(country, city, street, house, flat) {
    this.country = country;
    this.city = city;
    this.street = street;
    this.house = house;
    this.flat = flat;
}

function MoreInfo(notification) {
    this.notification = notification;
}

MoreInfo.prototype.showAddress = function (employeeId) {
    //TODO need add cash not to send request each time when we click on item
    $.ajax({
        method: "GET",
        url: "/address/get",
        data: {employeeId: employeeId},
        success: function (address) {
            if (!address) {
                MoreInfo._setAddress("", "", "", "", "");

                MoreInfo.notification.showError("Address undefined...");
                return;
            }

            MoreInfo._setAddress(address.country, address.city, address.street, address.house, address.flat);
        },
        error: function () {
            MoreInfo._setAddress("", "", "", "", "");

            MoreInfo.notification.showError("Address undefined...");
        }
    });
};

MoreInfo._setAddress = function (country, city, street, house, flat) {
    $("#country").val(country);
    $("#city").val(city);
    $("#street").val(street);
    $("#house").val(house);
    $("#flat").val(flat);
};

MoreInfo.prototype.showEmployeeInfo = function (employee) {
    $("#infoEmployeeName").val(employee.firstName);
    $("#infoEmployeeLName").val(employee.lastName);
    $("#infoEmployeeId").val(employee.id);
};

MoreInfo.prototype.deleteAddress = function (employeeId) {
    MoreInfo._deleteAddress(employeeId);
};

MoreInfo._deleteAddress = function (employeeId) {
    $.ajax({
        method: "POST",
        url: "/address/delete",
        data: {employeeId: employeeId},
        success: function () {
            MoreInfo.notification.showInfo("Deleted address with id" + employeeId);

            $("#country").val("");
            $("#city").val("");
            $("#street").val("");
            $("#house").val("");
            $("#flat").val("");
        },
        error: function (x, y, error) {
            MoreInfo.notification.showError(error);
        }
    });
};

$("#saveAddress").click(function () {
    var country = $("#country").val();
    var city = $("#city").val();
    var street = $("#street").val();
    var house = $("#house").val();
    var flat = $("#flat").val();

    var address = {country: country, city: city, street: street, house: house, flat: flat};

    $.ajax({
        method: "POST",
        url: "/address/save",
        data: {employeeId: $("#employeeId").text(), address: address},
        success: function () {
            MoreInfo.notification.showInfo("Address saved...");
        },
        error: function () {
            MoreInfo.notification.showError("Can't save address...")
        }
    });
});

$("#deleteAddress").click(MoreInfo._deleteAddress($("#employeeId").text()));
