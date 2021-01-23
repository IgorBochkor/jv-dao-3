<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<h1>Login page</h1>

<h4 style="color: brown">${errMessage}</h4>

<body>
<form method="post" action="${pageContext.request.contextPath}/login">
    login: <input type="text" name="login">
    password: <input type="password" name="password">
    <button type="submit">Login</button>
</form>
</body>
</html>
