package test;
import javax.swing.*;
import java.awt.*;

public class BibliotecaSimple {

    public static void main(String[] args) {

        JFrame ventana = new JFrame("Biblioteca");
        ventana.setSize(700,400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(null);

//Es elTITULO
        JLabel titulo = new JLabel("Sistema de Préstamo de Libros");
        titulo.setBounds(180,20,300,30);
        titulo.setFont(new Font("Arial", Font.BOLD,20));

//Es el BUSCADOR
        JTextField buscador = new JTextField();
        buscador.setBounds(150,70,250,30);

        JButton buscar = new JButton("Buscar");
        buscar.setBounds(420,70,100,30);

//Es el LIBRO 1
        JLabel libro1 = new JLabel(" Java Básico");
        libro1.setBounds(100,150,150,30);

        JButton solicitar1 = new JButton("Solicitar");
        solicitar1.setBounds(100,190,120,30);

//Es el LIBRO 2
        JLabel libro2 = new JLabel(" Base de Datos");
        libro2.setBounds(280,150,170,30);

        JButton solicitar2 = new JButton("Solicitar");
        solicitar2.setBounds(280,190,120,30);

 //Es el LIBRO 3
        JLabel libro3 = new JLabel(" Redes");
        libro3.setBounds(480,150,150,30);

        JButton solicitar3 = new JButton("Solicitar");
        solicitar3.setBounds(480,190,120,30);

//Es de  AGREGAR
        ventana.add(titulo);
        ventana.add(buscador);
        ventana.add(buscar);

        ventana.add(libro1);
        ventana.add(solicitar1);

        ventana.add(libro2);
        ventana.add(solicitar2);

        ventana.add(libro3);
        ventana.add(solicitar3);

        ventana.setVisible(true);
    }
}