package vista;

import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLoginFrame extends JFrame {

    public AdminLoginFrame() {

        setTitle("Login Administrador");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4,2,10,10));

        JTextField txtUsuario = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Ingresar");

        add(new JLabel("Usuario"));
        add(txtUsuario);

        add(new JLabel("Contraseña"));
        add(txtPassword);

        add(new JLabel(""));
        add(btnLogin);

        getRootPane().setDefaultButton(btnLogin);
        btnLogin.addActionListener(e -> {

            try {
                Connection con = ConexionBD.conectar();
                String sql =
                        "SELECT * "
                        + "FROM usuarios "
                        + "WHERE nombre=? "
                        + "AND contrasena=? "
                        + "AND id_rol=1";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, txtUsuario.getText());
                ps.setString(2,String.valueOf(txtPassword.getPassword()));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null,"Bienvenido Administrador");
                    dispose();
                    new AdminFrame();

                } else {
                    JOptionPane.showMessageDialog(null,"Credenciales incorrectas");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        setVisible(true);
    }
}