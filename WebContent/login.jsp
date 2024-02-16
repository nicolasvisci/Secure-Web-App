<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<style>
/* Stile del form */
#formLogin {
	max-width: 400px;
	margin: 0 auto;
	padding: 20px;
	background-color: #f7f7f7;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

/* Stile dell'intestazione */
h1 {
	font-size: 24px;
	color: #333;
	margin-bottom: 20px;
}

/* Stile della tabella */
table {
	width: 100%;
}

td {
	padding: 5px;
}

/* Stile dei campi del modulo */
input[type="text"], input[type="password"] {
	width: calc(100% - 20px);
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

/* Stile per i pulsanti */
input[type="submit"] {
	width: 100%;
	padding: 10px 20px;
	background-color: #008000;
	border: none;
	border-radius: 5px;
	color: #fff;
	cursor: pointer;
	transition: background-color 0.3s;
	margin-top: 10px;
}

input[type="button"] {
	width: 100%;
	padding: 10px 20px;
	background-color: #007bff;
	border: none;
	border-radius: 5px;
	color: #fff;
	cursor: pointer;
	transition: background-color 0.3s;
	margin-top: 10px;
}

input[type="submit"]:hover {
	background-color: #90ee90;
}

input[type="button"]:hover {
	background-color: #0056b3;
}
</style>
</head>
<body>
	<div>
		<form method="post" action="LoginServlet" id="formLogin">
			<h1>Login</h1>
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="pass"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Login"></td>
				</tr>
				<tr>
					<td>Ricordami</td>
					<td><input type="checkbox" name="ricordami"></td>
				</tr>
			</table>
			<input type="button" value="Registrati" id="registratiButton"
				onclick="location.href='registration.jsp';">
		</form>
	</div>
</body>
</html>