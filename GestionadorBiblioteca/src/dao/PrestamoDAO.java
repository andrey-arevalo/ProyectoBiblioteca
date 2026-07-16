package dao;

import conexion.ConexionBD;
import modelo.Prestamo;

import java.sql.*;

public class PrestamoDAO {
    public void guardarPrestamo(Prestamo prestamo) {
        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "INSERT INTO prestamos "
                    + "(id_estudiante,id_libro,fecha_prestamo) "
                    + "VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,prestamo.getEstudiante().getId_estudiante());
            ps.setInt(2,prestamo.getLibro().getId_libro());
            ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}