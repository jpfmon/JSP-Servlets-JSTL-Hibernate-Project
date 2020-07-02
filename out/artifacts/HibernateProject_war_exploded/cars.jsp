<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var='logged' value="${sessionScope.login}"/>
<c:if test="${logged != true}">
    <c:redirect url="/login"/>
</c:if>
<head>
    <title>Owners Dashboard</title>
    <style>
        .header {
            border-width: 1px;
            border-style: solid;
            border-color: green;
            text-align: center;
        }

        .container {
            border-width: 2px;
            border-style: solid;
            border-color: red;
            display: flex;
        }

        .box {
            border-width: 2px;
            border-style: solid;
            border-color: black;
            flex: 1; /* additionally, equal width */
            padding: 1em;
            border: solid;
            text-align: center;
        }

        .listsBox {
            border-width: 2px;
            border-style: solid;
            border-color: violet;
            margin: auto;
            text-align: center;
            display: flex;
        }

        .lists {
            flex: 1; /* additionally, equal width */
            padding: 1em;
            border: solid;
            color: orange;
            text-align: center;
        }

        .head {
            color: black;
        }
    </style>
</head>
<body>
<h1>Here you can find all the cars listed</h1>
<%--hola<c:out value="${carslist}"/>--%>
<hr>
<div>
    <p style="color: red">${error}</p>
    <table>
        <thead>
        <tr>
            <td>
                <form action="cars" method="get">
                    <input type="hidden" name="carsaction" value="newcar"/>
                    <input type="submit" value="Add New Car"/>
                </form>
            </td>
        </tr>
        </thead>
    </table>
</div>
<div class="listsBox">
    <table class="lists">
        <thead class="head">
        <tr>
            <td>Car Id</td>
            <td>Owner Id</td>
            <td>Brand</td>
            <td>Model</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cartemp" items="${carslist}">
            <c:url var="tempLink" value="/cars">
                <c:param name="carsaction" value="viewCar"/>
                <c:param name="carId" value="${cartemp.id}"/>
            </c:url>
            <tr>
                <td>${cartemp.id}</td>
                <td>${cartemp.owner.id}</td>
                <td>${cartemp.brand}</td>
                <td>${cartemp.model}</td>
                <td><a href="${tempLink}">View Car</a>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<form action="/dashboard">
    <input type="submit" value="Main Dashboard"/>
</form>
</body>
</html>
