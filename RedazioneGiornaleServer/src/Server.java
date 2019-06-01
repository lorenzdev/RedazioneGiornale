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
import java.util.ArrayList;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
/**
 *
 * @author totaro.christian
 */
public class Server {
    private static int porta = 9999;
    //private static File news = new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\News.xml");
    private static File news = new File("C:\\Program Files\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\News.xml");
    //private static File utenti = new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\Utenti.xml");
    private static File utenti = new File("C:\\Program Files\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\Utenti.xml");
    private static DBConnection dbc;
    private static int utenti_connessi = 0;
    private static ArrayList<ServerThread> multicasting = new ArrayList<ServerThread>();
    
    /**
     * Incrementa il numero di utenti connessi al server.
     */
    public static void aumentaUtentiConnessi(){
        Server.utenti_connessi++;
    }
    
    /**
     * Decrementa il numero di utenti connessi al server.
     */
    public static void diminuisciUtentiConnessi(){
        Server.utenti_connessi--;
    }
    
    /**
     * Aggiunge un client al vettore utilizzato durante la modalità multicast.
     * In tale modalità i client, o meglio i thread inseriti in questo vettore, rimangono in ascolto delle news aggiunte dagli altri client.
     * @param client è il thread che va aggiunto al vettore multicasting.
     */
    public static void aggiungiUtentiMulticasting(ServerThread client){
        multicasting.add(client);
    }
    /**
     * Invia ai client, il cui thread si trova nel vettore multicasting, la news composta dalle variabili passate al metodo.
     * @param topic è il topic della news.
     * @param titolo è il titolo della news.
     * @param descrizione è la descrizione della news.
     * @param contenuto è il contenuto della news.
     * @param data è la data di pubblicazione della news.
     * @param email_autore è l'email dell'autore della news.
     */
    public static void invioNewsMulticasting(String topic,String titolo,String descrizione,String contenuto,String data,String email_autore){
        try{
            for(int i=0;i<multicasting.size();i++){
                ServerThread client = multicasting.get(i);
                client.out.println("\n\n titolo:    "+titolo+"\n descrizione:    "+descrizione+"\n contenuto:    "+contenuto+"\n data pubblicazione:    "+data+"\n email autore:    "+email_autore+"\n\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        try{
            System.out.println("Creazione server socket...");
            ServerSocket server = new ServerSocket(porta);
            System.out.println("...conversione database in xml...");
            dbc = new DBConnection();
            while(true){
               /* if(utenti_connessi == 0){
                    dbc.ConversioneXMLtoDB();
                    dbc.ConversioneDBtoXML();
                }*/
                System.out.println("    [utenti connessi: "+utenti_connessi+" ]");
                System.out.println("...in attesa di connessioni...");
                Socket client = server.accept();
                System.out.println("...connessione "+client.getLocalAddress()+" effettuata...");
                ServerThread clientThread = new ServerThread(client,news, utenti,dbc);
                clientThread.start();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            dbc.ConversioneXMLtoDB();
        }
    }
}
