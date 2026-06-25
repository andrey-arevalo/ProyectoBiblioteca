package modelo;
public class Bibliotecario extends Usuario {
	
    public Bibliotecario(int id_bibliotecario, String nombre) {
        super(id_bibliotecario, nombre);
    }

    public void registrarPrestamo() {
        System.out.println("Prestamo registrado");
    }
}