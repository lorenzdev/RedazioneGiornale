/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.File;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.*;

/**
 *
 * @author totaro.christian
 */
public class DBConnection {

    static final String DB_URL = "jdbc:mysql://localhost/Giornale";
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_USER = "root";
    static final String DB_PASSWD = "";
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    private static DocumentBuilderFactory dbFactory;
    private static DocumentBuilder dBuilder;
    private static Document docNews;
    private static Node rootNews;
    private static NodeList listNews;
    private static Document docUtenti;
    private static Node rootUtenti;
    private static NodeList listUtenti;
    private static DBConnection dbc;
    private static boolean login;
    private static File FileUtenti;
    private static File FileNews;
    
    private static void AggiornaFileUtentiUtilizzato(){
        try{
            docUtenti = dBuilder.parse(DBConnection.FileUtenti);
            rootUtenti = docUtenti.getFirstChild();
            listUtenti = ((Element)rootUtenti).getElementsByTagName("Utente");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static void AggiornaFileNewsUtilizzato(){
        try{
            docNews = dBuilder.parse(DBConnection.FileNews);
            rootNews = docNews.getFirstChild();
            listNews = ((Element)rootNews).getElementsByTagName("News"); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public DBConnection(){
        try{
            //FileNews = new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\News.xml");
            FileNews = new File("C:\\Users\\chrit\\Desktop\\RedazioneGiornale\\RedazioneGiornaleServer\\News.xml");
            //FileUtenti = new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\Utenti.xml");
            FileUtenti = new File("C:\\Users\\chrit\\Desktop\\RedazioneGiornale\\RedazioneGiornaleServer\\Utenti.xml");
    
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            
            docUtenti = dBuilder.parse(FileUtenti);
            rootUtenti = docUtenti.getFirstChild();
            listUtenti = ((Element)rootUtenti).getElementsByTagName("Utente");
            
            docNews = dBuilder.parse(FileNews);
            rootNews = docNews.getFirstChild();
            listNews = ((Element)rootNews).getElementsByTagName("News");
            // CREO LA CONNESSIONE AL DATABASE
            Class.forName(DB_DRV);
            connection=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
            statement=connection.createStatement();
        }catch(SQLException sql_e){
            sql_e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void InserisciUtenteXML(String email, String password, String nome, String cognome, String telefono, String data_nascita, String indirizzo_residenza, String citta_residenza){
        try{
            AggiornaFileUtentiUtilizzato();
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new FileInputStream(FileUtenti));
            
            Element element = doc.getDocumentElement();
            Node Utente = doc.createElement("Utente");
            element.appendChild(Utente);

            Element Email = doc.createElement("email");
            Email.appendChild(doc.createTextNode(email));
            Utente.appendChild(Email);
            Element Password = doc.createElement("password");
            Password.appendChild(doc.createTextNode(password));
            Utente.appendChild(Password);
            Element Nome = doc.createElement("nome");
            Nome.appendChild(doc.createTextNode(nome));
            Utente.appendChild(Nome);
            Element Cognome = doc.createElement("cognome");
            Cognome.appendChild(doc.createTextNode(cognome));
            Utente.appendChild(Cognome);
            Element Telefono = doc.createElement("telefono");
            Telefono.appendChild(doc.createTextNode(telefono));
            Utente.appendChild(Telefono);
            Element Data_nascita = doc.createElement("data_nascita");
            Data_nascita.appendChild(doc.createTextNode(data_nascita));
            Utente.appendChild(Data_nascita);
            Element Indirizzo_residenza = doc.createElement("indirizzo_residenza");
            Indirizzo_residenza.appendChild(doc.createTextNode(indirizzo_residenza));
            Utente.appendChild(Indirizzo_residenza);
            Element Citta_residenza = doc.createElement("citta_residenza");
            Citta_residenza.appendChild(doc.createTextNode(citta_residenza));
            Utente.appendChild(Citta_residenza);
            Element stato_modifica = doc.createElement("stato_modifica");
            stato_modifica.appendChild(doc.createTextNode("1"));
            Utente.appendChild(stato_modifica);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(FileUtenti);
            transformer.transform(source, result);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    public static void InserisciNewsXML(String topic, String titolo, String descrizione, String contenuto, String data, String email_autore){
            try{
                AggiornaFileNewsUtilizzato();
                
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setValidating(false);
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document doc = db.parse(new FileInputStream(FileNews));

                Element element = doc.getDocumentElement();
                Node News = doc.createElement("News");
                element.appendChild(News);

                Element Topic = doc.createElement("topic");
                Topic.appendChild(doc.createTextNode(topic));
                News.appendChild(Topic);
                Element Titolo = doc.createElement("titolo");
                Titolo.appendChild(doc.createTextNode(titolo));
                News.appendChild(Titolo);
                Element Descrizione = doc.createElement("descrizione");
                Descrizione.appendChild(doc.createTextNode(descrizione));
                News.appendChild(Descrizione);
                Element Contenuto = doc.createElement("contenuto");
                Contenuto.appendChild(doc.createTextNode(contenuto));
                News.appendChild(Contenuto);
                Element Data = doc.createElement("data");
                Data.appendChild(doc.createTextNode(data));
                News.appendChild(Data);
                Element Email_autore = doc.createElement("email_autore");
                Email_autore.appendChild(doc.createTextNode(email_autore));
                News.appendChild(Email_autore);
                Element stato_modifica = doc.createElement("stato_modifica");
                stato_modifica.appendChild(doc.createTextNode("1"));
                News.appendChild(stato_modifica);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(FileNews);
                transformer.transform(source, result);
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    
    public static void ConversioneDBtoXML(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            System.out.println("    ...conversione file Utenti.xml...");
            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Utenti");
            doc.appendChild(rootElement);
            String query = "SELECT * FROM Utenti";
            PreparedStatement ins = connection.prepareStatement(query);
            ResultSet valori = ins.executeQuery();
            while(valori.next()){
                Element Utente = doc.createElement("Utente");
                rootElement.appendChild(Utente);
                Element Email = doc.createElement("email");
                Email.appendChild(doc.createTextNode(valori.getString("email")));
                Utente.appendChild(Email);
                Element Password = doc.createElement("password");
                Password.appendChild(doc.createTextNode(valori.getString("password")));
                Utente.appendChild(Password);
                Element Nome = doc.createElement("nome");
                Nome.appendChild(doc.createTextNode(valori.getString("nome")));
                Utente.appendChild(Nome);
                Element Cognome = doc.createElement("cognome");
                Cognome.appendChild(doc.createTextNode(valori.getString("cognome")));
                Utente.appendChild(Cognome);
                Element Telefono = doc.createElement("telefono");
                Telefono.appendChild(doc.createTextNode(valori.getString("telefono")));
                Utente.appendChild(Telefono);
                Element Data_nascita = doc.createElement("data_nascita");
                Data_nascita.appendChild(doc.createTextNode(valori.getString("data_nascita")));
                Utente.appendChild(Data_nascita);
                Element Indirizzo_residenza = doc.createElement("indirizzo_residenza");
                Indirizzo_residenza.appendChild(doc.createTextNode(valori.getString("indirizzo_residenza")));
                Utente.appendChild(Indirizzo_residenza);
                Element Citta_residenza = doc.createElement("citta_residenza");
                Citta_residenza.appendChild(doc.createTextNode(valori.getString("citta_residenza")));
                Utente.appendChild(Citta_residenza);
                Element stato_modifica = doc.createElement("stato_modifica");
                stato_modifica.appendChild(doc.createTextNode("0"));
                Utente.appendChild(stato_modifica);
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(FileUtenti);
            transformer.transform(source, result);
            System.out.println("    ...conversione file News.xml...");
            // root elements
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("Tidings");
            doc.appendChild(rootElement);
            query = "SELECT * FROM News";
            ins = connection.prepareStatement(query);
            valori = ins.executeQuery();
            while(valori.next()){
                Element News = doc.createElement("News");
                rootElement.appendChild(News);
                Element Topic = doc.createElement("topic");
                Topic.appendChild(doc.createTextNode(valori.getString("topic")));
                News.appendChild(Topic);
                Element Titolo = doc.createElement("titolo");
                Titolo.appendChild(doc.createTextNode(valori.getString("titolo")));
                News.appendChild(Titolo);
                Element Descrizione = doc.createElement("descrizione");
                Descrizione.appendChild(doc.createTextNode(valori.getString("descrizione")));
                News.appendChild(Descrizione);
                Element Contenuto = doc.createElement("contenuto");
                Contenuto.appendChild(doc.createTextNode(valori.getString("contenuto")));
                News.appendChild(Contenuto);
                Element Data = doc.createElement("data");
                Data.appendChild(doc.createTextNode(valori.getString("data")));
                News.appendChild(Data);
                Element Email_autore = doc.createElement("email_autore");
                Email_autore.appendChild(doc.createTextNode(valori.getString("email_autore")));
                News.appendChild(Email_autore);
                Element stato_modifica = doc.createElement("stato_modifica");
                stato_modifica.appendChild(doc.createTextNode("0"));
                News.appendChild(stato_modifica);
            }
            // write the content into xml file
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(doc);
            result = new StreamResult(FileNews);
            transformer.transform(source, result);
      } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
      } catch (TransformerException tfe) {
            tfe.printStackTrace();
      }catch(SQLException sql_e){
            sql_e.printStackTrace();
      }
    }
    
    public static void ConversioneXMLtoDB(){
        try{
            AggiornaFileNewsUtilizzato();
            AggiornaFileUtentiUtilizzato();
            
            System.out.println("...caricamento dei nuovi dati dell'xml nel database...");
            for(int i=0;i<listUtenti.getLength();i++){
                Node utenti = listUtenti.item(i);
                if(utenti.getNodeType() == Node.ELEMENT_NODE){
                    Element el = (Element)utenti;
                    if(el.getElementsByTagName("stato_modifica").item(0).getTextContent().equals("1")){
                        String email = el.getElementsByTagName("email").item(0).getTextContent();
                        String password = el.getElementsByTagName("password").item(0).getTextContent();
                        String nome = el.getElementsByTagName("nome").item(0).getTextContent();
                        String cognome = el.getElementsByTagName("cognome").item(0).getTextContent();
                        String telefono = el.getElementsByTagName("telefono").item(0).getTextContent();
                        String data_nascita = el.getElementsByTagName("data_nascita").item(0).getTextContent();
                        String indirizzo_residenza = el.getElementsByTagName("indirizzo_residenza").item(0).getTextContent();
                        String citta_residenza = el.getElementsByTagName("citta_residenza").item(0).getTextContent();
                        String query_insert_Utente = "INSERT INTO Utenti (email, password, nome, cognome, telefono, data_nascita, indirizzo_residenza, citta_residenza) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStmt = connection.prepareStatement(query_insert_Utente);
                        preparedStmt.setString(1, email);
                        preparedStmt.setString(2, password);
                        preparedStmt.setString(3, nome);
                        preparedStmt.setString(4, cognome);
                        preparedStmt.setString(5, telefono);
                        preparedStmt.setString(6, data_nascita);
                        preparedStmt.setString(7, indirizzo_residenza);
                        preparedStmt.setString(8, citta_residenza);
                        System.out.println("    ...inserimento nuovo utente: "+email+"...");
                        preparedStmt.executeUpdate();
                    }
                }
            }
            for(int i=0;i<listNews.getLength();i++){
                Node news = listNews.item(i);
                if(news.getNodeType() == Node.ELEMENT_NODE){
                    Element el = (Element)news;
                    if(el.getElementsByTagName("stato_modifica").item(0).getTextContent().equals("1")){
                        String topic = el.getElementsByTagName("topic").item(0).getTextContent();
                        String titolo = el.getElementsByTagName("titolo").item(0).getTextContent();
                        String descrizione = el.getElementsByTagName("descrizione").item(0).getTextContent();
                        String contenuto = el.getElementsByTagName("contenuto").item(0).getTextContent();
                        String data = el.getElementsByTagName("data").item(0).getTextContent();
                        String email_autore = el.getElementsByTagName("email_autore").item(0).getTextContent();
                        String query_insert_News = "INSERT INTO News (topic, titolo, descrizione, contenuto, data, email_autore) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStmt = connection.prepareStatement(query_insert_News);
                        preparedStmt.setString(1, topic);
                        preparedStmt.setString(2, titolo);
                        preparedStmt.setString(3, descrizione);
                        preparedStmt.setString(4, contenuto);
                        preparedStmt.setString(5, data);
                        preparedStmt.setString(6, email_autore);
                        System.out.println("    ...inserimento nuova news: "+titolo+"...");
                        preparedStmt.executeUpdate();
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }  
}
