<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
<head lang="en">
    <c:url value="/resources/css/main.css" var="css"/>
    <c:url value="/resources/css/notification.css" var="notificationCss"/>
    <c:url value="/resources/css/register.css" var="registerCss"/>
    <c:url value="/resources/css/search.css" var="searchCss"/>
    <c:url value="/resources/css/filter_list_box.css" var="listBoxCss"/>
    <c:url value="/resources/js/main.js" var="js"/>
    <c:url value="/resources/js/notification.js" var="notificationJS"/>
    <c:url value="/resources/js/register.js" var="registerJs"/>
    <c:url value="/resources/js/more_info.js" var="more_infoJs"/>
    <c:url value="/resources/js/search.js" var="searchJS"/>
    <c:url value="/resources/js/filter_list_box.js" var="listBoxJS"/>
    <c:url value="/resources/js/libraries/jquery-2.1.3.js" var="jquery"/>
    <c:url value="/resources/js/libraries/jquery-ui.min.js" var="jqueryUI"/>
    <c:url value="/resources/css/more_info.css" var="moreInfoCss"/>
    <link href="${moreInfoCss}" rel="stylesheet">
    <link href="${css}" rel="stylesheet">
    <link href="${registerCss}" rel="stylesheet">
    <link href="${notificationCss}" rel="stylesheet">
    <link href="${searchCss}" rel="stylesheet">
    <link href="${listBoxCss}" rel="stylesheet">
    <script src="${jquery}" type="text/javascript"></script>
    <script src="${more_infoJs}" type="text/javascript"></script>
    <script src="${searchJS}" type="text/javascript"></script>
    <script src="${notificationJS}" type="text/javascript"></script>
    <script src="${jqueryUI}" type="text/javascript"></script>
    <script src="${js}" type="text/javascript"></script>
    <script src="${registerJs}" type="text/javascript"></script>
    <script src="${listBoxJS}" type="text/javascript"></script>
    <title>employees data base</title>
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
            <div class="header">
                <div class="employeeTitle font">
                    <label class="employeesListTitle">Employee's list:</label>
                </div>

                <div id="deleteEmployeeBtn" class="addEmployeeBtn">
                    <label class="addEmployeeLabel font">Delete</label>
                </div>
                <div id="addEmployeeBtn" class="addEmployeeBtn">
                    <label class="addEmployeeLabel font addBtnMargin">Add</label>
                </div>
            </div>
            <div class="elementsPanel">
                <div class="leftScroll">
                    <div id="elements" class="elements"></div>
                </div>
            </div>

        </div>

        <div class="center">
            <div class="scroll">
                <div class="mainInfoForm">
                    <label class="formLabelAddress font">INN:</label>
                    <input id="infoEmployeeId" type="text" class="textFieldAddress">
                    <label class="formLabelAddress font">First Name:</label>
                    <input id="infoEmployeeName" type="text" class="textFieldAddress">
                    <label class="formLabelAddress font">Last Name:</label>
                    <input id="infoEmployeeLName" type="text" class="textFieldAddress">

                    <div class="splitter">_____________________________________</div>
                </div>

                <div class="buttonsPanelMainInfo">
                    <div id="saveMoreInfo" class="buttonAddress font">
                        <label class="infoBtn">Save</label>
                    </div>
                </div>
                <div class="addressLabel font">Address:</div>

                <div class="addressForm">
                    <label class="formLabelAddress font">Country:</label>
                    <input id="country" type="text" class="textFieldAddress">
                    <label class="formLabelAddress font">City:</label>
                    <input id="city" type="text" class="textFieldAddress">
                    <label class="formLabelAddress font">Street:</label>
                    <input id="street" type="text" class="textFieldAddress">
                    <label class="formLabelAddress font">House:</label>
                    <input id="house" type="text" class="textFieldAddress">
                    <label class="formLabelAddress font">Flat:</label>
                    <input id="flat" type="text" class="textFieldAddress">

                    <div class="splitter">_____________________________________</div>

                </div>

                <div class="buttonsPanelAddress">
                    <div id="saveAddress" class="buttonAddress font">
                        <label class="infoBtn">Save</label>
                    </div>
                    <div id="deleteAddress" class="buttonAddress font">
                        <label class="infoBtn">Delete</label>
                    </div>
                </div>
            </div>
        </div>

        <div class="east">
            <div id="notification" class="notification"></div>
            <div class="searchPanel">
                <div class="searchLabel font">Quick Search:</div>
                <input class="searchField" id="searchField" type="text" placeholder="enter id">

                <div class="searchScroll">
                    <div id="searchedElements" class="searchedElements"></div>
                </div>

                <div id="filter" class="filter">
                </div>

            </div>
        </div>

        <div class="south">South

        </div>
    </div>
</div>
</body>
</html>