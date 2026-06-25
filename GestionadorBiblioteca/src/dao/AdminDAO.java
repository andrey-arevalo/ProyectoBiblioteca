package dao;

import conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    // TOTAL LIBROS
    public int totalLibros() {

        int total = 0;

        try {

            Connection con =
                    ConexionBD.conectar();

            String sql =
                    "SELECT COUNT(*) AS total "
                    + "FROM libros";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                total =
                        rs.getInt("total");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return total;
    }

    // TOTAL PRESTAMOS
    public int totalPrestamos() {

        int total = 0;

        try {

            Connection con =
                    ConexionBD.conectar();

            String sql =
                    "SELECT COUNT(*) AS total "
                    + "FROM prestamos";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                total =
                        rs.getInt("total");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return total;
    }

    // TOTAL MULTAS
    public int totalMultas() {

        int total = 0;

        try {

            Connection con =
                    ConexionBD.conectar();

            String sql =
                    "SELECT COUNT(*) AS total "
                    + "FROM multas";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                total =
                        rs.getInt("total");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return total;
    }

    // TOTAL ESTUDIANTES
    public int totalEstudiantes() {

        int total = 0;

        try {

            Connection con =
                    ConexionBD.conectar();

            String sql =
                    "SELECT COUNT(*) AS total "
                    + "FROM estudiantes";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                total =
                        rs.getInt("total");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return total;
    }
}