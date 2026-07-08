package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;
import componentes.RoundedTextField;

import conexion.ConexionBD;
import dao.MultaDAO;
import modelo.Multa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MultasFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private RoundedTextField txtEstudiante;
    private RoundedTextField txtMotivo;
    private RoundedTextField txtMonto;
    private RoundedButton btnRegistrar;
    private RoundedButton btnVolver;

    public MultasFrame() {

        setTitle("Gestionar Multas");
        
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

        // --- ICONO CENTRAL ($) ---
        JLabel icono = new JLabel("$");
        icono.setFont(new Font("Arial", Font.BOLD, 80));
        icono.setForeground(new Color(85, 23, 0)); 
        icono.setHorizontalAlignment(SwingConstants.CENTER);
        icono.setBounds(420, 15, 100, 90);
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
        JLabel titulo = new JLabel("Registrar Multa");
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
        // CONFIGURACIÓN DE COMPONENTES DE ENTRADA CORREGIDOS
        // =======================================================
        
        // Margen interno estándar para que el texto respire y no se corte
        Insets paddingCampos = new Insets(2, 12, 2, 12);
        Font fuenteCampos = new Font("Arial", Font.PLAIN, 16);

        JLabel lblEstudiante = new JLabel("Nombre del estudiante:");
        lblEstudiante.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstudiante.setBounds(50, 190, 220, 35);
        panel.add(lblEstudiante);

        txtEstudiante = new RoundedTextField();
        txtEstudiante.setBounds(270, 190, 620, 35);
        txtEstudiante.setFont(fuenteCampos);
        txtEstudiante.setMargin(paddingCampos); // Evita cortes al escribir
        panel.add(txtEstudiante);

        JLabel lblMotivo = new JLabel("Motivo:");
        lblMotivo.setFont(new Font("Arial", Font.BOLD, 18));
        lblMotivo.setBounds(50, 235, 90, 35);
        panel.add(lblMotivo);

        txtMotivo = new RoundedTextField();
        txtMotivo.setBounds(150, 235, 740, 35);
        txtMotivo.setFont(fuenteCampos);
        txtMotivo.setMargin(paddingCampos); // Evita cortes al escribir
        panel.add(txtMotivo);

        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setFont(new Font("Arial", Font.BOLD, 18));
        lblMonto.setBounds(50, 280, 80, 35);
        panel.add(lblMonto);

        txtMonto = new RoundedTextField();
        txtMonto.setBounds(140, 280, 560, 35); // Reajustado el ancho para dar espacio al botón
        txtMonto.setFont(fuenteCampos);
        txtMonto.setMargin(paddingCampos); // Evita cortes al escribir
        panel.add(txtMonto);

        // --- BOTÓN REGISTRAR MULTA SIN CORTES ---
        btnRegistrar = new RoundedButton("Registrar multa");
        btnRegistrar.setBounds(715, 280, 175, 35); // Ancho holgado de 175px
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14)); // Compacto para asegurar que quepa completo
        btnRegistrar.setMargin(new Insets(0, 0, 0, 0)); // Resetea márgenes predeterminados molestos de Java
        panel.add(btnRegistrar);

        // ===================================
        // TABLA CONFIGURADA (Sin cortes)
        // ===================================
        String[] columnas = {
                "ID Multa",
                "ID Préstamo",
                "Estudiante",
                "Libro",
                "Monto",
                "Estado",
                "Fecha de pago"
        };

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        tabla.setRowHeight(32);
        tabla.setGridColor(new Color(200, 200, 200));

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setBackground(new Color(243, 233, 215));
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollTablaConfig();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        scroll.setBounds(50, 335, 840, 265);
        panel.add(scroll);

        // Carga inicial
        cargarMultas();

        // =======================================================
        // LÓGICA DE REGISTRO
        // =======================================================
        btnRegistrar.addActionListener(e -> {
            String nombreEstudiante = txtEstudiante.getText().trim();
            String motivo = txtMotivo.getText().trim();
            String montoStr = txtMonto.getText().trim();

            if (nombreEstudiante.isEmpty() || motivo.isEmpty() || montoStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.");
                return;
            }

            try {
                double monto = Double.parseDouble(montoStr);
                Connection con = ConexionBD.conectar();
                
                String sqlBuscarPrestamo = 
                        "SELECT p.id_prestamo FROM prestamos p " +
                        "JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                        "WHERE LOWER(e.nombre) LIKE LOWER(?) " +
                        "ORDER BY p.id_prestamo DESC LIMIT 1";

                PreparedStatement psBuscar = con.prepareStatement(sqlBuscarPrestamo);
                psBuscar.setString(1, "%" + nombreEstudiante + "%");
                ResultSet rsBuscar = psBuscar.executeQuery();

                if (rsBuscar.next()) {
                    int idPrestamo = rsBuscar.getInt("id_prestamo");

                    Multa multa = new Multa(idPrestamo, monto, motivo, "Pendiente");
                    MultaDAO dao = new MultaDAO();
                    dao.guardarMulta(multa);

                    JOptionPane.showMessageDialog(null, "Multa registrada correctamente al estudiante.");

                    txtEstudiante.setText("");
                    txtMotivo.setText("");
                    txtMonto.setText("");

                    modelo.setRowCount(0);
                    cargarMultas();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró ningún préstamo registrado para el estudiante: " + nombreEstudiante);
                }
                con.close();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El monto debe ser un valor numérico válido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al procesar el registro de la multa.");
                ex.printStackTrace();
            }
        });

        // ===================================
        // ACCIÓN DEL BOTÓN VOLVER
        // ===================================
        btnVolver.addActionListener(e -> {
            dispose();
            new AdminFrame();
        });

        setVisible(true);
    }

    private void scrollTablaConfig() {
        if (tabla.getColumnCount() >= 7) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(75);   // ID Multa
            tabla.getColumnModel().getColumn(1).setPreferredWidth(95);   // ID Préstamo
            tabla.getColumnModel().getColumn(2).setPreferredWidth(120);  // Estudiante
            tabla.getColumnModel().getColumn(3).setPreferredWidth(260);  // Libro
            tabla.getColumnModel().getColumn(4).setPreferredWidth(80);   // Monto
            tabla.getColumnModel().getColumn(5).setPreferredWidth(90);   // Estado
            tabla.getColumnModel().getColumn(6).setPreferredWidth(160);  // Fecha de Pago
        }
    }

    private void cargarMultas() {
        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "SELECT m.id_multa, p.id_prestamo, e.nombre AS estudiante, l.titulo, m.monto, m.estado, m.fecha_pago " +
                    "FROM multas m " +
                    "JOIN prestamos p ON m.id_prestamo = p.id_prestamo " +
                    "JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                    "JOIN libros l ON p.id_libro = l.id_libro " +
                    "ORDER BY m.id_multa ASC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_multa"),
                        rs.getInt("id_prestamo"),
                        rs.getString("estudiante"),
                        rs.getString("titulo"),
                        rs.getDouble("monto"),
                        rs.getString("estado"),
                        rs.getTimestamp("fecha_pago")
                });
            }
            formatearYCentrarColumnas();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar multas");
        }
    }

    private void formatearYCentrarColumnas() {
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(JLabel.CENTER);
        if (tabla.getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setCellRenderer(centro); 
            tabla.getColumnModel().getColumn(1).setCellRenderer(centro); 
            tabla.getColumnModel().getColumn(4).setCellRenderer(centro); 
            tabla.getColumnModel().getColumn(5).setCellRenderer(centro); 
            tabla.getColumnModel().getColumn(6).setCellRenderer(centro); 
        }
    }
}