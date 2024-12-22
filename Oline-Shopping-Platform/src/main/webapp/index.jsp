<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<form action="hello-servlet" method="post">
    <input type="text" name="num1"><br>
    <input type="text" name="num2"><br>
    <input type="submit" value="Submit">
</form>
<form action="ProductDetailsServlet?id=1" method="get">
    <input type="text" name="email" placeholder="Username"><br>
    <input type="password" name="password" placeholder="Password"><br>
    <input type="submit" value="Login">
</form>

<br/>
</body>
</html>