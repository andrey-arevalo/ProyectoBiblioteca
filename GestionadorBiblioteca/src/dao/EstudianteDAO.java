package dao;

import conexion.ConexionBD;
import modelo.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EstudianteDAO {

    // REGISTRAR ESTUDIANTE
    public void guardarEstudiante(Estudiante estudiante) {

        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "INSERT INTO estudiantes "
                    + "(nombre,carrera,contrasena) "
                    + "VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(
                    1,
                    estudiante.getNombre()
            );

            ps.setString(
                    2,
                    estudiante.getCarrera()
            );

            ps.setString(
                    3,
                    estudiante.getContrasena()
            );

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // LISTAR ESTUDIANTES
    public ArrayList<Estudiante> listarEstudiantes() {

        ArrayList<Estudiante> lista =
                new ArrayList<>();

        try {

            Connection con =
                    ConexionBD.conectar();

            String sql =
                    "SELECT * FROM estudiantes";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Estudiante estudiante =
                        new Estudiante(
                                rs.getInt("id_estudiante"),
                                rs.getString("nombre"),
                                rs.getString("carrera"),
                                rs.getString("contrasena")
                        );

                lista.add(estudiante);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR NOMBRE
    public Estudiante buscarPorNombre(String nombre) {

        try {

            Connection con =
                    ConexionBD.conectar();

            String sql =
                    "SELECT * FROM estudiantes "
                    + "WHERE LOWER(nombre)=LOWER(?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    nombre
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return new Estudiante(
                        rs.getInt("id_estudiante"),
                        rs.getString("nombre"),
                        rs.getString("carrera"),
                        rs.getString("contrasena")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}