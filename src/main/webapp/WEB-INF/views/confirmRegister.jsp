<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
<head lang="en">
    <c:url value="/resources/css/register.css" var="registerCss"/>
    <link href="${registerCss}" rel="stylesheet">
    <meta charset="UTF-8">
    <title>Register Confirmation</title>
</head>
<body>
<div class="registerConfirmation">
    <label class="confirmLabel">Congratulations, ${login}</label>
    <label class="confirmLabel">The registry link was sent to your email...</label>
    <label class="confirmLabel">Go to link in email to confirm the registry...</label>
</div>

</body>
</html>