<html>
<head>
<title>
creazione database
</title>
</head>
<?php
$dbhost="127.0.0.1";
$dbuser="root";
$dbpass="";
$conn=mysqli_connect($dbhost,$dbuser,$dbpass);
if(!$conn)die("".mysqli_error($conn));

$sql="CREATE DATABASE Giornale";
$ok=mysqli_query($conn,$sql);
if(!$ok)die("errore".mysqli_error($conn));
echo "db creato";

$sql="USE Giornale";
$ok=mysqli_query($conn,$sql);
if(!$ok)die("".mysqli_error($conn));
echo "db in uso";

$sql="CREATE TABLE Utenti(
		email varchar(50) PRIMARY KEY,
		password varchar(10),
		nome varchar(10),
		cognome varchar(10),
		telefono varchar(10),
		data_nascita date,
		indirizzo_residenza varchar(30),
		citta_residenza varchar(30))";
$ok=mysqli_query($conn,$sql);
if(!$ok)die("tabella non creata".mysqli_error($conn));

$sql="CREATE TABLE News(
		topic varchar(20),
		titolo varchar(50) PRIMARY KEY,
		descrizione text,
		contenuto text,
		data date,
		email_autore varchar(50),
		FOREIGN KEY (email_autore) REFERENCES Utenti(email))";
$ok=mysqli_query($conn,$sql);
if(!$ok)die("tabella non creata".mysqli_error($conn));
?>
</html>