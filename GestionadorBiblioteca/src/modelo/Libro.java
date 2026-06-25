package modelo;

public class Libro {

    private int id_libro;
    private String titulo;
    private String autor;
    private int idCategoria;
    private boolean disponible;

    // CONSTRUCTOR COMPLETO
    public Libro(
            int id_libro,
            String titulo,
            String autor,
            int idCategoria,
            boolean disponible
    ) {

        this.id_libro = id_libro;
        this.titulo = titulo;
        this.autor = autor;
        this.idCategoria = idCategoria;
        this.disponible = disponible;
    }

    // CONSTRUCTOR REGISTRO
    public Libro(
            int id_libro,
            String titulo,
            String autor,
            int idCategoria
    ) {

        this.id_libro = id_libro;
        this.titulo = titulo;
        this.autor = autor;
        this.idCategoria = idCategoria;
        this.disponible = true;
    }

    // GETTERS

    public int getId_libro() {
        return id_libro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // SETTERS

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void prestar() {
        disponible = false;
    }

    public void devolver() {
        disponible = true;
    }

    @Override
    public String toString() {

        return "ID: "
                + id_libro
                + " | Titulo: "
                + titulo
                + " | Autor: "
                + autor
                + " | Categoria: "
                + idCategoria
                + " | Disponible: "
                + (disponible ? "Si" : "No");
    }
}