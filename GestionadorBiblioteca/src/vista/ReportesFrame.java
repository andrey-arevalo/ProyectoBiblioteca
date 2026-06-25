package vista;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ReportesFrame extends JFrame {

    public ReportesFrame() {

        setTitle("Reporte de Libros Prestados");
        setSize(800,500);
        setLocationRelativeTo(null);

        String[] columnas = {
                "Libro",
                "Cantidad Prestamos"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas,0);
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla));

        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "SELECT l.titulo," +
                    " COUNT(*) AS cantidad " +
                    "FROM prestamos p " +
                    "JOIN libros l " +
                    "ON p.id_libro=l.id_libro " +
                    "GROUP BY l.titulo " +
                    "ORDER BY cantidad DESC";

            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while(rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getString("titulo"),
                        rs.getInt("cantidad")
                });
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        setVisible(true);
    }
}