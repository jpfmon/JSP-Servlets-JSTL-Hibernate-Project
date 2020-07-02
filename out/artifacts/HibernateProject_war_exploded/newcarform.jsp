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
    <title>New Car Form</title>
</head>
<body>
<h1>PLEASE, INTRODUCE DETAILS OF NEW CAR</h1>
<hr>
<table>
    <form action="/cars" method="get">
        <input type="hidden" name="carsaction" value="addthis">
        <tbody>
        <tr>
            <td>
                Owner:
            </td>
            <td>
                <select name="ownerId" required>
                    <c:forEach var="ownerTemp" items="${ownersList}">
                        <option value="${ownerTemp.id}">${ownerTemp.fullName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                Brand:
            </td>
            <td><input type="text" name="brand" required></td>
        </tr>
        <tr>
            <td>
                Model:
            </td>
            <td><input type="text" name="model" required></td>
        </tr>
        <tr>
            <td>
                Plate Number:
            </td>
            <td><input type="text" name="plate" required></td>
        </tr>
        <tr>
            <td><input type="submit" value="Save"></td>
    </form>
    <td>
        <button onclick="location.href = '/cars';">Cancel</button>
    </td>
    </tr>
    </tbody>

</table>
</body>
</html>
