package kata5p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Kata5 {
    public static void main(String[] args) {
        Kata5 kata5 = new Kata5();
        String ficheroEmail = "email.txt";
        List<String> listaCorreos = MailListReader.read(ficheroEmail);
        kata5.insertData(listaCorreos);
        kata5.selectAll();
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
    
    public void insertData(List<String> emails) {
        String sql = "INSERT INTO EMAIL(Mail) VALUES(?)";
        try (Connection conexion = connect();
                PreparedStatement pst = conexion.prepareStatement(sql)){
            for (String email : emails) {
                pst.setString(1, email);
                pst.executeUpdate();
            }
        } catch (SQLException e1){
            System.out.println(e1.getMessage());
        }
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