<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 12/12/20
  Time: 5:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cinema features</title>
</head>
<body>
    <h3>Features for clients: </h3>
    <ul>
        <li><a href="${pageContext.request.contextPath}/cinema/client/seats">Check available seat sets</a></li>
        <li><a href="${pageContext.request.contextPath}/cinema/client/reserve">Reserve a seat</a></li>
        <li><a href="${pageContext.request.contextPath}/cinema/client/view">View the theater</a></li>
    </ul>
</body>
</html>
