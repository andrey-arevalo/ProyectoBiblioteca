package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;
import modelo.Prestamo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PrestamoFrame extends JFrame {

    private final Color MARRON_TEXTO_Y_BOTONES = new Color(92, 43, 5); 
    private final Color BEIGE_CONTENEDOR_TARJETA = new Color(245, 237, 225); 

    // Al hacerla 'static', la lista de préstamos persiste en memoria durante toda la ejecución
    private static ArrayList<Prestamo> listaPrestamos = new ArrayList<>();

    // Variable para almacenar la ventana padre
    private JFrame ventanaPadre;

    // Modificado el constructor para recibir el JFrame padre
    public PrestamoFrame(JFrame padre) {
        this.ventanaPadre = padre;

        setTitle("Sistema de Biblioteca - Gestionar Préstamos");
        
        // Configuración de pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1366, 768));
        // IMPORTANTE: DISPOSE_ON_CLOSE para que no se cierre todo el sistema al cerrar esta ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //=========================
        // FONDO (Escalable)
        //=========================
        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        fondo.setLayout(new GridBagLayout()); 
        setContentPane(fondo);

        // Contenedor intermedio blanco traslúcido
        RoundedPanel panelBlancoPrincipal = new RoundedPanel();
        panelBlancoPrincipal.setLayout(null);
        panelBlancoPrincipal.setBackground(new Color(255, 255, 255, 235));
        panelBlancoPrincipal.setPreferredSize(new Dimension(1200, 680));
        fondo.add(panelBlancoPrincipal);

        //=========================
        // BOTÓN VOLVER
        //=========================
        RoundedButton btnVolver = new RoundedButton(" VOLVER");
        btnVolver.setBounds(25, 25, 180, 45);
        btnVolver.setBackground(MARRON_TEXTO_Y_BOTONES);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 18));
        
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/flecha_derecha.png"));
            int ancho = 24;
            int alto = 16;
            
            java.awt.image.BufferedImage imagenInvertida = new java.awt.image.BufferedImage(ancho, alto, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = imagenInvertida.createGraphics();
            
            g2d.drawImage(iconoOriginal.getImage(), ancho, 0, 0, alto, 0, 0, iconoOriginal.getIconWidth(), iconoOriginal.getIconHeight(), null);
            g2d.dispose();
            
            btnVolver.setIcon(new ImageIcon(imagenInvertida));
        } catch (Exception e) {
            btnVolver.setText(" VOLVER");
        }
        panelBlancoPrincipal.add(btnVolver);

        //=========================
        // ICONO SUPERIOR SIMULADO
        //=========================
        JLabel lblIconoPrestamo = new JLabel("(%)", SwingConstants.CENTER);
        lblIconoPrestamo.setBounds(500, 20, 200, 100);
        lblIconoPrestamo.setFont(new Font("Arial", Font.BOLD, 40));
        lblIconoPrestamo.setForeground(MARRON_TEXTO_Y_BOTONES);
        
        try {
            ImageIcon iconSrc = new ImageIcon(getClass().getResource("/imagenes/prestamo.png")); 
            Image imgScaled = iconSrc.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            lblIconoPrestamo.setIcon(new ImageIcon(imgScaled));
            lblIconoPrestamo.setText(""); 
        } catch (Exception e) {
            // Se mantiene el texto (%) si no encuentra la imagen
        }
        panelBlancoPrincipal.add(lblIconoPrestamo);

        //=========================
        // TÍTULO
        //=========================
        JLabel lblTitulo = new JLabel("Gestionar Préstamos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 40));
        lblTitulo.setForeground(MARRON_TEXTO_Y_BOTONES);
        lblTitulo.setBounds(200, 130, 800, 50);
        panelBlancoPrincipal.add(lblTitulo);

        JPanel lineaDivision = new JPanel();
        lineaDivision.setBackground(MARRON_TEXTO_Y_BOTONES);
        lineaDivision.setBounds(50, 120, 1100, 4);
        panelBlancoPrincipal.add(lineaDivision);

        //=========================
        // TARJETA CONTENEDORA CENTRAL
        //=========================
        RoundedPanel tarjetaCentral = new RoundedPanel();
        tarjetaCentral.setLayout(null);
        tarjetaCentral.setBackground(BEIGE_CONTENEDOR_TARJETA);
        tarjetaCentral.setBounds(375, 210, 450, 380);
        panelBlancoPrincipal.add(tarjetaCentral);

        // Botón Registrar
        RoundedButton btnRegistrar = new RoundedButton("Registrar préstamos");
        btnRegistrar.setBounds(40, 50, 370, 75);
        btnRegistrar.setBackground(MARRON_TEXTO_Y_BOTONES);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 24));
        tarjetaCentral.add(btnRegistrar);

        // Botón Visualizar
        RoundedButton btnVisualizar = new RoundedButton("<html><center>Visualizar préstamos<br>activos</center></html>");
        btnVisualizar.setBounds(40, 190, 370, 110);
        btnVisualizar.setBackground(MARRON_TEXTO_Y_BOTONES);
        btnVisualizar.setForeground(Color.WHITE);
        btnVisualizar.setFont(new Font("Segoe UI", Font.BOLD, 24));
        tarjetaCentral.add(btnVisualizar);

        //=========================
        // CONTROL DE EVENTOS (Modificado)
        //=========================
        
        // Volver al menú principal del Bibliotecario sin clonarlo
        btnVolver.addActionListener(e -> { 
            dispose(); 
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true);
            }
        });
        
        // Abrir la ventana de registro pasándole la ventana actual (this) como padre
        btnRegistrar.addActionListener(e -> { 
            setVisible(false); // Ocultamos esta en vez de destruirla para poder regresar
            // Nota: Deberás ajustar el constructor de RegistrarPrestamosFrame para que acepte (this)
            new RegistrarPrestamosFrame(this); 
        });

        // Abrir la ventana de visualización pasándole la ventana actual (this) como padre
        btnVisualizar.addActionListener(e -> { 
            setVisible(false); // Ocultamos esta en vez de destruirla para poder regresar
            // Nota: Deberás ajustar el constructor de PrestamosActivosFrame para que acepte (this)
            new PrestamosActivosFrame(this); 
        });

        setVisible(true);
    }
}