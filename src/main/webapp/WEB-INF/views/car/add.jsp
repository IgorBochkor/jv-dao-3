<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create car</title>
</head>
<body>
<h1>Please provide information about car: model and manufacturerId</h1>

<h4 style="color: brown">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/car/add">
    Please provide model: <input type="text" name="model">
    Please provide manufacturerId: <input type="text" name="manufacturerId">

    <button type="submit">Create car</button>
</form>
</body>
</html>
