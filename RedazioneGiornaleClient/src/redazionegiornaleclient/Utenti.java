/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package redazionegiornaleclient;
import java.util.*;
/**
 *
 * @author guberti.fabio
 */
public class Utenti {
    private static String nome;
    private static String cognome;
    private static String password;
    private static int telefono;
    Date datadinascita= new Date();
    private static String indirizzo;
    private static String citta;     
    
    public void setNome(String Nome){
        nome=Nome;
    }
    String getNome(){
        return nome;
    }
    public void setCognome(String Cognome){
        cognome=Cognome;
    }
    String getCognome(){
        return cognome;
    }
    public void setPassword(String Password){
        password=Password;
    }
    String getPassword(){
        return password;
    }
    public void setTelefono(int Telefono){
        telefono=Telefono;
    }
    int getTelefono(){
        return telefono;
    }
    public void setDatadinascita(Date Datadinascita){
        datadinascita=Datadinascita;
    }
    Date getDatadinascita(){
        return datadinascita;
    }
    public void setIndirizzo(String Indirizzo){
        indirizzo=Indirizzo;
    }
    String getIndirizzo(){
        return indirizzo;
    }
    public void setCitta(String Citta){
        citta=Citta;
    }
    String getCitta(){
        return citta;
    }
    
}
