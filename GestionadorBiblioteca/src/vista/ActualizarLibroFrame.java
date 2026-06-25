package vista;

import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ActualizarLibroFrame
        extends JFrame {

    public ActualizarLibroFrame() {

        setTitle("Actualizar Libro");
        setSize(500,400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6,2,10,10));

        // CAMPOS
        JTextField txtBuscar = new JTextField();

        JTextField txtTitulo = new JTextField();

        JTextField txtAutor =new JTextField();

        // COMBOBOX CATEGORIAS
        String[] categorias = {
                "Programacion",
                "Base de Datos",
                "Redes",
                "Matematicas",
                "Fisica",
                "Literatura"
        };

        JComboBox<String> cbCategoria = new JComboBox<>(categorias);
        JButton btnBuscar = new JButton("Buscar" );

        JButton btnActualizar =new JButton("Actualizar");

        // COMPONENTES
        add(new JLabel("Libro a Buscar"));

        add(txtBuscar);

        add(btnBuscar);

        add(new JLabel(""));

        add(new JLabel( "Nuevo Titulo" ));

        add(txtTitulo);

        add(new JLabel("Nuevo Autor"));

        add(txtAutor);

        add(new JLabel("Nueva Categoria"));

        add(cbCategoria);

        add(new JLabel(""));
        add(btnActualizar);

        // =========================
        // BUSCAR LIBRO
        // =========================

        btnBuscar.addActionListener(e -> {
            try {
                Connection con = ConexionBD.conectar();
                String sql =
                        "SELECT * "
                        + "FROM libros "
                        + "WHERE LOWER(titulo)=LOWER(?)";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,txtBuscar.getText());

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    txtTitulo.setText(rs.getString("titulo"));
                    txtAutor.setText(rs.getString("autor"));

                    JOptionPane.showMessageDialog(null,"Libro encontrado");

                } else {
                    JOptionPane.showMessageDialog(null,"Libro no encontrado");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // =========================
        // ACTUALIZAR
        // =========================
        btnActualizar.addActionListener(e -> {

            try {
                Connection con =
                        ConexionBD.conectar();

                // CATEGORIA A ID
                int idCategoria = 1;
                String categoria =
                        cbCategoria
                                .getSelectedItem()
                                .toString();
                switch (categoria) {
                    case "Programacion":
                        idCategoria = 1;
                        break;
                    case "Base de Datos":
                        idCategoria = 2;
                        break;
                    case "Redes":
                        idCategoria = 3;
                        break;
                    case "Matematicas":
                        idCategoria = 4;
                        break;
                    case "Fisica":
                        idCategoria = 5;
                        break;
                    case "Literatura":
                        idCategoria = 6;
                        break;
                }

                String sql =
                        "UPDATE libros "
                        + "SET titulo=?, "
                        + "autor=?, "
                        + "id_categoria=? "
                        + "WHERE LOWER(titulo)=LOWER(?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1,txtTitulo.getText());

                ps.setString(2,txtAutor.getText());

                ps.setInt(3,idCategoria);

                ps.setString(4,txtBuscar.getText());

                int filas = ps.executeUpdate();

                if (filas > 0) {
                	JOptionPane.showMessageDialog(null,"Libro actualizado");

                } else {
                    JOptionPane.showMessageDialog(null,"No se pudo actualizar");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        setVisible(true);
    }
}