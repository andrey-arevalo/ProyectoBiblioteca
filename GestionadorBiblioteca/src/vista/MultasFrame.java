package vista;

import conexion.ConexionBD;
import dao.MultaDAO;
import modelo.Multa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MultasFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtPrestamo;
    private JTextField txtMonto;
    private JTextField txtMotivo;

    public MultasFrame() {

        setTitle("Gestionar Multas");
        setSize(1000,550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =========================
        // PANEL SUPERIOR
        // =========================

        JPanel panelSuperior =
                new JPanel(new GridLayout(2,4,10,10));

        txtPrestamo = new JTextField();

        txtMonto = new JTextField();

        txtMotivo = new JTextField();

        JButton btnRegistrar =
                new JButton("Registrar Multa");

        panelSuperior.add(new JLabel("ID Préstamo"));
        panelSuperior.add(txtPrestamo);

        panelSuperior.add(new JLabel("Monto"));
        panelSuperior.add(txtMonto);

        panelSuperior.add(new JLabel("Motivo"));
        panelSuperior.add(txtMotivo);

        panelSuperior.add(new JLabel(""));
        panelSuperior.add(btnRegistrar);

        add(panelSuperior,BorderLayout.NORTH);

        // =========================
        // TABLA
        // =========================

        String[] columnas = {

                "ID Multa",
                "ID Préstamo",
                "Estudiante",
                "Libro",
                "Monto",
                "Estado",
                "Fecha Pago"
        };

        modelo =
                new DefaultTableModel(columnas,0);

        tabla =
                new JTable(modelo);

        add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        cargarMultas();

        // =========================
        // REGISTRAR MULTA
        // =========================

        btnRegistrar.addActionListener(e -> {

            try {
                int idPrestamo = Integer.parseInt( txtPrestamo.getText());

                double monto =
                        Double.parseDouble(
                                txtMonto.getText()
                        );

                String motivo =
                        txtMotivo.getText();

                Multa multa =
                        new Multa(
                                idPrestamo,
                                monto,
                                motivo,
                                "Pendiente"
                        );

                MultaDAO dao =
                        new MultaDAO();

                dao.guardarMulta(multa);

                JOptionPane.showMessageDialog(
                        null,
                        "Multa registrada correctamente"
                );

                txtPrestamo.setText("");
                txtMonto.setText("");
                txtMotivo.setText("");

                modelo.setRowCount(0);

                cargarMultas();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Datos inválidos");
                ex.printStackTrace();
            }
        });
        setVisible(true);
    }

    // =========================
    // CARGAR TABLA
    // =========================

    private void cargarMultas() {

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
                    "ORDER BY m.id_multa";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al cargar multas");
        }
    }
}