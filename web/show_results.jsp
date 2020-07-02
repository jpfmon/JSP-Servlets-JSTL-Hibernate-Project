<%--
  Created by IntelliJ IDEA.
  User: Juan
  Date: 17/05/2020
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Results</title>
</head>
<body>
<h1 style="text-align: center; color: red">Welcome to the JSP showing results</h1>
<ol>
<c:forEach var="temp" items="${list}">
    <li>${temp}</li>
</c:forEach>
</ol>
<hr>
<p> Prueba de seleccrionar uno</p><br>

<c:set var="id" value="${list[0]}"/>
<c:set var="owner" value="${list[1]}"/>
<c:set var="Modelo" value="${list[2]}"/>

Este es el id del coche: ${id}<br>
Este es el owner del coche: ${owner}<br>
Este es el modelo del coche: ${Modelo}<br>

</body>
</html>
