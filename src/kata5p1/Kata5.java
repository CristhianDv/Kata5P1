package kata5p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Kata5 {
    public static void main(String[] args) {
        Kata5 easy = new Kata5();
        easy.selectAll();
    }
    
    private Connection connect(){
        Connection conexion = null;
        
        try {
            String url = "jdbc:sqlite:KATA5.db";
            conexion = DriverManager.getConnection(url);
            System.out.println("Conexión establecida a SQLITE");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conexion;
    }
    
    public void selectAll() {
        String sql = "SELECT * FROM PEOPLE";
        
        try (   Connection conexion = connect();
                Statement statement = conexion.createStatement();
                ResultSet respuesta = statement.executeQuery(sql)){
            
            while (respuesta.next()){
                System.out.println(respuesta.getInt("Id") + "\t" + 
                                   respuesta.getString("Name") + "\t" +
                                   respuesta.getString("Apellidos") + "\t" + 
                                   respuesta.getString("Departamento") + "\t");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
}