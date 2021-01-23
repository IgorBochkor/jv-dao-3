<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create driver</title>
</head>
<body>
<h1>Please provide information about driver: name and license number</h1>

<h4 style="color: brown">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/drivers/add">
    Please provide name: <input type="text" name="name">
    license number: <input type="text" name="licenceNumber">
    login: <input type="text" name="login">
    password: <input type="password" name="password">
    <button type="submit">Create driver</button>
</form>
</body>
</html>
