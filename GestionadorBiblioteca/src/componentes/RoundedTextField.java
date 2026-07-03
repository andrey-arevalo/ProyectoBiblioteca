package componentes;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {

    public RoundedTextField() {
        setOpaque(false);
        setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20));

        setFont(new Font("Segoe UI",Font.PLAIN,20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(248,240,225));
        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                40,
                40);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2=(Graphics2D)g;
        g2.setColor(new Color(220,220,220));
        g2.drawRoundRect(
                0,
                0,
                getWidth()-1,
                getHeight()-1,
                40,
                40);
   }
}