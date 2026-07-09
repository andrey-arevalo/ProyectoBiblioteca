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

public class DisponibilidadFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Color MARRON = new Color(92, 43, 5);
    private final Color MARRON_CLARO_BUSCADOR = new Color(188, 102, 45);
    private final Color NARANJA_BOTONES = new Color(245, 155, 95);

    private JTextField txtBuscar;
    private JTable tabla;
    private JScrollPane scroll;

    private RoundedButton btnValidar;
    private RoundedButton btnLimpiar;
    private RoundedButton btnVolver;
    
    // Almacena la ventana padre para evitar duplicados al volver
    private JFrame ventanaPadre;

    public DisponibilidadFrame(JFrame padre) {
        this.ventanaPadre = padre;

        setTitle("Validar Disponibilidad");
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
        // BOTÓN VOLVER (Controlando ventana padre original)
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
        // ICONO SUPERIOR (validar.png) - AJUSTADO
        //==================================================
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/validar.png"));
            Image img = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            JLabel lblIcono = new JLabel(new ImageIcon(img));
            lblIcono.setBounds(545, 15, 110, 110); // Ubicación Y inicial
            panelPrincipal.add(lblIcono);
        } catch (Exception e) {
            JLabel lbl = new JLabel("🔍");
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
            lbl.setBounds(550, 20, 100, 100);
            panelPrincipal.add(lbl);
        }

        // CORRECCIÓN: Bajamos la línea a Y = 135 para que no pise el borde inferior de la lupa
        JPanel linea = new JPanel();
        linea.setBackground(MARRON);
        linea.setBounds(30, 135, 1140, 4);
        panelPrincipal.add(linea);

        //==================================================
        // TÍTULO - REDISTRIBUIDO
        //==================================================
        JLabel lblTitulo = new JLabel("Validar disponibilidad");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 38));
        lblTitulo.setForeground(MARRON);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 155, 1200, 45); // Bajado ligeramente a 155 para dar aire
        panelPrincipal.add(lblTitulo);

        //==================================================
        // SECCIÓN DE BÚSQUEDA
        //==================================================
        JLabel lblBuscar = new JLabel("Título:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 18));
        lblBuscar.setForeground(Color.BLACK);
        lblBuscar.setBounds(85, 235, 100, 30);
        panelPrincipal.add(lblBuscar);

        txtBuscar = new RoundedTextField();
        txtBuscar.setBounds(160, 230, 840, 42);
        txtBuscar.setBackground(MARRON_CLARO_BUSCADOR);
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        txtBuscar.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(txtBuscar);

        // Botón Validar (Marrón/Naranja oscuro según mockup)
        btnValidar = new RoundedButton("Validar");
        btnValidar.setBounds(1015, 212, 100, 35);
        btnValidar.setBackground(MARRON);
        btnValidar.setForeground(Color.WHITE);
        btnValidar.setFont(new Font("Arial", Font.BOLD, 15));
        panelPrincipal.add(btnValidar);

        // Botón Limpiar
        btnLimpiar = new RoundedButton("Limpiar");
        btnLimpiar.setBounds(1015, 255, 100, 35);
        btnLimpiar.setBackground(MARRON);
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 15));
        panelPrincipal.add(btnLimpiar);

        //==================================================
        // CONFIGURACIÓN DE LA TABLA (CABECERAS EXACTAS)
        //==================================================
        String columnas[] = {"ID Libro", "Título", "Autor", "Categoría", "Disponible"};
        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][]{}, columnas));
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(245, 237, 225)); // Color beige exacto
        header.setForeground(Color.BLACK);

        scroll = new JScrollPane(tabla);
        scroll.setBounds(85, 315, 1030, 305);
        panelPrincipal.add(scroll);

        //==================================================
        // EVENTOS Y LÓGICA
        //==================================================
        btnVolver.addActionListener(e -> {
            dispose(); // Destruye esta ventana
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true); // Regresa a la ventana original sin crear duplicados
            }
        });

        // Evento de disparo rápido con la tecla ENTER
        txtBuscar.addActionListener(e -> btnValidar.doClick());

        btnValidar.addActionListener(e -> {
            String filtro = txtBuscar.getText().trim();
            if (!filtro.isEmpty()) {
                validarLibro(filtro);
            } else {
                limpiarTodo();
            }
        });

        btnLimpiar.addActionListener(e -> limpiarTodo());

        limpiarTodo();
        setVisible(true);
    }

    private void limpiarTodo() {
        txtBuscar.setText("");
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        txtBuscar.requestFocusInWindow();
    }

    private void validarLibro(String tituloFiltro) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        try {
            Connection con = ConexionBD.conectar();
            String sql = "SELECT l.id_libro, l.titulo, l.autor, c.nombre AS categoria, l.disponible " +
                         "FROM libros l " +
                         "INNER JOIN categorias c ON l.id_categoria = c.id_categoria " +
                         "WHERE LOWER(l.titulo) LIKE LOWER(?) " +
                         "ORDER BY l.id_libro ASC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + tituloFiltro + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("categoria"),
                        rs.getBoolean("disponible") ? "Sí" : "No"
                });
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al validar la disponibilidad del libro.");
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