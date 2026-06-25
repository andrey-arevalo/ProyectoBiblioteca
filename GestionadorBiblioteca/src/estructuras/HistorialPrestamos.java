package estructuras;

import modelo.Prestamo;

import java.util.LinkedList;

public class HistorialPrestamos {

    private LinkedList<Prestamo> historial = new LinkedList<>();
    public void agregarHistorial(Prestamo p) {
        historial.add(p);
    }

    public void mostrarHistorial() {

        for (Prestamo p : historial) {
            System.out.println(p);
        }
    }
}