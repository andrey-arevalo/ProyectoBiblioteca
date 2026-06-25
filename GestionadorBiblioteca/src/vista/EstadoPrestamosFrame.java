package vista;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EstadoPrestamosFrame extends JFrame {
	
    private JTable tabla;
    private DefaultTableModel modelo;
    public EstadoPrestamosFrame(int idEstudiante) {

        setTitle("Mis Préstamos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        modelo.addColumn("ID Préstamo");
        modelo.addColumn("Libro");
        modelo.addColumn("Fecha Préstamo");
        modelo.addColumn("ID Libro");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);


        try {
            Connection con =ConexionBD.conectar();
            String sql =
                    "SELECT p.id_prestamo, l.titulo, p.fecha_prestamo, p.id_libro " +
                    "FROM prestamos p " +
                    "JOIN libros l ON p.id_libro = l.id_libro " +
                    "WHERE p.id_estudiante = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idEstudiante);
            ResultSet rs = ps.executeQuery();
           
            boolean hayPrestamos = false;
            while (rs.next()) {
            	hayPrestamos = true; 
            	modelo.addRow(new Object[]{
                        rs.getInt("id_prestamo"),
                        rs.getString("titulo"),
                        rs.getTimestamp("fecha_prestamo"),
                        rs.getInt("id_libro"),
                        "Activo"
                });
            }
            if (!hayPrestamos) {
            	modelo.addRow(new Object[]{
                        "", "No hay préstamos registrados", "", "", ""
                });
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al cargar prestamos");
        }
        setVisible(true);
    }
}