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
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document docNews;
    private Node rootNews;
    private NodeList listNews;
    private Document docUtenti;
    private Node rootUtenti;
    private NodeList listUtenti;
    
    public ServerThread(Socket client,File news,File utenti){
        this.client = client;
        try{
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            
            docNews = dBuilder.parse(news);
            rootNews = docNews.getFirstChild();
            listNews = ((Element)rootNews).getElementsByTagName("news");
            
            docUtenti = dBuilder.parse(news);
            rootUtenti = docUtenti.getFirstChild();
            listUtenti = ((Element)rootUtenti).getElementsByTagName("utente");
        }catch(Exception ex){
            System.out.println("Errore lettura file xml");
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        try{
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
            System.out.println("...in attesa delle credenziali...");
            String email = in.readLine();//
            System.out.println("[ email: "+email+" ]");
            String password = in.readLine();//
            System.out.println("[ password: "+password+" ]");
            System.out.println("...controllo delle credenziali...");
            //CONTROLLO DELLE CREDENZIALI
            boolean login = false;
            for(int i=0; i<listUtenti.getLength();i++){
                Node utente = listUtenti.item(i);
                if(utente.getNodeType() == Node.ELEMENT_NODE){
                    Element el = (Element)utente;
                    if(el.getElementsByTagName("email").equals(email) && el.getElementsByTagName("password").equals(password))
                        login = true;
                }
            }
            if(login){
                out.println("login avvenuto con successo");
                System.out.println("login avvenuto con successo");
            }
            out.println("end");
            System.out.println("...chiusura connessione "+client.getLocalAddress()+" ...");
            client.close();
            in.close();
            out.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
