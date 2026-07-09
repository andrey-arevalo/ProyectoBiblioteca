package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class EstudianteFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    // Paleta de colores exacta
    private final Color MARRON_OSCURO = new Color(92, 43, 5);
    private final Color MARRON_CLARO_TARJETAS = new Color(188, 102, 45);
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

        // Panel contenedor principal
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
            ImageIcon iconLibro = new ImageIcon(getClass().getResource("/imagenes/libro1.png"));
            Image img = iconLibro.getImage().getScaledInstance(90, 80, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(img));
            lblLogo.setBounds(115, 25, 90, 80);
            panelIzquierdo.add(lblLogo);
        } catch (Exception e) {
            JLabel lblFallback = new JLabel("📖", SwingConstants.CENTER);
            lblFallback.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
            lblFallback.setBounds(110, 25, 100, 80);
            panelIzquierdo.add(lblFallback);
        }

        JLabel lblSistema = new JLabel("Sistema", SwingConstants.CENTER);
        lblSistema.setFont(new Font("Georgia", Font.BOLD, 32));
        lblSistema.setForeground(MARRON_OSCURO);
        lblSistema.setBounds(0, 115, 320, 40);
        panelIzquierdo.add(lblSistema);

        JLabel lblBiblioteca = new JLabel("Biblioteca", SwingConstants.CENTER);
        lblBiblioteca.setFont(new Font("Georgia", Font.BOLD, 36));
        lblBiblioteca.setForeground(MARRON_OSCURO);
        lblBiblioteca.setBounds(0, 155, 320, 45);
        panelIzquierdo.add(lblBiblioteca);

        JPanel lineaMenuIzquierdo = new JPanel();
        lineaMenuIzquierdo.setBackground(MARRON_OSCURO);
        lineaMenuIzquierdo.setBounds(20, 215, 280, 2);
        panelIzquierdo.add(lineaMenuIzquierdo);

        // Botones laterales usando las imágenes que ya tienes listas
        RoundedButton btnMenuCat = crearBotonMenu("Catálogo", "/imagenes/libro1.png", 235);
        panelIzquierdo.add(btnMenuCat);

        RoundedButton btnMenuPres = crearBotonMenu("Préstamos", "/imagenes/prestamo.png", 305);
        panelIzquierdo.add(btnMenuPres);

        RoundedButton btnMenuMultas = crearBotonMenu("Multas", "/imagenes/multa.png", 375);
        panelIzquierdo.add(btnMenuMultas);

        JLabel lblSeparador = new JLabel("◆ ━━━━━━━ ◆", SwingConstants.CENTER);
        lblSeparador.setFont(new Font("Dialog", Font.BOLD, 14));
        lblSeparador.setForeground(MARRON_OSCURO);
        lblSeparador.setBounds(0, 445, 320, 20);
        panelIzquierdo.add(lblSeparador);

        RoundedButton btnCerrarSesion = new RoundedButton("Cerrar sesión");
        btnCerrarSesion.setBounds(40, 480, 240, 50);
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

        // Saludo dinámico corregido
        JLabel lblBienvenido = new JLabel("Bienvenido, " + this.nombreEstudiante);
        lblBienvenido.setFont(new Font("Georgia", Font.BOLD, 42));
        lblBienvenido.setForeground(MARRON_OSCURO);
        lblBienvenido.setBounds(40, 30, 600, 50);
        panelDerecho.add(lblBienvenido);

        JLabel lblSubtitulo = new JLabel("Sistema de gestión bibliotecaria");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblSubtitulo.setForeground(MARRON_OSCURO);
        lblSubtitulo.setBounds(40, 90, 400, 30);
        panelDerecho.add(lblSubtitulo);

        // Intentar cargar logo UTP si existe
        try {
            ImageIcon iconUtp = new ImageIcon(getClass().getResource("/imagenes/utp_logo.png"));
            Image imgUtp = iconUtp.getImage().getScaledInstance(140, 45, Image.SCALE_SMOOTH);
            JLabel lblUtp = new JLabel(new ImageIcon(imgUtp));
            lblUtp.setBounds(760, 80, 140, 45);
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
        // MÓDULOS DE ACCESO RÁPIDO CON COLORES CORREGIDOS
        //==================================================
        int anchoTarj = 220;
        int altoTarj = 240;
        int yTarjetas = 250;

        // --- TARJETA 1: Catálogo (libro1.png) ---
        RoundedPanel tarjetaCat = crearTarjetaAcceso(60, yTarjetas, anchoTarj, altoTarj);
        panelDerecho.add(tarjetaCat);

        JLabel icnCat = crearIconoTarjeta("/imagenes/libro1.png", "📂", 70, 20);
        tarjetaCat.add(icnCat);

        JLabel lblCatTit = crearLabelTarjeta("Catálogo", 10, 105, 200, 25, 22);
        tarjetaCat.add(lblCatTit);

        JLabel lblCatDesc = crearLabelTarjeta("Buscar libros", 10, 135, 200, 20, 15);
        tarjetaCat.add(lblCatDesc);

        RoundedButton btnIrCat = crearBotonFlecha(60, 180);
        tarjetaCat.add(btnIrCat);

        // --- Línea vertical 1 ---
        JPanel lineaVert1 = new JPanel();
        lineaVert1.setBackground(COLOR_LINEAS);
        lineaVert1.setBounds(325, yTarjetas, 5, altoTarj);
        panelDerecho.add(lineaVert1);

        // --- TARJETA 2: Multas (multa.png) ---
        RoundedPanel tarjetaMultas = crearTarjetaAcceso(360, yTarjetas, anchoTarj, altoTarj);
        panelDerecho.add(tarjetaMultas);

        JLabel icnMultas = crearIconoTarjeta("/imagenes/multa.png", "$", 70, 20);
        tarjetaMultas.add(icnMultas);

        JLabel lblMultasTit = crearLabelTarjeta("Multas", 10, 105, 200, 25, 22);
        tarjetaMultas.add(lblMultasTit);

        JLabel lblMultasDesc = crearLabelTarjeta("Multas pendientes", 10, 135, 200, 20, 15);
        tarjetaMultas.add(lblMultasDesc);

        RoundedButton btnIrMultas = crearBotonFlecha(60, 180);
        tarjetaMultas.add(btnIrMultas);

        // --- Línea vertical 2 ---
        JPanel lineaVert2 = new JPanel();
        lineaVert2.setBackground(COLOR_LINEAS);
        lineaVert2.setBounds(625, yTarjetas, 5, altoTarj);
        panelDerecho.add(lineaVert2);

        // --- TARJETA 3: Préstamos (prestamo.png) ---
        RoundedPanel tarjetaPres = crearTarjetaAcceso(660, yTarjetas, anchoTarj, altoTarj);
        panelDerecho.add(tarjetaPres);

        JLabel icnPres = crearIconoTarjeta("/imagenes/prestamo.png", "⏱", 70, 20);
        tarjetaPres.add(icnPres);

        JLabel lblPresTit = crearLabelTarjeta("Préstamos", 10, 105, 200, 25, 22);
        tarjetaPres.add(lblPresTit);

        JLabel lblPresDesc = crearLabelTarjeta("Historial Préstamos", 10, 135, 200, 20, 15);
        tarjetaPres.add(lblPresDesc);

        RoundedButton btnIrPres = crearBotonFlecha(60, 180);
        tarjetaPres.add(btnIrPres);


        //==================================================
        // ENLACE DE EVENTOS Y BOTONES
        //==================================================
     // ==================================================
     // ACCIÓN: BOTÓN CERRAR SESIÓN
     // ==================================================
     btnCerrarSesion.addActionListener(e -> {
         // Genera el cuadro de diálogo con opciones Sí y No
         int respuesta = JOptionPane.showConfirmDialog(
                 this, 
                 "¿Seguro que quieres cerrar sesión?", 
                 "Confirmar salida", 
                 JOptionPane.YES_NO_OPTION, 
                 JOptionPane.QUESTION_MESSAGE
         );
         
         // Si la opción elegida es "SÍ"
         if (respuesta == JOptionPane.YES_OPTION) {
             this.dispose(); // Destruye y libera la ventana actual de Estudiante
             
             // Abre una nueva instancia limpia de la pantalla de inicio de sesión
             new LoginFrame(); 
         }
         // Si elige "NO", no hace nada y mantiene al estudiante en el panel
     });

        btnMenuCat.addActionListener(e -> abrirBuscadorLibros());
        btnIrCat.addActionListener(e -> abrirBuscadorLibros());

        btnMenuMultas.addActionListener(e -> abrirMultasEstudiante());
        btnIrMultas.addActionListener(e -> abrirMultasEstudiante());

        btnMenuPres.addActionListener(e -> abrirHistorialPrestamos());
        btnIrPres.addActionListener(e -> abrirHistorialPrestamos());

        setVisible(true);
    }

    //==================================================
    // ACCIONES DE NAVEGACIÓN (Usa la lógica 'this' padre)
    //==================================================
    private void abrirBuscadorLibros() {
        setVisible(false); // Oculta la ventana de estudiante
        new BuscarLibroFrame(this); // Asumiendo que BuscarLibroFrame recibe (this)
    }

    private void abrirMultasEstudiante() {
        setVisible(false); // Oculta la ventana de estudiante
        new MultasPendientesFrame(this, this.idEstudiante); // Envía ventana padre e ID entero
    }

    private void abrirHistorialPrestamos() {
        setVisible(false); // Oculta la ventana de estudiante
        new HistorialFrameEstudiante(this, this.idEstudiante); // Envía ventana padre e ID entero
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

    private RoundedPanel crearTarjetaAcceso(int x, int y, int ancho, int alto) {
        RoundedPanel tarjeta = new RoundedPanel();
        tarjeta.setBounds(x, y, ancho, alto);
        // Aseguramos que la tarjeta use el color marrón claro opaco del mockup
        tarjeta.setBackground(MARRON_CLARO_TARJETAS);
        tarjeta.setLayout(null);
        return tarjeta;
    }

    private JLabel crearIconoTarjeta(String rutaIcono, String fallbackText, int tamaño, int y) {
        JLabel lblIcono = new JLabel("", SwingConstants.CENTER);
        lblIcono.setBounds(10, y, 200, tamaño);
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(rutaIcono));
            Image img = icon.getImage().getScaledInstance(tamaño, tamaño, Image.SCALE_SMOOTH);
            lblIcono.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblIcono.setText(fallbackText);
            lblIcono.setForeground(Color.WHITE);
            lblIcono.setFont(new Font("Arial", Font.BOLD, 45));
        }
        return lblIcono;
    }

    private JLabel crearLabelTarjeta(String texto, int x, int y, int ancho, int alto, int tamañoLetra) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setBounds(x, y, ancho, alto);
        // Letras blancas para que contrasten perfectamente con el fondo marrón claro
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, tamañoLetra));
        return label;
    }

    private RoundedButton crearBotonFlecha(int x, int y) {
        RoundedButton btnFlecha = new RoundedButton("");
        btnFlecha.setBounds(x, y, 100, 36);
        btnFlecha.setBackground(Color.WHITE);
        
        try {
            ImageIcon iconFlecha = new ImageIcon(getClass().getResource("/imagenes/flecha_derecha.png"));
            Image img = iconFlecha.getImage().getScaledInstance(28, 18, Image.SCALE_SMOOTH);
            btnFlecha.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            btnFlecha.setText("➔");
            btnFlecha.setFont(new Font("Arial", Font.BOLD, 18));
            btnFlecha.setForeground(MARRON_CLARO_TARJETAS);
        }
        return btnFlecha;
    }
}