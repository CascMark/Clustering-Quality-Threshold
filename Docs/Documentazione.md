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

2. **JavaFX (SDK):**

- La presenza delle librerie di JavaFX SDK (Versione 24.0.2) è di **fondamentale importanza** per poter eseguire correttamente l'applicazione, tali librerie sono presenti nella cartella **"Jar"** del progetto nella sezione **Estensione**.

3. **FARE PARTE MYQSL**

# 3 - Configurazione database, server e client

All'interno della cartella di progetto



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
