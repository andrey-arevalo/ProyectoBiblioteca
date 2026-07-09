package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;
import modelo.Prestamo;
import conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import javax.swing.*;
import java.awt.*;

public class RegistrarPrestamosFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Color MARRON = new Color(92, 43, 5);
    private final Color MARRON_CLARO = new Color(188, 102, 45);

    private JTextField txtEstudiante;
    private JTextField txtLibro;

    private JTable tabla;
    private JScrollPane scroll;

    private RoundedButton btnRegistrar;
    private RoundedButton btnVolver;
    
    // Almacena la referencia de PrestamoFrame
    private JFrame ventanaPadre;

    // Modificado el constructor para recibir el JFrame padre
    public RegistrarPrestamosFrame(JFrame padre) {
        this.ventanaPadre = padre;

        setTitle("Registrar Préstamos");
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
        panelPrincipal.setPreferredSize(new Dimension(1080, 620));
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
            btnVolver.setText("<- VOLVER");
        }
        panelPrincipal.add(btnVolver);

        //==================================================
        // ICONO SUPERIOR
        //==================================================
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/prestamo.png"));
            Image img = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            JLabel lblIcono = new JLabel(new ImageIcon(img));
            lblIcono.setBounds(485, 15, 110, 110);
            panelPrincipal.add(lblIcono);
        } catch (Exception e) {
            JLabel lbl = new JLabel("📋");
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
            lbl.setBounds(500, 20, 100, 100);
            panelPrincipal.add(lbl);
        }

        JPanel linea = new JPanel();
        linea.setBackground(MARRON);
        linea.setBounds(10, 135, 1060, 5);
        panelPrincipal.add(linea);

        //==================================================
        // TÍTULO Y DESCRIPCIÓN
        //==================================================
        JLabel lblTitulo = new JLabel("Registrar Préstamos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 38));
        lblTitulo.setForeground(MARRON);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 155, 1080, 45);
        panelPrincipal.add(lblTitulo);

        JLabel lblDescripcion = new JLabel("Escribir el nombre del estudiante y del libro que tomó prestado.");
        lblDescripcion.setFont(new Font("Arial", Font.BOLD, 18));
        lblDescripcion.setBounds(55, 220, 700, 30);
        panelPrincipal.add(lblDescripcion);

        //==================================================
        // CAMPOS DE TEXTO (ESTUDIANTE Y LIBRO)
        //==================================================
        JLabel lblEstudiante = new JLabel("Nombre del estudiante:");
        lblEstudiante.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstudiante.setBounds(55, 275, 250, 30);
        panelPrincipal.add(lblEstudiante);

        txtEstudiante = new RoundedTextField();
        txtEstudiante.setBounds(270, 270, 780, 42);
        txtEstudiante.setBackground(MARRON_CLARO);
        txtEstudiante.setForeground(Color.WHITE);
        txtEstudiante.setCaretColor(Color.WHITE);
        txtEstudiante.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        txtEstudiante.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(txtEstudiante);

        JLabel lblLibro = new JLabel("Libro prestado:");
        lblLibro.setFont(new Font("Arial", Font.BOLD, 18));
        lblLibro.setBounds(55, 325, 200, 30);
        panelPrincipal.add(lblLibro);

        txtLibro = new RoundedTextField();
        txtLibro.setBounds(270, 320, 780, 42);
        txtLibro.setBackground(MARRON_CLARO);
        txtLibro.setForeground(Color.WHITE);
        txtLibro.setCaretColor(Color.WHITE);
        txtLibro.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        txtLibro.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(txtLibro);

        //==================================================
        // BOTÓN REGISTRAR
        //==================================================
        btnRegistrar = new RoundedButton("Registrar préstamo");
        btnRegistrar.setBounds(430, 390, 220, 42);
        btnRegistrar.setBackground(new Color(245, 155, 95));
        btnRegistrar.setForeground(Color.BLACK);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(btnRegistrar);

        //==================================================
        // CONFIGURACIÓN DE LA TABLA
        //==================================================
        String columnas[] = {"ID Préstamo", "Estudiante", "Libro", "Estado", "Fecha de préstamo"};
        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][]{}, columnas));
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tabla.getTableHeader().setBackground(Color.WHITE);
        tabla.getTableHeader().setForeground(MARRON);
        tabla.setSelectionBackground(new Color(220, 190, 160));

        scroll = new JScrollPane(tabla);
        scroll.setBounds(55, 455, 995, 120);
        panelPrincipal.add(scroll);

        //==================================================
        // EVENTOS (Modificado para usar ventanaPadre)
        //==================================================
        btnVolver.addActionListener(e -> {
            dispose(); // Destruye esta ventana de registro
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true); // Vuelve a mostrar PrestamoFrame original
            }
        });
        
        txtEstudiante.addActionListener(e -> txtLibro.requestFocusInWindow());
        txtEstudiante.addActionListener(e -> btnRegistrar.doClick());
        txtLibro.addActionListener(e -> btnRegistrar.doClick());
        
        btnRegistrar.addActionListener(e -> {
            String estudiante = txtEstudiante.getText().trim();
            String libro = txtLibro.getText().trim();

            if (estudiante.isEmpty() || libro.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos.");
                return;
            }

            try {
                Connection con = ConexionBD.conectar();

                // Buscar estudiante
                String sqlEstudiante = "SELECT id_estudiante FROM estudiantes WHERE LOWER(nombre)=LOWER(?)";
                PreparedStatement psEst = con.prepareStatement(sqlEstudiante);
                psEst.setString(1, estudiante);
                ResultSet rsEst = psEst.executeQuery();

                if (!rsEst.next()) {
                    JOptionPane.showMessageDialog(null, "El estudiante no existe.");
                    return;
                }
                int idEstudiante = rsEst.getInt("id_estudiante");

                // Buscar libro
                String sqlLibro = "SELECT id_libro, disponible FROM libros WHERE LOWER(titulo)=LOWER(?)";
                PreparedStatement psLibro = con.prepareStatement(sqlLibro);
                psLibro.setString(1, libro);
                ResultSet rsLibro = psLibro.executeQuery();

                if (!rsLibro.next()) {
                    JOptionPane.showMessageDialog(null, "El libro no existe.");
                    return;
                }
                int idLibro = rsLibro.getInt("id_libro");
                boolean disponible = rsLibro.getBoolean("disponible");

                if (!disponible) {
                    JOptionPane.showMessageDialog(null, "El libro ya está prestado.");
                    return;
                }

                // Registrar préstamo
                String sqlPrestamo = "INSERT INTO prestamos(id_estudiante,id_libro,fecha_prestamo,estado) VALUES(?,?,?,?)";
                PreparedStatement psPrestamo = con.prepareStatement(sqlPrestamo);
                psPrestamo.setInt(1, idEstudiante);
                psPrestamo.setInt(2, idLibro);
                psPrestamo.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                psPrestamo.setString(4, "ACTIVO");
                psPrestamo.executeUpdate();
                
                // Cambiar disponibilidad del libro
                String sqlActualizar = "UPDATE libros SET disponible=false WHERE id_libro=?";
                PreparedStatement psActualizar = con.prepareStatement(sqlActualizar);
                psActualizar.setInt(1, idLibro);
                psActualizar.executeUpdate();

                JOptionPane.showMessageDialog(null, "Préstamo registrado correctamente.");
                txtEstudiante.setText("");
                txtLibro.setText("");

                cargarTabla();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar préstamo.");
            }
        });

        // Cargar los registros en la tabla automáticamente al abrir la ventana
        cargarTabla();
        setVisible(true);
    }

    //==================================================
    // MÉTODOS DE SOPORTE
    //==================================================
    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        try {
            Connection con = ConexionBD.conectar();
            String sql = "SELECT p.id_prestamo, " +
                    "e.nombre AS estudiante, " +
                    "l.titulo AS libro, " +
                    "p.estado, " +
                    "p.fecha_prestamo " +
                    "FROM prestamos p " +
                    "INNER JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                    "INNER JOIN libros l ON p.id_libro = l.id_libro " +
                    "ORDER BY p.id_prestamo ASC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_prestamo"),
                        rs.getString("estudiante"),
                        rs.getString("libro"),
                        rs.getString("estado"),
                        rs.getDate("fecha_prestamo")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los préstamos.");
        }
    }

    //==================================================
    // CLASE INTERNA
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