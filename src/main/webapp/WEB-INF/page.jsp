<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/my.tld" prefix="m" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="m2" %>

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
        <m:helloWorld message="I am HelloWorldTag with custom message" />

        <h5>Custom tag - single instance</h5>
        <m2:person name="loki2302" age="100" />

        <h5>Custom tag - list</h5>
        <ul>
        <c:forEach var="person" items="${people}">
            <li><m2:person name="${person.name}" age="${person.age}" /></li>
        </c:forEach>
        </ul>
    </body>
</html>
