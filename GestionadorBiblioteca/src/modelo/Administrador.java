package modelo;

public class Administrador extends Usuario {

    public Administrador(
            int id_usuario,
            String nombre
    ) {

        super(
                id_usuario,
                nombre
        );
    }

    public void generarReporte() {

        System.out.println(
                "Reporte generado correctamente"
        );
    }

    public void registrarLibro() {

        System.out.println(
                "Libro registrado"
        );
    }

    public void actualizarLibro() {

        System.out.println(
                "Libro actualizado"
        );
    }

    public void eliminarLibro() {

        System.out.println(
                "Libro eliminado"
        );
    }

    public void registrarEstudiante() {

        System.out.println(
                "Estudiante registrado"
        );
    }

    @Override
    public String toString() {

        return
                "Administrador: "
                + nombre;
    }
}