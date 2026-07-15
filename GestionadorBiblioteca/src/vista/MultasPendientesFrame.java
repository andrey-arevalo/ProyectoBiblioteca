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
    private JLabel lblMontoValor; 
    private JComboBox<String> comboMetodos;

    // Paleta de colores exacta
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

        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        fondo.setLayout(new GridBagLayout());
        setContentPane(fondo);

        RoundedPanel panelContenedor = new RoundedPanel();
        panelContenedor.setLayout(null);
        panelContenedor.setBackground(new Color(255, 255, 255, 240));
        panelContenedor.setPreferredSize(new Dimension(1200, 680));
        fondo.add(panelContenedor);

        // Botón Volver con flecha invertida
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

        // Icono Central de Dólar/Multas
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

        JPanel lineaSuperior = new JPanel();
        lineaSuperior.setBackground(MARRON_OSCURO);
        lineaSuperior.setBounds(30, 110, 1140, 5);
        panelContenedor.add(lineaSuperior);

        JLabel lblTitulo = new JLabel("Visualizar multas pendientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 36));
        lblTitulo.setForeground(MARRON_OSCURO);
        lblTitulo.setBounds(0, 130, 1200, 45);
        panelContenedor.add(lblTitulo);

        // Tabla
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

        tabla.getColumnModel().getColumn(0).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(140);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(300);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(110);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(70, 200, 1060, 310);
        scrollPane.setBorder(BorderFactory.createLineBorder(MARRON_OSCURO, 1));
        panelContenedor.add(scrollPane);

        JLabel lblInfo = new JLabel("Elige el monto a pagar y presione el botón para pagar.");
        lblInfo.setFont(new Font("Arial", Font.BOLD, 18));
        lblInfo.setForeground(Color.BLACK);
        lblInfo.setBounds(70, 530, 600, 30);
        panelContenedor.add(lblInfo);

        // 1. Selector de Métodos de Pago
        String[] opcionesPago = { "Métodos de pago", "BCP", "BBVA", "Plin", "Yape" };
        comboMetodos = new JComboBox<>(opcionesPago);
        comboMetodos.setBounds(910, 530, 220, 40);
        comboMetodos.setBackground(NARANJA_METODOS);
        comboMetodos.setForeground(Color.BLACK);
        comboMetodos.setFont(new Font("Arial", Font.BOLD, 16));
        comboMetodos.setBorder(BorderFactory.createEmptyBorder());
        panelContenedor.add(comboMetodos);

        JLabel lblMontoTxt = new JLabel("Monto:");
        lblMontoTxt.setFont(new Font("Arial", Font.BOLD, 22));
        lblMontoTxt.setForeground(Color.BLACK);
        lblMontoTxt.setBounds(70, 595, 100, 40);
        panelContenedor.add(lblMontoTxt);

        // Barra contenedora del monto
        lblMontoValor = new JLabel("  Seleccione una multa de la tabla...", SwingConstants.LEFT);
        lblMontoValor.setBounds(160, 595, 730, 40);
        lblMontoValor.setOpaque(true);
        lblMontoValor.setBackground(MARRON_OSCURO);
        lblMontoValor.setForeground(Color.WHITE);
        lblMontoValor.setFont(new Font("Arial", Font.BOLD, 18));
        lblMontoValor.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        panelContenedor.add(lblMontoValor);

        RoundedButton btnPagar = new RoundedButton("Pagar multa");
        btnPagar.setBounds(910, 595, 220, 40);
        btnPagar.setBackground(NARANJA_METODOS);
        btnPagar.setForeground(Color.BLACK);
        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
        panelContenedor.add(btnPagar);

        // =====================================================================
        // MEJORA 1: ACTUALIZACIÓN DINÁMICA DE LA BARRA SEGÚN EL MÉTODO DE PAGO
        // =====================================================================
        Runnable actualizarBarraDetalle = () -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                lblMontoValor.setText("  Seleccione una multa de la tabla...");
                return;
            }

            String monto = tabla.getValueAt(fila, 4).toString();
            String estado = tabla.getValueAt(fila, 5).toString();
            String metodo = comboMetodos.getSelectedItem().toString();

            if (estado.equalsIgnoreCase("Pagada")) {
                lblMontoValor.setText("  S/. " + monto + " (Esta multa ya se encuentra PAGADA)");
                return;
            }

            if (metodo.equals("Métodos de pago")) {
                lblMontoValor.setText("  S/. " + monto + " | Por favor, seleccione un método de pago en el botón naranja.");
            } else if (metodo.equals("Yape") || metodo.equals("Plin")) {
                lblMontoValor.setText("  S/. " + monto + " | Transferir al número de la Biblioteca: 987-654-321 (" + metodo + ")");
            } else {
                lblMontoValor.setText("  S/. " + monto + " | Depositar a la Cuenta Corriente: 191-3849204-0-93 (" + metodo + ")");
            }
        };

        // Evento al hacer clic en la tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarBarraDetalle.run();
            }
        });

        // Evento al cambiar el método de pago en el JComboBox
        comboMetodos.addActionListener(e -> actualizarBarraDetalle.run());

        // Volver
        btnVolver.addActionListener(e -> {
            dispose();
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true);
            }
        });

        // =====================================================================
        // MEJORA 2: INTERFAZ DE SIMULACIÓN DE PAGO AL CLICKEAR "PAGAR MULTA"
        // =====================================================================
        btnPagar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione la multa que desea pagar.");
                return;
            }

            String estado = tabla.getValueAt(fila, 5).toString();
            if (estado.equalsIgnoreCase("Pagada")) {
                JOptionPane.showMessageDialog(this, "Esta multa ya está cancelada.");
                return;
            }

            if (comboMetodos.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un método de pago (BCP, BBVA, Plin o Yape) en el selector.");
                return;
            }

            int idMulta = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
            String monto = tabla.getValueAt(fila, 4).toString();
            String metodo = comboMetodos.getSelectedItem().toString();

            // Ventana de simulación interactiva
            String transaccionSimulada = "";
            if (metodo.equals("Yape") || metodo.equals("Plin")) {
                transaccionSimulada = JOptionPane.showInputDialog(this, 
                        "Simulador de Pago " + metodo + "\n\n" +
                        "1. Abre tu app de " + metodo + ".\n" +
                        "2. Envía S/. " + monto + " al número: 987-654-321.\n" +
                        "3. Ingresa aquí el código de operación/aprobación de la app:", 
                        "Confirmación de Pago Celular", JOptionPane.INFORMATION_MESSAGE);
            } else {
                transaccionSimulada = JOptionPane.showInputDialog(this, 
                        "Simulador de Pago Bancario (" + metodo + ")\n\n" +
                        "1. Transfiere S/. " + monto + " a la cuenta: 191-3849204-0-93.\n" +
                        "2. Ingresa aquí el número de operación del voucher:", 
                        "Confirmación de Depósito Bancario", JOptionPane.INFORMATION_MESSAGE);
            }

            // Si el alumno cancela el cuadro de diálogo de simulación
            if (transaccionSimulada == null) {
                return; 
            }

            if (transaccionSimulada.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pago cancelado. Debe ingresar el código de validación de la transacción.");
                return;
            }

            // Procesar el pago tras la validación simulada
            try {
                Connection con = ConexionBD.conectar();
                String sql = "UPDATE multas SET estado='Pagada', fecha_pago=CURRENT_TIMESTAMP WHERE id_multa=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, idMulta);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, 
                        "¡Pago Procesado Correctamente!\n" +
                        "Método: " + metodo + "\n" +
                        "Transacción: " + transaccionSimulada.trim().toUpperCase() + "\n" +
                        "Monto: S/. " + monto);

                modelo.setRowCount(0);
                lblMontoValor.setText("  Seleccione una multa de la tabla...");
                comboMetodos.setSelectedIndex(0);
                cargarMultas(idEstudiante);

                ps.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error de conexión al procesar el cobro.");
            }
        });

        cargarMultas(idEstudiante);
        setVisible(true);
    }

    private void cargarMultas(int idEstudiante) {
        // Definimos el formato limpio y estético que queremos mostrar (Día/Mes/Año Hora:Minutos)
        java.text.SimpleDateFormat formateador = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            Connection con = ConexionBD.conectar();
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

                // Formateamos la Fecha de Préstamo
                java.sql.Timestamp tsPrestamo = rs.getTimestamp("fecha_prestamo");
                String fechaPrestamoFormateada = (tsPrestamo != null) ? formateador.format(tsPrestamo) : "-";

                // Formateamos la Fecha de Pago
                java.sql.Timestamp tsPago = rs.getTimestamp("fecha_pago");
                String fechaPagoFormateada = "Pendiente";
                if (tsPago != null) {
                    fechaPagoFormateada = formateador.format(tsPago);
                }

                modelo.addRow(new Object[]{
                        rs.getInt("id_multa"),
                        fechaPrestamoFormateada, // Ahora se muestra limpio (ej: 14/07/2026 00:00)
                        rs.getString("estudiante"),
                        rs.getString("titulo"),
                        rs.getDouble("monto"),
                        rs.getString("estado"),
                        fechaPagoFormateada      // Ahora se muestra limpio (ej: 14/07/2026 21:05)
                });
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