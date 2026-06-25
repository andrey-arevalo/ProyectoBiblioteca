package modelo;

public class Estudiante {

    private int id_estudiante;
    private String nombre;
    private String carrera;
    private String contrasena;

    // CONSTRUCTOR COMPLETO
    public Estudiante(
            int id_estudiante,
            String nombre,
            String carrera,
            String contrasena
    ) {

        this.id_estudiante = id_estudiante;
        this.nombre = nombre;
        this.carrera = carrera;
        this.contrasena = contrasena;
    }

    // CONSTRUCTOR SIN ID
    public Estudiante(
            String nombre,
            String carrera,
            String contrasena
    ) {

        this.nombre = nombre;
        this.carrera = carrera;
        this.contrasena = contrasena;
    }

    // GETTERS

    public int getId_estudiante() {
        return id_estudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getContrasena() {
        return contrasena;
    }

    // SETTERS

    public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {

        return "ID: "
                + id_estudiante
                + " | Nombre: "
                + nombre
                + " | Carrera: "
                + carrera;
    }
}