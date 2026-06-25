package estructuras;

import modelo.Prestamo;

import java.util.LinkedList;
import java.util.Queue;

public class ColaPrestamos {
    private Queue<Prestamo> cola = new LinkedList<>();
    public void agregar(Prestamo p) {
    	cola.offer(p);
    }
    public void mostrar() {
        for (Prestamo p : cola) {
            System.out.println(p);
        }
    }
}