package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import conexion.ConexionBD;


public class HistorialFrame extends JFrame {
	
    public HistorialFrame() {

        setTitle("Historial de Prestamos");
        setSize(800,500);
        setLayout(new BorderLayout());;
        JPanel panelSuperior = new JPanel();

        JLabel lblBuscar = new JLabel("Buscar estudiante:");

        JTextField txtBuscar = new JTextField(20);

        JButton btnBuscar = new JButton("Buscar");

        JButton btnLimpiar = new JButton("Limpiar");

        panelSuperior.add(lblBuscar);
        panelSuperior.add(txtBuscar);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnLimpiar);

        add(panelSuperior, BorderLayout.NORTH);
        
        String[] columnas = {
                "ID",
                "Estudiante",
                "Libro",
                "Fecha Préstamo",
                "Estado"
        };
        
        DefaultTableModel modelo = new DefaultTableModel(columnas,0);
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        
//BOTÓN BUSCAR        
        btnBuscar.addActionListener(e -> {modelo.setRowCount(0);
            try {
                Connection con = ConexionBD.conectar();
                String sql =
                        "SELECT p.id_prestamo,e.nombre AS estudiante,l.titulo AS libro,p.fecha_prestamo,p.estado " +
                        "FROM prestamos p " +
                        "JOIN estudiantes e ON p.id_estudiante=e.id_estudiante " +
                        "JOIN libros l ON p.id_libro=l.id_libro " +
                        "WHERE LOWER(e.nombre)=LOWER(?)";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, txtBuscar.getText());
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
                if(!encontrado){JOptionPane.showMessageDialog(null,"Ese estudiante no tiene préstamos.");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
        
//BOTON LIMPIAR
        btnLimpiar.addActionListener(e -> {txtBuscar.setText("");modelo.setRowCount(0);
        txtBuscar.setText("");
        modelo.setRowCount(0);
        txtBuscar.requestFocus();
        
            try {
                Connection con = ConexionBD.conectar();
                String sql =
                        "SELECT p.id_prestamo,e.nombre AS estudiante,l.titulo AS libro,p.fecha_prestamo,p.estado " +
                        "FROM prestamos p " +
                        "JOIN estudiantes e ON p.id_estudiante=e.id_estudiante " +
                        "JOIN libros l ON p.id_libro=l.id_libro " +
                        "ORDER BY p.fecha_prestamo DESC";

                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    modelo.addRow(new Object[]{
                            rs.getInt("id_prestamo"),
                            rs.getString("estudiante"),
                            rs.getString("libro"),
                            rs.getTimestamp("fecha_prestamo"),
                            rs.getString("estado")
                    });
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
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
}