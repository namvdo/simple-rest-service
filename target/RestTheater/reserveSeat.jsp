<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 12/12/20
  Time: 6:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Reserve a seat</h3>
<h5>Note: You need to enter both column and row position,
    the first seat starts with the coordinate (0, 0) - the
    first 0 is the row position, where the second 0 is the
    column position. You also need to provide your party
</h5>
<form action="${pageContext.request.contextPath}/cinema/client/reserve" method="post">
    <label for="party">Enter your party: </label>
    <input type="text" name="party" id="party" required>
    <br>
    <label for="row">Enter the row position: </label>
    <input type="text" name="row" id="row" required>
    <br>
    <label for="column">Enter the column position: </label>
    <input type="text" name="column" id="column" required>
    <br>
    <input type="submit" value="Reserve">
</form>
</body>
</html>
