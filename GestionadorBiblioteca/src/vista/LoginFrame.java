package vista;

import componentes.*;
import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    private PlaceholderTextField txtUsuario;
    private PlaceholderPasswordField txtPassword;
    private RoundedButton btnLogin;

    public LoginFrame() {

        setTitle("Sistema Biblioteca");
        setSize(1100,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        setContentPane(fondo);
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(null);
        panel.setBounds(330,90,430,500);
        fondo.add(panel);
        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN");
        lblTitulo.setFont(new Font("Serif",Font.BOLD,34));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(
                20,
                35,
                390,
                40);

        panel.add(lblTitulo);

        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/libro.png"));

        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                        90,
                        90,
                        Image.SCALE_SMOOTH);

        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));

        lblIcono.setBounds(170,90,90,90);
        panel.add(lblIcono);
        
        ImageIcon utpOriginal = new ImageIcon(
                getClass().getResource("/imagenes/logoutp.png"));
        System.out.println(utpOriginal.getIconWidth());
        System.out.println(utpOriginal.getIconHeight());
        Image utpEscalada =
                utpOriginal.getImage().getScaledInstance(
                        240,
                        60,
                        Image.SCALE_SMOOTH);

        JLabel lblUTP =
                new JLabel(new ImageIcon(utpEscalada));

        lblUTP.setHorizontalAlignment(SwingConstants.CENTER);

        lblUTP.setBounds(95, 180, 240, 60);

        panel.add(lblUTP);
        
        txtUsuario = new PlaceholderTextField("Usuario");
        txtUsuario.setBounds(55,260,320,45);
        panel.add(txtUsuario);
        txtPassword = new PlaceholderPasswordField("Contraseña");
        txtPassword.setBounds(55,330,320,45);
        panel.add(txtPassword);
        btnLogin = new RoundedButton("Iniciar Sesión");
        btnLogin.setBounds(95,415,240,55);
        panel.add(btnLogin);

        // ===============================
        // MÉTODO LOGIN
        // ===============================

        Runnable login = () -> {
            String usuario = txtUsuario.getText().trim();
            String password = String.valueOf(txtPassword.getPassword());
            try {
                Connection con = ConexionBD.conectar();
                String sql =
                        "SELECT u.nombre,r.nombre_rol,e.id_estudiante " +
                        "FROM usuarios u " +
                        "JOIN roles r ON u.id_rol=r.id_rol " +
                        "LEFT JOIN estudiantes e ON u.id_usuario=e.id_usuario " +
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
                        default:
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Rol no reconocido"
                            );
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Error al iniciar sesión"
                );
            }
        };

        // ===============================
        // EVENTOS
        // ===============================

        btnLogin.addActionListener(e -> login.run());
        txtPassword.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login.run();
                }
            }
        });

        txtUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    txtPassword.requestFocus();
                }
            }
        });

        setVisible(true);
    }
}