package componentes;

import java.awt.*;

public class PlaceholderTextField extends RoundedTextField {

    private String placeholder;

    public PlaceholderTextField(String placeholder) {

        this.placeholder = placeholder;

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if(getText().isEmpty() && !isFocusOwner()) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );

            g2.setColor(new Color(150,150,150));

            g2.setFont(getFont());

            Insets in = getInsets();

            FontMetrics fm = g2.getFontMetrics();

            g2.drawString(
                    placeholder,
                    in.left + 15,
                    getHeight()/2 + fm.getAscent()/2 - 3
            );

            g2.dispose();

        }

    }

}