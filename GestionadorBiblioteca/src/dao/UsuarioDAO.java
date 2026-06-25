package dao;

import conexion.ConexionBD;
import modelo.UsuarioSistema;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {

    // GUARDAR USUARIO
    public void guardarUsuario(UsuarioSistema usuario) {

        try {

            Connection con =
                    ConexionBD.conectar();

            String sql =
                    "INSERT INTO usuarios "
                    + "(nombre,contrasena,id_rol) "
                    + "VALUES (?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    usuario.getNombre()
            );

            ps.setString(
                    2,
                    usuario.getPassword()
            );

            ps.setInt(
                    3,
                    usuario.getIdRol()
            );

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // LISTAR USUARIOS
    public ArrayList<UsuarioSistema> listarUsuarios() {

        ArrayList<UsuarioSistema> lista =
                new ArrayList<>();

        try {

            Connection con =
                    ConexionBD.conectar();

            String sql =

                    "SELECT "
                    + "u.id_usuario, "
                    + "u.nombre, "
                    + "u.contrasena, "
                    + "u.id_rol, "
                    + "r.nombre_rol "
                    + "FROM usuarios u "
                    + "JOIN roles r "
                    + "ON u.id_rol = r.id_rol";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                UsuarioSistema usuario =
                        new UsuarioSistema(

                                rs.getInt(
                                        "id_usuario"
                                ),

                                rs.getString(
                                        "nombre"
                                ),

                                rs.getString(
                                        "contrasena"
                                ),

                                rs.getInt(
                                        "id_rol"
                                ),

                                rs.getString(
                                        "nombre_rol"
                                )
                        );

                lista.add(usuario);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR NOMBRE
    public UsuarioSistema buscarUsuario(
            String nombre
    ) {

        try {

            Connection con =
                    ConexionBD.conectar();

            String sql =

                    "SELECT "
                    + "u.id_usuario, "
                    + "u.nombre, "
                    + "u.contrasena, "
                    + "u.id_rol, "
                    + "r.nombre_rol "
                    + "FROM usuarios u "
                    + "JOIN roles r "
                    + "ON u.id_rol = r.id_rol "
                    + "WHERE LOWER(u.nombre)=LOWER(?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    nombre
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return new UsuarioSistema(

                        rs.getInt(
                                "id_usuario"
                        ),

                        rs.getString(
                                "nombre"
                        ),

                        rs.getString(
                                "contrasena"
                        ),

                        rs.getInt(
                                "id_rol"
                        ),

                        rs.getString(
                                "nombre_rol"
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}