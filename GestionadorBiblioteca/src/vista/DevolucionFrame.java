package vista;

import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DevolucionFrame extends JFrame {

    public DevolucionFrame() {

        setTitle("Registrar Devolucion");
        setSize(500,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,2,10,10));

        // CAMPOS
        JTextField txtNombre = new JTextField();
        JTextField txtLibro = new JTextField();
        JButton btnDevolver = new JButton("Registrar");

        // COMPONENTES
        add(new JLabel("Nombre Estudiante"));
        add(txtNombre);
        add(new JLabel("Nombre Libro"));
        add(txtLibro);
        add(new JLabel(""));
        add(btnDevolver);

        // EVENTO
        btnDevolver.addActionListener(e -> {

            try {
                Connection con = ConexionBD.conectar();

                // =========================
                // BUSCAR ESTUDIANTE
                // =========================

                String buscarEstudiante =
                        "SELECT id_estudiante "
                        + "FROM estudiantes "
                        + "WHERE LOWER(nombre)=LOWER(?)";
                PreparedStatement psBuscarEstudiante = con.prepareStatement(buscarEstudiante);
                psBuscarEstudiante.setString( 1,txtNombre.getText());
                ResultSet rsEstudiante = psBuscarEstudiante.executeQuery();
                
                if (!rsEstudiante.next()) {
                    JOptionPane.showMessageDialog(null,"Estudiante no encontrado");
                    return;
                }
                
                int idEstudiante = rsEstudiante.getInt("id_estudiante");

                // =========================
                // BUSCAR LIBRO
                // =========================

                String buscarLibro =
                        "SELECT id_libro "
                        + "FROM libros "
                        + "WHERE LOWER(titulo)=LOWER(?)";
                PreparedStatement psBuscarLibro = con.prepareStatement(buscarLibro);

                psBuscarLibro.setString(1,txtLibro.getText());

                ResultSet rsLibro = psBuscarLibro.executeQuery();

                if (!rsLibro.next()) {
                    JOptionPane.showMessageDialog(null,"Libro no encontrado");
                    return;
                }
                int idLibro = rsLibro.getInt("id_libro");

                // =========================
                // VALIDAR PRESTAMO
                // =========================

                String validar =
                        "SELECT * "
                        + "FROM prestamos "
                        + "WHERE id_estudiante=? "
                        + "AND id_libro=?";

                PreparedStatement psValidar = con.prepareStatement(validar);

                psValidar.setInt(1,idEstudiante);
                psValidar.setInt(2,idLibro);

                ResultSet rsPrestamo = psValidar.executeQuery();

                if (!rsPrestamo.next()) {
                    JOptionPane.showMessageDialog( null, "Ese estudiante no tiene ese libro");
                    return;
                }

                // =========================
                // ELIMINAR PRESTAMO
                // =========================

                int idPrestamo = rsPrestamo.getInt("id_prestamo");

                String actualizarPrestamo =
                        "UPDATE prestamos " +
                        "SET estado='Devuelto' " +
                        "WHERE id_prestamo=?";

                PreparedStatement psActualizarPrestamo =con.prepareStatement(actualizarPrestamo);
                psActualizarPrestamo.setInt(1,idPrestamo);
                psActualizarPrestamo.executeUpdate();

                // =========================
                // LIBRO DISPONIBLE
                // =========================

                String update =
                        "UPDATE libros "
                        + "SET disponible=true "
                        + "WHERE id_libro=?";

                PreparedStatement psUpdate =con.prepareStatement(update);

                psUpdate.setInt(1,idLibro);
                psUpdate.executeUpdate();
                JOptionPane.showMessageDialog(null,"Devolucion registrada");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        setVisible(true);
    }
}