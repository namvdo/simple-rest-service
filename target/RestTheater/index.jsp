<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 12/11/20
  Time: 12:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>REST service</title>
  </head>
  <body>
  <h3>ACME Co</h3>
  <h4>Choose an option: </h4>
  <ul>
      <li><a href="${pageContext.request.contextPath}/cinema">Operating cinema</a></li>
      <li><a href="${pageContext.request.contextPath}/client">Features for clients</a></li>
  </ul>
  </body>
</html>
