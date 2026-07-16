package dao;

import conexion.ConexionBD;
import modelo.Multa;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MultaDAO {

    public void guardarMulta(Multa multa) {

        try {
            Connection con = ConexionBD.conectar();
            String sql =
                    "INSERT INTO multas " +
                    "(id_prestamo,monto,motivo,estado) " +
                    "VALUES (?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, multa.getIdPrestamo());
            ps.setDouble( 2,multa.getMonto() );
            ps.setString(3,multa.getMotivo());
            ps.setString(4, multa.getEstado());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}