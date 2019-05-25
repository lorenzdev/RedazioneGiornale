/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package redazionegiornaleclient;

import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author rognoni.federico
 */
public class RedazioneGiornaleClient {
private static int port = 9999; 

public static void main(String[] args){
        try{
            
            Scanner scanner = new Scanner(System.in);
           
            
            System.out.println("Qual è l'indirizzo del server?");          
            String address = scanner.nextLine();
            
            // CREO IL SOCKET
            Socket client = new Socket(address, port);
            
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            //menu scelta login/iscrizione
            System.out.println(" A) Login\n B) Iscrizione");
            String scelta = scanner.nextLine();
            out.println(scelta);
            if(scelta.equals("A")){
                //richiedo email e password e li mando al server
                System.out.println("inserisci la tua email");
                String email = scanner.nextLine();
                out.println(email);

                System.out.println("inserisci la password");
                String password = scanner.nextLine();
                out.println(password);

                String mem=in.readLine();
                System.out.println(mem);
                
                //menu scelta operazione
                System.out.println(" A) Richiedi news\n B) Inserisci news");
                scelta = scanner.nextLine();
                out.println(scelta);
                
                //se sono richieste le news
                if(scelta.equals("A")){
                    System.out.println("inserisci il topic delle news che vuoi visualizzare");
                    scelta = scanner.nextLine();
                    out.println(scelta);
                    mem=in.readLine();
                    //attendo che il server mandi le news
                    while(!mem.equals("end")){
                        System.out.println(mem);
                        mem=in.readLine();
                    }
                }
                //inserimento di una news
                else{
                    System.out.println("inserisci il topic della news");
                    String topic= scanner.nextLine();
                    out.println(topic);
                    
                    System.out.println("inserisci il titolo della news");
                    String titolo= scanner.nextLine();
                    out.println(titolo);
                    
                }
                
            }
            else{
                System.out.println("inserisci l'email che vuoi utlizzare");
                String email = scanner.nextLine();
                out.println(email);
                
                System.out.println("inserisci la password che vuoi utilizzare");
                String password = scanner.nextLine();
                out.println(password);
                
                System.out.println("inserisci il tuo nome");
                String nome = scanner.nextLine();
                out.println(nome);
                
                System.out.println("inserisci il tuo cognome");
                String cognome = scanner.nextLine();
                out.println(cognome);
                
                System.out.println("inserisci il tuo telefono");
                String telefono = scanner.nextLine();
                out.println(telefono);
                
                System.out.println("inserisci l'anno di nascita");
                String anno = scanner.nextLine();
                out.println(anno);
                
                System.out.println("inserisci il mese di nascita");
                String mese = scanner.nextLine();
                out.println(mese);
                
                System.out.println("inserisci il giorno di nascita");
                String giorno = scanner.nextLine();
                out.println(giorno);
                
                System.out.println("inserisci l'indirizzo di residenza");
                String indirizzo_residenza = scanner.nextLine();
                out.println(indirizzo_residenza);
                
                System.out.println("inserisci la citta' di residenza");
                String citta_residenza = scanner.nextLine();
                out.println(citta_residenza);
            }
            client.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
    
