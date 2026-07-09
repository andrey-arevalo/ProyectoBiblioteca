package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;
import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultarEstudiantesFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Color MARRON = new Color(92, 43, 5);
    private final Color MARRON_CLARO_BUSCADOR = new Color(188, 102, 45);
    private final Color NARANJA_BOTONES = new Color(245, 155, 95);

    private JTextField txtNombre;
    private JTable tabla;
    private JScrollPane scroll;

    private RoundedButton btnValidar;
    private RoundedButton btnLimpiar;
    private RoundedButton btnVolver;
    
    // Almacena la ventana padre para evitar que se dupliquen pantallas al regresar
    private JFrame ventanaPadre;

    // Modificado el constructor para recibir la ventana de procedencia
    public ConsultarEstudiantesFrame(JFrame padre) {
        this.ventanaPadre = padre;

        setTitle("Consultar Estudiantes");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        fondo.setLayout(new GridBagLayout());
        setContentPane(fondo);

        RoundedPanel panelPrincipal = new RoundedPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(255, 255, 255, 235));
        panelPrincipal.setPreferredSize(new Dimension(1200, 680));
        fondo.add(panelPrincipal);

        //==================================================
        // BOTÓN VOLVER 
        //==================================================
        btnVolver = new RoundedButton(" VOLVER");
        btnVolver.setBounds(25, 25, 180, 45);
        btnVolver.setBackground(MARRON);
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
            btnVolver.setText("<- VOLVER");
        }
        panelPrincipal.add(btnVolver);

        //==================================================
        // ICONO SUPERIOR
        //==================================================
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/estudiante_icono.png"));
            Image img = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            JLabel lblIcono = new JLabel(new ImageIcon(img));
            lblIcono.setBounds(545, 15, 110, 110);
            panelPrincipal.add(lblIcono);
        } catch (Exception e) {
            JLabel lbl = new JLabel("👤");
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
            lbl.setBounds(550, 20, 100, 100);
            panelPrincipal.add(lbl);
        }

        JPanel linea = new JPanel();
        linea.setBackground(MARRON);
        linea.setBounds(30, 120, 1140, 4);
        panelPrincipal.add(linea);

        //==================================================
        // TÍTULO MOCKUP
        //==================================================
        JLabel lblTitulo = new JLabel("Consultar estudiante");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 38));
        lblTitulo.setForeground(MARRON);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 145, 1200, 45);
        panelPrincipal.add(lblTitulo);

        //==================================================
        // APARTADO DE BÚSQUEDA / VALIDACIÓN
        //==================================================
        JLabel lblBuscar = new JLabel("Nombre:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 18));
        lblBuscar.setForeground(Color.BLACK);
        lblBuscar.setBounds(85, 225, 150, 30);
        panelPrincipal.add(lblBuscar);

        txtNombre = new RoundedTextField();
        txtNombre.setBounds(180, 220, 820, 42);
        txtNombre.setBackground(MARRON_CLARO_BUSCADOR);
        txtNombre.setForeground(Color.WHITE);
        txtNombre.setCaretColor(Color.WHITE);
        txtNombre.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        txtNombre.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(txtNombre);

        // Botón Validar (Naranja ovalado)
        btnValidar = new RoundedButton("Validar");
        btnValidar.setBounds(1015, 202, 100, 35);
        btnValidar.setBackground(NARANJA_BOTONES);
        btnValidar.setForeground(Color.BLACK);
        btnValidar.setFont(new Font("Arial", Font.BOLD, 15));
        panelPrincipal.add(btnValidar);

        // Botón Limpiar (Naranja ovalado)
        btnLimpiar = new RoundedButton("Limpiar");
        btnLimpiar.setBounds(1015, 245, 100, 35);
        btnLimpiar.setBackground(NARANJA_BOTONES);
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 15));
        panelPrincipal.add(btnLimpiar);

        //==================================================
        // CONFIGURACIÓN DE LA TABLA (CABECERAS EXACTAS)
        //==================================================
        String columnas[] = {"ID Estudiante", "Nombre", "Carrera", "Fecha préstamo"};
        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][]{}, columnas));
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(245, 237, 225)); // Color beige exacto
        header.setForeground(Color.BLACK);

        scroll = new JScrollPane(tabla);
        scroll.setBounds(85, 300, 1030, 320);
        panelPrincipal.add(scroll);

        //==================================================
        // EVENTOS Y ENLACES (Corregido con ventanaPadre)
        //==================================================
        btnVolver.addActionListener(e -> {
            dispose(); // Destruye la ventana de consulta actual
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true); // Hace visible el BibliotecarioFrame original
            }
        });

        // Evento para procesar la búsqueda de inmediato con ENTER
        txtNombre.addActionListener(e -> btnValidar.doClick());

        // Lógica del botón Validar
        btnValidar.addActionListener(e -> {
            String filtro = txtNombre.getText().trim();
            if (!filtro.isEmpty()) {
                consultarEstudiante(filtro);
            } else {
                limpiarTodo();
            }
        });

        // Lógica del botón Limpiar
        btnLimpiar.addActionListener(e -> limpiarTodo());

        // Al iniciar la pantalla la tabla aparece limpia sin registros previos
        limpiarTodo();

        setVisible(true);
    }

    private void limpiarTodo() {
        txtNombre.setText("");
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        txtNombre.requestFocusInWindow();
    }

    private void consultarEstudiante(String nombreFiltro) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        try {
            Connection con = ConexionBD.conectar();
            
            // Nueva consulta SQL vinculada a la lógica de préstamos activos solicitada
            String sql = "SELECT e.id_estudiante, e.nombre, e.carrera, p.fecha_prestamo " +
                         "FROM estudiantes e " +
                         "LEFT JOIN prestamos p ON e.id_estudiante = p.id_estudiante AND p.estado = 'ACTIVO' " +
                         "WHERE LOWER(e.nombre) LIKE LOWER(?) " +
                         "ORDER BY e.nombre";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombreFiltro + "%");
            ResultSet rs = ps.executeQuery();

            boolean hayDatos = false;

            while (rs.next()) {
                hayDatos = true;
                int id = rs.getInt("id_estudiante");
                String nombre = rs.getString("nombre");
                String carrera = rs.getString("carrera");
                java.sql.Date fechaSql = rs.getDate("fecha_prestamo");
                
                // Si la fecha es null significa que no tiene préstamos pendientes de devolución
                String fechaPrestamoStr = (fechaSql != null) ? fechaSql.toString() : "No tiene préstamo";

                modelo.addRow(new Object[]{id, nombre, carrera, fechaPrestamoStr});
            }

            if (!hayDatos) {
                JOptionPane.showMessageDialog(this, "No existen estudiantes registrados con ese nombre.");
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar la información del estudiante.");
        }
    }

    //==================================================
    // CLASE INTERNA PARA EL BUSCADOR REDONDEADO
    //==================================================
    private class RoundedTextField extends JTextField {
        private static final long serialVersionUID = 1L;

        public RoundedTextField() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}