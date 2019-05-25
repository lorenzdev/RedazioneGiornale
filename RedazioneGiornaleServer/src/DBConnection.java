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
    
    public DBConnection(){
        try{
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
    //da fare
    public static void InserisciUtenteDB(String email, String password, String nome, String cognome, String telefono, String data_nascita, String indirizzo_residenza, String citta_residenza){
        try{
            String query = "INSERT INTO Utenti (email,password,nome,cognome,telefono,data_nascita,indirizzo_residenza,citta_residenza)VALUES"
                    + "('"+email+"','"+password+"','"+nome+"','"+cognome+"','"+telefono+"','"+data_nascita+"','"+indirizzo_residenza+"','"+citta_residenza+"')";
            PreparedStatement ins = connection.prepareStatement(query);
            ins.executeUpdate();
        }catch(SQLException sql_e){
            sql_e.printStackTrace();
        }
    }
    
    public static void InserisciUtenteXML(String email, String password, String nome, String cognome, String telefono, String data_nascita, String indirizzo_residenza, String citta_residenza){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new FileInputStream(new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\Utenti.xml")));

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
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\Utenti.xml"));
            transformer.transform(source, result);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    //da fare
    public static void InserisciNewsDB(String topic, String titolo, String descrizione, String contenuto, String data, String email_autore){
        try{
            String query = "INSERT INTO News (topic,titolo,descrizione,contenuto,data,email_autore)VALUES"
                    + "('"+topic+"','"+titolo+"','"+descrizione+"','"+contenuto+"','"+data+"','"+email_autore+"')";
            PreparedStatement ins = connection.prepareStatement(query);
            ins.executeUpdate();
        }catch(SQLException sql_e){
            sql_e.printStackTrace();
        }
    }
    
    public static void InserisciNewsXML(String topic, String titolo, String descrizione, String contenuto, String data, String email_autore){
            try{
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setValidating(false);
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document doc = db.parse(new FileInputStream(new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\News.xml")));

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

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\News.xml"));
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
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\Utenti.xml"));
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
            }
            // write the content into xml file
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(doc);
            result = new StreamResult(new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\News.xml"));
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
        
    }
    /*public static void updateRow(){
        
        String email = "maurizio.buoni@gmail.com";

        try{
            
            String query = "UPDATE utenti SET indirizzo = 'via Pasolini, 5' WHERE email = '"+email+"'";
            PreparedStatement ins = connection.prepareStatement(query);
            ins.executeUpdate();
            
        }catch(SQLException sql_e){
            sql_e.printStackTrace();
        }
    }
    
    public static void deleteRow(){
        
        String email = "maurizio.buoni@gmail.com";

        try{
            
            String query = "DELETE FROM utenti WHERE email = '"+email+"'";
            PreparedStatement ins = connection.prepareStatement(query);
            ins.executeUpdate();
            
        }catch(SQLException sql_e){
            sql_e.printStackTrace();
        }
    }*/
        
}
