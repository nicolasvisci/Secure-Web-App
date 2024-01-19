<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<form method="post" action="LoginServlet" id="formLogin">
		<table>
			<tr>
				<td>User name</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="pass"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="login"></td>
			</tr>
			<tr>
				<td>Ricordami</td>
				<td><input type="checkbox" name="ricordami"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="registrati"></td>
			</tr>
		</table>
	</form>
</body>
</html>