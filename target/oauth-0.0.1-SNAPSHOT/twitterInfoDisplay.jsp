<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Returned information from Twitter</title>
</head>
<body>

<table>
	<tr>
		<td>First name</td>
		<td><bean:write name="infoDisplayFormModel" property="name" /></td>
	</tr>
	<tr>
		<td>Username</td>
		<td><bean:write name="infoDisplayFormModel" property="username"/></td>
	</tr>
	<tr>
		<td>Twitter API does not expose user's email address</td>
	</tr>
</table>
	<INPUT type="button" value="To Home Page" onClick="location.href='/oauth/index.jsp'">
</body>
</html>