package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BibliotecarioFrame extends JFrame {

    // Colores exactos de tu Mockup
    private final Color MARRON_TARJETAS = new Color(166, 85, 35); // #A65523
    private final Color MARRON_HOVER = new Color(125, 60, 20);
    private final Color MARRON_TEXTO_Y_BOTONES = new Color(92, 43, 5); // #5C2B05

    public BibliotecarioFrame() {
        setTitle("Sistema de Biblioteca - Bibliotecario");
        
        // PANTALLA COMPLETA
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1366, 768));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //=========================
        // FONDO (Escalable)
        //=========================
        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        fondo.setLayout(new GridBagLayout()); 
        setContentPane(fondo);

        // Contenedor intermedio de tamaño fijo para asegurar el centrado
        JPanel contenedorContenido = new JPanel();
        contenedorContenido.setOpaque(false);
        contenedorContenido.setPreferredSize(new Dimension(1360, 730));
        contenedorContenido.setLayout(null);
        fondo.add(contenedorContenido);

        //=========================
        // PANEL IZQUIERDO (MENU)
        //=========================
        RoundedPanel menu = new RoundedPanel();
        menu.setLayout(null);
        menu.setBackground(new Color(255, 255, 255, 240));
        menu.setBounds(10, 10, 310, 710);
        contenedorContenido.add(menu);

        //=========================
        // PANEL DERECHO (CONTENIDO)
        //=========================
        RoundedPanel contenido = new RoundedPanel();
        contenido.setLayout(null);
        contenido.setBackground(new Color(255, 255, 255, 240));
        contenido.setBounds(335, 10, 1015, 710);
        contenedorContenido.add(contenido);

        //=========================
        // ELEMENTOS DEL MENÚ IZQUIERDO
        //=========================
        ImageIcon iconoLibro = new ImageIcon(getClass().getResource("/imagenes/libro.png"));
        Image imgLibro = iconoLibro.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        JLabel lblLibro = new JLabel(new ImageIcon(imgLibro));
        lblLibro.setBounds(110, 20, 90, 90);
        menu.add(lblLibro);

        JLabel lblSistema = new JLabel("Sistema", SwingConstants.CENTER);
        lblSistema.setFont(new Font("Georgia", Font.BOLD, 34));
        lblSistema.setForeground(MARRON_TEXTO_Y_BOTONES);
        lblSistema.setBounds(15, 115, 280, 40);
        menu.add(lblSistema);

        JLabel lblBiblioteca = new JLabel("Biblioteca", SwingConstants.CENTER);
        lblBiblioteca.setFont(new Font("Georgia", Font.BOLD, 34));
        lblBiblioteca.setForeground(MARRON_TEXTO_Y_BOTONES);
        lblBiblioteca.setBounds(15, 155, 280, 45);
        menu.add(lblBiblioteca);

        JLabel lblAdorno1 = new JLabel("◆ ━━━━━━━ ◆", SwingConstants.CENTER);
        lblAdorno1.setFont(new Font("Dialog", Font.BOLD, 14));
        lblAdorno1.setForeground(MARRON_TEXTO_Y_BOTONES);
        lblAdorno1.setBounds(15, 205, 280, 20);
        menu.add(lblAdorno1);

        // Botones del menú con sus respectivos iconos laterales
        RoundedButton btnPrestamo = createBotonMenuConIcono("  Préstamos", "/imagenes/prestamo.png", 240);
        RoundedButton btnDevolucion = createBotonMenuConIcono("  Devolución", "/imagenes/devolucion.png", 295);
        RoundedButton btnBuscar = createBotonMenuConIcono("  Libros", "/imagenes/libro1.png", 350);
        RoundedButton btnDisponibilidad = createBotonMenuConIcono("  Disponibilidad", "/imagenes/validar.png", 405); 
        RoundedButton btnConsultar = createBotonMenuConIcono("  Estudiantes", "/imagenes/estudiante.png", 460);
        
        RoundedButton btnCerrar = new RoundedButton("Cerrar sesión");
        btnCerrar.setBounds(35, 580, 240, 45);
        btnCerrar.setBackground(MARRON_TEXTO_Y_BOTONES);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        menu.add(btnPrestamo); menu.add(btnDevolucion); menu.add(btnBuscar);
        menu.add(btnDisponibilidad); menu.add(btnConsultar); menu.add(btnCerrar);

        JLabel lblAdorno2 = new JLabel("◆", SwingConstants.CENTER);
        lblAdorno2.setFont(new Font("Dialog", Font.BOLD, 16));
        lblAdorno2.setForeground(MARRON_TEXTO_Y_BOTONES);
        lblAdorno2.setBounds(15, 595, 280, 20);
        menu.add(lblAdorno2);

        JSeparator lineaMenu = new JSeparator();
        lineaMenu.setBounds(15, 530, 280, 2);
        menu.add(lineaMenu);

        //=========================
        // ELEMENTOS DEL PANEL DERECHO
        //=========================
        JLabel lblBienvenido = new JLabel("Bienvenido, Bibliotecario");
        lblBienvenido.setFont(new Font("Georgia", Font.BOLD, 42));
        lblBienvenido.setForeground(MARRON_TEXTO_Y_BOTONES);
        lblBienvenido.setBounds(40, 25, 650, 50);
        contenido.add(lblBienvenido);

        JLabel lblSistemaGestion = new JLabel("Sistema de gestión bibliotecaria");
        lblSistemaGestion.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblSistemaGestion.setForeground(MARRON_TEXTO_Y_BOTONES);
        lblSistemaGestion.setBounds(40, 80, 430, 30);
        contenido.add(lblSistemaGestion);

        ImageIcon iconUTP = new ImageIcon(getClass().getResource("/imagenes/logoutp.png"));
        Image imgUTP = iconUTP.getImage().getScaledInstance(170, 55, Image.SCALE_SMOOTH);
        JLabel lblUTP = new JLabel(new ImageIcon(imgUTP));
        lblUTP.setBounds(780, 55, 170, 60);
        contenido.add(lblUTP);

        JPanel lineaOrganica = new JPanel();
        lineaOrganica.setBackground(MARRON_TEXTO_Y_BOTONES);
        lineaOrganica.setBounds(40, 135, 915, 4);
        contenido.add(lineaOrganica);

        JLabel lblAcceso = new JLabel("Acceso rápido");
        lblAcceso.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblAcceso.setForeground(MARRON_TEXTO_Y_BOTONES);
        lblAcceso.setBounds(65, 160, 250, 35);
        contenido.add(lblAcceso);

        JPanel barraIndicadora = new JPanel();
        barraIndicadora.setBackground(MARRON_TEXTO_Y_BOTONES);
        barraIndicadora.setBounds(45, 162, 5, 30);
        contenido.add(barraIndicadora);

        //=========================
        // CUADRÍCULA DE TARJETAS (Pixel Perfect 3x2)
        //=========================
        
        // Fila 1
        CardTarjeta cardPrestamo = new CardTarjeta("/imagenes/prestamo.png", "Préstamos", "Gestionar préstamos");
        cardPrestamo.setBounds(55, 220, 240, 210);
        contenido.add(cardPrestamo);

        JPanel sepVertical1 = createSeparadorVertical();
        sepVertical1.setBounds(335, 220, 4, 450);
        contenido.add(sepVertical1);

        CardTarjeta cardDevolucion = new CardTarjeta("/imagenes/devolucion.png", "Devolución", "Gestionar devolución");
        cardDevolucion.setBounds(380, 220, 240, 210);
        contenido.add(cardDevolucion);

        JPanel sepVertical2 = createSeparadorVertical();
        sepVertical2.setBounds(660, 220, 4, 450);
        contenido.add(sepVertical2);

        CardTarjeta cardLibros = new CardTarjeta("/imagenes/libro1.png", "Libros", "Buscar libros");
        cardLibros.setBounds(705, 220, 240, 210);
        contenido.add(cardLibros);

        // Fila 2
        CardTarjeta cardDisponibilidad = new CardTarjeta("/imagenes/validar.png", "Disponibilidad", "Validar disponibilidad");
        cardDisponibilidad.setBounds(55, 460, 240, 210);
        contenido.add(cardDisponibilidad);

        CardTarjeta cardEstudiantes = new CardTarjeta("/imagenes/estudiante.png", "Estudiantes", "Consultar estudiantes");
        cardEstudiantes.setBounds(380, 460, 240, 210);
        contenido.add(cardEstudiantes);

        //=========================
        // ACTION LISTENERS
        //=========================
        // Eventos del Menú Lateral
        btnPrestamo.addActionListener(e -> new PrestamoFrame(this));
        btnDevolucion.addActionListener(e -> new DevolucionFrame(this));
        btnBuscar.addActionListener(e -> new BuscarLibroFrame(this));
        btnDisponibilidad.addActionListener(e -> new DisponibilidadFrame(this));
     // Dentro del evento del botón que abre la consulta en BibliotecarioFrame:
        btnConsultar.addActionListener(e -> {
            this.setVisible(false); // Oculta el menú principal sin destruirlo
            new ConsultarEstudiantesFrame(this); // Le pasa "this" (esta misma ventana)
        });

        // Eventos de las Tarjetas Centrales
        cardPrestamo.addActionListener(e -> new PrestamoFrame(this));
        cardDevolucion.addActionListener(e -> new DevolucionFrame(this));
        cardLibros.addActionListener(e -> new BuscarLibroFrame(this));
        cardDisponibilidad.addActionListener(e -> new DisponibilidadFrame(this));
        cardEstudiantes.addActionListener(e -> new ConsultarEstudiantesFrame(this));

        // Evento de Cerrar Sesión
        btnCerrar.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Desea cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });
        setVisible(true);
    }

    private RoundedButton createBotonMenuConIcono(String texto, String rutaIcono, int yPos) {
        RoundedButton btn = new RoundedButton(texto);
        try {
            ImageIcon iconSrc = new ImageIcon(getClass().getResource(rutaIcono));
            Image imgScaled = iconSrc.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(imgScaled));
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono del menú: " + rutaIcono);
        }
        btn.setBounds(20, yPos, 270, 45);
        btn.setBackground(MARRON_TEXTO_Y_BOTONES);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createSeparadorVertical() {
        JPanel sep = new JPanel();
        sep.setBackground(MARRON_TEXTO_Y_BOTONES);
        return sep;
    }

    //=======================================================================
    // CLASE INTERNA: CardTarjeta
    //=======================================================================
    private class CardTarjeta extends JPanel {
        private final RoundedButton btnAccion;
        private final Rectangle baseBounds = new Rectangle();
        private java.awt.event.ActionListener actionListener;

        public CardTarjeta(String rutaIcono, String titulo, String subtitulo) {
            setLayout(null);
            setOpaque(false);
            setBackground(MARRON_TARJETAS);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new SombraBordeSuave(20));

            JLabel icon = new JLabel();
            try {
                ImageIcon iconSrc = new ImageIcon(getClass().getResource(rutaIcono));
                Image imgScaled = iconSrc.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
                icon.setIcon(new ImageIcon(imgScaled));
            } catch (Exception e) {
                icon.setText("[!]");
                icon.setForeground(Color.WHITE);
            }
            icon.setHorizontalAlignment(SwingConstants.CENTER);
            icon.setBounds(20, 15, 200, 60);
            add(icon);

            JLabel lblTit = new JLabel(titulo);
            lblTit.setHorizontalAlignment(SwingConstants.CENTER);
            lblTit.setFont(new Font("Arial", Font.BOLD, 20));
            lblTit.setForeground(Color.WHITE);
            lblTit.setBounds(20, 85, 200, 30);
            add(lblTit);

            JLabel lblSub = new JLabel(subtitulo);
            lblSub.setHorizontalAlignment(SwingConstants.CENTER);
            lblSub.setFont(new Font("Arial", Font.PLAIN, 14));
            lblSub.setForeground(new Color(245, 245, 245));
            lblSub.setBounds(10, 115, 220, 20);
            add(lblSub);

            btnAccion = new RoundedButton("");
            try {
                ImageIcon iconFlecha = new ImageIcon(getClass().getResource("/imagenes/flecha_derecha.png"));
                Image imgFlecha = iconFlecha.getImage().getScaledInstance(28, 14, Image.SCALE_SMOOTH);
                btnAccion.setIcon(new ImageIcon(imgFlecha));
            } catch (Exception e) {
                btnAccion.setText("->");
                btnAccion.setFont(new Font("Arial", Font.BOLD, 18));
            }
            btnAccion.setBounds(75, 155, 90, 35);
            btnAccion.setBackground(Color.WHITE);
            btnAccion.setForeground(MARRON_TARJETAS);
            btnAccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(btnAccion);

            btnAccion.addActionListener(e -> {
                if (actionListener != null) {
                    actionListener.actionPerformed(new java.awt.event.ActionEvent(this, java.awt.event.ActionEvent.ACTION_PERFORMED, "click"));
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(MARRON_HOVER);
                    baseBounds.setBounds(getBounds());
                    setBounds(baseBounds.x - 3, baseBounds.y - 3, baseBounds.width + 6, baseBounds.height + 6);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(MARRON_TARJETAS);
                    setBounds(baseBounds);
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (actionListener != null) {
                        actionListener.actionPerformed(new java.awt.event.ActionEvent(this, java.awt.event.ActionEvent.ACTION_PERFORMED, "click"));
                    }
                }
            });
        }

        public void addActionListener(java.awt.event.ActionListener listener) {
            this.actionListener = listener;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(4, 4, getWidth() - 9, getHeight() - 9, 35, 35);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    //=======================================================================
    // CLASE INTERNA: SombraBordeSuave
    //=======================================================================
    private class SombraBordeSuave extends AbstractBorder {
        private final int radio;
        public SombraBordeSuave(int radio) { this.radio = radio; }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 0; i < 4; i++) {
                g2.setColor(new Color(0, 0, 0, 12 - (i * 3)));
                g2.drawRoundRect(x + i, y + i, width - 1 - (i * 2), height - 1 - (i * 2), radio + 15, radio + 15);
            }
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) { return new Insets(5, 5, 5, 5); }
    }
}