<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Simple GAE Auth Showcase</title>
</head>

<body>
<h3>Sers</h3>


<a href="/auth?network=google">Google Login</a>
<a href="/auth?network=facebook">Facebook Login</a>
<a href="/auth?network=twitter">Twitter Login</a>


</body>
</html>
