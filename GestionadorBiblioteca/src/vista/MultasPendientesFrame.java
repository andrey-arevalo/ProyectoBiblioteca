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

public class MultasPendientesFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tabla;
    private DefaultTableModel modelo;
    private EstudianteFrame ventanaPadre;
    private JLabel lblMontoValor; // Barra marrón que mostrará el monto dinámico

    // Paleta de colores exacta basada en el mockup
    private final Color MARRON_OSCURO = new Color(92, 43, 5);
    private final Color COLOR_CREMA_CABECERA = new Color(245, 232, 212);
    private final Color COLOR_TEXTO_CABECERA = new Color(40, 20, 3);
    private final Color NARANJA_METODOS = new Color(224, 130, 68);

    public MultasPendientesFrame(EstudianteFrame padre, int idEstudiante) {
        this.ventanaPadre = padre;

        setTitle("Visualizar multas pendientes");
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
        btnVolver.setHorizontalTextPosition(SwingConstants.RIGHT);

        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imagenes/flecha_derecha.png"));
            Image originalImg = originalIcon.getImage();
            int w = originalImg.getWidth(null);
            int h = originalImg.getHeight(null);
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bi.createGraphics();
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-w, 0);
            g2d.drawImage(originalImg, tx, null);
            g2d.dispose();
            Image imgInvertidaEscalada = bi.getScaledInstance(24, 20, Image.SCALE_SMOOTH);
            btnVolver.setIcon(new ImageIcon(imgInvertidaEscalada));
        } catch (Exception e) {
            btnVolver.setText("◀ VOLVER");
        }
        panelContenedor.add(btnVolver);

        // 4. Icono Central de Multas (multa.png o signo de dólar del mockup)
        try {
            ImageIcon iconMulta = new ImageIcon(getClass().getResource("/imagenes/multa.png"));
            Image img = iconMulta.getImage().getScaledInstance(75, 80, Image.SCALE_SMOOTH);
            JLabel lblIcono = new JLabel(new ImageIcon(img));
            lblIcono.setBounds(562, 15, 75, 80);
            panelContenedor.add(lblIcono);
        } catch (Exception e) {
            JLabel lblFallback = new JLabel("$", SwingConstants.CENTER);
            lblFallback.setFont(new Font("Georgia", Font.BOLD, 65));
            lblFallback.setForeground(MARRON_OSCURO);
            lblFallback.setBounds(550, 15, 100, 80);
            panelContenedor.add(lblFallback);
        }

        // 5. Línea divisoria superior marrón oscuro
        JPanel lineaSuperior = new JPanel();
        lineaSuperior.setBackground(MARRON_OSCURO);
        lineaSuperior.setBounds(30, 110, 1140, 5);
        panelContenedor.add(lineaSuperior);

        // 6. Título: Visualizar multas pendientes
        JLabel lblTitulo = new JLabel("Visualizar multas pendientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 36));
        lblTitulo.setForeground(MARRON_OSCURO);
        lblTitulo.setBounds(0, 130, 1200, 45);
        panelContenedor.add(lblTitulo);

        // 7. Configuración de la Tabla y Columnas exactas del Mockup
        String[] columnas = { "ID Multa", "Fecha Préstamo", "Estudiante", "Libro", "Monto", "Estado", "Fecha de pago" };
        modelo = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        tabla.setRowHeight(35);
        tabla.setGridColor(MARRON_OSCURO);
        tabla.setShowGrid(true);

        // Estilizar las Cabeceras
        JTableHeader header = tabla.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBackground(COLOR_CREMA_CABECERA);
        header.setForeground(COLOR_TEXTO_CABECERA);
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setBorder(BorderFactory.createLineBorder(MARRON_OSCURO, 1));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ajustar anchos proporcionales
        tabla.getColumnModel().getColumn(0).setPreferredWidth(90);   // ID Multa
        tabla.getColumnModel().getColumn(1).setPreferredWidth(140);  // Fecha Préstamo
        tabla.getColumnModel().getColumn(2).setPreferredWidth(180);  // Estudiante
        tabla.getColumnModel().getColumn(3).setPreferredWidth(300);  // Libro
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100);  // Monto
        tabla.getColumnModel().getColumn(5).setPreferredWidth(110);  // Estado
        tabla.getColumnModel().getColumn(6).setPreferredWidth(200);  // Fecha de pago

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(70, 200, 1060, 310);
        scrollPane.setBorder(BorderFactory.createLineBorder(MARRON_OSCURO, 1));
        panelContenedor.add(scrollPane);

        // 8. TEXTO INFORMATIVO INFERIOR
        JLabel lblInfo = new JLabel("Elige el monto a pagar y presione el botón para pagar.");
        lblInfo.setFont(new Font("Arial", Font.BOLD, 18));
        lblInfo.setForeground(Color.BLACK);
        lblInfo.setBounds(70, 530, 600, 30);
        panelContenedor.add(lblInfo);

        // 9. COMBOBOX DE MÉTODOS DE PAGO (Botón de categorías/elecciones naranja del mockup)
        String[] opcionesPago = { " Métodos de pago", " BCP", " BBVA", " Plin", " Yape" };
        JComboBox<String> comboMetodos = new JComboBox<>(opcionesPago);
        comboMetodos.setBounds(910, 530, 220, 40);
        comboMetodos.setBackground(NARANJA_METODOS);
        comboMetodos.setForeground(Color.BLACK);
        comboMetodos.setFont(new Font("Arial", Font.BOLD, 16));
        // Truco visual para que parezca un botón plano redondeado como el mockup
        comboMetodos.setBorder(BorderFactory.createEmptyBorder());
        panelContenedor.add(comboMetodos);

        // 10. SECCIÓN DEL MONTO SELECCIONADO (Barra larga marrón)
        JLabel lblMontoTxt = new JLabel("Monto:");
        lblMontoTxt.setFont(new Font("Arial", Font.BOLD, 22));
        lblMontoTxt.setForeground(Color.BLACK);
        lblMontoTxt.setBounds(70, 595, 100, 40);
        panelContenedor.add(lblMontoTxt);

        // La barra contenedora marrón del monto
        lblMontoValor = new JLabel("  Seleccione una multa de la tabla...", SwingConstants.LEFT);
        lblMontoValor.setBounds(160, 595, 730, 40);
        lblMontoValor.setOpaque(true);
        lblMontoValor.setBackground(MARRON_OSCURO);
        lblMontoValor.setForeground(Color.WHITE);
        lblMontoValor.setFont(new Font("Arial", Font.BOLD, 18));
        lblMontoValor.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        panelContenedor.add(lblMontoValor);

        // 11. BOTÓN PAGAR MULTA (Óvalo de la esquina inferior derecha)
        RoundedButton btnPagar = new RoundedButton("Pagar multa");
        btnPagar.setBounds(910, 595, 220, 40);
        btnPagar.setBackground(NARANJA_METODOS);
        btnPagar.setForeground(Color.BLACK);
        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
        panelContenedor.add(btnPagar);

        // ==================================================
        // EVENTO: SELECCIONAR FILA DE LA TABLA
        // ==================================================
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                String monto = tabla.getValueAt(fila, 4).toString();
                String estado = tabla.getValueAt(fila, 5).toString();
                
                if(estado.equalsIgnoreCase("Pagada")) {
                    lblMontoValor.setText("  S/. " + monto + " (Ya pagada)");
                } else {
                    lblMontoValor.setText("  S/. " + monto);
                }
            }
        });

        // ==================================================
        // EVENTO: BOTÓN VOLVER
        // ==================================================
        btnVolver.addActionListener(e -> {
            dispose();
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true);
            }
        });

        // ==================================================
        // EVENTO: ACCIÓN PAGAR MULTA
        // ==================================================
        btnPagar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una multa de la tabla.");
                return;
            }

            // Validar que se haya escogido un método real
            if (comboMetodos.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un método de pago (BCP, BBVA, Plin o Yape).");
                return;
            }

            String estado = tabla.getValueAt(fila, 5).toString();
            if (estado.equalsIgnoreCase("Pagada")) {
                JOptionPane.showMessageDialog(null, "Esta multa ya se encuentra pagada.");
                return;
            }

            int idMulta = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
            String metodoSeleccionado = comboMetodos.getSelectedItem().toString().trim();

            try {
                Connection con = ConexionBD.conectar();
                String sql =
                        "UPDATE multas " +
                        "SET estado='Pagada', " +
                        "fecha_pago=CURRENT_TIMESTAMP " +
                        "WHERE id_multa=?";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, idMulta);
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "¡Pago de S/. " + tabla.getValueAt(fila, 4) + " realizado con éxito vía " + metodoSeleccionado + "!");
                
                // Reiniciar campos e interfaz
                modelo.setRowCount(0);
                lblMontoValor.setText("  Seleccione una multa de la tabla...");
                comboMetodos.setSelectedIndex(0);
                
                // Recargar base de datos
                cargarMultas(idEstudiante);

                ps.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error crítico al procesar el pago.");
            }
        });

        // Cargar multas del alumno logueado
        cargarMultas(idEstudiante);

        setVisible(true);
    }

    // ==================================================
    // CARGAR MULTAS DESDE BASE DE DATOS
    // ==================================================
    private void cargarMultas(int idEstudiante) {
        try {
            Connection con = ConexionBD.conectar();
            // Adaptado para obtener la Fecha de Préstamo requerida por la columna 2 del nuevo mockup
            String sql =
                    "SELECT m.id_multa, p.fecha_prestamo, e.nombre AS estudiante, l.titulo, m.monto, m.estado, m.fecha_pago " +
                    "FROM multas m " +
                    "JOIN prestamos p ON m.id_prestamo = p.id_prestamo " +
                    "JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                    "JOIN libros l ON p.id_libro = l.id_libro " +
                    "WHERE p.id_estudiante = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            boolean hayMultas = false;
            
            while (rs.next()) {
                hayMultas = true;
                modelo.addRow(
                        new Object[]{
                                rs.getInt("id_multa"),
                                rs.getTimestamp("fecha_prestamo"), // Agregado según mockup
                                rs.getString("estudiante"),
                                rs.getString("titulo"),
                                rs.getDouble("monto"),
                                rs.getString("estado"),
                                rs.getTimestamp("fecha_pago") != null ? rs.getTimestamp("fecha_pago") : "Pendiente"
                        }
                );
            }

            if (!hayMultas) {
                modelo.addRow(new Object[]{"-", "-", "No tienes multas pendientes", "-", "-", "-", "-"});
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar la lista de multas.");
        }
    }
}