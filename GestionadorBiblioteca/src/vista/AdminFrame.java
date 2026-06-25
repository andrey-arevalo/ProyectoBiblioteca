package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminFrame extends JFrame {

    public AdminFrame() {

        setTitle("Administrador");
        setSize(500,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // PANEL PRINCIPAL
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20,50,20,50));

        // TITULO
        JLabel titulo =new JLabel("PANEL ADMINISTRADOR");
        titulo.setFont(new Font("Arial",Font.BOLD,22
                )
        );

        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0,30)));

        // BOTONES
        JButton btnRegistrarUsuario = new JButton("Registrar Usuario");
        JButton btnRegistrarLibro = new JButton("Registrar Libro");
        JButton btnActualizarLibro = new JButton("Actualizar Libro");
        JButton btnEliminarLibro = new JButton("Eliminar Libro" );
        JButton btnHistorial = new JButton("Historial Prestamos");
        JButton btnMultas = new JButton("Gestionar Multas");
        JButton btnReportes = new JButton("Ver Reportes");
        JButton btnCerrarSesion =new JButton("Cerrar Sesion");

        // ARRAY BOTONES
        JButton[] botones = {

                btnRegistrarUsuario,
                btnRegistrarLibro,
                btnActualizarLibro,
                btnEliminarLibro,
                btnHistorial,
                btnMultas,
                btnReportes,
                btnCerrarSesion
        };

        // ESTILO BOTONES
        for (JButton b : botones) {
            b.setMaximumSize(new Dimension(250,40));
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(b);
            panel.add(Box.createRigidArea(new Dimension(0,15)));
        }

        // REGISTRAR USUARIO
        btnRegistrarUsuario
                .addActionListener(e ->new RegistrarUsuarioFrame());

        // REGISTRAR LIBRO
        btnRegistrarLibro.addActionListener(e ->new RegistrarLibroFrame());

        // ACTUALIZAR LIBRO
        btnActualizarLibro.addActionListener(e ->new ActualizarLibroFrame());

        // ELIMINAR LIBRO
        btnEliminarLibro.addActionListener(e ->new EliminarLibroFrame());

        // HISTORIAL
        btnHistorial.addActionListener(e ->new HistorialFrame());

        // MULTAS
        btnMultas.addActionListener(e -> new MultasFrame());

        // REPORTES
        btnReportes.addActionListener(e ->new ReportesFrame());

        // CERRAR SESION
        btnCerrarSesion.addActionListener(e -> { 
        	dispose();new LoginFrame(); 
        	});
        add(panel);
        setVisible(true);
    }
}