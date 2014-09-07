<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/my.tld" prefix="m" %>

<%
String time = (String)request.getAttribute("currentTime");
%>

<!doctype html>
<html>
    <head>
        <title>Hello World!</title>
    </head>
    <body>
        <h1>Hello JSP!</h1>
        <h2>Time is <%= time %></h2>
        <h3>Time is ${currentTime}</h3>
        <ul>
        <c:forEach var="thing" items="${things}">
            <li>${thing}</li>
        </c:forEach>
        </ul>
        <m:helloWorld />
    </body>
</html>
