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
    <title>Set up the theater</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/cinema/theater/setup" method="POST">
        <label for="row">Enter number of rows: </label>
        <input type="text" name="row" id="row" required>
        <br>
        <label for="column">Enter number of columns: </label>
        <input type="text" name="column" id="column" required>
        <br>
        <label for="distance">Enter the min distance between different parties: </label>
        <input type="text" name="distance" id="distance" required>
        <br>
        <input type="submit">
    </form>
</body>
</html>
