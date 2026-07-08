package vista;

import dao.LibroDAO;
import modelo.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

public class EliminarLibroFrame extends JFrame {

    private static final Color MOCKUP_BROWN = new Color(134, 58, 22); // Marrón campos
    private static final Color DARK_BROWN = new Color(85, 23, 0);       // Marrón botones/líneas
    private static final Color PANEL_WHITE_OVERLAY = new Color(255, 255, 255, 225); 

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private LibroDAO libroDAO; // Instancia global de tu DAO

    public EliminarLibroFrame() {
        setTitle("Eliminar Libro");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicializamos tu DAO
        libroDAO = new LibroDAO();

        ImageIcon bgIcon = cargarIcono("/imagenes/bibliotecafondo.jpg");
        ImageIcon libroIcon = cargarIcono("/imagenes/libro1.png");

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

        // --- COMPONENTES SUPERIORES ---
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

        JLabel lblBookIcon = new JLabel("", SwingConstants.CENTER);
        if (libroIcon != null) {
            lblBookIcon.setIcon(new ImageIcon(libroIcon.getImage().getScaledInstance(120, 90, Image.SCALE_SMOOTH)));
        }
        mainPanel.add(lblBookIcon);

        JPanel pnlLine = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(DARK_BROWN);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
            }
        };
        pnlLine.setOpaque(false);
        mainPanel.add(pnlLine);

        JLabel lblHeader = new JLabel("Eliminar libro", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 36));
        lblHeader.setForeground(DARK_BROWN);
        mainPanel.add(lblHeader);

        // --- FORMULARIO DE ELIMINACIÓN ---
        JLabel lblId = new JLabel("ID Libro:", SwingConstants.LEFT);
        lblId.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(lblId);

        RoundedTextField txtId = new RoundedTextField();
        // Deshabilitamos la edición manual para que el ID solo entre de forma segura mediante clic en la tabla
        txtId.setEditable(false); 
        mainPanel.add(txtId);

        // Botón Eliminar
        JButton btnEliminar = new JButton("Eliminar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(DARK_BROWN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 22));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setOpaque(false); btnEliminar.setContentAreaFilled(false);
        btnEliminar.setBorderPainted(false); btnEliminar.setFocusPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(btnEliminar);
        getRootPane().setDefaultButton(btnEliminar);

        // --- ETIQUETA DE INSTRUCCIÓN ---
        JLabel lblInstruccion = new JLabel("Elija el libro a eliminar:", SwingConstants.LEFT);
        lblInstruccion.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
        lblInstruccion.setForeground(MOCKUP_BROWN);
        mainPanel.add(lblInstruccion);

        // --- CONFIGURACIÓN DE LA JTABLE DE CONSULTA ---
        String[] columnas = {"ID", "Título del Libro", "Autor", "Categoría", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaLibros.setRowHeight(32);
        tablaLibros.setGridColor(new Color(220, 220, 220));
        tablaLibros.setSelectionBackground(MOCKUP_BROWN);
        tablaLibros.setSelectionForeground(Color.WHITE);

        JTableHeader header = tablaLibros.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(DARK_BROWN);
        header.setForeground(Color.WHITE);

        scrollTabla = new JScrollPane(tablaLibros);
        scrollTabla.setBorder(BorderFactory.createLineBorder(DARK_BROWN, 1));
        mainPanel.add(scrollTabla);

        // --- BACKGROUND COMPLETO ---
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

                int panelW = (int) (frameW * 0.85), panelH = (int) (frameH * 0.90);
                mainPanel.setBounds((frameW - panelW) / 2, (frameH - panelH) / 2, panelW, panelH);

                btnVolver.setBounds(35, 25, 140, 42);
                lblBookIcon.setBounds((panelW - 120) / 2, 15, 120, 90);
                pnlLine.setBounds((panelW - (int)(panelW * 0.94)) / 2, 115, (int)(panelW * 0.94), 4); 
                lblHeader.setBounds(0, 130, panelW, 40);

                // Aquí como es un solo campo (ID), adaptamos las proporciones para mantener la simetría estilizada
                int labelX = (int) (panelW * 0.05), labelW = (int) (panelW * 0.15);
                int inputX = labelX + labelW + 15, inputW = (int) (panelW * 0.70), inputH = 45; 

                lblId.setBounds(labelX, 220, labelW, inputH);
                txtId.setBounds(inputX, 220, inputW, inputH);

                btnEliminar.setBounds((panelW - 180) / 2, 310, 180, 45);

                int instruccionY = 385;
                lblInstruccion.setBounds(labelX, instruccionY, panelW - (labelX * 2), 25);

                int tablaY = instruccionY + 30; 
                int tablaH = panelH - tablaY - 25;
                scrollTabla.setBounds(labelX, tablaY, panelW - (labelX * 2), tablaH);
            }
        });

        // Carga automática inicial de la base de datos
        actualizarTabla();

        // --- CONTROLADORES DE EVENTOS ---
        btnVolver.addActionListener(e -> dispose());

        // Al hacer clic en un libro de la tabla, su ID se traslada al input
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaLibros.getSelectedRow();
                if (fila != -1) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                }
            }
        });

        // Acción: Eliminar Registro
        btnEliminar.addActionListener(e -> {
            String idTexto = txtId.getText().trim();
            
            if (idTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione primero un libro de la tabla inferior.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idTexto);
            int fila = tablaLibros.getSelectedRow();
            String tituloLibro = (fila != -1) ? (String) modeloTabla.getValueAt(fila, 1) : "";

            // Confirmación de seguridad
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar el libro \"" + tituloLibro + "\"?\nEsta acción eliminará el registro permanentemente de PostgreSQL.",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                try {
                    // Ejecuta tu método nativo de LibroDAO
                    libroDAO.eliminarLibro(id);
                    
                    txtId.setText(""); // Limpiar campo
                    actualizarTabla();  // Refrescar automáticamente la vista con la BD
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    // --- MÉTODO SINCRONIZADO CON TU METODO LISTARLIBROS() ---
    private void actualizarTabla() {
        try {
            modeloTabla.setRowCount(0); // Limpiar registros previos de la tabla gráfica
            
            // Usamos tu método nativo ArrayList<Libro> de LibroDAO
            ArrayList<Libro> lista = libroDAO.listarLibros(); 
            
            if (lista != null) {
            	lista.sort((l1, l2) -> Integer.compare(l1.getId_libro(), l2.getId_libro()));
                for (Libro l : lista) {
                    String catText = "Programacion";
                    switch (l.getIdCategoria()) {
                        case 1: catText = "Programacion"; break;
                        case 2: catText = "Base de Datos"; break;
                        case 3: catText = "Redes"; break;
                        case 4: catText = "Matematicas"; break;
                        case 5: catText = "Fisica"; break;
                        case 6: catText = "Literatura"; break;
                    }
                    
                    String estado = l.isDisponible() ? "Disponible" : "Prestado";
                    
                    Object[] fila = { l.getId_libro(), l.getTitulo(), l.getAutor(), catText, estado };
                    modeloTabla.addRow(fila);
                }
            }
            javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            tablaLibros.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        } catch (Exception e) {
            System.err.println("Error cargando la vista de la tabla: " + e.getMessage());
        }
    }

    private ImageIcon cargarIcono(String ruta) {
        URL url = getClass().getResource(ruta);
        return (url != null) ? new ImageIcon(url) : null;
    }

    // --- ESTILO CAPSULA REDONDEADA ---
    class RoundedTextField extends JTextField {
        public RoundedTextField() {
            setOpaque(false); setForeground(Color.WHITE); setBackground(MOCKUP_BROWN);
            setFont(new Font("Arial", Font.BOLD, 18));
            setBorder(new EmptyBorder(0, 20, 0, 20)); setCaretColor(Color.WHITE);
            setHorizontalAlignment(JTextField.CENTER); // Centramos el ID para mejor estética
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
            super.paintComponent(g2);
            g2.dispose();
        }
    }
}