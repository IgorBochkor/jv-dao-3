<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create manufacturer</title>
</head>
<body>
<h1>Please provide information about manufacturer: name and country</h1>

<h4 style="color: brown">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/manufacturer/add">
    Please provide name: <input type="text" name="name">
    Please provide country: <input type="text" name="country">

    <button type="submit">Create manufacturer</button>

</form>
</body>
</html>
