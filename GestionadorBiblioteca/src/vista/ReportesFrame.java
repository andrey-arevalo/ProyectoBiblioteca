package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportesFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private RoundedButton btnVolver;

    public ReportesFrame() {

        setTitle("Reportes Generales");
        
        // --- VENTANA MAXIMIZADA RESPETANDO LA BARRA DE TAREAS ---
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        setContentPane(fondo);
        fondo.setLayout(null);

        // --- PANEL CENTRAL REDONDEADO ---
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(null);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int panelW = 940; 
        int panelH = 650;
        int panelX = (screenSize.width - panelW) / 2;
        int panelY = (screenSize.height - panelH) / 2;
        panel.setBounds(panelX, panelY, panelW, panelH);
        fondo.add(panel);

        // --- ICONO CENTRAL DE REPORTES (Lista con lupa/check) ---
        JLabel icono = new JLabel();
        icono.setHorizontalAlignment(SwingConstants.CENTER);
        icono.setBounds(420, 15, 100, 90);
        try {
            // Intentamos cargar tu imagen de icono si existe
            ImageIcon iconSrc = new ImageIcon(getClass().getResource("/imagenes/reportes.png"));
            Image imgScaled = iconSrc.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            icono.setIcon(new ImageIcon(imgScaled));
        } catch (Exception e) {
            // Alternativa en texto por si no encuentra la imagen
            icono.setText("📋");
            icono.setFont(new Font("Arial", Font.PLAIN, 65));
            icono.setForeground(new Color(85, 23, 0));
        }
        panel.add(icono);

        // --- LÍNEA DIVISORIA ---
        JPanel pnlLine = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(85, 23, 0));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
            }
        };
        pnlLine.setBounds(30, 120, 880, 4);
        panel.add(pnlLine);

        // --- TÍTULO PRINCIPAL ---
        JLabel titulo = new JLabel("Reportes");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(new Color(85, 23, 0));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(100, 135, 740, 40);
        panel.add(titulo);

        // --- BOTÓN VOLVER (Esquina superior izquierda) ---
        btnVolver = new RoundedButton("Volver");
        btnVolver.setBounds(30, 25, 150, 40);
        
        try {
            ImageIcon iconSrc = new ImageIcon(getClass().getResource("/imagenes/flecha_derecha.png"));
            Image imgOriginal = iconSrc.getImage();
            int w = imgOriginal.getWidth(null), h = imgOriginal.getHeight(null);
            if (w > 0 && h > 0) {
                java.awt.image.BufferedImage flipped = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = flipped.createGraphics();
                g.drawImage(imgOriginal, 0, 0, w, h, w, 0, 0, h, null); 
                g.dispose();
                btnVolver.setIcon(new ImageIcon(flipped.getScaledInstance(18, 12, Image.SCALE_SMOOTH)));
            }
        } catch(Exception e) {
            btnVolver.setText("← Volver");
        }
        panel.add(btnVolver);

        // =======================================================
        // TABLA CONFIGURADA CON LAS 4 COLUMNAS DEL MOCKUP
        // =======================================================
        String[] columnas = {
                "Titulo del Libro",
                "Estudiante",
                "Cantidad de préstamos",
                "Cantidad de multas"
        };

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        tabla.setRowHeight(35); // Altura cómoda para leer bien las filas
        tabla.setGridColor(new Color(200, 200, 200));

        // Diseño del encabezado de la tabla (Color beige/crema como el mockup)
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(243, 233, 215)); 
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(new Color(85, 23, 0), 1));

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollTablaConfig();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        scroll.setBounds(50, 200, 840, 400); // Espacio holgado abajo
        panel.add(scroll);

        // Cargar los datos cruzados desde la Base de Datos
        cargarReporteUnificado();

        // --- ACCIÓN DEL BOTÓN VOLVER ---
        btnVolver.addActionListener(e -> {
            dispose();
            new AdminFrame();
        });

        setVisible(true);
    }

    private void scrollTablaConfig() {
        if (tabla.getColumnCount() >= 4) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(280); // Titulo del Libro
            tabla.getColumnModel().getColumn(1).setPreferredWidth(200); // Estudiante
            tabla.getColumnModel().getColumn(2).setPreferredWidth(180); // Cantidad de préstamos
            tabla.getColumnModel().getColumn(3).setPreferredWidth(180); // Cantidad de multas
        }
    }

    // Lógica SQL para unificar libros, estudiantes, sus préstamos y sus respectivas multas
    private void cargarReporteUnificado() {
        try {
            Connection con = ConexionBD.conectar();
            
            // Consulta que cuenta los préstamos de un estudiante por libro y calcula cuántas multas se le generaron
            String sql =
                    "SELECT l.titulo AS libro, e.nombre AS estudiante, " +
                    "COUNT(p.id_prestamo) AS cant_prestamos, " +
                    "COUNT(m.id_multa) AS cant_multas " +
                    "FROM prestamos p " +
                    "JOIN libros l ON p.id_libro = l.id_libro " +
                    "JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                    "LEFT JOIN multas m ON p.id_prestamo = m.id_prestamo " +
                    "GROUP BY l.titulo, e.nombre " +
                    "ORDER BY cant_prestamos DESC, e.nombre ASC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getString("libro"),
                        rs.getString("estudiante"),
                        rs.getInt("cant_prestamos"),
                        rs.getInt("cant_multas")
                });
            }

            formatearYCentrarColumnas();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al procesar el reporte consolidado.");
        }
    }

    private void formatearYCentrarColumnas() {
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(JLabel.CENTER);
        if (tabla.getColumnCount() >= 4) {
            tabla.getColumnModel().getColumn(2).setCellRenderer(centro); // Centrar cantidad préstamos
            tabla.getColumnModel().getColumn(3).setCellRenderer(centro); // Centrar cantidad multas
        }
    }
}