/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package redazionegiornaleclient;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Calendar;
/**
 *
 * @author rognoni.federico
 */
public class RedazioneGiornaleClient {
private static int port = 9999; 
private static String email;
private static String password;
private static Calendar calendario;

public static void main(String[] args){
        try{
            
            Scanner scanner = new Scanner(System.in);
            calendario = Calendar.getInstance();
            
            System.out.println("Qual è l'indirizzo del server?");          
            String address = scanner.nextLine();
            
            // CREO IL SOCKET
            Socket client = new Socket(address, port);
            
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while(true){
                //menu scelta login/iscrizione
                System.out.println(" A) Login\n B) Iscrizione\n C) Esci");
                String scelta = scanner.nextLine();
                out.println(scelta);
                if(scelta.equals("A")){
                    //richiedo email e password e li mando al server
                    System.out.println("inserisci la tua email");
                    email = scanner.nextLine();
                    out.println(email);

                    System.out.println("inserisci la password");
                    password = scanner.nextLine();
                    out.println(password);

                    String mem=in.readLine();
                    System.out.println("\n"+mem+"\n");

                    if(mem.equals("Login avvenuto con successo")){
                        while(true){
                            //menu scelta operazione
                            System.out.println(" A) Richiedi news\n B) Inserisci news\n C) Logout");
                            scelta = scanner.nextLine();
                            out.println(scelta);

                            //se sono richieste le news
                            if(scelta.equals("A")){
                                System.out.println(" A) Ricerca per topic\n B) Ricerca per intervallo \n C) Ricerca per email dell'autore");
                                scelta = scanner.nextLine();
                                out.println(scelta);
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
                                }else if(scelta.equals("B")){
                                    System.out.println("inserisci il mese (numerico) dell'intervallo in cui vuoi cercare");
                                    scelta = scanner.nextLine();
                                    out.println(scelta);
                                    mem=in.readLine();
                                    //attendo che il server mandi le news
                                    while(!mem.equals("end")){
                                        System.out.println(mem);
                                        mem=in.readLine();
                                    }
                                }else if(scelta.equals("C")){
                                    System.out.println("inserisci l'email dell'autore di cui vuoi visualizzare i post");
                                    scelta = scanner.nextLine();
                                    out.println(scelta);
                                    mem=in.readLine();
                                    //attendo che il server mandi le news
                                    while(!mem.equals("end")){
                                        System.out.println(mem);
                                        mem=in.readLine();
                                    }
                                }
                            }else if(scelta.equals("B")){
                                System.out.println("inserisci il topic della news che vuoi inserire");
                                String topic = scanner.nextLine();
                                out.println(topic);
                                System.out.println("inserisci il titolo della news che vuoi inserire");
                                String titolo= scanner.nextLine();
                                out.println(titolo);
                                System.out.println("inserisci la descrizione della news che vuoi inserire");
                                String descrizione = scanner.nextLine();
                                out.println(descrizione);
                                System.out.println("inserisci il contenuto della news che vuoi inserire");
                                String contenuto = scanner.nextLine();
                                out.println(contenuto);
                                //invio la data attuale come data di creazione della news
                                out.println(calendario.get(Calendar.YEAR)+"-"+(calendario.get(Calendar.MONTH)+1)+"-"+calendario.get(Calendar.DATE));
                                //invio l'email dell'utente loggato come email dell'autore
                                out.println(email);
                                //attendo risposta dal server
                                mem=in.readLine();
                                System.out.println("\n"+mem+"\n");
                            }else if(scelta.equals("C")){
                                break;
                            }
                        }
                    }
                }else if(scelta.equals("B")){
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

                    System.out.println("inserisci il mese di nascita");
                    String mese = scanner.nextLine();

                    System.out.println("inserisci il giorno di nascita");
                    String giorno = scanner.nextLine();
                    out.println(anno+"-"+mese+"-"+giorno);

                    System.out.println("inserisci l'indirizzo di residenza");
                    String indirizzo_residenza = scanner.nextLine();
                    out.println(indirizzo_residenza);

                    System.out.println("inserisci la citta' di residenza");
                    String citta_residenza = scanner.nextLine();
                    out.println(citta_residenza);
                    //attendo risposta dal server
                    String mem=in.readLine();
                    System.out.println("\n"+mem+"\n");
                }else if(scelta.equals("C")){
                    break;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
    
