package vista;

import componentes.BackgroundPanel;
import componentes.RoundedButton;
import componentes.RoundedPanel;
import componentes.RoundedTextField;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrestamosFrame extends JFrame {

    private RoundedTextField txtBuscar;
    private RoundedButton btnBuscar;
    private RoundedButton btnVolver;

    private JTable tabla;
    private DefaultTableModel modelo;

    public PrestamosFrame() {

        setTitle("Gestión de Préstamos");
        setSize(1100,700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        BackgroundPanel fondo = new BackgroundPanel("/imagenes/bibliotecafondo.jpg");
        setContentPane(fondo);
        RoundedPanel panel = new RoundedPanel();

        panel.setLayout(null);
        panel.setBounds(180,40,740,600);
        fondo.add(panel);
       
        JLabel titulo = new JLabel("GESTIÓN DE PRÉSTAMOS");
        titulo.setFont(new Font("Georgia",Font.BOLD,30));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(100,25,540,40);
        panel.add(titulo);
        
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/libro1.png"));
        Image imagen = iconoOriginal.getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH);
        JLabel icono = new JLabel(new ImageIcon(imagen));
        icono.setBounds(335,80,70,70);
        panel.add(icono);
        
        JLabel lblBuscar = new JLabel("Buscar estudiante:");
        lblBuscar.setFont(new Font("Segoe UI",Font.BOLD,15));
        lblBuscar.setBounds(70,180,180,25);
        panel.add(lblBuscar);
        txtBuscar = new RoundedTextField();
        txtBuscar.setBounds(70,215,430,40);
        panel.add(txtBuscar);
        
        btnBuscar = new RoundedButton("Buscar");
        btnBuscar.setBounds(530,215,120,40);
        panel.add(btnBuscar);
        
        String columnas[] = {
                "ID",
                "Libro",
                "Fecha",
                "Estado",
                "Multa"
        };

        modelo = new DefaultTableModel(columnas,0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(70,280,580,220);
        panel.add(scroll);
        
        btnVolver = new RoundedButton("Volver");
        btnVolver.setBounds(20,20,110,40);
        panel.add(btnVolver);
     // ===============================
     // BOTÓN BUSCAR
     // ===============================

     btnBuscar.addActionListener(e -> {
         modelo.setRowCount(0);
         String nombre = txtBuscar.getText().trim();

         if(nombre.isEmpty()){
        	 JOptionPane.showMessageDialog( null,"Ingrese el nombre del estudiante.");
             return;
         }
         try{
             Connection con = ConexionBD.conectar();
             String sql =
                     "SELECT " +
                     "p.id_prestamo, " +
                     "l.titulo, " +
                     "p.fecha_prestamo, " +
                     "p.estado, " +
                     "COALESCE(m.estado,'Sin multa') AS multa " +
                     "FROM prestamos p " +
                     "JOIN estudiantes e " +
                     "ON p.id_estudiante=e.id_estudiante " +
                     "JOIN libros l " +
                     "ON p.id_libro=l.id_libro " +
                     "LEFT JOIN multas m " +
                     "ON p.id_prestamo=m.id_prestamo " +
                     "WHERE LOWER(e.nombre)=LOWER(?) " +
                     "ORDER BY p.fecha_prestamo DESC";

             PreparedStatement ps = con.prepareStatement(sql);
             ps.setString(1,nombre);
             ResultSet rs = ps.executeQuery();
             boolean encontrado = false;

             while(rs.next()){
                 encontrado = true;
                 modelo.addRow(new Object[]{
                         rs.getInt("id_prestamo"),
                         rs.getString("titulo"),
                         rs.getTimestamp("fecha_prestamo"),
                         rs.getString("estado"),
                         rs.getString("multa")
                 });
             }
             if(!encontrado){
                 JOptionPane.showMessageDialog(null,"El estudiante no tiene préstamos registrados.");
             }
         }catch(Exception ex){
             ex.printStackTrace();
             JOptionPane.showMessageDialog(null,"Error al buscar préstamos.");
         }
     });
     btnVolver.addActionListener(e -> {
    	    dispose();
    	    new AdminFrame();
    	});
     txtBuscar.addActionListener(e -> btnBuscar.doClick());
     setVisible(true);
    }
    }
       