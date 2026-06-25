package vista;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MultasPendientesFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public MultasPendientesFrame(int idEstudiante) {

        setTitle("Mis Multas");
        setSize(900,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnas = {
                "ID Multa",
                "ID Prestamo",
                "Estudiante",
                "Libro",
                "Monto",
                "Estado",
                "Fecha Pago"
        };
        
        modelo = new DefaultTableModel(columnas,0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla),BorderLayout.CENTER);
        JButton btnPagar = new JButton("Pagar Multa");

        add(btnPagar,BorderLayout.SOUTH);

        cargarMultas(idEstudiante);

        // =========================
        // PAGAR MULTA
        // =========================

        btnPagar.addActionListener(e -> {
            int fila =tabla.getSelectedRow();
            
            if (fila == -1) {
                JOptionPane.showMessageDialog(null,"Seleccione una multa");
                return;
            }

            String estado = tabla.getValueAt(fila,5).toString();

            if (estado.equalsIgnoreCase("Pagada")) {
                JOptionPane.showMessageDialog(null,"La multa ya fue pagada");
                return;
            }

            int idMulta = Integer.parseInt(tabla.getValueAt(fila,0).toString());

            try {
                Connection con = ConexionBD.conectar();
                String sql =
                        "UPDATE multas " +
                        "SET estado='Pagada', " +
                        "fecha_pago=CURRENT_TIMESTAMP " +
                        "WHERE id_multa=?";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1,idMulta);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null,"Multa pagada correctamente");
                modelo.setRowCount(0);
                cargarMultas(idEstudiante);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error al pagar multa");
            }
        });
        setVisible(true);
    }

    // =========================
    // CARGAR MULTAS
    // =========================

    private void cargarMultas(int idEstudiante) {

        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "SELECT " +
                    "m.id_multa, " +
                    "p.id_prestamo, " +
                    "e.nombre AS estudiante, " +
                    "l.titulo, " +
                    "m.monto, " +
                    "m.estado, " +
                    "m.fecha_pago " +
                    "FROM multas m " +
                    "JOIN prestamos p " +
                    "ON m.id_prestamo = p.id_prestamo " +
                    "JOIN estudiantes e " +
                    "ON p.id_estudiante = e.id_estudiante " +
                    "JOIN libros l " +
                    "ON p.id_libro = l.id_libro " +
                    "WHERE p.id_estudiante = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idEstudiante);
            ResultSet rs = ps.executeQuery();
            boolean hayMultas = false;
            
            while (rs.next()) {
                hayMultas = true;
                modelo.addRow(
                        new Object[]{
                        		rs.getInt("id_multa"),
                                rs.getInt("id_prestamo"),
                                rs.getString("estudiante"),
                                rs.getString("titulo"),
                                rs.getDouble("monto"),
                                rs.getString("estado"),
                                rs.getTimestamp("fecha_pago")
                        }
                );
            }

            if (!hayMultas) {
                modelo.addRow(
                        new Object[]{
                                "","","No tiene multas registradas","","","",""
                        }
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al cargar multas");
        }
    }
}