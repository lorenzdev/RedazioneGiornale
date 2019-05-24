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
    private static BufferedReader in;
    private static PrintWriter out;
    private static DocumentBuilderFactory dbFactory;
    private static DocumentBuilder dBuilder;
    private static Document docNews;
    private static Node rootNews;
    private static NodeList listNews;
    private static Document docUtenti;
    private static Node rootUtenti;
    private static NodeList listUtenti;
    
    public ServerThread(Socket client,File news,File utenti){
        this.client = client;
        try{
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            
            docUtenti = dBuilder.parse(utenti);
            rootUtenti = docUtenti.getFirstChild();
            listUtenti = ((Element)rootUtenti).getElementsByTagName("Utente");
            
            docNews = dBuilder.parse(news);
            rootNews = docNews.getFirstChild();
            listNews = ((Element)rootNews).getElementsByTagName("News"); 
        }catch(Exception ex){
            System.out.println("Errore lettura file xml");
            ex.printStackTrace();
        }
    }
    
    private static void ControlloCredenziali(String email, String password){
        System.out.println("...controllo delle credenziali...");
        //CONTROLLO DELLE CREDENZIALI
        boolean login = false;
        for(int i=0; i<listUtenti.getLength();i++){
            Node utente = listUtenti.item(i);
            if(utente.getNodeType() == Node.ELEMENT_NODE){
                Element el = (Element)utente;
                System.out.println(el.getElementsByTagName("email").item(0).getTextContent()+" "+email+" "+el.getElementsByTagName("password").item(0).getTextContent()+" "+password+"\n");
                if(el.getElementsByTagName("email").item(0).getTextContent().equals(email) && el.getElementsByTagName("password").item(0).getTextContent().equals(password))
                    login = true;
            }
        }
        if(login){
            out.println("login avvenuto con successo");
            System.out.println("login avvenuto con successo");
        }else{
            out.println("nome utente o password non validi");
            System.out.println("nome utente o password non validi");
        }
    }
    
    @Override
    public void run(){
        try{
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
            System.out.println("...in attesa di ordini dal client...");
            String scelta = in.readLine();
            if(scelta.equals("A")){
                System.out.println("...in attesa delle credenziali...");
                String email = in.readLine();//
                System.out.println("[ email: "+email+" ]");
                String password = in.readLine();//
                System.out.println("[ password: "+password+" ]");
                ControlloCredenziali(email,password);
            }else if(scelta.equals("B")){
                //inserimento da fare per registrazione
            }
            System.out.println("...in attesa di ordini dal client loggato...");
            scelta = in.readLine();
            if(scelta.equals("A")){
                System.out.println("Hai richiesto news...");
            }
            if(scelta.equals("B")){
                System.out.println("Vuoi inserire news...");
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
