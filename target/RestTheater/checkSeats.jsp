<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 12/12/20
  Time: 6:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>Check seats available together</h3>
    <form action="${pageContext.request.contextPath}/cinema/client/seats" method="post">
        <label for="seats">Enter number of required seats together: </label>
        <input type="text" id="seats" name="seats" required>
        <input type="submit" value="Check">
    </form>
</body>
</html>
