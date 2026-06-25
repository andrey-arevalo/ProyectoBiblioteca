package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static final String URL = "jdbc:postgresql://localhost:5432/gestorbiblioteca";
    private static final String USER = "postgres";
    private static final String PASSWORD = "482";

    public static Connection conectar() {

        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);

            System.out.println("Conexion exitosa");
            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}