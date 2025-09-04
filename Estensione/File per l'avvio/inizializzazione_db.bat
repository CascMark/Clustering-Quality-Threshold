@echo off

"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p < "inizializzazione_db.sql"

IF ERRORLEVEL 1 (
echo Errore durante l'inizializzazione del database. Controllare il file di log di MySQL per maggiori informazioni.
) ELSE (
echo Inizializzazione del Database MapDB e dell'utente MapUSER riuscita.
)

pause