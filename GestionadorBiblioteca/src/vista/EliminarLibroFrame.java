package vista;

import dao.LibroDAO;

import javax.swing.*;
import java.awt.*;

public class EliminarLibroFrame extends JFrame {

    public EliminarLibroFrame() {

        setTitle("Eliminar Libro");
        setSize(350,200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,2));
        JTextField txtId = new JTextField();
        JButton btnEliminar = new JButton("Eliminar");

        add(new JLabel("ID Libro"));
        add(txtId);
        add(btnEliminar);
        getRootPane().setDefaultButton(btnEliminar);
        btnEliminar.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            LibroDAO dao = new LibroDAO();
            dao.eliminarLibro(id);
        });
        setVisible(true);
    }
}