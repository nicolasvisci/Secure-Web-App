<!DOCTYPE html>
<html lang="it">
<head>

<script>
    
var isUserLoggedIn = <%=request.getAttribute("login")%>;

if (!isUserLoggedIn || isUserLoggedIn == null) {
    // L'utente non è autenticato, reindirizzalo alla pagina di login
    window.location.href = "login.jsp";
} 
</script>

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<script>
var isLogoutExecuted = false;

function logout() {
    if (!isLogoutExecuted) {
        // Effettua una chiamata al server per invalidare la sessione
        $.ajax({
            url: 'LogoutServlet',
            type: 'GET',
            success: function () {
                localStorage.removeItem('nomeUtente');

                // Reindirizza alla pagina di login dopo il logout
                window.location.href = "login.jsp";
            },
            error: function (xhr, status, error) {
                console.error('Errore durante il logout:', status, error);
            }
        });
        isLogoutExecuted = true;
    }
}

//Gestisci il logout quando l'utente sta lasciando la pagina

window.addEventListener('beforeunload', function (event) {
    isRefreshing = true;

    console.log('beforeunload event');

    if (!event.persisted && !isLogoutExecuted && !isRefreshing && !isProposteLoading) {
        // Esegui la funzione di logout solo se necessario e non è un aggiornamento della pagina
                console.log('Performing logout');

        logout();
    }
});

// Gestisci il logout quando l'utente fa clic sul pulsante di navigazione del browser
window.addEventListener('popstate', function () {
    console.log('popstate event');

    if (!isLogoutExecuted && !isRefreshing) {
        // Esegui la funzione di logout solo se necessario
                console.log('Performing logout');

        logout();
    }
});


$(document).ready(function() {
      var inattivitaTimer; // Variabile per memorizzare l'ID del timer

    // Funzione chiamata quando l'utente compie un'azione
    function resetInattivitaTimer() {
        clearTimeout(inattivitaTimer); // Resetta il timer
        inattivitaTimer = setTimeout(function() {
            // Esegui la funzione di logout dopo il periodo di inattività
            logout();
        }, 600000); // 10minuti
    }

    // Chiamata quando la pagina si carica
    $(document).on('click keypress mousemove', function() {
        resetInattivitaTimer();
    });

});


function checkAuthentication() {
    var nomeUtente = "${nomeUtente}"; // Ottieni il valore dalla JSP
    console.log("Nome utente:", nomeUtente); // Aggiungi questa riga per stampare nella console

    if (!nomeUtente || nomeUtente.trim() === "") {
        window.location.href = "login.jsp";
    }
}

</script>

<meta charset="UTF-8">
<title>HomePage - Proposte progettuali</title>
</head>
<body>
	<!-- Form di caricamento proposta -->
	<form id="uploadForm" method="post" action="javascript:void(0);"
		enctype="multipart/form-data" onsubmit="">
		<input type="hidden" name="nomeUtente" value="${nomeUtente}" />
		<table>
			<tr>
				<h1>Benvenuto, ${nomeUtente}!</h1>
			</tr>
			<tr>
				<td><input type="file" name="Proposta progettuale" autocomplete="off"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Carica Proposta"></td>			
			</tr>
			<tr>			
				<td><input type="button" value="Logout" onclick="logout()"></td>
			</tr>	
		</table>
		
	</form>
</body>
</html>
