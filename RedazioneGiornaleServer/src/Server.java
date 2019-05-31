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
    private static File news = new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\News.xml");
    //private static File news = new File("C:\\Users\\chrit\\Desktop\\RedazioneGiornale\\RedazioneGiornaleServer\\News.xml");
    private static File utenti = new File("C:\\Users\\totaro.christian.VOLTA\\AppData\\Local\\Programs\\Git\\RedazioneGiornale\\RedazioneGiornaleServer\\Utenti.xml");
    //private static File utenti = new File("C:\\Users\\chrit\\Desktop\\RedazioneGiornale\\RedazioneGiornaleServer\\Utenti.xml");
    private static DBConnection dbc;
    private static int utenti_connessi = 0;
    private static ArrayList<ServerThread> multicasting = new ArrayList<ServerThread>();
  
    public static void aumentaUtentiConnessi(){
        Server.utenti_connessi++;
    }
    
    public static void diminuisciUtentiConnessi(){
        Server.utenti_connessi--;
    }
    
    public static void aggiungiUtentiMulticasting(ServerThread client){
        multicasting.add(client);
        System.out.println("AGGIUNGO MULTICASTING: "+multicasting.size());
    }
    
    public static void invioNewsMulticasting(String topic,String titolo,String descrizione,String contenuto,String data,String email_autore){
        try{
            System.out.println("MULTICASTING: "+multicasting.size());
            for(int i=0;i<multicasting.size();i++){
                ServerThread client = multicasting.get(i);
                System.out.println(client.getId());
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
