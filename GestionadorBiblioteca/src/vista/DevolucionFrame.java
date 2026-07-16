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
import java.text.SimpleDateFormat;

public class DevolucionFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Color MARRON = new Color(92, 43, 5);
    private final Color MARRON_CLARO = new Color(188, 102, 45);

    private JTextField txtNombre;
    private JTextField txtLibro;
    private JTable tabla;
    private JScrollPane scroll;

    private RoundedButton btnDevolver;
    private RoundedButton btnVolver;
    
    // Referencia de la ventana principal para evitar duplicidad de interfaces
    private JFrame ventanaPadre;

    // Modificado el constructor para recibir el JFrame padre
    public DevolucionFrame(JFrame padre) {
        this.ventanaPadre = padre;

        setTitle("Registrar Devolución");
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
        // ICONO SUPERIOR (devolucion.png)
        //==================================================
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/devolucion.png"));
            Image img = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            JLabel lblIcono = new JLabel(new ImageIcon(img));
            lblIcono.setBounds(545, 15, 110, 110);
            panelPrincipal.add(lblIcono);
        } catch (Exception e) {
            JLabel lbl = new JLabel("📦");
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
            lbl.setBounds(550, 20, 100, 100);
            panelPrincipal.add(lbl);
        }

        JPanel linea = new JPanel();
        linea.setBackground(MARRON);
        linea.setBounds(30, 135, 1140, 4);
        panelPrincipal.add(linea);

        //==================================================
        // TÍTULO Y DESCRIPCIÓN
        //==================================================
        JLabel lblTitulo = new JLabel("Registrar devolución");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 38));
        lblTitulo.setForeground(MARRON);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 155, 1200, 45);
        panelPrincipal.add(lblTitulo);

        JLabel lblDescripcion = new JLabel("Escribir el nombre del estudiante y del libro que debe devolver.");
        lblDescripcion.setFont(new Font("Arial", Font.BOLD, 18));
        lblDescripcion.setForeground(Color.BLACK);
        lblDescripcion.setBounds(85, 220, 700, 30);
        panelPrincipal.add(lblDescripcion);

        //==================================================
        // CAMPOS DE TEXTO
        //==================================================
        JLabel lblEstudiante = new JLabel("Nombre del estudiante:");
        lblEstudiante.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstudiante.setForeground(Color.BLACK);
        lblEstudiante.setBounds(85, 275, 250, 30);
        panelPrincipal.add(lblEstudiante);

        txtNombre = new RoundedTextField();
        txtNombre.setBounds(300, 270, 815, 42);
        txtNombre.setBackground(MARRON_CLARO);
        txtNombre.setForeground(Color.WHITE);
        txtNombre.setCaretColor(Color.WHITE);
        txtNombre.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        txtNombre.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(txtNombre);

        JLabel lblLibro = new JLabel("Libro devuelto:");
        lblLibro.setFont(new Font("Arial", Font.BOLD, 18));
        lblLibro.setForeground(Color.BLACK);
        lblLibro.setBounds(85, 325, 200, 30);
        panelPrincipal.add(lblLibro);

        txtLibro = new RoundedTextField();
        txtLibro.setBounds(235, 320, 880, 42);
        txtLibro.setBackground(MARRON_CLARO);
        txtLibro.setForeground(Color.WHITE);
        txtLibro.setCaretColor(Color.WHITE);
        txtLibro.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        txtLibro.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(txtLibro);

        //==================================================
        // BOTÓN REGISTRAR DEVOLUCIÓN
        //==================================================
        btnDevolver = new RoundedButton("Registrar devolución");
        btnDevolver.setBounds(490, 385, 220, 42);
        btnDevolver.setBackground(new Color(245, 155, 95));
        btnDevolver.setForeground(Color.BLACK);
        btnDevolver.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(btnDevolver);

        //==================================================
        // CONFIGURACIÓN DE LA TABLA
        //==================================================
        String columnas[] = {"ID Libro", "Estudiante", "Libro devuelto", "Fecha de préstamos"};
        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][]{}, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(245, 237, 225));
        header.setForeground(Color.BLACK);

        scroll = new JScrollPane(tabla);
        scroll.setBounds(85, 450, 1030, 180);
        panelPrincipal.add(scroll);

        //==================================================
        // EVENTOS Y LÓGICA DE RETORNO CONTROLADO
        //==================================================
        btnVolver.addActionListener(e -> {
            dispose(); // Destruye el formulario de devoluciones
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true); // Reaparece el menú original limpio
            }
        });

        // Al presionar Enter en Estudiante saltará a Libro
        txtNombre.addActionListener(e -> txtLibro.requestFocusInWindow());

        // Al presionar Enter en Libro registrará de inmediato la devolución
        txtLibro.addActionListener(e -> btnDevolver.doClick());

        btnDevolver.addActionListener(e -> {
            String estudiante = txtNombre.getText().trim();
            String libro = txtLibro.getText().trim();

            if (estudiante.isEmpty() || libro.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos.");
                return;
            }

            try {
                Connection con = ConexionBD.conectar();

                // Buscar estudiante
                String buscarEstudiante = "SELECT id_estudiante FROM estudiantes WHERE LOWER(nombre)=LOWER(?)";
                PreparedStatement psBuscarEstudiante = con.prepareStatement(buscarEstudiante);
                psBuscarEstudiante.setString(1, estudiante);
                ResultSet rsEstudiante = psBuscarEstudiante.executeQuery();
                
                if (!rsEstudiante.next()) {
                    JOptionPane.showMessageDialog(null, "Estudiante no encontrado");
                    return;
                }
                int idEstudiante = rsEstudiante.getInt("id_estudiante");

                // Buscar libro
                String buscarLibro = "SELECT id_libro FROM libros WHERE LOWER(titulo)=LOWER(?)";
                PreparedStatement psBuscarLibro = con.prepareStatement(buscarLibro);
                psBuscarLibro.setString(1, libro);
                ResultSet rsLibro = psBuscarLibro.executeQuery();

                if (!rsLibro.next()) {
                    JOptionPane.showMessageDialog(null, "Libro no encontrado");
                    return;
                }
                int idLibro = rsLibro.getInt("id_libro");

                // Validar si existe préstamo ACTIVO para ese estudiante y libro
                String validar = "SELECT id_prestamo FROM prestamos WHERE id_estudiante=? AND id_libro=? AND estado='ACTIVO'";
                PreparedStatement psValidar = con.prepareStatement(validar);
                psValidar.setInt(1, idEstudiante);
                psValidar.setInt(2, idLibro);
                ResultSet rsPrestamo = psValidar.executeQuery();

                if (!rsPrestamo.next()) {
                    JOptionPane.showMessageDialog(null, "Ese estudiante no tiene ese libro en préstamo activo");
                    return;
                }
                int idPrestamo = rsPrestamo.getInt("id_prestamo");

                // Actualizar estado del préstamo a 'Devuelto'
                String actualizarPrestamo = "UPDATE prestamos SET estado='Devuelto' WHERE id_prestamo=?";
                PreparedStatement psActualizarPrestamo = con.prepareStatement(actualizarPrestamo);
                psActualizarPrestamo.setInt(1, idPrestamo);
                psActualizarPrestamo.executeUpdate();

                // Cambiar disponibilidad del libro a true
                String update = "UPDATE libros SET disponible=true WHERE id_libro=?";
                PreparedStatement psUpdate = con.prepareStatement(update);
                psUpdate.setInt(1, idLibro);
                psUpdate.executeUpdate();

                JOptionPane.showMessageDialog(null, "Devolución registrada correctamente.");
                txtNombre.setText("");
                txtLibro.setText("");

                cargarTabla();
                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar la devolución.");
            }
        });

        cargarTabla();
        setVisible(true);
    }

    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        try {
            Connection con = ConexionBD.conectar();
            String sql = "SELECT l.id_libro, e.nombre AS estudiante, l.titulo AS libro, p.fecha_prestamo " +
                         "FROM prestamos p " +
                         "INNER JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                         "INNER JOIN libros l ON p.id_libro = l.id_libro " +
                         "WHERE p.estado = 'Devuelto' " +
                         "ORDER BY p.id_prestamo DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_libro"),
                        rs.getString("estudiante"),
                        rs.getString("libro"),
                        rs.getTimestamp("fecha_prestamo") // Obtenemos el Timestamp para conservar horas y minutos
                });
            }
            formatearColumnas();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void formatearColumnas() {
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(JLabel.CENTER);

        // Renderizador con el formato de fecha solicitado
        DefaultTableCellRenderer renderizadorFecha = new DefaultTableCellRenderer() {
            private final SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            @Override
            protected void setValue(Object value) {
                if (value instanceof java.util.Date) {
                    setText(formateador.format((java.util.Date) value));
                } else if (value != null) {
                    setText(value.toString());
                } else {
                    setText("-");
                }
            }
        };
        renderizadorFecha.setHorizontalAlignment(JLabel.CENTER);

        if (tabla.getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setCellRenderer(centro); // Centrar ID Libro
            tabla.getColumnModel().getColumn(3).setCellRenderer(renderizadorFecha); // Formatear y centrar Fecha de préstamo
        }
    }

    //==================================================
    // CLASE INTERNA PARA EL CAMPO REDONDEADO
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