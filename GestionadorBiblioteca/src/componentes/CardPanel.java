package componentes;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    public CardPanel() {
        setOpaque(false);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(186,104,50));

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                35,
                35);

        super.paintComponent(g);
    }
}