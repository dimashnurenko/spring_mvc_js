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

        $.ajax({
            method: "POST",
            url: "address/save",
            data: {employeeId: employeeId, country: country, city: city, street: street, house: house, flat: flat},
            success: function () {
                notification.showInfo("Address saved for " + employeeId)
            },
            error: function () {
                notification.showError("Can't save address...")
            }

        });
    });

}
