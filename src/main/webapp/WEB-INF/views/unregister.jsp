<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
<head lang="en">
    <c:url value="/resources/css/style.css" var="css"/>
    <c:url value="/resources/css/register.css" var="registerCss"/>
    <c:url value="/resources/js/index.js" var="js"/>
    <c:url value="/resources/js/register.js" var="registerJs"/>
    <c:url value="/resources/js/libraries/jquery-2.1.3.js" var="jquery"/>
    <c:url value="/resources/js/libraries/jquery-ui.min.js" var="jqueryUI"/>
    <link href="${css}" rel="stylesheet">
    <link href="${registerCss}" rel="stylesheet">
    <script src="${jquery}" type="text/javascript"></script>
    <script src="${jqueryUI}" type="text/javascript"></script>
    <script src="${js}" type="text/javascript"></script>
    <script src="${registerJs}" type="text/javascript"></script>
    <title>spring_js</title>
</head>
<body>
<div id="parent" class="parent">
    <div class="main">
        <div class="north">
            <div id="register" class="registerPanelButton">Register</div>
        </div>

        <div class="west">
            <div id="buttons_panel" class="buttonsPanel">

            </div>
        </div>

        <div class="center">
            <div id="table" class="table">
                <%--add employee form--%>
                <div id="form" class="form">
                    <div>
                        <label class="formLabel">Employee Number:</label>
                        <input id="number" type="text" class="textField">
                    </div>
                    <div>
                        <label class="formLabel">First name:</label>
                        <input id="firstName" type="text" class="textField">
                    </div>
                    <div>
                        <label class="formLabel">Last name:</label>
                        <input id="lastName" type="text" class="textField">
                    </div>

                    <div id="cancel" class="formButtonCancel">Cancel</div>
                    <div id="formAddBtn" class="formButtonAdd">Add Employee</div>
                </div>
                <%---------------------%>

                <div class="tableTitle">
                    <label class="tableTitleId">ID</label>
                    <label class="tableTitleFirstName">FIRST NAME</label>
                    <label class="tableTitleLastName">LAST NAME</label>
                </div>

                <div id="elements" class="elements">

                </div>
            </div>
        </div>

        <div class="east">
            <div id="search" class="search">
                <label class="searchLabel">Search:</label>
                <input id="searchField" type="text" class="searchField" placeholder="input id or first name"
                       disabled="disabled">
            </div>

            <div id="searchResult" class="searchResult">

            </div>

            <div class="loginPanel">
                <form id="loginForm" method="post" action="/user/login">
                    <div class="loginParameterPanel">
                        <label class="loginLabel">Login:</label>
                        <input id="login" name="login" class="loginTextBox" type="text" placeholder="login">
                    </div>
                    <div class="loginParameterPanel">
                        <label class="loginLabel">Password:</label>
                        <input id="loginPassword" name="password" class="loginTextBox" type="password"
                               placeholder="password">
                    </div>

                    <input id="loginButton" class="loginButton" type="submit" value="login">
                </form>
            </div>
        </div>

        <div class="south">
        </div>
        <%--register window--%>
        <div id="modal_form" class="modal_form">
            <span id="modal_close" class="modal_close">X</span>

            <form name="register" class="registerForm" method="post" action="/user/register">
                <div class="registerParameterPanel">
                    <label class="registerLabel">Login:</label>
                    <input id="name" name="login" class="registerTextBox" type="text">
                </div>
                <div class="registerParameterPanel">
                    <label id="emailLabel" class="registerLabel">Email:</label>
                    <input id="email" name="email" class="registerTextBox" type="text">
                </div>
                <div class="registerParameterPanel">
                    <label class="registerLabel">Password:</label>
                    <input id="password" name="password" class="registerTextBox" type="password">
                </div>
                <div class="registerParameterPanel">
                    <label id="repeatPasswordLabel" class="registerLabel">Repeat Password:</label>
                    <input id="repeatPassword" name="repeatPassword" class="registerTextBox" type="password">
                </div>

                <input id="registerButton" class="registerButton" type="submit" value="Register">
            </form>
        </div>
        <div id="overlay" class="overlay"></div>
    </div>
</div>
</body>
</html>