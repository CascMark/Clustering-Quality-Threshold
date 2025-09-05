# **Manuale utente - QTClustering - Estensione**

## Credits software e documentazione: **Marco Pio Cascella**

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

3. **Scaricare ed installare MySQL**

    Un'altra componente fondamentale per il corretto funzionamento del software è il downlaod e la configurazione di **MySQL Community Server** scariabile al seguente [link](https://dev.mysql.com/downloads/mysql/) (Si consiglia di scaricare la versione 8.0.40, l'utilizzo di versioni inferiori o superiori non garantisce la compatibilità con il software), l'installazione avverrà successivamente mediante strumento Wizard.

    Scrivendo il seguente comando all'interno del terminale di Windows è possibile verificare se l'installazione è avvenuta correttamente:

```cmd
    mysql --version
```

Un esempio di output è il seguente:

```cmd
    mysql  Ver 8.0.40 for Win64 on x86_64 (MySQL Community Server - GPL)
```

## **3. Esecuzione del sistema**

All'interno della directory del progetto, più precisamente all'interno della cartella **"File per l'avvio"** sono presenti quattro file batch (Bisogna eseguirli in ordine per poter utilizzare correttamente il software):

- **inizializzazione_db.bat**: Facendo doppio click sul file bat, la prima cosa che verrà chiesta all'utente è quella di inserire la password dell'user root, configurata al momento dell'installazione di MySQL.

La funzionalità principale di questo file batch è quella di automatizzare la creazione del database **MapDB** e dell'utente **MapUser**, identificato dalla password **"map"**, (UTILE PER IL SECONDO FILE BATCH) richiamando uno script sql (**"inizializzazione_db.sql"** presente all'interno della cartella **"File per l'avvio"**), di seguito il contenuto del file batch:

```cmd
    @echo off

    "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p < "inizializzazione_db.sql"

    IF ERRORLEVEL 1 (
    echo Errore durante l'inizializzazione del database. Controllare il file di log di MySQL per maggiori informazioni.
    ) ELSE (
    echo Inizializzazione del Database MapDB e dell'utente MapUSER riuscita.
    )

    pause
```

- **tabella_campione.bat**: Il seguente file batch automatizza la creazione di una tabella di esempio per poter testare il software, facendo doppio click sul file bat, la prima cosa che verrà richiesta all'utente è quella di inserire la password, che in questo caso sarà **"map"** come specificato anche nel punto precedente. Una volta inserita la password, a video verrà mostrato un messaggio che comunica all'utente se la creazione della tabella di esempio è avvenuta con successo. 

Di seguito è riportato il contenuto del file batch:

```cmd
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

```

(I CONTENUTI DEI FILE .SQL SONO VISUALIZZABILI E SI TROVANO ALL'INTERNO DELLA CARTELLA **"File per l'avvio"**)

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

## **4. Manuale d'uso**

Una volta avviata correttamente l'applicazione, l'utente potrà visualizzare la schermata principale.

La schermata principale presenta diverse opzioni:

- **START**: Permette all'utente di accedere alla schermata dove vengono erogati i principali servizi di clustering

- **Scopri il QTClustering**: Permette all'utente di accedere ad una schermata dove sono riportate le principali informazioni sull'algoritmo di Clustering.

- **GitHub**: Permette all'utente di visitare il profilo GitHub del creator.

Inoltre in basso a sinistra c'è un flag che comunica all'utente se il software è connesso correttamente al server (verde se connesso, rosso altrimenti).

![start](./img%20docs/start.png)

Premendo sul pulsante **START** l'utente può accedere alla schermata dove sono presenti i due principali servizi offerti dal software:

![schermata_vuota](./img%20docs/schermatavuota.png)



- **Carica cluster da file**: Permette all'utente di poter aprire un file già esistente denominato nome tabella + raggio (Per esempio playTennis3.dat), mostrando a video solo i centroidi.

![file](./img%20docs/file.png)

- **Carica cluster da DB**: Permette all'utente di utilizzare l'algoritmo di clustering di una tabella qualsiasi del database collegato al software, il clustering avviene specificando il nome della tabella del db e il raggio di clustering desiderato. Una volta aver premuto **AVVIA**, a video verranno mostrati i risultati del clustering che verranno memorizzati in un file .dat denominato nome tabella + raggio . dat.

![db](./img%20docs/db.png)

Premendo sul tasto **indietro** l'utente verrà riportato alla schermata principale.

Dalla schermata principale, se l'utente preme il pulsante **Scopri il QTClustering**, potrà accedere alla seguente schermata:

![info](./img%20docs/info.png)

In questa schermata, premendo sul pulsante **Altre info...** l'utente verrà reindirizzato ad una pagina contenente maggiori informazioni riguardo all'argomento.

Premendo sul pulsante **Indietro** l'utente potrà tornare alla schermata principale.

## **5. Architettura di sistema UML**

In questa sezione descriviamo l'architettura di sistema mediante diagrammi UML, utili per descrivere modelli software con un approccio Object Oriented, per maggiori informazioni riguardo eventuali omissioni di package e info utili alla consultazione dei diagrammi è possibile leggere il file **"UML_info"** presente nella cartella UML della directory del progetto.

- **Package data**

![data](./img%20docs/data.png)

- **Package mining**

![mining](./img%20docs/mining.png)

- **Package database**

![database](./img%20docs/database.png)

- **Package default_package**

![default](./img%20docs/default_package_estensione.png)

- **Package server**

![server](./img%20docs/server.png)