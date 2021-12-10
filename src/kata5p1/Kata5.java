package kata5p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Kata5 {
    public static void main(String[] args) {
        Kata5 easy = new Kata5();
        crearTabla();
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
        String sql = "SELECT * FROM EMAIL";
        
        try (   Connection conexion = connect();
                Statement statement = conexion.createStatement();
                ResultSet respuesta = statement.executeQuery(sql)){
            if (!respuesta.next()) System.out.println("La tabla existe pero no tiene contenido");
            while (respuesta.next()){
                System.out.println(respuesta.getInt("Id") + "\t" + 
                                   respuesta.getString("Mail") + "\t");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void crearTabla(){
        String url = "jdbc:sqlite:KATA5.db";
        
        String sql = "CREATE TABLE IF NOT EXISTS EMAIL (\n"
                + " Id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " Mail text NOT NULL);";
        
        try (Connection conexion = DriverManager.getConnection(url);
                Statement statement = conexion.createStatement()){
            statement.execute(sql);
            System.out.println("Tabla creada");
        } catch (SQLException e1){
            System.out.println(e1.getMessage());
        }
    }
}