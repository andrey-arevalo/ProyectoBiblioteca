package componentes;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private Color color = new Color(102, 51, 0);
    private Color hover = new Color(130, 70, 20);

    public RoundedButton(String texto) {
        super(texto);

        setForeground(Color.WHITE);
        setFont(new Font("Serif", Font.BOLD, 24));

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                color = hover;
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                color = new Color(102,51,0);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),50,50);
        super.paintComponent(g2);
        g2.dispose();

    }

}