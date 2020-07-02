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
<h1>Here you can find all the owners listed</h1>
<%--hola<c:out value="${ownerslist}"/>--%>
<hr>
<div>
    <p style="color: red">${error}</p>
    <table>
        <thead>
        <tr>
            <td>
                <form action="owners" method="get">
                    <input type="hidden" name="ownersaction" value="newowner"/>
                    <input type="submit" value="Add New Owner"/>
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
            <td>Id</td>
            <td>Full name</td>
            <td>Id Card Number</td>
            <td>Phone</td>
            <td>Email</td>
            <td>View owner</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ownertemp" items="${ownerslist}">
            <c:url var="tempLink" value="/owners">
                <c:param name="ownersaction" value="viewOwner"/>
                <c:param name="ownerId" value="${ownertemp.id}"/>
            </c:url>
            <tr>
                <td>${ownertemp.id}</td>
                <td>${ownertemp.fullName}</td>
                <td>${ownertemp.idCardNumber}</td>
                <td>${ownertemp.phone}</td>
                <td>${ownertemp.email}</td>
                <td><a href="${tempLink}">View Owner</a>
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
