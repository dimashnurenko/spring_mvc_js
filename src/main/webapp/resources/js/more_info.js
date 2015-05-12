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

MoreInfo.prototype.setMainInfo = function (INN, firstName, lastName) {
    $("#infoEmployeeId").val(INN);
    $("#infoEmployeeName").val(firstName);
    $("#infoEmployeeLName").val(lastName);
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