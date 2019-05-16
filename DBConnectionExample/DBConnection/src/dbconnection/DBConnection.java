package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *
 * @author lorenzovitale
 */
public class DBConnection {

    static final String DB_URL = "jdbc:mysql://localhost:8889/prova";
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_USER = "root";
    static final String DB_PASSWD = "root";
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    
    public static void main(String[] args){
        
            
        try{
            
           // CREO LA CONNESSIONE AL DATABASE
           Class.forName(DB_DRV);
           connection=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
           statement=connection.createStatement();
           
           // EFFETTUO UNA SELECT
           DBConnection.viewRows();
           
           // ESEGUO UNA QUERY DI INSERIMENTO
           System.out.println("*********** Inserisco un nuovo utente ************");
           DBConnection.insertRow();
           
           // EFFETTUO UNA SELECT (CON IL VALORE AGGIUNTO)
           DBConnection.viewRows();
           
           // ESEGUO UNA QUERY DI AGGIORNAMENTO
           System.out.println("*********** Aggiorno la via dell'utente inserito ************");
           DBConnection.updateRow();
           
           // EFFETTUO UNA SELECT (CON IL VALORE AGGIORNATO)
           DBConnection.viewRows();
           
           // EFFETTUO UNA DELETE
           System.out.println("*********** Cancello l' utente ************");
           DBConnection.deleteRow();
           
           // EFFETTUO UNA SELECT (CON LA RIMOZIONE DELLA RIGA INSERITA)
           DBConnection.viewRows();
           
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
           try {
              resultSet.close();
              statement.close();
              connection.close();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
        }
     }
    
    
    public static void viewRows(){
        
        try{
            // ESEGUO UNA QUERY DI SELEZIONE
            PreparedStatement sel = connection.prepareStatement("SELECT * FROM utenti");
            resultSet=sel.executeQuery();

            int cont = 1;
            while(resultSet.next()){
               System.out.printf("****** Utente "+cont+" *******\n");
               System.out.printf("Email: %s\t\n",resultSet.getString("email"));
               System.out.printf("Nome: %s\t\n",resultSet.getString("nome"));
               System.out.printf("Cognome: %s\t\n",resultSet.getString("cognome"));
               System.out.printf("Indirizzo: %s\t\n",resultSet.getString("indirizzo"));
               System.out.printf("Città: %s",resultSet.getString("citta"));
               System.out.printf("\n\n ");
               cont++;
            }
        }catch(SQLException sql_ex){
            sql_ex.printStackTrace();
        }
    }
    
    
    public static void insertRow(){
        
        String email = "maurizio.buoni@gmail.com";
        String nome = "Maurizio";
        String cognome = "Buoni";
        String indirizzo = "via dell Repubblica, 5";
        String citta = "Palermo";

        try{
            
            String query = "INSERT INTO utenti (email,nome,cognome,indirizzo,citta)VALUES"
                    + "('"+email+"','"+nome+"','"+cognome+"','"+indirizzo+"','"+citta+"')";
            
            PreparedStatement ins = connection.prepareStatement(query);
            ins.executeUpdate();
            
        }catch(SQLException sql_e){
            sql_e.printStackTrace();
        }
    }
    
    public static void updateRow(){
        
        String email = "maurizio.buoni@gmail.com";

        try{
            
            String query = "UPDATE utenti SET indirizzo = 'via Pasolini, 5' WHERE email = '"+email+"'";
            PreparedStatement ins = connection.prepareStatement(query);
            ins.executeUpdate();
            
        }catch(SQLException sql_e){
            sql_e.printStackTrace();
        }
    }
    
    public static void deleteRow(){
        
        String email = "maurizio.buoni@gmail.com";

        try{
            
            String query = "DELETE FROM utenti WHERE email = '"+email+"'";
            PreparedStatement ins = connection.prepareStatement(query);
            ins.executeUpdate();
            
        }catch(SQLException sql_e){
            sql_e.printStackTrace();
        }
    }
        
}
