package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class UsuariosFrame extends JFrame {

    private RoundedButton btnAdministrador;
    private RoundedButton btnBibliotecario;
    private RoundedButton btnEstudiante;
    private RoundedButton btnVolver;

    // PALETA DE COLORES EXACTA DE LA SEGUNDA IMAGEN
    private final Color MARRON_MOCKUP_PRINCIPAL = new Color(133, 62, 23); // Fondo terracota del contenedor
    private final Color MARRON_TEXTO_TITULO = new Color(92, 43, 5);      // Marrón oscuro para títulos
    private final Color CREMA_SEGUNDA_IMAGEN = new Color(245, 237, 222); // Fondo crema exacto de los botones
    private final Color BLANCO_TEXTO_BOTON = new Color(255, 255, 255);   // CAMBIADO A BLANCO

    public UsuariosFrame() {
        setTitle("Gestión de Usuarios");
        
        // CONFIGURACIÓN PANTALLA COMPLETA
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fondo con GridBagLayout para centrado dinámico perfecto
        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        fondo.setLayout(new GridBagLayout()); 
        setContentPane(fondo);

        // PANEL BLANCO PRINCIPAL translúcido
        RoundedPanel panelPrincipal = new RoundedPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(255, 255, 255, 240));
        panelPrincipal.setPreferredSize(new Dimension(860, 620));
        fondo.add(panelPrincipal, new GridBagConstraints());

        // ====================================================
        // BOTÓN VOLVER
        // ====================================================
        btnVolver = new RoundedButton("  VOLVER");
        btnVolver.setBounds(35, 25, 135, 45);
        btnVolver.setBackground(MARRON_MOCKUP_PRINCIPAL); 
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
            btnVolver.setText("← VOLVER");
        }
        panelPrincipal.add(btnVolver);

        // ====================================================
        // ICONO DE USUARIO (Centrado arriba)
        // ====================================================
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/usuario.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        lblIcono.setBounds(385, 20, 90, 90);
        panelPrincipal.add(lblIcono);

        // LÍNEA DIVISORIA ORGÁNICA MARRÓN
        JPanel lineaDivisoria = new JPanel();
        lineaDivisoria.setBackground(MARRON_TEXTO_TITULO);
        lineaDivisoria.setBounds(35, 125, 790, 4);
        panelPrincipal.add(lineaDivisoria);

        // TITULO: Gestión de usuarios
        JLabel titulo = new JLabel("Gestión de usuarios");
        titulo.setFont(new Font("Georgia", Font.BOLD, 32));
        titulo.setForeground(MARRON_TEXTO_TITULO); 
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(35, 145, 790, 45);
        panelPrincipal.add(titulo);   

        // ====================================================
        // CONTENEDOR MARRÓN CENTRAL PARA LOS BOTONES
        // ====================================================
        RoundedPanel panelBotones = new RoundedPanel();
        panelBotones.setLayout(null);
        panelBotones.setBackground(MARRON_MOCKUP_PRINCIPAL); 
        panelBotones.setBounds(170, 210, 520, 350);
        panelPrincipal.add(panelBotones);

        // BOTÓN: GESTIÓN ADMINISTRADOR
        btnAdministrador = new RoundedButton("GESTIÓN ADMINISTRADOR");
        btnAdministrador.setBounds(50, 35, 420, 55);
        setupEstiloBoton(btnAdministrador);
        panelBotones.add(btnAdministrador);

        // BOTÓN: GESTIÓN BIBLIOTECARIO
        btnBibliotecario = new RoundedButton("GESTIÓN BIBLIOTECARIO");
        btnBibliotecario.setBounds(50, 135, 420, 55);
        setupEstiloBoton(btnBibliotecario);
        panelBotones.add(btnBibliotecario);

        // BOTÓN: GESTIÓN ESTUDIANTE
        btnEstudiante = new RoundedButton("GESTIÓN ESTUDIANTE");
        btnEstudiante.setBounds(50, 235, 420, 55);
        setupEstiloBoton(btnEstudiante);
        panelBotones.add(btnEstudiante);

        // ====================================================
        // ACTION LISTENERS
        // ====================================================
        btnAdministrador.addActionListener(e -> new RegistrarUsuarioFrame("Administrador"));
        btnBibliotecario.addActionListener(e -> new RegistrarUsuarioFrame("Bibliotecario"));
        btnEstudiante.addActionListener(e -> new RegistrarUsuarioFrame("Estudiante"));
        
        btnVolver.addActionListener(e -> {
            dispose();
            new AdminFrame();
        });

        setVisible(true);
    }

    /**
     * Aplica el estilo visual (Crema + Texto Blanco)
     */
    private void setupEstiloBoton(RoundedButton boton) {
        boton.setBackground(CREMA_SEGUNDA_IMAGEN);
        boton.setForeground(BLANCO_TEXTO_BOTON); // Aplicando el color blanco modificado
        boton.setFont(new Font("Arial", Font.BOLD, 22)); // Tipografía marcada y limpia
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}