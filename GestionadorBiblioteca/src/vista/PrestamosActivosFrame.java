package vista;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PrestamosActivosFrame extends JFrame {

    public PrestamosActivosFrame() {

        setTitle("Préstamos Activos");
        setSize(900,500);
        setLocationRelativeTo(null);

        String[] columnas = {
                "Estudiante",
                "Libro",
                "Fecha"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas,0);
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla));

        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "SELECT e.nombre AS estudiante," +
                    " l.titulo AS libro," +
                    " p.fecha_prestamo " +
                    "FROM prestamos p " +
                    "JOIN estudiantes e ON p.id_estudiante=e.id_estudiante " +
                    "JOIN libros l ON p.id_libro=l.id_libro";

            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while(rs.next()) {

                modelo.addRow(new Object[]{
                        rs.getString("estudiante"),
                        rs.getString("libro"),
                        rs.getDate("fecha_prestamo")
                });
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        setVisible(true);
    }
}