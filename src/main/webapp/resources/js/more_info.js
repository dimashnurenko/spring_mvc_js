/**
 * @author Dmitry Shnurenko
 */
function init() {
    var notification = new Notification();

    $("#saveAddress").click(function () {
        var employeeId = $("#employeeId").text();

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

}
