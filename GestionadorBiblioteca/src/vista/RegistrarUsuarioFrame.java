package vista;

import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrarUsuarioFrame extends JFrame {

    public RegistrarUsuarioFrame() {

        setTitle("Registrar Estudiante");

        setSize(450,350);

        setLocationRelativeTo(null);

        setLayout(
                new GridLayout(
                        5,
                        2,
                        10,
                        10
                )
        );

        // CAMPOS
        JTextField txtNombre =
                new JTextField();

        JTextField txtCarrera =
                new JTextField();

        JPasswordField txtPassword =
                new JPasswordField();

        JButton btnRegistrar =
                new JButton("Registrar");

        // COMPONENTES

        add(new JLabel("Nombre"));
        add(txtNombre);

        add(new JLabel("Carrera"));
        add(txtCarrera);

        add(new JLabel("Contraseña"));
        add(txtPassword);

        add(new JLabel(""));
        add(btnRegistrar);

        // EVENTO

        btnRegistrar.addActionListener(e -> {

            try {

                Connection con =
                        ConexionBD.conectar();

                String nombre =
                        txtNombre.getText();

                String password =
                        String.valueOf(
                                txtPassword.getPassword()
                        );

                String carrera =
                        txtCarrera.getText();

                // ========================
                // INSERTAR EN USUARIOS
                // ========================

                String sqlUsuario =

                        "INSERT INTO usuarios "
                        + "(nombre,contrasena,id_rol) "
                        + "VALUES (?,?,3) "
                        + "RETURNING id_usuario";

                PreparedStatement psUsuario =
                        con.prepareStatement(
                                sqlUsuario
                        );

                psUsuario.setString(
                        1,
                        nombre
                );

                psUsuario.setString(
                        2,
                        password
                );

                ResultSet rs =
                        psUsuario.executeQuery();

                int idUsuario = 0;

                if (rs.next()) {

                    idUsuario =
                            rs.getInt(
                                    "id_usuario"
                            );
                }

                // ========================
                // INSERTAR EN ESTUDIANTES
                // ========================

                String sqlEstudiante =

                        "INSERT INTO estudiantes "
                        + "(nombre,carrera,id_usuario) "
                        + "VALUES (?,?,?)";

                PreparedStatement psEstudiante =
                        con.prepareStatement(
                                sqlEstudiante
                        );

                psEstudiante.setString(
                        1,
                        nombre
                );

                psEstudiante.setString(
                        2,
                        carrera
                );

                psEstudiante.setInt(
                        3,
                        idUsuario
                );

                psEstudiante.executeUpdate();

                JOptionPane.showMessageDialog(
                        null,
                        "Estudiante registrado correctamente"
                );

                dispose();

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        "Error al registrar estudiante"
                );
            }
        });

        setVisible(true);
    }
}