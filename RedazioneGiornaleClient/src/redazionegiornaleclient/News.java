/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package redazionegiornaleclient;
import java.util.*;
/**
 *
 * @author rognoni.federico
 */
public class News {
    private static String topic;
    private static String titolo;
    private static String descrizione;
    private static String contenuto;
    Date data = new Date();
    private static String email_autore;
    public void setTopic(String Topic){
        topic=Topic;
    }
    String getTopic(){
        return topic;
    }
    public void setTitolo(String Titolo){
    titolo=Titolo;
    }
    String getTitolo(){
        return titolo;
    }
    public void setDescrizione(String Descrizione){
    descrizione=Descrizione;
    }
    String getDescrizione(){
        return descrizione;
    }
    public void setContentuto(String Contenuto){
    contenuto=Contenuto;
    }
    String getContenuto(){
        return contenuto;
    }
    public void setData(Date Data){
    data=Data;
    }
    Date getData(){
        return data;
    }
    public void setEmailAutore(String Email_Autore){
    email_autore=Email_Autore;
    }
    String getEmailAutore(){
        return email_autore;
    }
    
    
}
