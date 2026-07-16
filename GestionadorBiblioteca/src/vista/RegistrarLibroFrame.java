package vista;

import dao.LibroDAO;
import modelo.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

public class RegistrarLibroFrame extends JFrame {

    private static final Color MOCKUP_BROWN = new Color(134, 58, 22); 
    private static final Color DARK_BROWN = new Color(85, 23, 0);       
    private static final Color PANEL_WHITE_OVERLAY = new Color(255, 255, 255, 225); 

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter; // Sorter para ordenar columnas
    private JScrollPane scrollTabla;
    private LibroDAO libroDAO; 

    public RegistrarLibroFrame() {
        setTitle("Registrar Libro");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
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

        JLabel lblHeader = new JLabel("Registrar libro", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 36));
        lblHeader.setForeground(DARK_BROWN);
        mainPanel.add(lblHeader);

        // --- FORMULARIO ---
        JLabel lblNombre = new JLabel("Nombre:", SwingConstants.LEFT);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(lblNombre);

        JLabel lblAutor = new JLabel("Autor:", SwingConstants.LEFT);
        lblAutor.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(lblAutor);

        JLabel lblCategoria = new JLabel("Categoría:", SwingConstants.LEFT);
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(lblCategoria);

        RoundedTextField txtTitulo = new RoundedTextField();
        mainPanel.add(txtTitulo);

        RoundedTextField txtAutor = new RoundedTextField();
        mainPanel.add(txtAutor);

        String[] categorias = {"Programacion", "Base de Datos", "Redes", "Matematicas", "Fisica", "Literatura"};
        RoundedComboBox cbCategoria = new RoundedComboBox(categorias);
        mainPanel.add(cbCategoria);

        JButton btnGuardar = new JButton("Registrar") {
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
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 22));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setOpaque(false); btnGuardar.setContentAreaFilled(false);
        btnGuardar.setBorderPainted(false); btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(btnGuardar);

        // --- CONFIGURACIÓN DE LA TABLA CON FILTRADO/ORDENACIÓN ---
        String[] columnas = {"ID", "Título del Libro", "Autor", "Categoría", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Forzar que la columna 0 (ID) se ordene como entero y no alfabéticamente
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };
        
        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaLibros.setRowHeight(32);
        tablaLibros.setGridColor(new Color(220, 220, 220));
        tablaLibros.setSelectionBackground(MOCKUP_BROWN);
        tablaLibros.setSelectionForeground(Color.WHITE);

        // Habilitar el Sorter dinámico en la JTable
        sorter = new TableRowSorter<>(modeloTabla);
        tablaLibros.setRowSorter(sorter);

        JTableHeader header = tablaLibros.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(DARK_BROWN);
        header.setForeground(Color.WHITE);

        scrollTabla = new JScrollPane(tablaLibros);
        scrollTabla.setBorder(BorderFactory.createLineBorder(DARK_BROWN, 1));
        mainPanel.add(scrollTabla);

        // --- COMPONENTE DE FONDO ---
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgIcon != null) g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.add(backgroundPanel);

        // --- DISPOSICIÓN DINÁMICA DE ELEMENTOS ---
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

                int labelX = (int) (panelW * 0.05), labelW = (int) (panelW * 0.12);
                int inputX = labelX + labelW + 15, inputW = (int) (panelW * 0.73), inputH = 45; 

                lblNombre.setBounds(labelX, 185, labelW, inputH);
                txtTitulo.setBounds(inputX, 185, inputW, inputH);

                lblAutor.setBounds(labelX, 240, labelW, inputH);
                txtAutor.setBounds(inputX, 240, inputW, inputH);

                lblCategoria.setBounds(labelX, 295, labelW, inputH);
                cbCategoria.setBounds(inputX, 295, inputW, inputH);

                btnGuardar.setBounds((panelW - 180) / 2, 355, 180, 45);

                int tablaY = 415;
                int tablaH = panelH - tablaY - 25;
                scrollTabla.setBounds(labelX, tablaY, panelW - (labelX * 2), tablaH);
            }
        });

        actualizarTabla();

        // --- CONTROLADORES DE EVENTOS ---
        btnVolver.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String categoriaSeleccionada = cbCategoria.getSelectedItem().toString();

            if(titulo.isEmpty() || autor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor completa todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idCategoria = 1;
            switch (categoriaSeleccionada) {
                case "Programacion": idCategoria = 1; break;
                case "Base de Datos": idCategoria = 2; break;
                case "Redes": idCategoria = 3; break;
                case "Matematicas": idCategoria = 4; break;
                case "Fisica": idCategoria = 5; break;
                case "Literatura": idCategoria = 6; break;
            }

            try {
                Libro libro = new Libro(0, titulo, autor, idCategoria);
                libroDAO.guardarLibro(libro);

                JOptionPane.showMessageDialog(this, "Libro registrado exitosamente.");
                
                txtTitulo.setText("");
                txtAutor.setText("");
                cbCategoria.setSelectedIndex(0);

                actualizarTabla();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Doble clic en una fila de la tabla para eliminar el libro
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int filaSeleccionada = tablaLibros.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        // Importante: usar convertRowIndexToModel para evitar errores al borrar cuando la tabla está ordenada
                        int filaModelo = tablaLibros.convertRowIndexToModel(filaSeleccionada);
                        
                        int idLibro = (int) modeloTabla.getValueAt(filaModelo, 0);
                        String tituloLibro = (String) modeloTabla.getValueAt(filaModelo, 1);

                        int respuesta = JOptionPane.showConfirmDialog(
                                RegistrarLibroFrame.this,
                                "¿Estás seguro de que deseas eliminar el libro \"" + tituloLibro + "\"?\nEsta acción es irreversible en PostgreSQL.",
                                "Confirmar eliminación",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );

                        if (respuesta == JOptionPane.YES_OPTION) {
                            libroDAO.eliminarLibro(idLibro);
                            actualizarTabla();
                        }
                    }
                }
            }
        });

        setVisible(true);
    }

    private void actualizarTabla() {
        try {
            modeloTabla.setRowCount(0); 
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
            // Centrar columnas ID y Estado
            DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
            centro.setHorizontalAlignment(JLabel.CENTER);
            tablaLibros.getColumnModel().getColumn(0).setCellRenderer(centro);
            tablaLibros.getColumnModel().getColumn(4).setCellRenderer(centro);
            
        } catch (Exception e) {
            System.err.println("Error cargando la vista de la tabla: " + e.getMessage());
        }
    }

    private ImageIcon cargarIcono(String ruta) {
        URL url = getClass().getResource(ruta);
        return (url != null) ? new ImageIcon(url) : null;
    }

    // --- DISEÑO DE LOS INPUTS CORREGIDOS ---
    class RoundedTextField extends JTextField {
        public RoundedTextField() {
            setOpaque(false); 
            setForeground(Color.WHITE); 
            setBackground(MOCKUP_BROWN);
            setFont(new Font("Arial", Font.BOLD, 18));
            setBorder(new EmptyBorder(0, 20, 0, 20)); 
            setCaretColor(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
            
            // Forzar que el texto mantenga el color correcto al redibujarse
            g2.setColor(getForeground());
            super.paintComponent(g); // Cambiado a super.paintComponent(g) estándar para que Swing gestione el texto correctamente sin cortarlo
            g2.dispose();
        }
    }

    class RoundedComboBox extends JComboBox<String> {
        public RoundedComboBox(String[] items) {
            super(items); 
            setOpaque(false); 
            setForeground(Color.WHITE); 
            setBackground(MOCKUP_BROWN);
            setFont(new Font("Arial", Font.BOLD, 18)); 
            setFocusable(false);
            
            // Cambiar la vista del renderizador de la lista desplegable para que se lea en blanco y negro estándar
            setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (isSelected) {
                        label.setBackground(DARK_BROWN);
                        label.setForeground(Color.WHITE);
                    } else {
                        label.setBackground(Color.WHITE);
                        label.setForeground(Color.BLACK);
                    }
                    label.setBorder(new EmptyBorder(5, 10, 5, 10));
                    return label;
                }
            });

            setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
                @Override
                protected JButton createArrowButton() {
                    JButton button = new JButton() {
                        @Override
                        public void paint(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2.setColor(Color.WHITE);
                            int[] xPoints = {getWidth() / 2 - 6, getWidth() / 2, getWidth() / 2 + 6};
                            int[] yPoints = {getHeight() / 2 + 3, getHeight() / 2 - 4, getHeight() / 2 + 3};
                            g2.fillPolygon(xPoints, yPoints, 3);
                            g2.dispose();
                        }
                    };
                    button.setBorder(BorderFactory.createEmptyBorder());
                    button.setContentAreaFilled(false);
                    return button;
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
            super.paintComponent(g); // Cambiado a super.paintComponent(g) estándar
            g2.dispose();
        }
    }
}