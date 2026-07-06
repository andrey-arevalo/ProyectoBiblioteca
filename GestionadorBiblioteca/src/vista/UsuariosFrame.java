package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class UsuariosFrame extends JFrame {

    private RoundedButton btnAdministrador;
    private RoundedButton btnBibliotecario;
    private RoundedButton btnEstudiante;

    public UsuariosFrame() {

        setTitle("Gestión de Usuarios");
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
       
        JLabel titulo =new JLabel("GESTIÓN DE USUARIOS");
        
        titulo.setFont(new Font("Georgia",Font.BOLD,30));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(120,25,500,40);
        panel.add(titulo);   
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/usuario.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        lblIcono.setBounds(335,80,70,70);

        panel.add(lblIcono);
        btnAdministrador = new RoundedButton("Registrar Administrador");
        btnAdministrador.setBounds(220,180,300,45);
        panel.add(btnAdministrador);
        btnBibliotecario = new RoundedButton("Registrar Bibliotecario");
        btnBibliotecario.setBounds(220,250,300,45);
        panel.add(btnBibliotecario);
        btnEstudiante =new RoundedButton("Registrar Estudiante");
        btnEstudiante.setBounds(220,320,300,45);
        panel.add(btnEstudiante);
        
     // PANEL CONTENEDOR DEL FORMULARIO

        RoundedPanel panelFormulario = new RoundedPanel();
        panelFormulario.setLayout(new BorderLayout());
        panelFormulario.setBounds(40,400,660,170);
        panel.add(panelFormulario);
        RoundedButton btnVolver = new RoundedButton("Volver");
        btnVolver.setBounds(20,20,110,40);
        panel.add(btnVolver);
        
        btnAdministrador.addActionListener(e ->{
            new RegistrarUsuarioFrame("Administrador");
        });
        
        btnBibliotecario.addActionListener(e ->{
            new RegistrarUsuarioFrame("Bibliotecario");
        });
        
        btnEstudiante.addActionListener(e ->{
            new RegistrarUsuarioFrame("Estudiante");
        });
        btnVolver.addActionListener(e ->{
            dispose();
            new AdminFrame();
        });
        setVisible(true);
    }
    }