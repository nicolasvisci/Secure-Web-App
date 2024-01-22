<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>HomePage - Proposte progettuali</title>
</head>
<body>
	<h1>Benvenuto</h1>
	<h2>Carica proposta progettuale</h2>
	<form id="uploadForm" method="post" action=""
		enctype="multipart/form-data" onsubmit="">
		<table>
			<tr>
				<td><input type="file" name="Proposta progettuale" autocomplete="off"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Carica Proposta"></td>			
			</tr>
			<tr>			
				<td><input type="button" value="Logout" onclick="location.href='login.jsp';"></td>
			</tr>	
		</table>
		
	</form>
</body>
</html>
