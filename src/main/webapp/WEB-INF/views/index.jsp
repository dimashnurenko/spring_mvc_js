<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
<head lang="en">
    <c:url value="/resources/css/style.css" var="css"/>
    <c:url value="/resources/css/notification.css" var="notificationCss"/>
    <c:url value="/resources/css/register.css" var="registerCss"/>
    <c:url value="/resources/js/index.js" var="js"/>
    <c:url value="/resources/js/notification.js" var="notificationJS"/>
    <c:url value="/resources/js/register.js" var="registerJs"/>
    <c:url value="/resources/js/libraries/jquery-2.1.3.js" var="jquery"/>
    <c:url value="/resources/js/libraries/jquery-ui.min.js" var="jqueryUI"/>
    <c:url value="/resources/css/more_info.css" var="moreInfoCss"/>
    <link href="${moreInfoCss}" rel="stylesheet">
    <link href="${css}" rel="stylesheet">
    <link href="${registerCss}" rel="stylesheet">
    <link href="${notificationCss}" rel="stylesheet">
    <script src="${jquery}" type="text/javascript"></script>
    <script src="${notificationJS}" type="text/javascript"></script>
    <script src="${jqueryUI}" type="text/javascript"></script>
    <script src="${js}" type="text/javascript"></script>
    <script src="${registerJs}" type="text/javascript"></script>
    <title>spring_js</title>
</head>
<body>
<div id="parent" class="parent">
    <div class="main">
        <div class="north">
            <div id="logout" class="registerPanelButton">Logout</div>
            <div id="welcomeUser" class="welcomeUser">Hello, <%=session.getAttribute("login")%></div>
        </div>

        <div class="west">
            <div id="buttons_panel" class="buttonsPanel">
                <div id="add" class="button">Add</div>
                <div id="edit" class="button">Edit</div>
                <div id="delete" class="button">Remove</div>
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
                    <label class="tableTitleMoreInfo">MORE INFO</label>
                </div>

                <div id="elements" class="elements">

                </div>
            </div>
        </div>

        <div class="east">
            <div id = "notification" class="notification">

            </div>
            <div id="search" class="search">
                <label class="searchLabel">Search:</label>
                <input id="searchField" type="text" class="searchField" placeholder="input id or first name">
            </div>

            <div id="searchResult" class="searchResult">

            </div>

        </div>

        <div class="south">
        </div>
    </div>
</div>
</body>
</html>