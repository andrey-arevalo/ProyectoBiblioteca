package modelo;

public class UsuarioSistema {

    private int idUsuario;
    private String nombre;
    private String password;
    private int idRol;
    private String nombreRol;

    // CONSTRUCTOR COMPLETO
    public UsuarioSistema(
            int idUsuario,
            String nombre,
            String password,
            int idRol,
            String nombreRol
    ) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.password = password;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    // GETTERS

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public int getIdRol() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    // SETTERS

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @Override
    public String toString() {

        return
                "ID: " + idUsuario +
                " | Nombre: " + nombre +
                " | Rol: " + nombreRol;
    }
}