<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
<head lang="en">
    <c:url value="/resources/css/style.css" var="css"/>
    <c:url value="/resources/js/index.js" var="js"/>
    <c:url value="/resources/js/jquery-2.1.3.js" var="jquery"/>
    <link href="${css}" rel="stylesheet">
    <script src="${jquery}" type="text/javascript"></script>
    <script src="${js}" type="text/javascript"></script>
    <title>spring_js</title>
</head>
<body>
<div class="parent">
    <div class="main">
        <div class="north">

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
                <div id="form" class="form">
                    <div>
                        <label class="formLabel">Employee Number:</label>
                        <input id="number" type="text" class="textField">
                    </div>
                    <div>
                        <label class="formLabel">Employee name:</label>
                        <input id="name" type="text" class="textField">
                    </div>

                    <div id="cancel" class="formButtonCancel">Cancel</div>
                    <div id="formAddBtn" class="formButtonAdd">Add Employee</div>
                </div>

                <div class="tableTitle">
                    <label class="tableTitleId">ID</label>
                    <label class="tableTitleName">NAME</label>
                </div>
            </div>
        </div>

        <div class="east">

        </div>

        <div class="south">

        </div>
    </div>
</div>

</body>
</html>