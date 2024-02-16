<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
<style>
/* Stile del form */
form {
	max-width: 600px;
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
input[type="text"], input[type="password"], input[type="file"] {
	width: calc(100% - 20px);
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

/* Stile per il messaggio di avviso */
small {
	color: #888;
}

/* Stile per i pulsanti */
input[type="submit"] {
	padding: 10px 20px;
	background-color: #008000;
	border: none;
	border-radius: 5px;
	color: #fff;
	cursor: pointer;
	transition: background-color 0.3s;
}

input[type="button"] {
	padding: 10px 20px;
	background-color: #007bff;
	border: none;
	border-radius: 5px;
	color: #fff;
	cursor: pointer;
	transition: background-color 0.3s;
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
	<form method="post" action="RegistrationServlet"
		enctype="multipart/form-data">
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
				<td><small>La password deve essere minimo di 8
						caratteri e contenere almeno una lettera maiuscola, un numero ed
						un carattere speciale</small></td>
			</tr>
			<tr>
				<td><label for="conferma_password">Conferma Password</label></td>
				<td><input type="password" name="conferma_password"
					id="conferma_password"></td>
			</tr>
			<tr>
				<td><label for="ImmagineProfilo">Immagine Profilo</label></td>
				<td><input type="file" name="ImmagineProfilo"
					id="ImmagineProfilo"></td>
			</tr>
			<tr>
				<td></td>
				<td><small>L'immagine deve essere di un formato
						.png/.jpeg/.jpg e pesare massimo 5MB</small></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Registrati"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="button" value="Ho già un account"
					onclick="location.href='login.jsp';"></td>
			</tr>
		</table>
	</form>
</body>
</html>