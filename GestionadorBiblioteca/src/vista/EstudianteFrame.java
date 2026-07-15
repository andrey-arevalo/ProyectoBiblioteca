package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EstudianteFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    // Paleta de colores exacta extraída del mockup
    private final Color MARRON_OSCURO = new Color(92, 43, 5);
    private final Color MARRON_CLARO_TARJETAS = new Color(171, 90, 41); // Marrón terracota del mockup
    private final Color COLOR_LINEAS = new Color(92, 43, 5);

    private String nombreEstudiante;
    private int idEstudiante;
    
    public EstudianteFrame(String nombreEstudiante, int idEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
        this.idEstudiante = idEstudiante;

        setTitle("Sistema de Biblioteca - Estudiante");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fondo escalable de la biblioteca
        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        fondo.setLayout(new GridBagLayout());
        setContentPane(fondo);

        // Panel contenedor principal translúcido
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(null);
        panelContenedor.setOpaque(false);
        panelContenedor.setPreferredSize(new Dimension(1280, 680));
        fondo.add(panelContenedor);

        //==================================================
        // PANEL IZQUIERDO (Menú Lateral)
        //==================================================
        RoundedPanel panelIzquierdo = new RoundedPanel();
        panelIzquierdo.setBounds(0, 0, 320, 680);
        panelIzquierdo.setBackground(new Color(255, 255, 255, 240));
        panelIzquierdo.setLayout(null);
        panelContenedor.add(panelIzquierdo);

        // Logo Libro Superior Izquierdo
        try {
            ImageIcon iconLibro = new ImageIcon(getClass().getResource("/imagenes/libro_logo.png"));
            Image img = iconLibro.getImage().getScaledInstance(100, 90, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(img));
            lblLogo.setBounds(110, 25, 100, 90);
            panelIzquierdo.add(lblLogo);
        } catch (Exception e) {
            JLabel lblFallback = new JLabel("📖", SwingConstants.CENTER);
            lblFallback.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 55));
            lblFallback.setBounds(110, 25, 100, 90);
            panelIzquierdo.add(lblFallback);
        }

        JLabel lblSistema = new JLabel("Sistema", SwingConstants.CENTER);
        lblSistema.setFont(new Font("Georgia", Font.BOLD, 34));
        lblSistema.setForeground(MARRON_OSCURO);
        lblSistema.setBounds(0, 125, 320, 40);
        panelIzquierdo.add(lblSistema);

        JLabel lblBiblioteca = new JLabel("Biblioteca", SwingConstants.CENTER);
        lblBiblioteca.setFont(new Font("Georgia", Font.BOLD, 38));
        lblBiblioteca.setForeground(MARRON_OSCURO);
        lblBiblioteca.setBounds(0, 165, 320, 45);
        panelIzquierdo.add(lblBiblioteca);

        JPanel lineaMenuIzquierdo = new JPanel();
        lineaMenuIzquierdo.setBackground(MARRON_OSCURO);
        lineaMenuIzquierdo.setBounds(20, 225, 280, 3);
        panelIzquierdo.add(lineaMenuIzquierdo);

        // Botones laterales usando las imágenes correspondientes
        RoundedButton btnMenuCat = crearBotonMenu("Catálogo", "/imagenes/libro1.png", 245);
        panelIzquierdo.add(btnMenuCat);

        RoundedButton btnMenuPres = crearBotonMenu("Préstamos", "/imagenes/prestamo.png", 315);
        panelIzquierdo.add(btnMenuPres);

        RoundedButton btnMenuMultas = crearBotonMenu("Multas", "/imagenes/multa.png", 385);
        panelIzquierdo.add(btnMenuMultas);

        JLabel lblSeparador = new JLabel("◆ ━━━━━━━ ◆", SwingConstants.CENTER);
        lblSeparador.setFont(new Font("Dialog", Font.BOLD, 14));
        lblSeparador.setForeground(MARRON_OSCURO);
        lblSeparador.setBounds(0, 455, 320, 20);
        panelIzquierdo.add(lblSeparador);

        RoundedButton btnCerrarSesion = new RoundedButton("Cerrar sesión");
        btnCerrarSesion.setBounds(40, 490, 240, 50);
        btnCerrarSesion.setBackground(MARRON_OSCURO);
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 18));
        panelIzquierdo.add(btnCerrarSesion);


        //==================================================
        // PANEL DERECHO (Contenido Principal)
        //==================================================
        RoundedPanel panelDerecho = new RoundedPanel();
        panelDerecho.setBounds(340, 0, 940, 680);
        panelDerecho.setBackground(new Color(255, 255, 255, 240));
        panelDerecho.setLayout(null);
        panelContenedor.add(panelDerecho);

        // Saludo dinámico
        JLabel lblBienvenido = new JLabel("Bienvenido, " + this.nombreEstudiante);
        lblBienvenido.setFont(new Font("Georgia", Font.BOLD, 42));
        lblBienvenido.setForeground(MARRON_OSCURO);
        lblBienvenido.setBounds(40, 30, 580, 50);
        panelDerecho.add(lblBienvenido);

        JLabel lblSubtitulo = new JLabel("Sistema de gestión bibliotecaria");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblSubtitulo.setForeground(MARRON_OSCURO);
        lblSubtitulo.setBounds(40, 90, 400, 30);
        panelDerecho.add(lblSubtitulo);

        // Logo UTP alineado a la derecha
        try {
            ImageIcon iconUtp = new ImageIcon(getClass().getResource("/imagenes/utp_logo.png"));
            Image imgUtp = iconUtp.getImage().getScaledInstance(150, 48, Image.SCALE_SMOOTH);
            JLabel lblUtp = new JLabel(new ImageIcon(imgUtp));
            lblUtp.setBounds(740, 75, 150, 48);
            panelDerecho.add(lblUtp);
        } catch (Exception e) {}

        JPanel lineaHome = new JPanel();
        lineaHome.setBackground(MARRON_OSCURO);
        lineaHome.setBounds(30, 145, 880, 4);
        panelDerecho.add(lineaHome);

        JLabel lblAccesoRapido = new JLabel("| Acceso rápido");
        lblAccesoRapido.setFont(new Font("Georgia", Font.BOLD, 24));
        lblAccesoRapido.setForeground(MARRON_OSCURO);
        lblAccesoRapido.setBounds(40, 170, 300, 35);
        panelDerecho.add(lblAccesoRapido);


        //==================================================
        // MÓDULOS DE ACCESO RÁPIDO (CARDS ANIMADAS)
        //==================================================
        int anchoTarj = 220;
        int altoTarj = 260;
        int yTarjetas = 240;

        // --- TARJETA 1: Catálogo (Izquierda) ---
        AnimatableCard tarjetaCat = new AnimatableCard();
        tarjetaCat.setBounds(60, yTarjetas, anchoTarj, altoTarj);
        panelDerecho.add(tarjetaCat);

        JLabel icnCat = crearIconoTarjeta("/imagenes/libro1.png", "📂", 75, 20);
        tarjetaCat.add(icnCat);

        JLabel lblCatTit = crearLabelTarjeta("Catálogo", 10, 110, 200, 25, 20);
        tarjetaCat.add(lblCatTit);

        JLabel lblCatDesc = crearLabelTarjeta("Buscar libros", 10, 140, 200, 20, 14);
        tarjetaCat.add(lblCatDesc);

        RoundedButton btnIrCat = crearBotonFlecha(60, 190);
        tarjetaCat.add(btnIrCat);

        // --- Línea vertical separadora 1 ---
        JPanel lineaVert1 = new JPanel();
        lineaVert1.setBackground(COLOR_LINEAS);
        lineaVert1.setBounds(325, yTarjetas, 6, altoTarj);
        panelDerecho.add(lineaVert1);

        // --- TARJETA 2: Préstamos (Centro) ---
        AnimatableCard tarjetaPres = new AnimatableCard();
        tarjetaPres.setBounds(360, yTarjetas, anchoTarj, altoTarj);
        panelDerecho.add(tarjetaPres);

        JLabel icnPres = crearIconoTarjeta("/imagenes/prestamo.png", "⏱", 75, 20);
        tarjetaPres.add(icnPres);

        JLabel lblPresTit = crearLabelTarjeta("Préstamos", 10, 110, 200, 25, 20);
        tarjetaPres.add(lblPresTit);

        JLabel lblPresDesc = crearLabelTarjeta("Historial Préstamos", 10, 140, 200, 20, 14);
        tarjetaPres.add(lblPresDesc);

        RoundedButton btnIrPres = crearBotonFlecha(60, 190);
        tarjetaPres.add(btnIrPres);

        // --- Línea vertical separadora 2 ---
        JPanel lineaVert2 = new JPanel();
        lineaVert2.setBackground(COLOR_LINEAS);
        lineaVert2.setBounds(625, yTarjetas, 6, altoTarj);
        panelDerecho.add(lineaVert2);

        // --- TARJETA 3: Multas (Derecha) ---
        AnimatableCard tarjetaMultas = new AnimatableCard();
        tarjetaMultas.setBounds(660, yTarjetas, anchoTarj, altoTarj);
        panelDerecho.add(tarjetaMultas);

        JLabel icnMultas = crearIconoTarjeta("/imagenes/multa.png", "$", 75, 20);
        tarjetaMultas.add(icnMultas);

        JLabel lblMultasTit = crearLabelTarjeta("Multas", 10, 110, 200, 25, 20);
        tarjetaMultas.add(lblMultasTit);

        JLabel lblMultasDesc = crearLabelTarjeta("Multas pendientes", 10, 140, 200, 20, 14);
        tarjetaMultas.add(lblMultasDesc);

        RoundedButton btnIrMultas = crearBotonFlecha(60, 190);
        tarjetaMultas.add(btnIrMultas);


        //==================================================
        // ENLACE DE EVENTOS Y BOTONES
        //==================================================
        
        // Cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                    this, 
                    "¿Seguro que quieres cerrar sesión?", 
                    "Confirmar salida", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginFrame(); 
            }
        });

        // Navegación Catálogo (Botón Lateral, Botón Flecha y clic en la Card)
        btnMenuCat.addActionListener(e -> abrirBuscadorLibros());
        btnIrCat.addActionListener(e -> abrirBuscadorLibros());
        tarjetaCat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirBuscadorLibros();
            }
        });

        // Navegación Préstamos (Botón Lateral, Botón Flecha y clic en la Card)
        btnMenuPres.addActionListener(e -> abrirHistorialPrestamos());
        btnIrPres.addActionListener(e -> abrirHistorialPrestamos());
        tarjetaPres.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirHistorialPrestamos();
            }
        });

        // Navegación Multas (Botón Lateral, Botón Flecha y clic en la Card)
        btnMenuMultas.addActionListener(e -> abrirMultasEstudiante());
        btnIrMultas.addActionListener(e -> abrirMultasEstudiante());
        tarjetaMultas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirMultasEstudiante();
            }
        });

        setVisible(true);
    }

    //==================================================
    // ACCIONES DE NAVEGACIÓN
    //==================================================
    private void abrirBuscadorLibros() {
        setVisible(false);
        new BuscarLibroFrame(this);
    }

    private void abrirMultasEstudiante() {
        setVisible(false);
        new MultasPendientesFrame(this, this.idEstudiante);
    }

    private void abrirHistorialPrestamos() {
        setVisible(false);
        new HistorialFrameEstudiante(this, this.idEstudiante);
    }


    //==================================================
    // CONSTRUCTORES AUXILIARES
    //==================================================
    private RoundedButton crearBotonMenu(String texto, String rutaIcono, int y) {
        RoundedButton btn = new RoundedButton("  " + texto);
        btn.setBounds(30, y, 260, 48);
        btn.setBackground(MARRON_OSCURO);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(rutaIcono));
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(img));
        } catch (Exception e) {}
        return btn;
    }

    private JLabel crearIconoTarjeta(String rutaIcono, String fallbackText, int tamano, int y) {
        JLabel lblIcono = new JLabel("", SwingConstants.CENTER);
        lblIcono.setBounds(10, y, 200, tamano);
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(rutaIcono));
            Image img = icon.getImage().getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
            lblIcono.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblIcono.setText(fallbackText);
            lblIcono.setForeground(Color.WHITE);
            lblIcono.setFont(new Font("Arial", Font.BOLD, 45));
        }
        return lblIcono;
    }

    private JLabel crearLabelTarjeta(String texto, int x, int y, int ancho, int alto, int tamanoLetra) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setBounds(x, y, ancho, alto);
        // Texto blanco exacto
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, tamanoLetra));
        return label;
    }

    /**
     * CORREGIDO: Ahora carga la imagen 'flecha_derecha.png' de tus recursos en lugar de texto Unicode.
     */
    private RoundedButton crearBotonFlecha(int x, int y) {
        RoundedButton btnFlecha = new RoundedButton(""); // Sin texto para que solo se vea el icono
        btnFlecha.setBounds(x, y, 100, 38);
        btnFlecha.setBackground(Color.WHITE);
        btnFlecha.setFocusable(false);
        
        try {
            // Buscamos la imagen flecha_derecha en la carpeta de recursos
            ImageIcon iconFlecha = new ImageIcon(getClass().getResource("/imagenes/flecha_derecha.png"));
            // La escalamos a un tamaño elegante para el botón (32 de ancho por 18 de alto)
            Image img = iconFlecha.getImage().getScaledInstance(32, 18, Image.SCALE_SMOOTH);
            btnFlecha.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            // Si por alguna razón falla la carga, usa un fallback seguro
            btnFlecha.setText("->");
            btnFlecha.setFont(new Font("Arial", Font.BOLD, 18));
            btnFlecha.setForeground(MARRON_OSCURO);
        }
        return btnFlecha;
    }

    // =========================================================================
    // CLASE INTERNA: TARJETA ANIMADA (EFECTO AGRANDARSE SUTIL CON TIMER)
    // =========================================================================
    private class AnimatableCard extends JPanel {
        private static final long serialVersionUID = 1L;
        
        private double scale = 1.0;
        private double targetScale = 1.0;
        private Timer timerAnimacion;
        private Rectangle limitesOriginales;

        public AnimatableCard() {
            setBackground(MARRON_CLARO_TARJETAS);
            setOpaque(false); 
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setLayout(null);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (limitesOriginales == null) {
                        limitesOriginales = getBounds();
                    }
                    targetScale = 1.06; // Incremento del 6% al pasar encima
                    iniciarAnimacion();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    targetScale = 1.0; // Restablecer
                    iniciarAnimacion();
                }
            });
        }

        private void iniciarAnimacion() {
            if (timerAnimacion != null && timerAnimacion.isRunning()) {
                timerAnimacion.stop();
            }

            timerAnimacion = new Timer(12, e -> {
                double diff = targetScale - scale;
                if (Math.abs(diff) < 0.005) {
                    scale = targetScale;
                    timerAnimacion.stop();
                } else {
                    scale += diff * 0.25;
                }
                
                if (limitesOriginales != null) {
                    int nuevoAncho = (int) (limitesOriginales.width * scale);
                    int nuevoAlto = (int) (limitesOriginales.height * scale);
                    int dx = (limitesOriginales.width - nuevoAncho) / 2;
                    int dy = (limitesOriginales.height - nuevoAlto) / 2;
                    
                    // Elevación sutil en el eje Y al pasar por encima
                    int elevacionY = (int) ((scale - 1.0) * 80); 
                    
                    setBounds(
                        limitesOriginales.x + dx,
                        limitesOriginales.y + dy - (elevacionY / 2),
                        nuevoAncho,
                        nuevoAlto
                    );
                    revalidate();
                    repaint();
                }
            });
            timerAnimacion.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Fondo redondeado con el color marrón del mockup
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
            
            g2.dispose();
            super.paintComponent(g);
        }
    }
}