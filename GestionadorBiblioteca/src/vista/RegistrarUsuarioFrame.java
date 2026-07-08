package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;
import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrarUsuarioFrame extends JFrame {

    private static final long serialVersionUID = 1L; // Evita el Warning en Eclipse
    
    private JTextField txtNombre;
    private JTextField txtCarrera;
    private JPasswordField txtPassword;
    private RoundedButton btnRegistrar;
    private RoundedButton btnEliminar;
    private RoundedButton btnVolver;

    // PALETA DE COLORES EXACTA DEL MOCKUP
    private final Color MARRON_MOCKUP_PRINCIPAL = new Color(133, 62, 23); // Cuadro marrón grande
    private final Color MARRON_TEXTO_TITULO = new Color(92, 43, 5);      // Título superior y botón volver
    private final Color CREMA_BOTONES = new Color(255, 253, 208);         // COLOR MODIFICADO: Blanco fuerte exacto
    private final Color MARRON_INPUTS = new Color(166, 77, 29);           // Fondo más oscuro para los campos de texto

    public RegistrarUsuarioFrame(String rol) {
        setTitle("Gestión de " + rol.toLowerCase());
        
        // CONFIGURACIÓN PANTALLA COMPLETA
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fondo con GridBagLayout para centrado dinámico del cuadro blanco
        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        fondo.setLayout(new GridBagLayout()); 
        setContentPane(fondo);

        // PANEL BLANCO PRINCIPAL (Tarjeta contenedora grande del mockup)
        RoundedPanel panelPrincipal = new RoundedPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(255,255,255,240));
        panelPrincipal.setPreferredSize(new Dimension(860,620));
        fondo.add(panelPrincipal,new GridBagConstraints());

        // ====================================================
        // BOTÓN VOLVER (Esquina superior izquierda)
        // ====================================================
        btnVolver = new RoundedButton("VOLVER");
        btnVolver.setBounds(35, 25, 135, 45);
        btnVolver.setBackground(MARRON_TEXTO_TITULO); 
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        try {
            ImageIcon iconSrc = new ImageIcon(getClass().getResource("/imagenes/flecha_derecha.png"));
            Image imgOriginal = iconSrc.getImage();
            
            int w = imgOriginal.getWidth(null);
            int h = imgOriginal.getHeight(null);
            if (w > 0 && h > 0) {
                java.awt.image.BufferedImage flipped = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = flipped.createGraphics();
                g.drawImage(imgOriginal, 0, 0, w, h, w, 0, 0, h, null);
                g.dispose();
                
                Image imgScaled = flipped.getScaledInstance(24, 12, Image.SCALE_SMOOTH);
                btnVolver.setIcon(new ImageIcon(imgScaled));
            }
        } catch(Exception e) {
        	dispose();
            btnVolver.setText("← VOLVER");
        }
        panelPrincipal.add(btnVolver);

        // ====================================================
        // ICONO DE USUARIO (Centrado arriba)
        // ====================================================
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/usuario.png"));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
            lblIcono.setBounds(385, 20, 90, 90);
            panelPrincipal.add(lblIcono);
        } catch (Exception e) {
            JLabel lblIcono = new JLabel("👤");
            lblIcono.setFont(new Font("Arial", Font.PLAIN, 60));
            lblIcono.setForeground(MARRON_TEXTO_TITULO);
            lblIcono.setBounds(400, 20, 90, 90);
            panelPrincipal.add(lblIcono);
        }

        // LÍNEA DIVISORIA MARRÓN
        JPanel lineaDivisoria = new JPanel();
        lineaDivisoria.setBackground(MARRON_TEXTO_TITULO);
        lineaDivisoria.setBounds(35, 125, 790, 4);
        panelPrincipal.add(lineaDivisoria);

        // TITULO: Gestión de administrador
        JLabel titulo = new JLabel("Gestión de " + rol.toLowerCase());
        titulo.setFont(new Font("Arial", Font.BOLD, 32)); 
        titulo.setForeground(MARRON_TEXTO_TITULO); 
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(35, 145, 790, 45);
        panelPrincipal.add(titulo);   

        // ====================================================
        // CUADRO MARRÓN COMPLETO DEL MOCKUP
        // ====================================================
        RoundedPanel panelFormulario = new RoundedPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setBackground(MARRON_MOCKUP_PRINCIPAL); 
        
        int alturaFormulario = rol.equalsIgnoreCase("Estudiante") ? 410 : 360;
        panelFormulario.setBounds(35, 210, 790, alturaFormulario); 
        panelPrincipal.add(panelFormulario);

        // ET_NOMBRE
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 26)); 
        lblNombre.setForeground(Color.BLACK);
        lblNombre.setBounds(50, 45, 150, 40);
        panelFormulario.add(lblNombre);

        txtNombre = new RoundedTextField();
        txtNombre.setBounds(240, 45, 500, 40);
        setupEstiloInput(txtNombre);
        panelFormulario.add(txtNombre);

        // CONTROL DE REPOSICIONAMIENTO SI ES ESTUDIANTE
        int desplazamientoY = 0; 
        if (rol.equalsIgnoreCase("Estudiante")) {
            JLabel lblCarrera = new JLabel("Carrera:");
            lblCarrera.setFont(new Font("Arial", Font.BOLD, 26));
            lblCarrera.setForeground(Color.BLACK);
            lblCarrera.setBounds(50, 115, 150, 40);
            panelFormulario.add(lblCarrera);

            txtCarrera = new RoundedTextField();
            txtCarrera.setBounds(240, 115, 500, 40);
            setupEstiloInput(txtCarrera);
            panelFormulario.add(txtCarrera);
            
            desplazamientoY = 70; 
        }

        // ET_CONTRASEÑA
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 26));
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBounds(50, 115 + desplazamientoY, 170, 40);
        panelFormulario.add(lblPassword);

        txtPassword = new RoundedPasswordField();
        txtPassword.setBounds(240, 115 + desplazamientoY, 500, 40);
        setupEstiloInput(txtPassword);
        panelFormulario.add(txtPassword);

        // ====================================================
        // BOTONES DE ACCIÓN (Blancos e intensos)
        // ====================================================
        int botonY = 250 + desplazamientoY; 
        
        btnRegistrar = new RoundedButton("Registrar");
        btnRegistrar.setBounds(210, botonY, 160, 45);
        setupEstiloBotonAccion(btnRegistrar);
        panelFormulario.add(btnRegistrar);

        btnEliminar = new RoundedButton("Eliminar");
        btnEliminar.setBounds(420, botonY, 160, 45);
        setupEstiloBotonAccion(btnEliminar);
        panelFormulario.add(btnEliminar);

        // ====================================================
        // CONTROLADORES DE EVENTOS (PostgreSQL Nativo)
        // ====================================================
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String password = String.valueOf(txtPassword.getPassword()).trim();

            if (nombre.isEmpty() || password.isEmpty() || (rol.equalsIgnoreCase("Estudiante") && txtCarrera.getText().trim().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Por favor, llene todos los campos del formulario.");
                return;
            }

            Connection con = null;
            try {
                con = ConexionBD.conectar();
                con.setAutoCommit(false); 

                int idRol = 0;
                if (rol.equalsIgnoreCase("Administrador")) idRol = 1;
                else if (rol.equalsIgnoreCase("Bibliotecario")) idRol = 2;
                else if (rol.equalsIgnoreCase("Estudiante")) idRol = 3;

                String sqlUsuario = "INSERT INTO usuarios(nombre, contrasena, id_rol) VALUES(?, ?, ?) RETURNING id_usuario";
                PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
                psUsuario.setString(1, nombre);
                psUsuario.setString(2, password);
                psUsuario.setInt(3, idRol);
                
                ResultSet rs = psUsuario.executeQuery();
                int idUsuario = 0;
                if (rs.next()) {
                    idUsuario = rs.getInt("id_usuario");
                }

                if (rol.equalsIgnoreCase("Estudiante")) {
                    String sqlEstudiante = "INSERT INTO estudiantes(nombre, carrera, id_usuario) VALUES(?, ?, ?)";
                    PreparedStatement psEstudiante = con.prepareStatement(sqlEstudiante);
                    psEstudiante.setString(1, nombre);
                    psEstudiante.setString(2, txtCarrera.getText().trim());
                    psEstudiante.setInt(3, idUsuario);
                    psEstudiante.executeUpdate();
                }

                con.commit(); 
                JOptionPane.showMessageDialog(null, rol + " registrado correctamente.");
                limpiarCampos();

            } catch (Exception ex) {
                if (con != null) { try { con.rollback(); } catch (Exception rb) { rb.printStackTrace(); } }
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error de PostgreSQL: " + ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, introduzca el nombre en el formulario para eliminar.");
                return;
            }

            int confirmar = JOptionPane.showConfirmDialog(null, 
                "¿Desea borrar permanentemente al " + rol.toLowerCase() + " " + nombre + "?", 
                "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmar != JOptionPane.YES_OPTION) return;

            Connection con = null;
            try {
                con = ConexionBD.conectar();
                con.setAutoCommit(false);

                int idRol = 0;
                if (rol.equalsIgnoreCase("Administrador")) idRol = 1;
                else if (rol.equalsIgnoreCase("Bibliotecario")) idRol = 2;
                else if (rol.equalsIgnoreCase("Estudiante")) idRol = 3;

                if (rol.equalsIgnoreCase("Estudiante")) {
                    String sqlDelEstudiante = "DELETE FROM estudiantes WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE LOWER(nombre) = LOWER(?) AND id_rol = ?)";
                    PreparedStatement psDelEstudiante = con.prepareStatement(sqlDelEstudiante);
                    psDelEstudiante.setString(1, nombre);
                    psDelEstudiante.setInt(2, idRol);
                    psDelEstudiante.executeUpdate();
                }

                String sqlDelUsuario = "DELETE FROM usuarios WHERE LOWER(nombre) = LOWER(?) AND id_rol = ?";
                PreparedStatement psDelUsuario = con.prepareStatement(sqlDelUsuario);
                psDelUsuario.setString(1, nombre);
                psDelUsuario.setInt(2, idRol);
                
                int filasBorradas = psDelUsuario.executeUpdate();

                if (filasBorradas > 0) {
                    con.commit();
                    JOptionPane.showMessageDialog(null, rol + " eliminado con éxito.");
                    limpiarCampos();
                } else {
                    con.rollback();
                    JOptionPane.showMessageDialog(null, "No se encontró ningún registro coincidente en la BD.");
                }

            } catch (Exception ex) {
                if (con != null) { try { con.rollback(); } catch (Exception rb) { rb.printStackTrace(); } }
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al eliminar en PostgreSQL: " + ex.getMessage());
            }
        });

        btnVolver.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    private void setupEstiloInput(JTextField field) {
        field.setBackground(MARRON_INPUTS);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Arial", Font.BOLD, 20));
        field.setBorder(BorderFactory.createEmptyBorder(5, 22, 5, 22)); 
    }

    private void setupEstiloBotonAccion(RoundedButton boton) {
        boton.setBackground(CREMA_BOTONES);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtPassword.setText("");
        if (txtCarrera != null) {
            txtCarrera.setText("");
        }
    }

    // ====================================================================
    // CLASES INTERNAS PARA EL RENDEREADO OVALADO SEGURO EN SWING
    // ====================================================================
    private class RoundedTextField extends JTextField {
        private static final long serialVersionUID = 1L;
        public RoundedTextField() { setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 35, 35));
            g2d.dispose();
            super.paintComponent(g);
        }
    }

    private class RoundedPasswordField extends JPasswordField {
        private static final long serialVersionUID = 1L;
        public RoundedPasswordField() { setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 35, 35));
            g2d.dispose();
            super.paintComponent(g);
        }
    }
}