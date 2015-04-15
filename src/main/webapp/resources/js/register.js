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

    $('#modal_close, #overlay').click(function () {
        $('#modal_form')
            .animate({opacity: 0, top: '45%'}, 200,
            function () {
                $(this).css('display', 'none');
                $('#overlay').fadeOut(400);
            }
        );
    });

    $("#logout").click(function () {
        $.ajax({
            url: "user/logout",
            success: function () {
                location.reload();
            }
        });

    });

    var loginPattern = /^[a-z0-9_-]{3,16}$/;
    var emailPattern = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    var passwordPattern = /^[a-z0-9_-]{6,18}$/;

    //login form validation
    $("#loginForm").submit(function (event) {
        var login = $("#login");
        var password = $("#loginPassword");

        resetField(login);
        resetField(password);

        if (!isFieldValid(loginPattern, login)) {
            event.preventDefault();
            return;
        }

        if (!isFieldValid(passwordPattern, password)) {
            event.preventDefault();
        }
    });

    function resetField(field) {
        field.keyup(function () {
            field.css("border-color", "#615050");
        });
    }

    function isFieldValid(pattern, field) {
        if (!pattern.test(field.val())) {
            field.css('border-color', 'red');
            return false;
        }

        return true;
    }

    //register form validation
    $(".registerForm").submit(function (event) {
        var name = $('#name');
        var email = $('#email');
        var password = $('#password');
        var repeatPassword = $('#repeatPassword');

        resetField(name);
        resetField(email);
        resetField(password);
        resetField(repeatPassword);

        if (!isFieldValid(loginPattern, name)) {
            event.preventDefault();
            return;
        }

        if (!isFieldValid(emailPattern, email)) {
            event.preventDefault();
            return;
        }

        if (!isFieldValid(passwordPattern, password)) {
            event.preventDefault();
            return;
        }

        if (password.val() !== repeatPassword.val()) {
            repeatPassword.css("border-color", "red");
            event.preventDefault();
        }
    });
});


