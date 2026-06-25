package estructuras;

import modelo.Prestamo;

import java.util.Stack;

public class PilaDevoluciones {

    private Stack<Prestamo> pila = new Stack<>();

    public void agregarDevolucion(Prestamo p) {
        pila.push(p);
    }

    public void mostrar() {

        for (Prestamo p : pila) {
            System.out.println(p);
        }
    }
}