package modelo;

import java.sql.Timestamp;

public class Multa {

    private int idMulta;
    private int idPrestamo;
    private double monto;
    private String motivo;
    private String estado;
    private Timestamp fechaPago;

    // CONSTRUCTOR VACIO
    public Multa() {

    }

    // CONSTRUCTOR SIN ID
    public Multa(
            int idPrestamo,
            double monto,
            String motivo,
            String estado
    ) {
        this.idPrestamo = idPrestamo;
        this.monto = monto;
        this.motivo = motivo;
        this.estado = estado;
    }
    
    // CONSTRUCTOR COMPLETO
    public Multa(
            int idMulta,
            int idPrestamo,
            double monto,
            String motivo,
            String estado,
            Timestamp fechaPago
    ) {

        this.idMulta = idMulta;
        this.idPrestamo = idPrestamo;
        this.monto = monto;
        this.motivo = motivo;
        this.estado = estado;
        this.fechaPago = fechaPago;
    }

    // GETTERS

    public int getIdMulta() {
        return idMulta;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public double getMonto() {
        return monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getEstado() {
        return estado;
    }

    public Timestamp getFechaPago() {
        return fechaPago;
    }

    // SETTERS

    public void setIdMulta(int idMulta) {
        this.idMulta = idMulta;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaPago(Timestamp fechaPago) {
        this.fechaPago = fechaPago;
    }

    @Override
    public String toString() {

        return "Multa -> "
                + "Prestamo: "
                + idPrestamo
                + " | Monto: S/."
                + monto
                + " | Motivo: "
                + motivo
                + " | Estado: "
                + estado;
    }
}