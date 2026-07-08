package dao;

import conexion.ConexionBD;
import modelo.Libro;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LibroDAO {

    // GUARDAR LIBRO
    public void guardarLibro(Libro libro) {
        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "INSERT INTO libros "
                    + "(titulo,autor,id_categoria,disponible) "
                    + "VALUES (?,?,?,true)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setInt(3, libro.getIdCategoria());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LISTAR LIBROS
    public ArrayList<Libro> listarLibros() {
        ArrayList<Libro> lista = new ArrayList<>();

        try {
            Connection con = ConexionBD.conectar();
            String sql =  "SELECT * FROM libros";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro(
                                rs.getInt("id_libro"),
                                rs.getString("titulo"),
                                rs.getString("autor"),
                                rs.getInt("id_categoria"),
                                rs.getBoolean("disponible")
                        );
                lista.add(libro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ELIMINAR LIBRO
    public void eliminarLibro(int idLibro) {

        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "DELETE FROM libros "
                    + "WHERE id_libro=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idLibro);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null,"Libro eliminado");
            } else {
                JOptionPane.showMessageDialog(null,"Libro no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BUSCAR LIBRO POR ID
    public Libro buscarLibro(int idLibro) {
        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "SELECT * FROM libros "
                    + "WHERE id_libro=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idLibro);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Libro(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("id_categoria"),
                        rs.getBoolean("disponible")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}