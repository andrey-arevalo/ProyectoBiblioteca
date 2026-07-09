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

public class PrestamosActivosFrame extends JFrame {
    
    private final Color MARRON_TEXTO_Y_BOTONES = new Color(92, 43, 5);
    private final Color MARRON_CLARO_BUSCADOR = new Color(188, 102, 45);
    
    private JTable tabla;
    private RoundedButton btnVolver;
    private RoundedTextField txtBuscarLibro;
    private RoundedButton btnBuscar;
    private RoundedButton btnLimpiar;

    // Almacena la referencia de PrestamoFrame
    private JFrame ventanaPadre;

    // Modificado el constructor para recibir el JFrame padre
    public PrestamosActivosFrame(JFrame padre) {
        this.ventanaPadre = padre;

        setTitle("Listado de Préstamos Activos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1366, 768));
        // Corregido: DISPOSE_ON_CLOSE para no romper el ciclo de la app
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        fondo.setLayout(new GridBagLayout());
        setContentPane(fondo);

        RoundedPanel contenedor = new RoundedPanel();
        contenedor.setLayout(null);
        contenedor.setBackground(new Color(255, 255, 255, 235));
        contenedor.setPreferredSize(new Dimension(1200, 680));
        fondo.add(contenedor);

        //==================================================
        // BOTÓN VOLVER
        //==================================================
        btnVolver = new RoundedButton(" VOLVER");
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
            btnVolver.setText("<- VOLVER");
        }
        contenedor.add(btnVolver);

        //==================================================
        // ICONO SUPERIOR (Mano con moneda / porcentaje)
        //==================================================
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/prestamo.png"));
            Image img = icono.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            JLabel lblIcono = new JLabel(new ImageIcon(img));
            lblIcono.setBounds(550, 15, 90, 90);
            contenedor.add(lblIcono);
        } catch (Exception e) {
            JLabel lbl = new JLabel("(%)");
            lbl.setFont(new Font("Arial", Font.BOLD, 40));
            lbl.setForeground(MARRON_TEXTO_Y_BOTONES);
            lbl.setBounds(550, 20, 100, 80);
            contenedor.add(lbl);
        }

        // LÍNEA DIVISORIA SUPERIOR
        JPanel linea = new JPanel();
        linea.setBackground(MARRON_TEXTO_Y_BOTONES);
        linea.setBounds(30, 120, 1140, 4);
        contenedor.add(linea);

        //==================================================
        // TÍTULO
        //==================================================
        JLabel lblTitulo = new JLabel("Listado de Préstamos activos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 38));
        lblTitulo.setForeground(MARRON_TEXTO_Y_BOTONES);
        lblTitulo.setBounds(100, 145, 1000, 50);
        contenedor.add(lblTitulo);

        //==================================================
        // APARTADO DE BÚSQUEDA
        //==================================================
        JLabel lblBuscar = new JLabel("Título del libro:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 18));
        lblBuscar.setForeground(Color.BLACK);
        lblBuscar.setBounds(85, 225, 150, 30); 
        contenedor.add(lblBuscar);

        txtBuscarLibro = new RoundedTextField();
        txtBuscarLibro.setBounds(230, 220, 770, 42); 
        txtBuscarLibro.setBackground(MARRON_CLARO_BUSCADOR);
        txtBuscarLibro.setForeground(Color.WHITE);
        txtBuscarLibro.setCaretColor(Color.WHITE);
        txtBuscarLibro.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        txtBuscarLibro.setFont(new Font("Arial", Font.BOLD, 18));
        contenedor.add(txtBuscarLibro);

        // Botón Buscar (Ovalado)
        btnBuscar = new RoundedButton("Buscar");
        btnBuscar.setBounds(1015, 202, 100, 35);
        btnBuscar.setBackground(MARRON_TEXTO_Y_BOTONES);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 15));
        contenedor.add(btnBuscar);

        // Botón Limpiar (Ovalado)
        btnLimpiar = new RoundedButton("Limpiar");
        btnLimpiar.setBounds(1015, 245, 100, 35);
        btnLimpiar.setBackground(MARRON_TEXTO_Y_BOTONES);
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 15));
        contenedor.add(btnLimpiar);

        //==================================================
        // TABLA CONFIGURADA
        //==================================================
        String[] columnas = {"ID Prestamo", "Título del libro", "Estudiante", "Fecha de préstamo"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(245, 237, 225)); 
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 15));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(85, 300, 1030, 320); 
        contenedor.add(scroll);

        //==================================================
        // LÓGICA DE EVENTOS (Modificado)
        //==================================================
        btnVolver.addActionListener(e -> { 
            dispose(); // Cierra esta ventana de listado activo
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true); // Hace visible el menú de gestión de préstamos original
            }
        });

        // Buscar únicamente cuando se presiona el botón Buscar
        btnBuscar.addActionListener(e -> {
            String filtro = txtBuscarLibro.getText().trim();
            if (!filtro.isEmpty()) {
                cargarPrestamosActivos(filtro);
            } else {
                limpiarTabla();
            }
        });
        
        // Permitir que se busque al presionar la tecla ENTER en el campo de texto
        txtBuscarLibro.addActionListener(e -> {
            String filtro = txtBuscarLibro.getText().trim();
            if (!filtro.isEmpty()) {
                cargarPrestamosActivos(filtro);
            } else {
                limpiarTabla();
            }
        });
        
        // El botón limpiar remueve todo el contenido visual de la tabla
        btnLimpiar.addActionListener(e -> {
            txtBuscarLibro.setText("");
            limpiarTabla();
        });

        // Al iniciar la pantalla la tabla aparece limpia sin registros
        limpiarTabla();

        setVisible(true);
    }

    private void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
    }

    private void cargarPrestamosActivos(String libroFiltro) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        try {
            Connection con = ConexionBD.conectar();
            String sql = "SELECT p.id_prestamo, l.titulo AS libro, e.nombre AS estudiante, p.fecha_prestamo " +
                         "FROM prestamos p " +
                         "INNER JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                         "INNER JOIN libros l ON p.id_libro = l.id_libro " +
                         "WHERE p.estado = 'ACTIVO' AND LOWER(l.titulo) LIKE LOWER(?) " + 
                         "ORDER BY p.id_prestamo ASC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + libroFiltro + "%");
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_prestamo"),
                    rs.getString("libro"),
                    rs.getString("estudiante"),
                    rs.getDate("fecha_prestamo")
                });
            }
            
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al filtrar los préstamos activos.");
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