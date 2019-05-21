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

$sql="CREATE DATABASE GIORNALE";
$ok=mysqli_query($conn,$sql);
if(!$ok)die("errore".mysqli_error($conn));
echo "db creato";

$sql="USE GIORNALE";
$ok=mysqli_query($conn,$sql);
if(!$ok)die("".mysqli_error($conn));
echo "db in uso";

$sql="CREATE TABLE Utenti(
EmailUtente varchar(20) PRIMARY KEY,
Nome varchar(10),
Cognome varchar(10),
Password varchar(10),
Telefono integer(10),
DataDiNascita date,
Indirizzo varchar(15),
Citta varchar(10))";
$ok=mysqli_query($conn,$sql);
if(!$ok)die("tabella non creata".mysqli_error($conn));

$sql="CREATE TABLE News(
EmailUtente varchar(20),
Data date,
Contenuto text,
Descrizione text,
Titolo varchar(20) PRIMARY KEY,
Topic varchar(10),
FOREIGN KEY (EmailUtente) REFERENCES Utenti(EmailUtente))";
$ok=mysqli_query($conn,$sql);
if(!$ok)die("tabella non creata".mysqli_error($conn));
?>
</html>