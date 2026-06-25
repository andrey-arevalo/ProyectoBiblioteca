package vista;

import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrestamoFrame
        extends JFrame {

    public PrestamoFrame() {

        setTitle("Registrar Prestamo");
        setSize(500,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,2,10,10));

        // CAMPOS
        JTextField txtNombre = new JTextField();
        JTextField txtLibro = new JTextField();
        JButton btnRegistrar = new JButton("Registrar");

        // COMPONENTES
        add(new JLabel("Nombre Estudiante"));
        add(txtNombre);
        add(new JLabel("Nombre Libro"));
        add(txtLibro);
        add(new JLabel(""));
        add(btnRegistrar);

        // EVENTO
        btnRegistrar.addActionListener(e -> {
            try {
                Connection con = ConexionBD.conectar();

                // =========================
                // BUSCAR ESTUDIANTE
                // =========================

                String buscarEstudiante =
                        "SELECT id_estudiante "
                        + "FROM estudiantes "
                        + "WHERE LOWER(nombre)=LOWER(?)";
                PreparedStatement psBuscarEstudiante =
                        con.prepareStatement(buscarEstudiante);
                psBuscarEstudiante.setString(1,txtNombre.getText());
                ResultSet rsEstudiante = psBuscarEstudiante.executeQuery();

                // SI NO EXISTE
                if (!rsEstudiante.next()) {
                    JOptionPane.showMessageDialog(null,"Estudiante no encontrado");
                    return;
                }
                int idEstudiante =rsEstudiante.getInt("id_estudiante");

                // =========================
                // BUSCAR LIBRO
                // =========================

                String buscarLibro =
                        "SELECT id_libro, disponible "
                        + "FROM libros "
                        + "WHERE LOWER(titulo)=LOWER(?)";

                PreparedStatement psBuscarLibro =con.prepareStatement(buscarLibro);
                psBuscarLibro.setString( 1,txtLibro.getText());
                ResultSet rsLibro = psBuscarLibro.executeQuery();

                // SI NO EXISTE LIBRO
                if (!rsLibro.next()) {
                    JOptionPane.showMessageDialog(null,"Libro no encontrado");
                    return;
                }
                int idLibro = rsLibro.getInt("id_libro");
                boolean disponible = rsLibro.getBoolean("disponible");

                // VALIDAR DISPONIBILIDAD
                if (!disponible) {
                    JOptionPane.showMessageDialog(null,"Libro no disponible");
                    return;
                }

                // =========================
                // REGISTRAR PRESTAMO
                // =========================

                String sql =
                        "INSERT INTO prestamos "
                        + "(id_estudiante,id_libro) "
                        + "VALUES (?,?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1,idEstudiante);
                ps.setInt(2,idLibro);
                ps.executeUpdate();

                // =========================
                // ACTUALIZAR DISPONIBILIDAD
                // =========================

                String update =
                        "UPDATE libros "
                        + "SET disponible=false "
                        + "WHERE id_libro=?";

                PreparedStatement psUpdate = con.prepareStatement(update);
                psUpdate.setInt(1,idLibro);
                psUpdate.executeUpdate();
                JOptionPane.showMessageDialog(null,"Prestamo registrado");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        setVisible(true);
    }
}