/**
 * @author Dmitry Shnurenko
 */
$(document).ready(function () {
    $('#register').click(function (event) {
        showDialog(event);
    });

    function showDialog(event) {
        event.preventDefault();
        $('#overlay').fadeIn(400,
            function () {
                $('#modal_form')
                    .css('display', 'block')
                    .animate({opacity: 1, top: '50%'}, 200);
            });
    }

    $('#modal_close, #overlay, #registerButton').click(function () {
        $('#modal_form')
            .animate({opacity: 0, top: '45%'}, 200,
            function () {
                $(this).css('display', 'none');
                $('#overlay').fadeOut(400);
            }
        );
    });

    $("#login").click(function (event) {
        $("#emailLabel").remove();
        $("#repeatPasswordLabel").remove();
        $("#email").remove();
        $("#repeatPassword").remove();
        $("#registerButton").val("Login");

        showDialog(event)
    });
});

