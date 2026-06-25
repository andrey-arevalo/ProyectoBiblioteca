package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BibliotecarioFrame extends JFrame {

    public BibliotecarioFrame() {
        setTitle("Bibliotecario");
        setSize(500,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20,50,20,50));
        JLabel titulo = new JLabel("PANEL BIBLIOTECARIO");
        titulo.setFont(new Font("Arial",Font.BOLD,22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0,30)));

        // BOTONES
        JButton btnPrestamo = new JButton("Registrar Prestamo");
        JButton btnDevolucion = new JButton("Registrar Devolucion");
        JButton btnBuscar = new JButton("Buscar Libro");
        JButton btnDisponibilidad = new JButton("Validar Disponibilidad");
        JButton btnConsultar = new JButton("Consultar Estudiantes");
        JButton btnPrestamosActivos = new JButton("Prestamos Activos");
        JButton btnCerrarSesion = new JButton("Cerrar Sesion");
        JButton[] botones = {
                btnPrestamo,
                btnDevolucion,
                btnBuscar,
                btnDisponibilidad,
                btnConsultar,
                btnPrestamosActivos,
                btnCerrarSesion
        };
        for (JButton b : botones) {
            b.setMaximumSize(new Dimension(250,40));
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(b);
            panel.add(Box.createRigidArea(new Dimension(0,15)));
        }
        // EVENTOS
        btnPrestamo.addActionListener(e ->new PrestamoFrame());
        btnDevolucion.addActionListener(e -> new DevolucionFrame());
        btnBuscar.addActionListener(e ->new BuscarLibroFrame() );
        btnDisponibilidad.addActionListener(e ->new DisponibilidadFrame());
        btnConsultar.addActionListener(e ->new ConsultarEstudiantesFrame());
        btnPrestamosActivos.addActionListener(e -> new PrestamosActivosFrame());

        // CERRAR SESION
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
        add(panel);
        setVisible(true);
    }
}