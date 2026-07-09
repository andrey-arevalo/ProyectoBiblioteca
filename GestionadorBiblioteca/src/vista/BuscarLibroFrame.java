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

public class BuscarLibroFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Color MARRON = new Color(92, 43, 5);
    private final Color MARRON_CLARO_BUSCADOR = new Color(188, 102, 45);
    private final Color NARANJA_BOTONES = new Color(245, 155, 95);

    private JTextField txtBuscar;
    private JTable tabla;
    private JScrollPane scroll;

    private RoundedButton btnBuscar;
    private RoundedButton btnLimpiar;
    private RoundedButton btnVolver;
    
    // Almacena la referencia de la ventana padre para no duplicar pantallas
    private JFrame ventanaPadre;

    // Modificado el constructor para recibir la ventana de procedencia
    public BuscarLibroFrame(JFrame padre) {
        this.ventanaPadre = padre;

        setTitle("Buscar Libro");
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
            btnVolver.setText(" VOLVER");
        }
        panelPrincipal.add(btnVolver);

        //==================================================
        // ICONO SUPERIOR (libro1.png)
        //==================================================
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/libro1.png"));
            Image img = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            JLabel lblIcono = new JLabel(new ImageIcon(img));
            lblIcono.setBounds(545, 15, 110, 110);
            panelPrincipal.add(lblIcono);
        } catch (Exception e) {
            JLabel lbl = new JLabel("📖");
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
            lbl.setBounds(550, 20, 100, 100);
            panelPrincipal.add(lbl);
        }

        JPanel linea = new JPanel();
        linea.setBackground(MARRON);
        linea.setBounds(30, 120, 1140, 4);
        panelPrincipal.add(linea);

        //==================================================
        // TÍTULO SEGÚN MOCKUP
        //==================================================
        JLabel lblTitulo = new JLabel("Buscar libros");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 38));
        lblTitulo.setForeground(MARRON);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 145, 1200, 45);
        panelPrincipal.add(lblTitulo);

        //==================================================
        // APARTADO DE BÚSQUEDA
        //==================================================
        JLabel lblBuscar = new JLabel("Título del libro:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 18));
        lblBuscar.setForeground(Color.BLACK);
        lblBuscar.setBounds(85, 225, 150, 30);
        panelPrincipal.add(lblBuscar);

        txtBuscar = new RoundedTextField();
        txtBuscar.setBounds(230, 220, 770, 42);
        txtBuscar.setBackground(MARRON_CLARO_BUSCADOR);
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        txtBuscar.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(txtBuscar);

        // Botón Buscar (Naranja, ovalado y pequeño)
        btnBuscar = new RoundedButton("Buscar");
        btnBuscar.setBounds(1015, 202, 100, 35);
        btnBuscar.setBackground(NARANJA_BOTONES);
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 15));
        panelPrincipal.add(btnBuscar);

        // Botón Limpiar (Naranja, ovalado y pequeño)
        btnLimpiar = new RoundedButton("Limpiar");
        btnLimpiar.setBounds(1015, 245, 100, 35);
        btnLimpiar.setBackground(NARANJA_BOTONES);
        btnLimpiar.setForeground(Color.BLACK);
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
        header.setBackground(new Color(245, 237, 225)); // Color beige del mockup
        header.setForeground(Color.BLACK);

        scroll = new JScrollPane(tabla);
        scroll.setBounds(85, 300, 1030, 320);
        panelPrincipal.add(scroll);

        //==================================================
        // EVENTOS Y LÓGICA DE FUNCIONAMIENTO (Modificado)
        //==================================================
        btnVolver.addActionListener(e -> {
            dispose(); // Destruye esta ventana secundaria
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true); // Regresa al menú sin recrearlo
            }
        });

        // Evento para que funcione la tecla ENTER en el buscador
        txtBuscar.addActionListener(e -> btnBuscar.doClick());

        // Lógica del botón Buscar
        btnBuscar.addActionListener(e -> {
            String filtro = txtBuscar.getText().trim();
            if (!filtro.isEmpty()) {
                buscarLibros(filtro);
            } else {
                limpiarTodo();
            }
        });
        
        // Lógica del botón Limpiar (Borra tabla, texto y da el foco)
        btnLimpiar.addActionListener(e -> limpiarTodo());
        
        // Al iniciar la pantalla la tabla aparece limpia sin registros
        limpiarTodo();
        setVisible(true);
    }

    private void limpiarTodo() {
        txtBuscar.setText("");
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        txtBuscar.requestFocusInWindow();
    }

    private void buscarLibros(String tituloFiltro) {
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
                        rs.getBoolean("disponible") ? "Sí" : "No" // Formato amigable para el usuario
                });
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al realizar la búsqueda del libro.");
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