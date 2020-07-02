<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Juan
  Date: 28/05/2020
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Service</title>
</head>
<body>
<h1>VIEW SERVICE</h1>
<hr>
<table>
        <tbody>
        <tr>
            <td>
                Service id:
            </td>
        <td>${viewedService.id}</td>
        </tr>
        <tr>
            <td>
                Name:
            </td>
            <td>${viewedService.name}</td>
        </tr>
        <tr>
            <td>
                Date:
            </td>
        <td>${viewedService.date}</td>
        </tr>
        <tr>
            <td>
                Car Id:
            </td>
            <td>${viewedService.carId}</td>
            <td><a href="/cars?carsaction=viewCar&carId=${viewedService.carId}">Go to this car</a></td>
        </tr>
        <tr>
            <td>
                Notes:
            </td>
        <td>${viewedService.notes}</td>
        </tr>
        <tr>
            <td>
                Price:
            </td>
        <td>${viewedService.price}</td>
        </tr>
        <tr>
    <td>
        <button onclick="location.href = '/services';">Return</button>
    </td>
    </tr>
    </tbody>
</table>
<form action="/services" method="get"
      onsubmit="return confirm('Do you really want to delete this service?');">
    <input type="hidden" name="serviceaction" value="deleteThis">
    <input type="hidden" name="serviceId" value="${viewedService.id}">
    <input type="submit" value="Delete Service" style="color:red;"/>
</form>
</body>
</html>
