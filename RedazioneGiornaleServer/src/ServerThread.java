/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.io.*;
import java.net.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
/**
 *
 * @author totaro.christian
 */
public class ServerThread extends Thread{
    private static Socket client;
    private BufferedReader in;
    public PrintWriter out;
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
    
    public ServerThread(Socket client,File news,File utenti, DBConnection dbc){
        this.FileUtenti = utenti;
        this.FileNews = news;
        this.client = client;
        this.dbc = dbc;
        this.login = false;
        try{
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            
            docUtenti = dBuilder.parse(this.FileUtenti);
            rootUtenti = docUtenti.getFirstChild();
            listUtenti = ((Element)rootUtenti).getElementsByTagName("Utente");
            
            docNews = dBuilder.parse(this.FileNews);
            rootNews = docNews.getFirstChild();
            listNews = ((Element)rootNews).getElementsByTagName("News"); 
        }catch(Exception ex){
            System.out.println("Errore lettura file xml");
            ex.printStackTrace();
        }
    }
    private static void AggiornaFileUtentiUtilizzato(){
        try{
            docUtenti = dBuilder.parse(ServerThread.FileUtenti);
            rootUtenti = docUtenti.getFirstChild();
            listUtenti = ((Element)rootUtenti).getElementsByTagName("Utente");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static void AggiornaFileNewsUtilizzato(){
        try{
            docNews = dBuilder.parse(ServerThread.FileNews);
            rootNews = docNews.getFirstChild();
            listNews = ((Element)rootNews).getElementsByTagName("News"); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void ControlloCredenziali(String email, String password){
        System.out.println("...controllo delle credenziali...");
        //CONTROLLO DELLE CREDENZIALI
        for(int i=0; i<listUtenti.getLength();i++){
            Node utente = listUtenti.item(i);
            if(utente.getNodeType() == Node.ELEMENT_NODE){
                Element el = (Element)utente;
                if(el.getElementsByTagName("email").item(0).getTextContent().equals(email) && el.getElementsByTagName("password").item(0).getTextContent().equals(password))
                    login = true;
            }
        }
        if(login){
            out.println("Login avvenuto con successo");
            System.out.println("...login avvenuto con successo...");
        }else{
            out.println("Nome utente o password non validi");
            System.out.println("...nome utente o password non validi...");
        }
    }
    
    private void RichiestaNews(String tipo, String value){
        boolean trovate = false;
        if(tipo.equals("A")){
            for(int i=(listNews.getLength()-1);i>=0;i--){
                Node news = listNews.item(i);
                if(news.getNodeType() == Node.ELEMENT_NODE){
                    Element el = (Element)news;
                    if(el.getElementsByTagName("topic").item(0).getTextContent().equals(value)){
                        trovate = true;
                        out.println("\n\n titolo:    "+el.getElementsByTagName("titolo").item(0).getTextContent()+"\n descrizione:    "+el.getElementsByTagName("descrizione").item(0).getTextContent()+"\n contenuto:    "+el.getElementsByTagName("contenuto").item(0).getTextContent()+"\n data pubblicazione:    "+el.getElementsByTagName("data").item(0).getTextContent()+"\n email autore:    "+el.getElementsByTagName("email_autore").item(0).getTextContent()+"\n\n");
                    } 
                }
            }
        }else if(tipo.equals("B")){
            for(int i=(listNews.getLength()-1);i>=0;i--){
                Node news = listNews.item(i);
                if(news.getNodeType() == Node.ELEMENT_NODE){
                    Element el = (Element)news;
                    String mese = el.getElementsByTagName("data").item(0).getTextContent().split("-")[1];
                    if(mese.equals(value)){
                        trovate = true;
                        out.println("\n\n titolo:    "+el.getElementsByTagName("titolo").item(0).getTextContent()+"\n descrizione:    "+el.getElementsByTagName("descrizione").item(0).getTextContent()+"\n contenuto:    "+el.getElementsByTagName("contenuto").item(0).getTextContent()+"\n data pubblicazione:    "+el.getElementsByTagName("data").item(0).getTextContent()+"\n email autore:    "+el.getElementsByTagName("email_autore").item(0).getTextContent()+"\n\n");
                    } 
                }
            }
        }else if(tipo.equals("C")){
            for(int i=(listNews.getLength()-1);i>=0;i--){
                Node news = listNews.item(i);
                if(news.getNodeType() == Node.ELEMENT_NODE){
                    Element el = (Element)news;
                    if(el.getElementsByTagName("email_autore").item(0).getTextContent().equals(value)){
                        trovate = true;
                        out.println("\n\n titolo:    "+el.getElementsByTagName("titolo").item(0).getTextContent()+"\n descrizione:    "+el.getElementsByTagName("descrizione").item(0).getTextContent()+"\n contenuto:    "+el.getElementsByTagName("contenuto").item(0).getTextContent()+"\n data pubblicazione:    "+el.getElementsByTagName("data").item(0).getTextContent()+"\n email autore:    "+el.getElementsByTagName("email_autore").item(0).getTextContent()+"\n\n");
                    } 
                }
            }
        }
        
        if(!trovate){
            System.out.println("    ...nessuna news trovata...");
            out.println("Nessuna news trovata");
        }
        out.println("end");
    }
    
    public void RegistrazioneCredenziali(String email,String password,String nome,String cognome,String telefono,String data_nascita,String indirizzo_residenza,String citta_residenza){
        dbc.InserisciUtenteXML(email, password, nome, cognome, telefono, data_nascita, indirizzo_residenza, citta_residenza);
        System.out.println("...utente registrato...");
        out.println("Registrazione effettuata correttamente");
    }
    
    public void SalvataggioNews(String topic,String titolo,String descrizione,String contenuto,String data,String email_autore){
        dbc.InserisciNewsXML(topic, titolo, descrizione, contenuto, data, email_autore);
        System.out.println("...news salvata...");
        out.println("Salvataggio news effettuato correttamente");
    }
    
    @Override
    public void run(){
        try{
            Server.aumentaUtentiConnessi();
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
            while(true){
                System.out.println("...in attesa di ordini dal client...");
                String scelta = in.readLine();
                if(scelta.equals("A")){
                    System.out.println("...in attesa delle credenziali...");
                    String email = in.readLine();//
                    System.out.println("    [ email: "+email+" ]");
                    String password = in.readLine();//
                    System.out.println("    [ password: "+password+" ]");
                    ControlloCredenziali(email,password);
                    if(login){
                        while(true){
                            System.out.println("...in attesa di ordini dal client loggato...");
                            scelta = in.readLine();
                            if(scelta.equals("A")){
                                System.out.println("...richiesta delle news...\n    ...in attesa del tipo di richiesta...");
                                String tipo = in.readLine();
                                System.out.println("    [ tipo ricerca: "+tipo+" ]");
                                if(tipo.equals("A")){
                                    String topic = in.readLine();
                                    System.out.println("    [ topic: "+topic+" ]");
                                    RichiestaNews(tipo,topic);
                                }else if(tipo.equals("B")){
                                    String mese = in.readLine();
                                    System.out.println("    [ mese: "+mese+" ]");
                                    RichiestaNews(tipo,mese);
                                }else if(tipo.equals("C")){
                                    String email_autore = in.readLine();
                                    System.out.println("    [ email autore: "+email_autore+" ]");
                                    RichiestaNews(tipo,email_autore);
                                }
                            } else if(scelta.equals("B")){
                                System.out.println("...in attesa dei dati della news da aggiungere...");
                                String topic = in.readLine();
                                System.out.println("    [ topic: "+topic+" ]");
                                String titolo = in.readLine();
                                System.out.println("    [ titolo: "+titolo+" ]");
                                String descrizione = in.readLine();
                                System.out.println("    [ descrizione: "+descrizione+" ]");
                                String contenuto = in.readLine();
                                System.out.println("    [ contenuto: "+contenuto+" ]");
                                String data = in.readLine();
                                System.out.println("    [ data: "+data+" ]");
                                String email_autore = in.readLine();
                                System.out.println("    [ email autore: "+email_autore+" ]");
                                SalvataggioNews(topic,titolo,descrizione,contenuto,data,email_autore);
                                AggiornaFileNewsUtilizzato();
                            } else if(scelta.equals("C")){
                                login = false;
                                break;
                            }else if(scelta.equals("D")){
                                Server.aggiungiUtentiMulticasting(this);
                            }
                        }
                    }
                }else if(scelta.equals("B")){
                    System.out.println("...in attesa dei dati di registrazione...");
                    String email = in.readLine();//
                    System.out.println("    [ email: "+email+" ]");
                    String password = in.readLine();//
                    System.out.println("    [ password: "+password+" ]");
                    String nome = in.readLine();//
                    System.out.println("    [ nome: "+nome+" ]");
                    String cognome = in.readLine();//
                    System.out.println("    [ cognome: "+cognome+" ]");
                    String telefono = in.readLine();//
                    System.out.println("    [ telefono: "+telefono+" ]");
                    String data_nascita = in.readLine();//
                    System.out.println("    [ data di nascita: "+data_nascita+" ]");
                    String indirizzo_residenza = in.readLine();//
                    System.out.println("    [ indirizzo_residenza: "+indirizzo_residenza+" ]");
                    String citta_residenza = in.readLine();//
                    System.out.println("    [ città di residenza: "+citta_residenza+" ]");
                    RegistrazioneCredenziali(email,password,nome,cognome,telefono,data_nascita,indirizzo_residenza,citta_residenza);
                    AggiornaFileUtentiUtilizzato();
                }else if(scelta.equals("C")){
                    System.out.println("...chiusura connessione "+client.getLocalAddress()+" ...");
                    Server.diminuisciUtentiConnessi();
                    client.close();
                    break;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
