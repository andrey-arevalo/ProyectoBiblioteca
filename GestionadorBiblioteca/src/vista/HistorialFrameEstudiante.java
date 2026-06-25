package vista;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HistorialFrameEstudiante extends JFrame {

    public HistorialFrameEstudiante(int idEstudiante) {

        setTitle("Mi Historial de Préstamos");
        setSize(700,450);
        setLocationRelativeTo(null);

        String[] columnas = {
        		"ID",
                "Estudiante",
                "Libro",
                "Fecha Préstamo"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas,0);
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla));
        boolean hayPrestamos = false;
        
        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "SELECT " +
                            "p.id_prestamo, " +
                            "e.nombre AS estudiante, " +
                            "l.titulo, " +
                            "p.fecha_prestamo " +
                            "FROM prestamos p " +
                            "JOIN estudiantes e " +
                            "ON p.id_estudiante=e.id_estudiante " +
                            "JOIN libros l " +
                            "ON p.id_libro=l.id_libro " +
                            "WHERE p.id_estudiante=? " +
                            "ORDER BY p.fecha_prestamo DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idEstudiante);
            ResultSet rs = ps.executeQuery();
            
            boolean hayPrestamosE = false;
            while(rs.next()) {
            	hayPrestamosE = true;
            	modelo.addRow(new Object[]{
            					rs.getInt("id_prestamo"),
                                rs.getString("estudiante"),
                                rs.getString("titulo"),
                                rs.getTimestamp("fecha_prestamo")
                        }
                );
            }

            if (!hayPrestamosE) {
            	modelo.addRow(new Object[]
            			{"", "No hay prestamos registrados", "", ""
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setVisible(true);
    }
}