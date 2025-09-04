@echo off
setlocal

REM Percorso dello script
set "SCRIPT_DIR=%~dp0"

REM Percorso completo di mysql.exe
set "MYSQL_PATH=C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"

REM Messaggio iniziale
echo Avvio esecuzione SQL per la tabella di esempio...

REM Esegui il file SQL usando ridirezione e password interattiva
"%MYSQL_PATH%" -u MapUser -p < "%SCRIPT_DIR%tabella_campione.sql"

REM Controlla l'errore
if %ERRORLEVEL% neq 0 (
    echo.
    echo ERRORE: Impossibile eseguire lo script SQL.
    echo Verifica la password e che l'utente MapUser abbia accesso al database MapDB.
    pause
    exit /b 1
) else (
    echo.
    echo SUCCESSO: Lo script SQL e' stato eseguito correttamente!
)

pause
