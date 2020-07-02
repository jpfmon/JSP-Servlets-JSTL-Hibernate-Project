<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Juan
  Date: 14/05/2020
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var='logged' value="${sessionScope.login}"/>
<c:if test="${logged !=null}">
    <c:if test="${logged == true}">
        <c:redirect url="/dashboard" />
    </c:if>
</c:if>
<head>
    <title>Car Management Login</title>
</head>
<body>

<h1>Login to Car Management app</h1>
<br>
<hr>
<div>
    <c:set var="logged" value="${sessionScope.login}"/>
<%--    <p><c:out value="${logged}"/></p>--%>
    <p style="color: red">${error}</p>
    <form action="login" method="post">
        <table>
            <tbody>
            <tr>
                <td><label> Username:</label></td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td><label> Password: </label></td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td><label></label></td>
                <td>
                    <button type="submit">Login</button>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <hr>
</div>


<%--<%! int Name(){ return 2+2;}
%>
<%= new java.util.Date() %>
<br>
<%! int SecondName(){
  return 15;
}
int sum;%>
<% sum = Name() + SecondName();%>
La suma total es: <%= sum %>--%>


<%--  <% response.sendRedirect("/holahola");%>--%>
</body>
</html>
