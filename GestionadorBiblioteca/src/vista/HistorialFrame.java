package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;
import componentes.RoundedTextField;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HistorialFrame extends JFrame {

    private RoundedTextField txtBuscar;
    private RoundedButton btnBuscar;
    private RoundedButton btnLimpiar;
    private RoundedButton btnVolver;

    private JTable tabla;
    private DefaultTableModel modelo;

    public HistorialFrame() {

        setTitle("Historial de Préstamos");
        
        // --- MAXIMIZADO RESPECTANDO LA BARRA DE TAREAS ---
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        setContentPane(fondo);
        fondo.setLayout(null); 

        // --- PANEL CENTRAL REDONDEADO (Centrado dinámicamente) ---
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(null);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int panelW = 940; // Ampliado ligeramente para dar más aire a los componentes
        int panelH = 650;
        int panelX = (screenSize.width - panelW) / 2;
        int panelY = (screenSize.height - panelH) / 2;
        panel.setBounds(panelX, panelY, panelW, panelH);
        fondo.add(panel);
       
        // --- TÍTULO PRINCIPAL ---
        JLabel titulo = new JLabel("Historial de préstamos");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(new Color(85, 23, 0)); 
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(100, 145, 740, 45);
        panel.add(titulo);
        
        // --- ICONO CENTRAL DEL CHECKLIST ---
        JLabel icono = new JLabel();
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/lista_prestamos.png"));
            Image imagen = iconoOriginal.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            icono.setIcon(new ImageIcon(imagen));
        } catch (Exception e) {
            try {
                ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/libro1.png"));
                Image imagen = iconoOriginal.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                icono.setIcon(new ImageIcon(imagen));
            } catch(Exception ex) {}
        }
        icono.setBounds(425, 25, 90, 90);
        panel.add(icono);
        
        // --- LÍNEA DIVISORIA ESTILIZADA ---
        JPanel pnlLine = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(85, 23, 0));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
            }
        };
        pnlLine.setBounds(30, 130, 880, 4);
        panel.add(pnlLine);
        
        // --- ELEMENTOS DE BÚSQUEDA ---
        JLabel lblBuscar = new JLabel("Buscar estudiante:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 20));
        lblBuscar.setBounds(50, 215, 190, 35);
        panel.add(lblBuscar);
        
        txtBuscar = new RoundedTextField();
        txtBuscar.setBounds(245, 215, 500, 40);
        panel.add(txtBuscar);
        
        // Aumentado el ancho a 125 para evitar que "Buscar" y "Limpiar" se corten
        btnBuscar = new RoundedButton("Buscar");
        btnBuscar.setBounds(765, 200, 125, 35);
        panel.add(btnBuscar);
        
        btnLimpiar = new RoundedButton("Limpiar");
        btnLimpiar.setBounds(765, 245, 125, 35);
        panel.add(btnLimpiar);
        
        // --- CONFIGURACIÓN DE LA TABLA ---
        String columnas[] = {
                "ID Préstamo",
                "Estudiante",
                "Libro",
                "Fecha Préstamo",
                "Estado"
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

        // Asignación de anchos preferidos para que las palabras de la tabla queden holgadas
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollTablaConfig();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        scroll.setBounds(50, 305, 840, 290);
        panel.add(scroll);
        
        // --- BOTÓN VOLVER CON ANCHO CORREGIDO (150px) ---
        btnVolver = new RoundedButton("Volver");
        btnVolver.setBounds(30, 25, 150, 40); // Más ancho para albergar el icono + texto holgadamente
        
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

        // ===================================
        // LÓGICA DEL BOTÓN BUSCAR
        // ===================================
        btnBuscar.addActionListener(e -> {
            modelo.setRowCount(0); 
            String nombre = txtBuscar.getText().trim();

            if(nombre.isEmpty()){
                 JOptionPane.showMessageDialog(null, "Ingrese el nombre del estudiante.");
                 return;
            }
            try{
                Connection con = ConexionBD.conectar();
                String sql =
                        "SELECT p.id_prestamo, e.nombre AS estudiante, l.titulo AS libro, p.fecha_prestamo, p.estado " +
                        "FROM prestamos p " +
                        "JOIN estudiantes e ON p.id_estudiante=e.id_estudiante " +
                        "JOIN libros l ON p.id_libro=l.id_libro " +
                        "WHERE LOWER(e.nombre) LIKE LOWER(?) " +
                        "ORDER BY p.id_prestamo ASC"; 

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, "%" + nombre + "%");
                ResultSet rs = ps.executeQuery();
                boolean encontrado = false;

                while(rs.next()){
                    encontrado = true;
                    modelo.addRow(new Object[]{
                            rs.getInt("id_prestamo"),
                            rs.getString("estudiante"),
                            rs.getString("libro"),
                            rs.getTimestamp("fecha_prestamo"),
                            rs.getString("estado")
                    });
                }

                formatearYCentrarColumnas();

                if(!encontrado){
                    JOptionPane.showMessageDialog(null, "El estudiante no tiene préstamos registrados.");
                }
                con.close();
            }catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al buscar préstamos.");
            }
        });

        // BOTÓN LIMPIAR: Vacía por completo la tabla
        btnLimpiar.addActionListener(e -> {
            txtBuscar.setText("");
            modelo.setRowCount(0); 
            txtBuscar.requestFocus();
        });

        btnVolver.addActionListener(e -> {
            dispose();
            new AdminFrame();
        });

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnBuscar.doClick();
                }
            }
        });

        setVisible(true);
    }

    private void scrollTablaConfig() {
        if (tabla.getColumnCount() >= 5) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(100); // ID Préstamo
            tabla.getColumnModel().getColumn(1).setPreferredWidth(140); // Estudiante
            tabla.getColumnModel().getColumn(2).setPreferredWidth(280); // Libro (más espacio para evitar cortes)
            tabla.getColumnModel().getColumn(3).setPreferredWidth(180); // Fecha Préstamo
            tabla.getColumnModel().getColumn(4).setPreferredWidth(110); // Estado
        }
    }

    private void formatearYCentrarColumnas() {
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(JLabel.CENTER);
        if (tabla.getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setCellRenderer(centro); 
            tabla.getColumnModel().getColumn(4).setCellRenderer(centro); 
        }
    }
}