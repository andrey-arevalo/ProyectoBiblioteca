package vista;

import conexion.ConexionBD;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ActualizarLibroFrame extends JFrame {

    private static final Color MOCKUP_BROWN = new Color(134, 58, 22); // Marrón campos
    private static final Color DARK_BROWN = new Color(85, 23, 0);       // Marrón botones/líneas
    private static final Color PANEL_WHITE_OVERLAY = new Color(255, 255, 255, 225); 

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    
    // Almacenará el ID del libro seleccionado para actualizar con precisión quirúrgica
    private int idLibroSeleccionado = -1;

    public ActualizarLibroFrame() {
        setTitle("Actualizar Libro");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        JLabel lblHeader = new JLabel("Actualizar libro", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 36));
        lblHeader.setForeground(DARK_BROWN);
        mainPanel.add(lblHeader);

        // --- FORMULARIO DE EDICIÓN ---
        JLabel lblNombre = new JLabel("Nuevo Título:", SwingConstants.LEFT);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(lblNombre);

        JLabel lblAutor = new JLabel("Nuevo Autor:", SwingConstants.LEFT);
        lblAutor.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(lblAutor);

        JLabel lblCategoria = new JLabel("Nueva Categoría:", SwingConstants.LEFT);
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(lblCategoria);

        RoundedTextField txtTitulo = new RoundedTextField();
        mainPanel.add(txtTitulo);

        RoundedTextField txtAutor = new RoundedTextField();
        mainPanel.add(txtAutor);

        String[] categorias = {"Programacion", "Base de Datos", "Redes", "Matematicas", "Fisica", "Literatura"};
        RoundedComboBox cbCategoria = new RoundedComboBox(categorias);
        mainPanel.add(cbCategoria);

        // Botón Actualizar
        JButton btnActualizar = new JButton("Actualizar") {
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
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 22));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setOpaque(false); btnActualizar.setContentAreaFilled(false);
        btnActualizar.setBorderPainted(false); btnActualizar.setFocusPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(btnActualizar);

        // --- ETIQUETA DE INSTRUCCIÓN ADICIONADA ---
        JLabel lblInstruccion = new JLabel("Elija el libro a actualizar:", SwingConstants.LEFT);
        lblInstruccion.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
        lblInstruccion.setForeground(MOCKUP_BROWN); // Combina con los colores establecidos
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

                int labelX = (int) (panelW * 0.05), labelW = (int) (panelW * 0.15);
                int inputX = labelX + labelW + 15, inputW = (int) (panelW * 0.70), inputH = 45; 

                lblNombre.setBounds(labelX, 185, labelW, inputH);
                txtTitulo.setBounds(inputX, 185, inputW, inputH);

                lblAutor.setBounds(labelX, 240, labelW, inputH);
                txtAutor.setBounds(inputX, 240, inputW, inputH);

                lblCategoria.setBounds(labelX, 295, labelW, inputH);
                cbCategoria.setBounds(inputX, 295, inputW, inputH);

                btnActualizar.setBounds((panelW - 180) / 2, 355, 180, 45);

                // Posicionamiento de la etiqueta de instrucción y reajuste del Scroll de la Tabla
                int instruccionY = 415;
                lblInstruccion.setBounds(labelX, instruccionY, panelW - (labelX * 2), 25);

                int tablaY = instruccionY + 30; // Deja un espacio después del texto informativo
                int tablaH = panelH - tablaY - 25;
                scrollTabla.setBounds(labelX, tablaY, panelW - (labelX * 2), tablaH);
            }
        });

        // Carga automática inicial de la base de datos
        actualizarTabla();

        // --- CONTROLADORES DE EVENTOS POSTGRESQL ---
        btnVolver.addActionListener(e -> dispose());

        // Lógica al seleccionar un registro de la tabla
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaLibros.getSelectedRow();
                if (fila != -1) {
                    idLibroSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
                    txtTitulo.setText((String) modeloTabla.getValueAt(fila, 1));
                    txtAutor.setText((String) modeloTabla.getValueAt(fila, 2));
                    cbCategoria.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
                }
            }
        });

        // Acción: Guardar Modificaciones
        btnActualizar.addActionListener(e -> {
            if (idLibroSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione primero un libro de la tabla inferior.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String categoriaSeleccionada = cbCategoria.getSelectedItem().toString();

            if (titulo.isEmpty() || autor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los campos no pueden quedar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
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
                Connection con = ConexionBD.conectar();
                String sql = "UPDATE libros SET titulo=?, autor=?, id_categoria=? WHERE id_libro=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, titulo);
                ps.setString(2, autor);
                ps.setInt(3, idCategoria);
                ps.setInt(4, idLibroSeleccionado);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    JOptionPane.showMessageDialog(this, "¡Libro modificado exitosamente!");
                    txtTitulo.setText("");
                    txtAutor.setText("");
                    cbCategoria.setSelectedIndex(0);
                    idLibroSeleccionado = -1; // Resetear selección
                    
                    actualizarTabla(); // Refrescar tabla automáticamente
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el registro en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    // --- MÉTODO CONECTADO A TU POSTGRESQL PARA TRAER LOS DATOS EN TIEMPO REAL ---
    private void actualizarTabla() {
        try {
            modeloTabla.setRowCount(0);
            Connection con = ConexionBD.conectar();
            String sql = "SELECT * FROM libros ORDER BY id_libro ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idCat = rs.getInt("id_categoria");
                String catText = "Programacion";
                switch (idCat) {
                    case 1: catText = "Programacion"; break;
                    case 2: catText = "Base de Datos"; break;
                    case 3: catText = "Redes"; break;
                    case 4: catText = "Matematicas"; break;
                    case 5: catText = "Fisica"; break;
                    case 6: catText = "Literatura"; break;
                }

                String estado = rs.getBoolean("disponible") ? "Disponible" : "Prestado";
                Object[] fila = {
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    catText,
                    estado
                };
                modeloTabla.addRow(fila);
            }
            con.close();
        } catch (Exception e) {
            System.err.println("Error al sincronizar datos de PostgreSQL: " + e.getMessage());
        }
    }

    private ImageIcon cargarIcono(String ruta) {
        URL url = getClass().getResource(ruta);
        return (url != null) ? new ImageIcon(url) : null;
    }

    // --- ESTILOS DE INTERFAZ REDONDEADA ---
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
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    class RoundedComboBox extends JComboBox<String> {
        public RoundedComboBox(String[] items) {
            super(items); setOpaque(false); setForeground(Color.WHITE); setBackground(MOCKUP_BROWN);
            setFont(new Font("Arial", Font.BOLD, 18)); setFocusable(false);
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
            super.paintComponent(g2);
            g2.dispose();
        }
    }
}