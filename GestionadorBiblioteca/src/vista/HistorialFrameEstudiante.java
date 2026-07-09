package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;
import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HistorialFrameEstudiante extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tabla;
    private DefaultTableModel modelo;
    private EstudianteFrame ventanaPadre;

    // Paleta de colores exacta basada en el mockup
    private final Color MARRON_OSCURO = new Color(92, 43, 5);
    private final Color COLOR_CREMA_CABECERA = new Color(245, 232, 212);
    private final Color COLOR_TEXTO_CABECERA = new Color(40, 20, 3);

    public HistorialFrameEstudiante(EstudianteFrame padre, int idEstudiante) {
        this.ventanaPadre = padre;

        setTitle("Historial de Préstamos - Estudiante");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1. Fondo escalable de la biblioteca
        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        fondo.setLayout(new GridBagLayout());
        setContentPane(fondo);

        // 2. Panel contenedor principal (Blanco translúcido con bordes redondeados)
        RoundedPanel panelContenedor = new RoundedPanel();
        panelContenedor.setLayout(null);
        panelContenedor.setBackground(new Color(255, 255, 255, 240));
        panelContenedor.setPreferredSize(new Dimension(1200, 680));
        fondo.add(panelContenedor);

        // 3. Botón Volver con la imagen 'flecha_derecha.png' invertida por software
        RoundedButton btnVolver = new RoundedButton("VOLVER");
        btnVolver.setBounds(30, 25, 175, 45);
        btnVolver.setBackground(MARRON_OSCURO);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 16));
        btnVolver.setHorizontalTextPosition(SwingConstants.RIGHT); // Texto a la derecha de la flecha

        try {
            // Cargar imagen original flecha_derecha.png
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imagenes/flecha_derecha.png"));
            Image originalImg = originalIcon.getImage();
            
            // Crear un buffer para manipularla
            int w = originalImg.getWidth(null);
            int h = originalImg.getHeight(null);
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bi.createGraphics();
            
            // Inversión horizontal mediante AffineTransform
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-w, 0);
            g2d.drawImage(originalImg, tx, null);
            g2d.dispose();
            
            // Escalar la flecha ya invertida para que quepa armoniosamente en el botón
            Image imgInvertidaEscalada = bi.getScaledInstance(24, 20, Image.SCALE_SMOOTH);
            btnVolver.setIcon(new ImageIcon(imgInvertidaEscalada));
            
        } catch (Exception e) {
            // Fallback clásico en texto si la imagen no se encuentra
            btnVolver.setText("◀ VOLVER");
        }
        panelContenedor.add(btnVolver);

        // 4. Icono Central de Préstamos (prestamo.png)
        try {
            ImageIcon iconPrestamo = new ImageIcon(getClass().getResource("/imagenes/prestamo.png"));
            Image img = iconPrestamo.getImage().getScaledInstance(90, 80, Image.SCALE_SMOOTH);
            JLabel lblIcono = new JLabel(new ImageIcon(img));
            lblIcono.setBounds(555, 15, 90, 80);
            panelContenedor.add(lblIcono);
        } catch (Exception e) {
            JLabel lblFallback = new JLabel("🫴%️", SwingConstants.CENTER);
            lblFallback.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
            lblFallback.setBounds(550, 15, 100, 80);
            panelContenedor.add(lblFallback);
        }

        // 5. Línea divisoria superior marrón oscuro
        JPanel lineaSuperior = new JPanel();
        lineaSuperior.setBackground(MARRON_OSCURO);
        lineaSuperior.setBounds(30, 110, 1140, 5);
        panelContenedor.add(lineaSuperior);

        // 6. Título: HISTORIAL DE PRÉSTAMOS
        JLabel lblTitulo = new JLabel("HISTORIAL DE PRÉSTAMOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 38));
        lblTitulo.setForeground(MARRON_OSCURO);
        lblTitulo.setBounds(0, 130, 1200, 45);
        panelContenedor.add(lblTitulo);

        // 7. Configuración de la Tabla y Columnas del Mockup
        String[] columnas = { "ID Libro", "Estudiante", "Libro", "Fecha de préstamo" };
        modelo = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 16));
        tabla.setRowHeight(35);
        tabla.setGridColor(MARRON_OSCURO);
        tabla.setShowGrid(true);

        // Estilizar las Cabeceras de la Tabla (Color crema, texto oscuro, bordes oscuros)
        JTableHeader header = tabla.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBackground(COLOR_CREMA_CABECERA);
        header.setForeground(COLOR_TEXTO_CABECERA);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBorder(BorderFactory.createLineBorder(MARRON_OSCURO, 1));

        // Renderizador para centrar los textos de la tabla
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ajustar anchos específicos de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(120); // ID Libro
        tabla.getColumnModel().getColumn(1).setPreferredWidth(280); // Estudiante
        tabla.getColumnModel().getColumn(2).setPreferredWidth(450); // Libro
        tabla.getColumnModel().getColumn(3).setPreferredWidth(250); // Fecha de préstamo

        // ScrollPane transparente/integrado
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(70, 200, 1060, 420);
        scrollPane.setBorder(BorderFactory.createLineBorder(MARRON_OSCURO, 1));
        panelContenedor.add(scrollPane);

        // ==================================================
        // ACCIÓN DEL BOTÓN VOLVER
        // ==================================================
        btnVolver.addActionListener(e -> {
            dispose();
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true);
            }
        });

        // Cargar los datos desde la Base de Datos
        cargarHistorial(idEstudiante);

        setVisible(true);
    }

    private void cargarHistorial(int idEstudiante) {
        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "SELECT " +
                    "l.id_libro, " + 
                    "e.nombre AS estudiante, " +
                    "l.titulo, " +
                    "p.fecha_prestamo " +
                    "FROM prestamos p " +
                    "JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                    "JOIN libros l ON p.id_libro = l.id_libro " +
                    "WHERE p.id_estudiante = ? " +
                    "ORDER BY p.fecha_prestamo DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();

            boolean hayPrestamosE = false;
            while (rs.next()) {
                hayPrestamosE = true;
                modelo.addRow(new Object[]{
                        rs.getInt("id_libro"), 
                        rs.getString("estudiante"),
                        rs.getString("titulo"),
                        rs.getTimestamp("fecha_prestamo")
                });
            }

            if (!hayPrestamosE) {
                modelo.addRow(new Object[]{"-", "No hay préstamos registrados", "-", "-"});
            }
            
            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar el historial de préstamos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}