<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<html>
<head>
<script type="text/javascript">
function submitGoogleAuth()
{
document.forms[0].action = "UserAction.do?method=doGoogleAuth";
document.forms[0].submit();
}

function submitTwitterAuth()
{
document.forms[0].action = "UserAction.do?method=doTwitterAuth";
document.forms[0].submit();
}
</script>
</head>
<body>
<html:form action="/UserAction">
	<table>
		<tr>
			<td>Choose login provider:</td>
		</tr>
		<tr></tr>
		<tr>
			<td><html:submit value="GOOGLE" onclick="submitGoogleAuth()"/></td>
		</tr>
		<tr></tr>
		<tr>
			<td><html:submit value="TWITTER" onclick="submitTwitterAuth()" /></td>
		</tr>
	</table>
</html:form>
</body>
</html>