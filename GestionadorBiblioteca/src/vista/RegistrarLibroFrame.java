package vista;

import dao.LibroDAO;
import modelo.Libro;

import javax.swing.*;
import java.awt.*;

public class RegistrarLibroFrame
        extends JFrame {

    public RegistrarLibroFrame() {

        setTitle("Registrar Libro");
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,2));

        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();

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

        JButton btnGuardar = new JButton("Guardar");

        // COMPONENTES
        add(new JLabel("Titulo"));
        add(txtTitulo);

        add(new JLabel("Autor"));
        add(txtAutor);

        add(new JLabel("Categoria"));
        add(cbCategoria);

        add(btnGuardar);

        // EVENTO
        btnGuardar.addActionListener(e -> {

            String titulo = txtTitulo.getText();

            String autor = txtAutor.getText();

            // OBTENER CATEGORIA
            String categoriaSeleccionada = cbCategoria.getSelectedItem().toString();

            // CONVERTIR A ID
            int idCategoria = 1;

            switch (categoriaSeleccionada) {

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

            Libro libro =
                    new Libro(0,titulo,
                            autor,
                            idCategoria
                    );

            LibroDAO dao =
                    new LibroDAO();

            dao.guardarLibro(libro);

            JOptionPane.showMessageDialog(
                    null,
                    "Libro registrado"
            );
        });

        setVisible(true);
    }
}