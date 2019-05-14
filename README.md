# Redazione di un giornale

## Verifica TePSIT

### Gruppo 1

Totaro   
Guberti  
Rognoni    

### Docente

prof. Vitale Lorenzo

### Descrizione
**Obiettivo minimo (80pt)**  
Realizzare un programma Client-Server che implementi la redazione di un giornale in cui viene
gestita una banca dati di utenti e di news. Ogni utente ha un account composto da una email ed una
password e una serie di informazioni personali nome, cognome, telefono, data di nascita, indirizzo e
città di residenza. Le news, invece, sono composte da un topic (sport, cultura, politica, attualità), un
titolo, una descrizione, una data, l’email dell’autore come chiave esterna.
Il Server offre due servizi ai Client:  
**1.** Permette ad un nuovo utente di iscriversi inviando i propri dati al Server che procede a
memorizzarli nella propria banca dati;  
**2.** Gli utenti iscritti, dopo essersi loggati inviando email e password di accesso, possono
richiedere l’elenco delle news indicando il topic, l’intervallo della data di interesse e/o
l’email dell’autore;  
**3.** Gli utenti iscritti possono, inoltre, inserire nuove news;  
Le news mostrate all’utente, devono essere visualizzate secondo l’ordine di inserimento, dalla meno
recente alla più recente.
Ogni utente, ovviamente, per poter visualizzare l’elenco delle news, deve essere iscritto sul Server.


**Obiettivo avanzato (20pt)**  
Implementare un ulteriore servizio di multicasting che prevede che i Client si mettano in ascolto.
Il Server, quando riceve una nuova news da un Client, prima di salvarla nel database invia a tutti gli
altri Client connessi e in ascolto la news appena inserita.

*SUGGERIMENTO: Per semplificare la risoluzione di questo punto, prevedere che i Client, una volta in modalità
di ricezione multicasting, non ricevano comandi di input dall’utente ma restano esclusivamente in
ascolto.*


### Usage

Prima operazione da effettuare è ottenere il progetto dal repository lanciando il seguente comando dalla bash di git:

```
git clone https://github.com/lorenzdev/RedazioneGiornale.git
```

Successivamente lanciare i seguenti comandi per ottenere le modifiche:

```
git pull origin master
```

e per pubblicare le proprie modifiche:

```
git add .
```  
```
git commit -m "descrizione del commit"
```  
```
git push origin master
```  

### Consegna
Entro il 31/05/2019
