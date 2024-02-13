<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="propostaProgettuale.Proposta"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%
List<Proposta> propostePrimaCaricamento = (List<Proposta>) request.getAttribute("propostePrimaCaricamento");
%>

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

<meta charset="UTF-8">
<title>HomePage - Proposte progettuali</title>
<style>
/* Stile del form */
#uploadForm {
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

/* Stile dei campi del modulo */
input[type="file"] {
  width: 100%;
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
input[type="submit"],
input[type="button"] {
  padding: 10px 20px;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  color: #fff;
  cursor: pointer;
  transition: background-color 0.3s;
}

input[type="submit"]:hover,
input[type="button"]:hover {
  background-color: #0056b3;
}

input[type="button"].logout-button {
	background-color: red;
	color: white;
	cursor: pointer;
}

/* Stile del banner */
#contenutoBanner {
  background-color: #f9ca24;
  color: black;
  padding: 10px;
  margin-top: 20px;
  border-radius: 5px;
}

/* Stile della lista delle proposte */
#listaProposteBanner {
  margin-top: 20px;
  background-color: #dcdde1;
  padding: 10px;
  border-radius: 5px;
}

</style>

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<script>
	function uploadFile() {
		var formData = new FormData(document.getElementById('uploadForm'));

		$
				.ajax({
					url : 'PropostaServlet',
					type : 'POST',
					data : formData,
					processData : false,
					contentType : false,
					success : function(data) {
						// Aggiorna il contenuto del banner con il risultato della risposta AJAX
						$('#contenutoBanner').html(data);

					},
					error : function(xhr, status, error) {
						console.error(
								'Errore durante il caricamento del file:',
								status, error);
					}
				});
	}

	// Chiamata AJAX separata per caricare la lista delle proposte quando la pagina si carica
	$(document).ready(function() {
		caricaListaProposte();
	});

var scrittaAggiunta = false;

var isLogoutExecuted = false;
var isRefreshing = false;
var isProposteLoading = false;

// Funzione per caricare la lista delle proposte
function caricaListaProposte() {
    isProposteLoading = true;

    $.ajax({
        url: 'PropostaServlet',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            // Aggiorna la lista delle proposte nel banner
            var listaProposte = $('#listaProposteBanner');
            listaProposte.empty();

            // Aggiungo la scritta solo se non è stata ancora aggiunta
            if (!scrittaAggiunta) {
                listaProposte.append('<p><strong>Lista proposte progettuali</strong></p>');
                scrittaAggiunta = true; // Imposta la variabile a true dopo l'aggiunta
            }

            // Itera sulla lista delle proposte
            $.each(data, function (index, proposta) {
                // Utilizza un link anziché un paragrafo e aggiungi una classe per lo stile e l'interattività
                var link = $('<a>', {
                    class: 'file-link',
                    href: '#',
                    'data-nomefile': proposta.nomeFile,
                    'data-nomeutente': proposta.username,
                    'data-html': proposta.contenutoHtml,
                    text: 'Username: ' + proposta.username + ', Proposta: ' + proposta.nomeFile
                });
                listaProposte.append($('<li>').append(link));
            });

            
            // Aggiungo uno script jQuery per gestire il clic sul link del file
            $('.file-link').on('click', function (event) {
                event.preventDefault();
                var nomeFile = $(this).data('nomefile');
                var nomeUtente = $(this).data('nomeutente');
                var contenutoHtml = $(this).data('html');

                // Creazione del messaggio in grassetto
                var messaggio = "Stai visualizzando la proposta progettuale <strong>" + nomeFile + "</strong> di <strong>" + nomeUtente + "</strong>";

                
                // Visualizza il messaggio HTML nel banner
                $('#contenutoBanner').html('<p>' + messaggio + '</p>' + contenutoHtml);
            });
            isProposteLoading = false;

        },
        error: function (xhr, status, error) {
            console.error(
                'Errore durante il recupero della lista delle proposte:',
                status, error);
           		isProposteLoading = false;

        }
    });
}

var isLogoutExecuted = false;
var isRefreshing = false;


function logout() {
    if (!isLogoutExecuted) {
        // Effettua una chiamata al server per invalidare la sessione
        $.ajax({
            url: 'LogoutServlet',
            type: 'GET',
            success: function () {
                $('#listaProposteBanner').empty();
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

// Chiamata alla funzione durante il caricamento della pagina
document.addEventListener("DOMContentLoaded", function() {
    checkAuthentication();
    caricaListaProposte(); // Aggiunta la chiamata alla funzione caricaListaProposte
    
    // Quando la pagina è completamente caricata
    var contenutoBanner = $('#contenutoBanner');
    if (contenutoBanner.is(':empty')) {
        contenutoBanner.html('<p>Non stai visualizzando nessuna proposta</p>');
    }
});


</script>

</head>
<body>
	<!-- Form di caricamento proposta -->
	<form id="uploadForm" method="post" action="javascript:void(0);"
		enctype="multipart/form-data" onsubmit="uploadFile()">
		<input type="hidden" name="nomeUtente" value="${nomeUtente}" />
		<table>
			<tr>
				<h1>Benvenuto, ${nomeUtente}!</h1>
			</tr>
			<tr>
				<td>Carica proposta progettuale</td>
				<td><input type="file" name="Proposta progettuale"
					autocomplete="off"></td>

			</tr>
			<tr>
				<td></td>
				<td><small>Il file deve essere di formato .txt e pesare
						massimo 20 MB.</small></td>
			</tr>
			<tr>
				<td><input type="button" class="logout-button" value="Logout"
					onclick="logout()"></td>
				<td><input type="submit" value="Carica Proposta"></td>
			</tr>
			<tr>
				<!-- Banner -->
				<!--<div id="banner">-->

					<div id="contenutoBanner">
						
						<% if (propostePrimaCaricamento == null || propostePrimaCaricamento.isEmpty()) { %>
                    <p>Seleziona una proposta progettuale.</p>
                <% } %>
					</div>
				<!-- </div> -->

			</tr>
			<tr>

				<div id="listaProposteBanner">
                
            </div>

			</tr>
		</table>
	</form>
</body>
</html>