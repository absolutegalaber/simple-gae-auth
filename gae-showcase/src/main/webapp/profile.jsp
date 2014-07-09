<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="userProfile" type="org.simple.auth.model.BasicUserProfile" scope="request"/>
<html>
<head>
    <title>Simple GAE Auth Showcase</title>
</head>

<body>
<h3>All right!</h3>

<table>
    <tr>
        <td>Your ID (in <b><%=userProfile.getNetworkName().toUpperCase()%>)</b>:</td>
        <td>
            <%=userProfile.getNetworkId()%>
        </td>
    </tr>
    <tr>
        <td>Email:</td>
        <td>
            <%=userProfile.getEmail()%>
        </td>
    </tr>
    <tr>
        <td>Full Name:</td>
        <td>
            <%=userProfile.getName()%>
        </td>
    </tr>
    <tr>
        <td>First Name:</td>
        <td>
            <%=userProfile.getFirstName()%>
        </td>
    </tr>
    <tr>
        <td>Last Name:</td>
        <td>
            <%=userProfile.getLastName()%>
        </td>
    </tr>
    <tr>
        <td>Gender:</td>
        <td>
            <%=userProfile.getGender()%>
        </td>
    </tr>
    <tr>
        <td>Locale:</td>
        <td>
            <%=userProfile.getLocale()%>
        </td>
    </tr>
    <tr>
        <td>Your image:</td>
        <td>
            <img src="<%=userProfile.getPictureUrl()%>">
        </td>
    </tr>
</table>
</body>
</html>