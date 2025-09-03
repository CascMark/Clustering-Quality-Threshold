# **Manuale utente - QTClustering - Estensione**

## **Indice**

[1. Introduzione](#1-introduzione)

[2. Guida di installazione](#2-guida-di-installazione)

[3. Esecuzione del sistema](#3-esecuzione-del-sistema)

[4. Manuale d'uso](#4-manuale-duso)

[5. Architettura di sistema UML](#5-architettura-di-sistema-uml)

## **1. Introduzione**

Il progetto QTClustering si svolge durante l'anno accademico 2024-2025 nel corso di "Metodi avanzati di programmazione".
L'obiettivo principale di questo progetto è quello di creare un sistema client-server capace di implementare il clustering quality threshold.
([Per maggiori informazioni riguardo al clustering e in particolare a questo tipo di clustering](https://it.wikipedia.org/wiki/Clustering))

Come anticipato precedentemente, il nostro sistema si basa su due principali componenti:
- **Server:** Componente formata dalle principali classi che regolano e governano l'algoritmo di clustering.

- **Client:** Componente che permette all'utente di poter interagire con il lato server dell'applicazione e di poter utilizzare i servizi di clustering (e anche altri servizi).

L'estensione differisce dal progetto base grazie all'implementazione di una GUI (gestita dal lato Client), realizzata mediante le librerie offerte da [JavaFX](https://openjfx.io/) e [SceneBuilder](https://gluonhq.com/products/scene-builder/).

## **2. Guida di installazione**

Per installare il software QTClustering, è necessario seguire i seguenti passaggi:

1. **Scaricare e installare il Java Development Kit (JDK):**

- Scaricare il JDK versione 24.0.2 dal sito ufficiale di [Oracle](https://www.oracle.com/java/technologies/downloads/?er=221886)

- Dopo averlo scaricato, procediamo con l'installazione tramite terminale di Windows:
  
  - Aprire il **Prompt dei comandi come amministratore** e digitare il seguente comando:

   ```cmd
       curl -o jdk-24_windows-x64_bin.exe https://download.oracle.com/java/24/latest/jdk-24_windows-x64_bin.exe
   ```

  - Una volta completato il download, installare l'eseguibile del JDK eseguendo il comando:
   ```cmd
       jdk-24_windows-x64_bin.exe
   ```
  - Questo avvierà il processo di installazione del JDK.  
       Al termine dell'installazione, verificare che sia andata a buon fine digitando il seguente comando:
       ```cmd
       java -version
       ```
     - L'output dovrebbe restituire la versione di Java appena installata (24).  
       Un esempio di output atteso è:
    ```
       java version "24.0.2" 2025-07-15
       Java(TM) SE Runtime Environment (build 24.0.2+7-70)
       Java HotSpot(TM) 64-Bit Server VM (build 24.0.2+7-70, mixed mode, sharing)
    ```

   - Per aggiungere il JDK al **PATH** delle variabili di sistema, digitare il comando:
  ```cmd
     setx PATH "%PATH%;C:\Program Files\Java\jdk-24\bin"
  ```

   - Se si desidera verificare l’aggiunta del percorso al PATH, utilizzare il comando:
  ```cmd
     echo %PATH%
  ```

2. **JavaFX (SDK)**

- La presenza delle librerie di JavaFX SDK (Versione 24.0.2) è di **fondamentale importanza** per poter eseguire correttamente l'applicazione, tali librerie sono presenti nella cartella **"Jar"** della directory di progetto.

3. **FARE MYSQL**

## **3. Esecuzione del sistema**

All'interno della directory del progetto, più precisamente all'interno della cartella **"File per l'avvio"** sono presenti due file batch:

- **Server_setup_estensione.bat**: Questo file batch automatizza l'avvio del server, questo il suo contenuto:

```cmd
    @echo off
    echo Avvio del Server QT...
    java -jar "..\Jar\server_start_estensione.jar"
    pause
```

il file .bat dunque esegue il file .jar **"server_start_estensione.jar"** presente nella cartella **"Jar"** della cartella di progetto.

Facendo doppio click sul file, il terminale verrà aperto mostrando la seguente scritta in caso di avvio corretto e senza errori del server:

```cmd
    Avvio del Server QT...
    Started ServerSocket[addr=0.0.0.0/0.0.0.0,localport=8080]
```

- **Client_setup_estensione.bat**: Questo file batch automatizza l'avvio del Client e dunque dell'interfaccia grafica, questo il suo contenuto:

```cmd
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
```

il file .bat dunque esegue il file .jar **"app_start_estensione.jar"** presente nella cartella **"Jar"**.

Facendo doppio click sul file, il terminale verrà aperto mostrando la seguente scritta in caso di avvio corretto e di successo nella connessione al server (con successiva apertura della GUI):

```cmd
    addr = localhost/127.0.0.1
```