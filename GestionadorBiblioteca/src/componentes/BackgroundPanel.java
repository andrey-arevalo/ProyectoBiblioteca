package componentes;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private Image imagen;

    public BackgroundPanel(String rutaImagen) {

        ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
        imagen = icono.getImage();

        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(
                imagen,
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );

        Graphics2D g2 = (Graphics2D) g;

        GradientPaint degradado = new GradientPaint(
                0,
                getHeight() - 180,
                new Color(255,255,255,0),
                0,
                getHeight(),
                Color.WHITE
        );

        g2.setPaint(degradado);

        g2.fillRect(
                0,
                getHeight()-180,
                getWidth(),
                180
        );
    }

}