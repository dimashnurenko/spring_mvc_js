/**
 * @author Dmitry Shnurenko
 */
function init() {
    var notification = new Notification();

    var employeeId = $("#employeeId").text();

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
            data: {employeeId: employeeId, address: address},
            success: function () {
                notification.showInfo("Address saved for " + employeeId)
            },
            error: function () {
                notification.showError("Can't save address...")
            }

        });
    });

    $("#deleteAddress").click(function () {
        $.ajax({
            method: "POST",
            url: "/address/delete",
            data: {employeeId: employeeId},
            success: function () {
                notification.showInfo("Deleted address with id" + employeeId);

                $("#country").val("");
                $("#city").val("");
                $("#street").val("");
                $("#house").val("");
                $("#flat").val("");
            },
            error: function (x, y, error) {
                notification.showError(error);
            }
        });
    });

}
