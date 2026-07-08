package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class LibrosFrame extends JFrame {

    private RoundedButton btnRegistrar;
    private RoundedButton btnActualizar;
    private RoundedButton btnEliminar;
    private RoundedButton btnVolver;

    // PALETA DE COLORES EXACTA DEL MOCKUP
    private final Color MARRON_MOCKUP_PRINCIPAL = new Color(133, 62, 23); // Fondo terracota de los botones
    private final Color MARRON_TEXTO_TITULO = new Color(92, 43, 5);      // Marrón oscuro para títulos y línea
    private final Color CREMA_CONTENEDOR = new Color(245, 237, 222);    // Fondo crema del panel de botones
    private final Color BLANCO_TEXTO = new Color(255, 255, 255);        // Texto blanco para los botones

    public LibrosFrame() {
        setTitle("Gestión de Libros");
        
        // CONFIGURACIÓN DE PANTALLA COMPLETA COMPATIBLE
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
        // BOTÓN VOLVER (Esquina superior izquierda)
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
                // Voltear la flecha para que apunte a la izquierda si es necesario
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
        // ICONO DE USUARIO / CABECERA (Centrado arriba)
        // ====================================================
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/libro1.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        lblIcono.setBounds(385, 20, 90, 90);
        panelPrincipal.add(lblIcono);

        // LÍNEA DIVISORIA ORGÁNICA MARRÓN
        JPanel lineaDivisoria = new JPanel();
        lineaDivisoria.setBackground(MARRON_TEXTO_TITULO);
        lineaDivisoria.setBounds(35, 125, 790, 4);
        panelPrincipal.add(lineaDivisoria);

        // TITULO: Gestión de libros
        JLabel titulo = new JLabel("Gestión de libros");
        titulo.setFont(new Font("Georgia", Font.BOLD, 32));
        titulo.setForeground(MARRON_TEXTO_TITULO); 
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(35, 145, 790, 45);
        panelPrincipal.add(titulo);   

        // ====================================================
        // CONTENEDOR CREMA CENTRAL PARA LOS BOTONES
        // ====================================================
        RoundedPanel panelBotones = new RoundedPanel();
        panelBotones.setLayout(null);
        panelBotones.setBackground(CREMA_CONTENEDOR); 
        panelBotones.setBounds(210, 210, 440, 350);
        panelPrincipal.add(panelBotones);

        // BOTÓN: REGISTRAR LIBRO
        btnRegistrar = new RoundedButton("Registrar libro");
        btnRegistrar.setBounds(45, 35, 350, 60);
        setupEstiloBoton(btnRegistrar);
        panelBotones.add(btnRegistrar);

        // BOTÓN: ACTUALIZAR LIBRO
        btnActualizar = new RoundedButton("Actualizar libro");
        btnActualizar.setBounds(45, 140, 350, 60);
        setupEstiloBoton(btnActualizar);
        panelBotones.add(btnActualizar);

        // BOTÓN: ELIMINAR LIBRO
        btnEliminar = new RoundedButton("Eliminar libro");
        btnEliminar.setBounds(45, 245, 350, 60);
        setupEstiloBoton(btnEliminar);
        panelBotones.add(btnEliminar);

        // ====================================================
        // ACTION LISTENERS
        // ====================================================
        btnRegistrar.addActionListener(e -> {
            new RegistrarLibroFrame();
        });
        
        btnActualizar.addActionListener(e -> {
            new ActualizarLibroFrame();
        });
        
        btnEliminar.addActionListener(e -> {
            new EliminarLibroFrame();
        });
        
        btnVolver.addActionListener(e -> {
            dispose();
            new AdminFrame();
        });

        setVisible(true);
    }

    /**
     * Aplica el estilo visual exacto del Mockup (Botón Marrón + Texto Blanco)
     */
    private void setupEstiloBoton(RoundedButton boton) {
        boton.setBackground(MARRON_MOCKUP_PRINCIPAL);
        boton.setForeground(BLANCO_TEXTO);
        boton.setFont(new Font("Arial", Font.BOLD, 24)); // Tipografía marcada y legible
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}