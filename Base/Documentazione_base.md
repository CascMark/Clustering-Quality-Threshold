# **Manuale utente - QTClustering - Base**

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

2. **FARE PARTE MYQSL**

## **3. Esecuzione del sistema**

All'interno della directory del progetto, più precisamente all'interno della cartella **"File per l'avvio"** sono presenti due file batch:

- **Server_setup_base.bat**: Questo file batch automatizza l'avvio del server, questo il suo contenuto:

```cmd
    @echo off
    echo Avvio del Server QT...
    java -jar "..\Jar\server_start_base.jar"
    pause
```
il file .bat dunque esegue il file .jar **"server_start_base.jar"** presente nella cartella **"Jar"** della cartella di progetto.

Facendo doppio click sul file, il terminale verrà aperto mostrando la seguente scritta in caso di avvio corretto e senza errori del server:

```cmd
    Avvio del Server QT...
    Started ServerSocket[addr=0.0.0.0/0.0.0.0,localport=8080]
```

- **"Client_setup_base.bat"**: Questo file batch automatizza l'avvio del client, questo il suo contenuto:

```cmd
    @echo off
    java -jar "..\Jar\app_start_base.jar" localhost 8080
    pause
```

il file .bat dunque esegue il file .jar **"app_start_base.jar"** presente nella cartella **"Jar"** della cartella di progetto, prendendo come argomenti anche **"localhost 8080"** per stabilire la corretta connessione al server.

Facendo doppio click sul file, il terminale verrà aperto mostrando la seguente scritta in caso di avvio corretto e di successo nella connessione al server:

```cmd
    addr = localhost/127.0.0.1
    Socket[addr=localhost/127.0.0.1,port=8080,localport=51557]
    (1) Load clusters from file
    (2) Load data from db
    (1/2):
```

## **4. Manuale d’uso**

Una volta avviato correttamente l'applicazione, l'utente avrà difronte a sè la seguente schermata:

![schermata_iniziale](./img%20docs/schermata_iniziale.png)

- Scelta **(1) Load clusters from file**:
    Scegliendo l'opzione (1), l'utente potrà visualizzare il contenuto di un file precedentemente salvato con nome della tabella del database + raggio (vedremo successivamente grazie all'opzione (2)), mostrando a schermo però solo i centroidi:

![1_buon_fine](./img%20docs/1_apertura_file.png)

L'applicazione successivamente chiederà all'utente **"would you choose a new operation from menu? (y/n)"**, permettendo in base alla risposta di poter scegliere nuovamente se utilizzare l'opzione (1) o (2), oppure se chiudere l'applicazione.

- Scelta **(2) Load data from db**:
    Scegliendo l'opzione (2), l'utente potrà inserire il nome della tabella presa in considerazione e il raggio di clustering, visualizzando successivamente il risultato del clustering, successivamente i rispettivi centroidi verranno salvati in un file .dat chiamato nome tabella + raggio + .dat (Per esempio playTennis1.dat):

![2_buon_fine](./img%20docs/2_raggio_valido.png)

L'applicazione successivamente chiederà all'utente **"Would you repeat?(y/n)"** dove in caso di risposta affermativa, chiederà un nuovo raggio (il clustering sarà eseguito sempre sulla stessa tabella), in caso di risposta negativa invece, l'applicazione chiederà **"would you choose a new operation from menu? (y/n)"** che permetterà all'utente di scegliere se utilizzare un'altra funzionalità dell'applicazione, oppure di chiuderla.

**Input non validi:**

- Scelta (1): In caso di scelta (1), inserendo il nome di un file non esistente, verrà mostrata a schermo una frase di errore che sottolinea all'utente la non esistenza del file:

![1_errore](./img%20docs/1_errore.png)

- Scelta (2): In caso di scelta (2), inserendo il nome di una tabella inesistente o errata, verrà mostrata a schermo la seguente frase di errore:

![2_errore_tabella](./img%20docs/2_errore_tabella.png)

inserendo invece un raggio non valido:

![2_errore_radius](./img%20docs/2_errore_raggio.png)

## **5. Architettura di sistema UML**

In questa sezione descriviamo l'architettura di sistema mediante diagrammi UML, utili per descrivere modelli software con un approccio Object Oriented, per maggiori informazioni riguardo eventuali omissioni di package e info utili alla consultazione dei diagrammi è possibile leggere il file **"UML_info"** presente nella cartella UML della directory del progetto.

- **Package data**

![data](./img%20docs/data.png)

- **Package mining**

![mining](./img%20docs/mining.png)

- **Package database**

![database](./img%20docs/database.png)

- **Package default_package**

![default](./img%20docs/default_package.png)

- **Package server**

![server](./img%20docs/server.png)










