# Manuale utente - Clustering: Quality Threshold

# Indice

- [1 - Introduzione](#1---introduzione)
- [2 - Guida all'installazione](#2---guida-allinstallazione)
- [3 - Configurazione database, server e client](#3---configurazione-database-server-e-client)
- [4 - Guida all'uso](#4---guida-alluso)
- [5 - UML](#5---uml)


# 1 - Introduzione
Il progetto QTClustering si svolge durante l'anno accademico 2024-2025 nel corso di "Metodi avanzati di programmazione".
L'obiettivo principale di questo progetto è quello di creare un sistema client-server capace di implementare il clustering quality threshold.
([Per maggiori informazioni riguardo al clustering e in particolare a questo tipo di clustering](https://it.wikipedia.org/wiki/Clustering))

Come anticipato precedentemente, il nostro sistema si basa su due principali componenti:
- **Server:** Componente formata dalle principali classi che regolano e governano l'algoritmo di clustering.
- **Client:** Componente che permette all'utente di poter interagire con il lato server dell'applicazione e di poter utilizzare i servizi di clustering (e anche altri servizi).

# 2 - Guida all'installazione

Per installare il software QTClustering, è necessario seguire i seguenti passaggi:

1. **Scaricare e Installare Java Development Kit (JDK):**
   - Assicurarsi di avere installato il JDK versione 11 o successiva. È possibile scaricarlo dal sito ufficiale di Oracle.
     - [Scarica JDK da Oracle](https://www.oracle.com/java/technologies/downloads/)
   - Installazione JDK Versione 22 tramite Terminale di Windows:
     - Aprire il Prompt dei comandi di Windows come amministratore.
     - Digitare il comando seguente per scaricare l'eseguibile del Java JDK versione 22:
      ```
      curl -o jdk-22_windows-x64_bin.exe https://download.oracle.com/java/22/latest/jdk-22_windows-x64_bin.exe
      ```
   - Una volta completato il download, installare l'eseguibile del JDK eseguendo il comando:
    ```
     jdk-22_windows-x64_bin.exe
    ```
   - Questo avvierà il processo di installazione del JDK. Al termine dell'installazione, verificare che sia andata a buon fine digitando il seguente comando:
    ```
     java -version
     ```
   - L'output dovrebbe restituire la versione di Java appena installata (22). Un esempio di output atteso è:  
     java version "22.0.2" 2024-07-16  
     Java(TM) SE Runtime Environment (build 22.0.2+9-70)  
     Java HotSpot(TM) 64-Bit Server VM (build 22.0.2+9-70, mixed mode, sharing)  
   - Per aggiungere il JDK al PATH delle variabili di sistema, digitare il comando:
    ```
     setx PATH "%PATH%;C:\Program Files\Java\jdk-22\bin"
     ```
   - Se si desidera verificare l'aggiunta del percorso al PATH, utilizzare il comando:
    ```
     echo %PATH%
      ```
<br>

2. **Scaricare ed Estrarre JavaFX (SDK):**  
Il download delle librerie javafx è fondamentale per il corretto avvio dell'applicazione.

  - [Scarica JavaFX SDK](https://download2.gluonhq.com/openjfx/22.0.2/openjfx-22.0.2_windows-x64_bin-jmods.zip)
   - Installazione SDK Versione 22.0.2 tramite Terminale di Windows:
     - Aprire il Prompt dei comandi di Windows come amministratore.
     - Digitare il comando seguente per scaricare il file .zip del JavaFX SDK versione 22.0.2:
      ```
      curl -o C:\javafx-sdk-22.0.2_windows-x64_bin-sdk.zip https://download2.gluonhq.com/openjfx/22.0.2/openjfx-22.0.2_windows-x64_bin-sdk.zip
      ```
      - Dopo il download, estrarre il contenuto del file ZIP utilizzando il comando:
      ```
      powershell -Command "Expand-Archive -Path 'C:\javafx-sdk-22.0.2_windows-x64_bin-sdk.zip' -DestinationPath 'C:\javafx-sdk'"
      ```
      Assicurarsi di inserire i percorsi corretti sia per il path di partenza che per quello di destinazione.

<br>

3. **Scaricare e Installare MySQL:**
   - Installare MySQL Community Server. È possibile scaricarlo dal sito ufficiale di MySQL.
     - [Scarica MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

4. **Inserire MySQL tra le variabili d'ambiente:**
  - Aprire il menu `Start` e cercare "variabili d'ambiente".

  - Selezionare `Modifica le variabili d'ambiente di sistema`.

  - Nella finestra `Proprietà del sistema`, cliccare su `Variabili d'ambiente...`.

  - Nella sezione `Variabili di sistema`, cercare e selezionare la variabile `Path`, quindi cliccare su `Modifica...`.

  - Cliccare su `Nuovo` e aggiungere il percorso della cartella `bin` di MySQL. Il percorso predefinito è solitamente: `C:\Program Files\MySQL\MySQL Server [versione]\bin`

  - Cliccare su `OK` per chiudere tutte le finestre aperte.

5. **Verificare l'installazione**

  - Aprire un nuovo prompt dei comandi.

  - Digitare `mysql --version` e premere `Invio`.

  - Se MySQL è stato aggiunto correttamente al `PATH`, vedrai la versione di MySQL installata.

# 4 - Guida all'uso
L'interfaccia utente si presenta nel seguente modo:
- **START**: permette all'utente di poter accedere alla schermata dove potranno essere eseguiti i clustering
- **GITHUB**: permette all'utente di visualizzare il profilo github del creator
- **SCOPRI IL QT CLUSTERING**: permette all'utente di accedere alla pagina dove sono presenti informazioni riguardo il QT clustering
- **STATO CONNESSIONE**: Specifica all'utente se l'applicazione si è collegata correttamente al server

![START](./img%20doc/start.png)

Premendo sul pulsante "Scopri il QT Clustering", si aprirà la seguente interfaccia che presenta i seguenti comandi:
- **INDIETRO**: permette all'utente di tornare alla schermata principale
- **ALTRE INFO...**: permette all'utente di accedere ad una pagina web dove sono presenti approfondimenti sull'argomento

![INFO](./img%20doc/info.png)

Premendo sul pulsante "START", si aprirà la seguente interfaccia che presenta i seguenti comandi:
- **INDIETRO**: permette all'utente di tornare alla schermata principale
- **CARICA CLUSTER DA DB**: permette all'utente di utilizzare l'applicazione in modalità database.
- **CARICA CLUSTER DA FILE**: permette all'utente di utilizzare l'applicazione in modalità file.
- **AVVIA**: permette all'utente di eseguire il clustering

![SCHERMATA VUOTA](./img%20doc/schermatavuota.png)

**Nella modalità database:** l'utente deve inserire rispettivamente all'interno dei campi il nome della tabella del database e il raggio di clustering, dopo aver fatto ciò, premendo sul pulsante "AVVIA", nella schermata a destra verranno mostrati a video i risultati del clustering
![DB](./img%20doc/db.png)

**Nella modalità file**: l'utente deve inserire il nome della tabella e il raggio di clustering, i quali fanno riferimento al nome di un file .dat già esistente, una volta fatto ciò, premendo sul pulsante "AVVIA", verranno stampati a video i centroidi presenti sul file nella schermata a destra
![FILE](./img%20doc/file.png)

# 5 - UML

**PACKAGE DATA**

![data](./img%20doc/data.png)

**PACKAGE DATABASE**

![database](./img%20doc/database.png)

**PACKAGE MINING**

![mining](./img%20doc/mining.png)

**PACKAGE SERVER**

![server](./img%20doc/server.png)

**PACKAGE DEFAULT (BASE)**

![default base](./img%20doc/default_package.png)

**PACKAGE DEFAULT (ESTENSIONE)**

![default estensione](./img%20doc/default_package_estensione.png)
