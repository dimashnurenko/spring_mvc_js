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

function MoreInfo() {
}

MoreInfo.prototype.showAddress = function (employeeId) {
    //TODO need add cash not to send request each time when we click on item
    $.ajax({
        method: "GET",
        url: "/address/get",
        data: {employeeId: employeeId},
        success: function (address) {
            if (!address) {
                MoreInfo.prototype.setAddress("", "", "", "", "");

                new Notification().showInfo("Address undefined...");
                return;
            }

            MoreInfo.prototype.setAddress(address.country, address.city, address.street, address.house, address.flat);
        },
        error: function () {
            MoreInfo.prototype.setAddress("", "", "", "", "");

            new Notification().showError("Address undefined...");
        }
    });
};

MoreInfo.prototype.setAddress = function (country, city, street, house, flat) {
    $("#country").val(country);
    $("#city").val(city);
    $("#street").val(street);
    $("#house").val(house);
    $("#flat").val(flat);
};

MoreInfo.prototype.disableAddressForm = function (isDisable) {
    $("#country").prop("disabled", isDisable).val("save employee first");
    $("#city").prop("disabled", isDisable).val("save employee first");
    $("#street").prop("disabled", isDisable).val("save employee first");
    $("#house").prop("disabled", isDisable).val("save employee first");
    $("#flat").prop("disabled", isDisable).val("save employee first");
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
            new Notification().showInfo("Address saved...");
        },
        error: function () {
            new Notification().showError("Can't save address...")
        }
    });
});

$("#deleteAddress").click(MoreInfo._deleteAddress($("#employeeId").text()));
