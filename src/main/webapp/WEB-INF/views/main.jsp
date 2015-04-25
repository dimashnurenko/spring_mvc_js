<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
<head lang="en">
    <c:url value="/resources/css/main.css" var="css"/>
    <c:url value="/resources/css/notification.css" var="notificationCss"/>
    <c:url value="/resources/css/register.css" var="registerCss"/>
    <c:url value="/resources/js/main.js" var="js"/>
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
<div id="employeeId" hidden="hidden"></div>
<div id="parent" class="main">
    <div class="main">
        <div class="north">
            <div id="registerBtn" class="registerPanelButton font">Register</div>
            <div id="welcomeUser" class="welcomeUser">Hello, <%=session.getAttribute("login")%>
            </div>
        </div>

        <div class="west">
            <div class="employeeTitle font">
                <label class="employeesListTitle">Employees list:</label>
            </div>
            <div class="leftScroll">
                <div id="elements" class="elements">
                    <div id="addEmployeeBtn" class="addEmployeeBtn">
                        <label class="addEmployeeLabel font">Add Employee</label>
                    </div>
                </div>

            </div>

        </div>

        <div class="center">Center

        </div>

        <div class="east">East

        </div>

        <div class="south">South

        </div>
    </div>
</div>
</body>
</html>