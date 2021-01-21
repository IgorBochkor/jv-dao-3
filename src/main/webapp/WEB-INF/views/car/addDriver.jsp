<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver for car</title>
</head>
<body>
<h1>Please provide information adding driver for car: Driver id and Car id</h1>

<h4 style="color: brown">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/car/driver/add">
    Please provide Driver id: <input type="number" name="driverId">
    Please provide Car id: <input type="number" name="carId">
    <button type="submit">Add driver</button>
</form>
</body>
</html>
