package modelo;

import java.time.LocalDate;

public class Prestamo {

    private int idPrestamo;
    private Estudiante estudiante;
    private Libro libro;
    private LocalDate fechaPrestamo;

    // CONSTRUCTOR
    public Prestamo(Estudiante estudiante,Libro libro) {

        this.estudiante = estudiante;
        this.libro = libro;
        this.fechaPrestamo = LocalDate.now();
    }

    // CONSTRUCTOR COMPLETO
    public Prestamo(int idPrestamo,Estudiante estudiante,Libro libro,LocalDate fechaPrestamo) {

        this.idPrestamo = idPrestamo;
        this.estudiante = estudiante;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
    }

    // GETTERS Y SETTERS

    public int getIdPrestamo() {
        return idPrestamo;
    }
    public void setIdPrestamo(
            int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(
            Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    @Override
    public String toString() {
        return
                "Prestamo -> " +
                estudiante.getNombre()
                + " | Libro: "
                + libro.getTitulo()
                + " | Fecha: "
                + fechaPrestamo;
    }
}