@echo off
REM === Avvio applicazione JavaFX senza warning ===
setlocal

REM Percorso base (cartella superiore rispetto al .bat)
set BASE=%~dp0..

REM Percorso delle librerie JavaFX
set FXPATH=%BASE%\Jar\javafx-sdk-24.0.2\lib

REM Avvio dell'applicazione con opzioni per sopprimere i warning
java ^
  --enable-native-access=javafx.graphics ^
  --add-opens=java.base/sun.misc=ALL-UNNAMED ^
  --module-path "%FXPATH%" ^
  --add-modules javafx.controls,javafx.fxml ^
  -jar "%BASE%\Jar\app_start_estensione.jar" 2>nul

pause
