<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
</head>
<body>
	<form method="post" action="RegistrationServlet">
		<h1>Registrazione</h1>
		<table>
			<tr>
				<td><label for="username">Username</label></td>
				<td><input type="text" name="username" id="username"></td>
			</tr>
			<tr>
				<td></td>
				<td><small>Il nome può contenere solo lettere e numeri</small></td>
			</tr>
			<tr>
				<td><label for="password">Password</label></td>
				<td><input type="password" name="password" id="password"></td>
			</tr>
			<tr>
				<td></td>
				<td><small>La password deve essere minimo di 8 caratteri e
						contenere almeno una lettera maiuscola, un numero ed un carattere
						speciale</small></td>
			</tr>
			<tr>
				<td><label for="conferma_password">Conferma Password</label></td>
				<td><input type="password" name="conferma_password" id="conferma_password"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Registrati"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="button" value="Ho già un account" onclick="location.href='login.jsp';"></td>
			</tr>
		</table>
	</form>
</body>
</html>