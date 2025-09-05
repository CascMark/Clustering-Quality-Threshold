# Casi di test - Estensione - Marco Pio Cascella

## **Indice**

[1. Introduzione](#1-introduzione)

[2. Input validi](#2-input-validi)

[3. Input non validi](#3-input-non-validi)

## **1. Introduzione**

All'interno del seguente documento vengono analizzati i principali comportamenti del software "QTClustering". Per comodità e per garantire una migliore leggibilità e analisi, si è deciso di dividere il documento in due sezioni:

- **Input validi**: Sezione dove vengono mostrati tutti gli input che il software accetta e gestisce con successo.

- **Input non validi**: Sezione dove vengono mostrati tutti gli input che il software non accetta e/o non riconosce e lo segnala all'utente tramite messaggio d'errore stampato a video.

## **2. Input validi**

- Nella modalità **DATABASE**, inserendo il nome di una tabella esistente nel database e un raggio valido, produrrà a schermo il risultato dell'algoritmo di clustering:

![database_clus](./img%20docs/database_clus.png)

- Nella modalità **FILE**, inserendo il nome di un file esistene, verrà mostrato a video il suo contenuto:

![file_clus](./img%20docs/file_clus.png)

- Avviando il file inizializzazione_db.bat e inserendo la password corretta utilizzata dall'user root, verrà mostrato a video un messaggio di successo nella creazione del db e dell'user:

![db_success](./img%20docs/db_success.png)

- Avviando il file tabella_campione.bat e inserendo la password "map", utilizzata per identificare l'user MapUser, verrà mostrato a video un messaggio di successo nell'esecuzione dello script sql per creare la tabella d'esempio:

![tab_success](./img%20docs/tab_success.png)

- Avviando il file di setup del server, in assenza di errori, il file .bat mostrerà a video il successo dell'avvio del server:

![server_success](./img%20docs/server_success.png)


- Avviando il file di setup del client, se il server è stato avviato correttamente, il file mostrerà a video la seguente schermata (Schermata di avvio del software):

![client_success](./img%20docs/start.png)

Di conseguenza nel file di setup del server comparirà il seguente messaggio che segnala la connessione avvenuta con successo:

![server_client_success](./img%20docs/server_client_success.png)

## **3. Input non validi**

- Avviando il file setup del Client senza avviare il server, farà comparire il flag dello stato della connessione a rosso, indicando che non è stato possibile stabilire la connessione al server:

![no_conn](./img%20docs/no_connection.png)

- Nella modalità **FILE**, inserendo il nome di un file non esistente, provocheremo un errore che verrà stampato a video dal software:

![file_fail](./img%20docs/file_FAIL.png)

- Nella modalità **DATABASE**, inserendo il nome di una tabella non esistente nel database, provocheremo un errore che verrà stampato a video dal software:

![db_FAIL](./img%20docs/database_FAIL.png)

- Nella modalità **DATABASE**, inserendo un raggio non valido (dove per raggio non valido si intende un raggio eccessivamente grande o un numero intero <0), il software segnalerà all'utente la non validità del raggio:

![rad_fail](./img%20docs/radius_FAIL.png)

- Nel caso di mancata creazione del database o di un qualsiasi problema di connessione al database SQL, selezionando la modalità **DATABASE** e provando a effettuare il clustering su una tabella, l'utente riceverà a video un messaggio di errore dove il software segnala la non esistenza del db o la mancata connessione ad esso:

![db_error](./img%20docs/sql_error.png)

- L'apertura del Client.bat senza l'avvio del server o l'improvvisa disconnessione del server, farà terminare l'applicazione mostrando a video un messaggio di errore:

![server_error](./img%20docs/server_error.png)

- L'inserimento di una password errata da quella dell'user root all'interno del file inizializzazione_db.bat provocherà un errore che impedirà la creazione del db:

![root_error](./img%20docs/root_error.png)

- L'inserimento di una password che non sia "map" all'interno del file tabella_campione.bat provocherà un errore che impedirà la corretta esecuzione dello script sql per la creazione della tabella di esempio:

![map_error](./img%20docs/map_error.png)