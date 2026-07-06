package vista;

import conexion.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrarUsuarioFrame extends JFrame {

    public RegistrarUsuarioFrame(String rol) {

        setTitle("Registrar " + rol);
        setSize(450,350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5,2,10,10));

        JTextField txtNombre = new JTextField();
        JTextField txtCarrera = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVolver = new JButton("Volver");

        add(new JLabel("Nombre"));
        add(txtNombre);

        if(rol.equals("Estudiante")){
            add(new JLabel("Carrera"));
            add(txtCarrera);
        }else{
            add(new JLabel(""));
            add(new JLabel(""));
        }

        add(new JLabel("Contraseña"));
        add(txtPassword);

        add(btnVolver);
        add(btnRegistrar);

        btnRegistrar.addActionListener(e->{
            try{
                Connection con = ConexionBD.conectar();

                String nombre = txtNombre.getText();
                String password = String.valueOf(txtPassword.getPassword());

                int idRol = 0;
                switch(rol){
                    case "Administrador":
                        idRol = 1;
                        break;
                    case "Bibliotecario":
                        idRol = 2;
                        break;
                    case "Estudiante":
                        idRol = 3;
                        break;
                }
                String sqlUsuario =
                        "INSERT INTO usuarios(nombre,contrasena,id_rol) "
                      + "VALUES(?,?,?) RETURNING id_usuario";
                
                PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
                psUsuario.setString(1,nombre);
                psUsuario.setString(2,password);
                psUsuario.setInt(3,idRol);
                ResultSet rs = psUsuario.executeQuery();

                int idUsuario = 0;
                if(rs.next()){
                    idUsuario = rs.getInt("id_usuario");
                }
                if(rol.equals("Estudiante")){
                    String sqlEstudiante =
                            "INSERT INTO estudiantes(nombre,carrera,id_usuario) "
                          + "VALUES(?,?,?)";
                    PreparedStatement psEstudiante = con.prepareStatement(sqlEstudiante);
                    psEstudiante.setString(1,nombre);
                    psEstudiante.setString(2,txtCarrera.getText());
                    psEstudiante.setInt(3,idUsuario);
                    psEstudiante.executeUpdate();
                }
                JOptionPane.showMessageDialog(null,rol + " registrado correctamente");
                dispose();

            }catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Error al registrar " + rol);
            }
        });
        btnVolver.addActionListener(e ->{
            dispose();
        });
        setVisible(true);
    }
}