package vista;

import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    public LoginFrame() {

        setTitle("Inicio de Sesión");
        setSize(450,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,2,15,15));

        // CAMPOS
        JTextField txtUsuario = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");

        // COMPONENTES
        add(new JLabel("Usuario"));
        add(txtUsuario);

        add(new JLabel("Contraseña"));
        add(txtPassword);

        add(new JLabel(""));
        add(btnLogin);

        // MÉTODO LOGIN
        Runnable login = () -> {
            String usuario = txtUsuario.getText().trim();
            String password =String.valueOf(txtPassword.getPassword());

            try {
                Connection con = ConexionBD.conectar();
                String sql =
                        "SELECT u.nombre, r.nombre_rol, e.id_estudiante " +
                        "FROM usuarios u " +
                        "JOIN roles r ON u.id_rol = r.id_rol " +
                        "LEFT JOIN estudiantes e ON u.id_usuario = e.id_usuario " +
                        "WHERE LOWER(u.nombre)=LOWER(?) " +
                        "AND u.contrasena=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, usuario);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String rol = rs.getString("nombre_rol");
                    JOptionPane.showMessageDialog(null,"Bienvenido " + rol);
                    dispose();
                    
                    switch (rol) {
                        case "Administrador":
                            new AdminFrame();
                            break;

                        case "Bibliotecario":
                            new BibliotecarioFrame();
                            break;

                        case "Estudiante":
                            int idEstudiante = rs.getInt("id_estudiante");
                            new EstudianteFrame(idEstudiante);
                            break;

                        default: JOptionPane.showMessageDialog(null,"Rol no reconocido");
                    }

                } else {
                    JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error al iniciar sesión");
            }
        };

        // BOTÓN LOGIN
        btnLogin.addActionListener(e -> login.run());

        // ENTER
        txtPassword.addKeyListener(new KeyAdapter() {

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode()== KeyEvent.VK_ENTER) {
                            login.run();
                        }
                    }
                }
        );
        setVisible(true);
    }
}