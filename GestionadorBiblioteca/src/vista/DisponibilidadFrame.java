package vista;

import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DisponibilidadFrame extends JFrame {

    public DisponibilidadFrame() {
        setTitle("Validar Disponibilidad");
        setSize(500,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,2,10,10));

        // COMPONENTES
        JTextField txtLibro =new JTextField();
        JButton btnValidar =new JButton("Validar");
        JLabel lblResultado =new JLabel("");
        
        // AGREGAR COMPONENTES
        add(new JLabel("Nombre Libro"));
        add(txtLibro);
        add(new JLabel(""));
        add(btnValidar);
        add(new JLabel("Estado:"));
        add(lblResultado);

        // =========================
        // VALIDAR DISPONIBILIDAD
        // =========================

        btnValidar.addActionListener(e -> {
            try {
                Connection con =  ConexionBD.conectar();
                String sql =
                        "SELECT disponible "
                        + "FROM libros "
                        + "WHERE LOWER(titulo)=LOWER(?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1,txtLibro.getText());

                ResultSet rs = ps.executeQuery();

                // SI EXISTE
                if (rs.next()) {
                    boolean disponible =rs.getBoolean("disponible");
                    if (disponible) {
                        lblResultado.setText("Disponible");
                    } else {
                        lblResultado.setText("No disponible");
                    }
                } else {
                	
                	lblResultado.setText("Libro no encontrado");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        setVisible(true);
    }
}