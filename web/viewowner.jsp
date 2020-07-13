<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Owner Form</title>
</head>
<body>
<h1>DETAILS OF OWNER: <c:out value="${viewOwner.id}"/></h1>
<hr>
<table>
    <form action="owners" method="get">
        <input type="hidden" name="ownersaction" value="updateThis">
        <input type="hidden" name="ownerId" value="${viewOwner.id}">
        <tbody>
        <tr>
            <td>
                Full Name:
            </td>
            <td><input type="text" name="fullName" value="${viewOwner.fullName}"></td>
        </tr>
        <tr>
            <td>
                Id Card Number:
            </td>
            <td><input type="text" name="idCard" value="${viewOwner.idCardNumber}"></td>
        </tr>
        <tr>
            <td>
                Phone Number:
            </td>
            <td><input type="text" name="phoneNumber" value="${viewOwner.phone}"></td>
        </tr>
        <tr>
            <td>
                Email:
            </td>
            <td><input type="email" name="email" value="${viewOwner.email}"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Save Edits"></td>
    </form>
    <td>
        <button onclick="location.href = '/owners';">Cancel</button>
    </td>
    </tr>
    </tbody>
</table>
<form action="owners" method="get"
      onsubmit="return confirm('Do you really want to delete this owner and all Cars and Services associated to it?');">
    <input type="hidden" name="ownersaction" value="deleteThis">
    <input type="hidden" name="ownerId" value="${viewOwner.id}">
    <input type="submit" value="Delete Owner" style="color:red;"/>
</form>
<hr>
<div>
    <div>
        <h3>Cars Owned</h3>
        <table>
            <thead>
            <tr>
                <td></td>
                <td>Car id</td>
                <td>Car brand</td>
                <td>Car model</td>
                <td>Services</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cartemp" items="${carsList}">
                <tr>
                <td><a href="/cars?carsaction=viewCar&carId=${cartemp.id}">View this car</a></td>
                <td>${cartemp.id}</td>
                <td>${cartemp.brand}</td>
                <td>${cartemp.model}</td>
<%--When using Eager Fetch, can be like this: --%>
<%--                <c:forEach var="servtemp" items="${cartemp.services}">--%>
                <c:forEach var="servtemp" items="${servicesList}">
                    <c:if test="${servtemp.car.id == cartemp.id}">
                        <c:url var="tempLinkSer" value="/services">
                            <c:param name="serviceaction" value="viewService"/>
                            <c:param name="serviceId" value="${servtemp.id}"/>
                        </c:url>
                        <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><a href="${tempLinkSer}">${servtemp.id}</a> | ${servtemp.name} | ${servtemp.date}
                            | ${servtemp.price} | ${servtemp.notes}</td>
                    </c:if>
                </c:forEach>
                </tr>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
