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
    <title>View Car</title>
</head>
<body>
<h1>VIEW CAR</h1>
<hr>
<table>
        <tbody>
        <tr>
            <td>
                Car id:
            </td>
            <td>${viewedCar.id}</td>
        </tr>
        <tr>
            <td>
                Owner:
            </td>
            <td>${viewedCar.owner.id}</td>
            <td><a href="/owners?ownersaction=viewOwner&ownerId=${viewedCar.owner.id}">Go to this owner</a></td>
        </tr>
        <tr>
            <td>
                Brand:
            </td>
            <td>${viewedCar.brand}</td>
        </tr>
        <tr>
            <td>
                Model:
            </td>
            <td>${viewedCar.model}</td>
        </tr>
        <tr>
            <td>
                Plate Number:
            </td>
            <td>Here goes the plate number</td>
        </tr>
        <tr>
    <td>
        <button onclick="location.href = '/cars';">Return to cars list</button>
    </td>
    </tr>
    </tbody>
</table>
<form action="/cars" method="get"
      onsubmit="return confirm('Do you really want to delete this car and its services?');">
    <input type="hidden" name="carsaction" value="deleteThis">
    <input type="hidden" name="carId" value="${viewedCar.id}">
    <input type="submit" value="Delete Car" style="color:red;"/>
</form>
</body>
</html>
