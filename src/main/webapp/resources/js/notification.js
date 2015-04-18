/**
 * @author Dmitry Shnurenko
 */
function Notification() {
    var notification = $("#notification");
    notification.append("<svg id='imageFrame' class='image'></svg>");
    notification.append('<div id="message" class="message"></div>');
}

Notification.prototype.showError = function (message) {
    var image = createSVG(15, 15, 'resources/images/error.svg');
    $("#imageFrame").html(image);
    var messageDiv = $("#message");

    messageDiv.html(message);
    messageDiv.css('color', 'red');

    hideWithTimeout(3000);
};

function createSVG(height, width, location) {
    var image = document.createElementNS('http://www.w3.org/2000/svg', 'image');
    image.setAttributeNS(null, 'height', height.toString());
    image.setAttributeNS(null, 'width', width.toString());
    image.setAttributeNS('http://www.w3.org/1999/xlink', 'href', location);
    image.setAttributeNS(null, 'visibility', 'visible');

    return image;
}

function hideWithTimeout(timeout) {
    setTimeout(function () {
        $("#imageFrame").empty();
        $("#message").empty();
    }, timeout);
}

Notification.prototype.showInfo = function (message) {
    var image = createSVG(15, 15, 'resources/images/success.svg');
    $("#imageFrame").html(image);
    var messageDiv = $("#message");

    messageDiv.html(message);
    messageDiv.css('color', '#7FFF00');

    hideWithTimeout(3000);
};


