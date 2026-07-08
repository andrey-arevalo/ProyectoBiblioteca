package vista;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrestamoFrame extends JFrame {

    private static final Color MOCKUP_BROWN = new Color(171, 95, 47);   // Marrón de la barra de búsqueda
    private static final Color LIGHT_BROWN = new Color(230, 148, 103);  // Marrón claro de los botones Buscar/Limpiar
    private static final Color DARK_BROWN = new Color(85, 23, 0);       // Marrón oscuro para títulos/líneas
    private static final Color TABLE_HEADER_BG = new Color(243, 233, 215); // Fondo crema de la cabecera
    private static final Color PANEL_WHITE_OVERLAY = new Color(255, 255, 255, 225); 

    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JTextField txtBuscarEstudiante;

    public PrestamoFrame() {
        setTitle("Historial de Préstamos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon bgIcon = cargarIcono("/imagenes/bibliotecafondo.jpg");
        ImageIcon checklistIcon = cargarIcono("/imagenes/lista_prestamos.png"); // Reemplaza por tu icono de portapapeles/checklist

        JPanel contentPane = new JPanel(null);
        setContentPane(contentPane);

        // --- PANEL BLANCO CENTRAL CONTENEDOR ---
        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PANEL_WHITE_OVERLAY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
            }
        };
        mainPanel.setOpaque(false);
        contentPane.add(mainPanel);

        // --- BOTÓN VOLVER ---
        JButton btnVolver = new JButton(" VOLVER") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(DARK_BROWN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setOpaque(false); btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false); btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        try {
            ImageIcon iconSrc = new ImageIcon(getClass().getResource("/imagenes/flecha_derecha.png"));
            Image imgOriginal = iconSrc.getImage();
            int w = imgOriginal.getWidth(null), h = imgOriginal.getHeight(null);
            if (w > 0 && h > 0) {
                java.awt.image.BufferedImage flipped = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = flipped.createGraphics();
                g.drawImage(imgOriginal, 0, 0, w, h, w, 0, 0, h, null);
                g.dispose();
                btnVolver.setIcon(new ImageIcon(flipped.getScaledInstance(20, 14, Image.SCALE_SMOOTH)));
            }
        } catch(Exception e) {
            btnVolver.setText("← VOLVER");
        }
        mainPanel.add(btnVolver);

        // --- ICONO CENTRAL (CHECKLIST) ---
        JLabel lblChecklistIcon = new JLabel("", SwingConstants.CENTER);
        if (checklistIcon != null) {
            lblChecklistIcon.setIcon(new ImageIcon(checklistIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        }
        mainPanel.add(lblChecklistIcon);

        // --- LÍNEA DIVISORIA ESTILIZADA ---
        JPanel pnlLine = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(DARK_BROWN);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
            }
        };
        pnlLine.setOpaque(false);
        mainPanel.add(pnlLine);

        // --- TÍTULO PRINCIPAL ---
        JLabel lblHeader = new JLabel("Historial de préstamos", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 36));
        lblHeader.setForeground(DARK_BROWN);
        mainPanel.add(lblHeader);

        // --- BARRA DE BÚSQUEDA ---
        JLabel lblBuscar = new JLabel("Buscar estudiante:", SwingConstants.LEFT);
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 22));
        lblBuscar.setForeground(Color.BLACK);
        mainPanel.add(lblBuscar);

        txtBuscarEstudiante = new RoundedTextField();
        mainPanel.add(txtBuscarEstudiante);

        // --- BOTONES LATERALES ---
        JButton btnBuscar = new RoundedButton("Buscar", LIGHT_BROWN);
        mainPanel.add(btnBuscar);

        JButton btnLimpiar = new RoundedButton("Limpiar", LIGHT_BROWN);
        mainPanel.add(btnLimpiar);

        // --- JTABLE HISTORIAL ---
        String[] columnas = {"ID Préstamo", "Estudiante", "Libro", "Fecha Préstamo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaHistorial.setRowHeight(35);
        tablaHistorial.setGridColor(new Color(180, 180, 180));
        tablaHistorial.setSelectionBackground(MOCKUP_BROWN);
        tablaHistorial.setSelectionForeground(Color.WHITE);

        // Estilo de la cabecera idéntico al mockup (Bordes negros, texto oscuro, fondo crema)
        JTableHeader header = tablaHistorial.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        scrollTabla = new JScrollPane(tablaHistorial);
        scrollTabla.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        mainPanel.add(scrollTabla);

        // --- FONDO DE LA VENTANA ---
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgIcon != null) g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.add(backgroundPanel);

        // --- DISEÑO RESPONSIVO DINÁMICO ---
        contentPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameW = contentPane.getWidth(), frameH = contentPane.getHeight();
                backgroundPanel.setBounds(0, 0, frameW, frameH);

                int panelW = (int) (frameW * 0.92), panelH = (int) (frameH * 0.92);
                mainPanel.setBounds((frameW - panelW) / 2, (frameH - panelH) / 2, panelW, panelH);

                btnVolver.setBounds(35, 25, 140, 42);
                lblChecklistIcon.setBounds((panelW - 100) / 2, 20, 100, 100);
                pnlLine.setBounds((panelW - (int)(panelW * 0.94)) / 2, 135, (int)(panelW * 0.94), 5); 
                lblHeader.setBounds(0, 150, panelW, 45);

                // Fila de búsqueda coordinada
                int labelX = (int) (panelW * 0.03);
                int labelW = 210;
                int inputX = labelX + labelW + 5;
                int buttonW = 100, buttonH = 36;
                int inputW = panelW - inputX - buttonW - 50; 
                int rowY = 220;

                lblBuscar.setBounds(labelX, rowY, labelW, 45);
                txtBuscarEstudiante.setBounds(inputX, rowY, inputW, 45);
                
                // Botones alineados a la derecha de la barra
                btnBuscar.setBounds(inputX + inputW + 15, rowY, buttonW, buttonH);
                btnLimpiar.setBounds(inputX + inputW + 15, rowY + 42, buttonW, buttonH);

                // Tabla inferior expansiva
                int tablaY = rowY + 90;
                int tablaH = panelH - tablaY - 30;
                scrollTabla.setBounds(labelX, tablaY, panelW - (labelX * 2), tablaH);
            }
        });

        // --- ACCIONES Y FILTROS ---
        btnVolver.addActionListener(e -> dispose());
        
        btnBuscar.addActionListener(e -> cargarHistorial(txtBuscarEstudiante.getText().trim()));
        
        btnLimpiar.addActionListener(e -> {
            txtBuscarEstudiante.setText("");
            cargarHistorial("");
        });

        // Carga inicial completa sin filtros
        cargarHistorial("");
    }

    // --- CONSULTA SQL ADAPTADA CON INNER JOINS Y FILTRO ORDER BY ID ---
    private void cargarHistorial(String estudianteFiltro) {
        try {
            modeloTabla.setRowCount(0);
            Connection con = ConexionBD.conectar();

            // Consulta relacional ordenada ascendentemente por el ID del préstamo
            String sql = "SELECT p.id_prestamo, e.nombre AS estudiante, l.titulo AS libro, " +
                         "p.fecha_prestamo, l.disponible " +
                         "FROM prestamos p " +
                         "INNER JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                         "INNER JOIN libros l ON p.id_libro = l.id_libro ";

            if (!estudianteFiltro.isEmpty()) {
                sql += "WHERE LOWER(e.nombre) LIKE LOWER(?) ";
            }
            sql += "ORDER BY p.id_prestamo ASC";

            PreparedStatement ps = con.prepareStatement(sql);
            if (!estudianteFiltro.isEmpty()) {
                ps.setString(1, "%" + estudianteFiltro + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Si el libro no está disponible en la tabla de libros, significa que el préstamo sigue "Activo"
                String estado = !rs.getBoolean("disponible") ? "Activo" : "Devuelto";
                
                Object[] fila = {
                    rs.getInt("id_prestamo"),
                    rs.getString("estudiante"),
                    rs.getString("libro"),
                    rs.getDate("fecha_prestamo") != null ? rs.getDate("fecha_prestamo").toString() : "Sin fecha",
                    estado
                };
                modeloTabla.addRow(fila);
            }

            // Alineaciones en la tabla (Centrar ID y Estado para que luzca ordenado)
            javax.swing.table.DefaultTableCellRenderer centro = new javax.swing.table.DefaultTableCellRenderer();
            centro.setHorizontalAlignment(JLabel.CENTER);
            tablaHistorial.getColumnModel().getColumn(0).setCellRenderer(centro);
            tablaHistorial.getColumnModel().getColumn(4).setCellRenderer(centro);

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error al obtener el historial: " + ex.getMessage());
        }
    }

    private ImageIcon cargarIcono(String ruta) {
        URL url = getClass().getResource(ruta);
        return (url != null) ? new ImageIcon(url) : null;
    }

    // --- COMPONENTES PERSONALIZADOS ---
    class RoundedTextField extends JTextField {
        public RoundedTextField() {
            setOpaque(false); setForeground(Color.WHITE); setBackground(MOCKUP_BROWN);
            setFont(new Font("Arial", Font.BOLD, 18));
            setBorder(new EmptyBorder(0, 20, 0, 20)); setCaretColor(Color.WHITE);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    class RoundedButton extends JButton {
        private Color fondo;
        public RoundedButton(String texto, Color fondo) {
            super(texto);
            this.fondo = fondo;
            setFont(new Font("Arial", Font.BOLD, 15));
            setForeground(Color.BLACK);
            setOpaque(false); setContentAreaFilled(false);
            setBorderPainted(false); setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fondo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            super.paintComponent(g2);
            g2.dispose();
        }
    }
}