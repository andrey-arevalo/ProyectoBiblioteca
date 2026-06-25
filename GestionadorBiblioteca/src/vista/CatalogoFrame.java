package vista;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CatalogoFrame
        extends JFrame {

    public CatalogoFrame() {

        setTitle("Catalogo");
        setSize(800,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // COLUMNAS
        String[] columnas = {
                "ID",
                "Titulo",
                "Autor",
                "Categoria",
                "Disponible"
        };
        
        // MODELO
        DefaultTableModel modelo = new DefaultTableModel(columnas,0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // CARGAR DATOS
        try {
            Connection con = ConexionBD.conectar();

            String sql =
                    "SELECT "
                    + "l.id_libro, "
                    + "l.titulo, "
                    + "l.autor, "
                    + "c.nombre AS categoria, "
                    + "l.disponible "

                    + "FROM libros l "

                    + "JOIN categorias c "
                    + "ON l.id_categoria="
                    + "c.id_categoria";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Object[] fila = {

                        rs.getInt("id_libro"),
                        rs.getString("titulo"),rs.getString("autor"),
                        rs.getString("categoria"),
                        rs.getBoolean("disponible")
                };
                modelo.addRow(fila);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setVisible(true);
    }
}