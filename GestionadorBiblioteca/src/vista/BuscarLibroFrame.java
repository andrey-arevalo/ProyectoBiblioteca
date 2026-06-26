package vista;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BuscarLibroFrame extends JFrame {

    public BuscarLibroFrame() {

        setTitle("Buscar Libro");
        setSize(900,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel superior = new JPanel();
        JTextField txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        // 1. Creamos el botón de Limpiar
        JButton btnLimpiar = new JButton("Limpiar");
        
        superior.add(new JLabel("Título"));
        superior.add(txtBuscar);
        superior.add(btnBuscar);
        // 2. Lo añadimos al panel
        superior.add(btnLimpiar);
        add(superior, BorderLayout.NORTH);

        String[] columnas = {
                "ID", "Título", "Autor", "Categoría", "Disponible"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas,0);
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla),BorderLayout.CENTER);
        
        // Acción del botón Buscar
        btnBuscar.addActionListener(e -> {
            modelo.setRowCount(0);
            try {
                Connection con = ConexionBD.conectar();
                String sql = "SELECT l.id_libro, l.titulo, l.autor, c.nombre AS categoria, l.disponible " +
                             "FROM libros l " +
                             "JOIN categorias c ON l.id_categoria=c.id_categoria " +
                             "WHERE LOWER(l.titulo) LIKE LOWER(?)";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,"%" + txtBuscar.getText() + "%");
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    modelo.addRow(new Object[]{
                            rs.getInt("id_libro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("categoria"),
                            rs.getBoolean("disponible")
                    });
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        // 3. Acción del botón Limpiar
        btnLimpiar.addActionListener(e -> {
            txtBuscar.setText("");    // Borra el texto
            modelo.setRowCount(0);    // Borra las filas de la tabla
            txtBuscar.requestFocus(); // Devuelve el foco al campo de texto
        });

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnBuscar.doClick();
                }
            }
        });
        setVisible(true);
    }
}