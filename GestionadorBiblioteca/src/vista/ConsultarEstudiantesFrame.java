package vista;

import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultarEstudiantesFrame extends JFrame {

    public ConsultarEstudiantesFrame() {

        setTitle("Consultar Estudiantes");
        setSize(700,450);
        setLocationRelativeTo(null);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area));

        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "SELECT "
                    + "e.id_estudiante, "
                    + "e.nombre, "
                    + "e.carrera, "
                    + "u.nombre AS usuario, "
                    + "r.nombre_rol "
                    + "FROM estudiantes e "
                    + "LEFT JOIN usuarios u "
                    + "ON e.id_usuario = u.id_usuario "
                    + "LEFT JOIN roles r "
                    + "ON u.id_rol = r.id_rol "
                    + "ORDER BY e.nombre";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                area.append(
                        "ID: "
                        + rs.getInt("id_estudiante")
                        + " | Nombre: "
                        + rs.getString("nombre")
                        + " | Carrera: "
                        + rs.getString("carrera")
                        + " | Usuario: "
                        + rs.getString("usuario")
                        + " | Rol: "
                        + rs.getString("nombre_rol")
                        + "\n"
                );
            }

            if (!hayDatos) {
                area.setText("No existen estudiantes registrados");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al cargar estudiantes");
        }
        setVisible(true);
    }
}