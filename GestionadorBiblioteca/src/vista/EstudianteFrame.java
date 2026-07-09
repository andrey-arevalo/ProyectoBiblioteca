package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EstudianteFrame extends JFrame {
	private int idEstudiante;
	
    public EstudianteFrame(int idEstudiante) {
    	 this.idEstudiante = idEstudiante;
    
        setTitle("Estudiante");
        setSize(500,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder( new EmptyBorder(20,50,20,50));

        JLabel titulo = new JLabel("PANEL ESTUDIANTE");
        titulo.setFont(new Font("Arial",Font.BOLD,22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0,30)));

        // BOTONES
        JButton btnCatalogo =new JButton("Consultar Catalogo");
        JButton btnBuscar = new JButton("Buscar Libro");
        JButton btnHistorial = new JButton("Historial Prestamos");
        JButton btnEstado = new JButton("Estado Prestamos");
        JButton btnMultas = new JButton("Multas Pendientes");
        JButton btnCerrarSesion = new JButton("Cerrar Sesion");
        JButton[] botones = {
                btnCatalogo,
                btnBuscar,
                btnHistorial,
                btnEstado,
                btnMultas,
                btnCerrarSesion
        };

        for (JButton b : botones) {
            b.setMaximumSize(  new Dimension(250,40));
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(b);
            panel.add(Box.createRigidArea(new Dimension(0,15)
            ));
        }

        // EVENTOS

        btnCatalogo.addActionListener(e -> new CatalogoFrame());
        btnBuscar.addActionListener(e ->new BuscarLibroFrame(this));
        btnHistorial.addActionListener(e -> new HistorialFrameEstudiante(idEstudiante));
        btnEstado.addActionListener(e -> new EstadoPrestamosFrame(idEstudiante));
        btnMultas.addActionListener(e ->new MultasPendientesFrame(idEstudiante));

        // CERRAR SESION
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        add(panel);

        setVisible(true);
    }
}