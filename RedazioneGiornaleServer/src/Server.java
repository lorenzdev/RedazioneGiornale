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
public class Server {
    private static int porta = 9999;
    private static File news = new File("News.xml");
    private static File utenti = new File("Utenti.xml");
    public static void main(String[] args){
        try{
            System.out.println("Creazione server socket...");
            ServerSocket server = new ServerSocket(porta);
            System.out.println("...in attesa di connessioni...");
            while(true){
                Socket client = server.accept();
                System.out.println("...connessione "+client.getLocalAddress()+" effettuata...");
                ServerThread clientThread = new ServerThread(client,news, utenti);
                clientThread.run();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}