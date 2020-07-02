<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<c:set var = 'logged' value = "${sessionScope.login}"/>--%>
<%--<c:if test="${logged != true}">--%>
<%--    <c:redirect url="/login"/>--%>
<%--</c:if>--%>
<%--<head>--%>
<%--    <title>Services Dashboard</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Here you can find all the services listed</h1>--%>

<%--<form action="/dashboard">--%>
<%--    <input type="submit" value="Main Dashboard"/>--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var='logged' value="${sessionScope.login}"/>
<c:if test="${logged != true}">
    <c:redirect url="/login"/>
</c:if>
<head>
    <title>Services Dashboard</title>
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
<h1>Here you can find all the services listed</h1>
<%--hola<c:out value="${carslist}"/>--%>
<hr>
<div>
    <p style="color: red">${error}</p>
    <table>
        <thead>
        <tr>
            <td>
                <form action="services" method="get">
                    <input type="hidden" name="serviceaction" value="newservice"/>
                    <input type="submit" value="Add New Service"/>
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
            <td>Service Id</td>
            <td>Name</td>
            <td>Date</td>
            <td>Car Id</td>
            <td>Notes</td>
            <td>Price</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="servicetemp" items="${serviceslist}">
            <c:url var="tempLink" value="/services">
                <c:param name="serviceaction" value="viewService"/>
                <c:param name="serviceId" value="${servicetemp.id}"/>
            </c:url>
            <tr>
                <td>${servicetemp.id}</td>
                <td>${servicetemp.name}</td>
                <td>${servicetemp.date}</td>
                <td>${servicetemp.car.id}</td>
                <td>${servicetemp.notes}</td>
                <td>${servicetemp.price}</td>
                <td><a href="${tempLink}">View Service</a>
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
