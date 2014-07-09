

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="authException" type="org.simple.auth.model.OAuthException" scope="request"/>
<html>
<head>
    <title>Simple GAE Auth Showcase</title>
</head>

<body>
<h3>Damn</h3>

<%=authException.getMessage()%>

</body>
</html>
