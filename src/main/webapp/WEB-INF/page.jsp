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
    </body>
</html>
