package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class LibrosFrame extends JFrame {

    private RoundedButton btnRegistrar;
    private RoundedButton btnActualizar;
    private RoundedButton btnEliminar;

    public LibrosFrame() {

        setTitle("Gestión de Libros");
        setSize(1100,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        setContentPane(fondo);
        
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(null);
        panel.setBounds(180,40,740,600);
        fondo.add(panel);       
       
        JLabel titulo = new JLabel("GESTIÓN DE LIBROS");
        titulo.setFont(new Font("Georgia",Font.BOLD,30));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        titulo.setBounds(120,25,500,40);
        panel.add(titulo);
        
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/libro1.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH);
        
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        lblIcono.setBounds(335,80,70,70);
        panel.add(lblIcono);
        
        btnRegistrar = new RoundedButton("Registrar Libro");
        btnRegistrar.setBounds(220,180,300,45);
        panel.add(btnRegistrar);
        
        btnActualizar = new RoundedButton("Actualizar Libro");
        btnActualizar.setBounds(220,250,300,45);
        panel.add(btnActualizar);
        
        btnEliminar = new RoundedButton("Eliminar Libro");
        btnEliminar.setBounds(220,320,300,45);
        panel.add(btnEliminar);
        
        RoundedPanel panelFormulario =new RoundedPanel();  
        panelFormulario.setLayout(new BorderLayout());
        panelFormulario.setBounds(40,400,660,170);
        panel.add(panelFormulario);
        
        RoundedButton btnVolver = new RoundedButton("Volver");
        btnVolver.setBounds(20,20,110,40);        
        panel.add(btnVolver);
        
        btnRegistrar.addActionListener(e->{
            new RegistrarLibroFrame();
        });
        btnActualizar.addActionListener(e->{
            new ActualizarLibroFrame();
        });
        btnEliminar.addActionListener(e->{
        	new EliminarLibroFrame();
        });
        btnVolver.addActionListener(e->{
            dispose();
            new AdminFrame();
        });
        setVisible(true);
    }
}