package componentes;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    public RoundedPanel() {

        setOpaque(false);

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2=(Graphics2D)g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255,255,255,205));

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                40,
                40);

        g2.dispose();

        super.paintComponent(g);

    }

}