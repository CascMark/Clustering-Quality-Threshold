@echo off
REM === Avvio applicazione JavaFX ===
setlocal

REM Percorso base (cartella superiore rispetto al .bat)
set BASE=%~dp0..

REM Percorso delle librerie JavaFX
set FXPATH=%BASE%\Jar\javafx-sdk-24.0.2\lib

REM Avvio dell'applicazione
java --module-path "%FXPATH%" --add-modules javafx.controls,javafx.fxml -jar "%BASE%\Jar\app_start_estensione.jar"

pause
