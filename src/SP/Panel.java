package SP;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Panel extends JPanel {

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Game game = new Game();
        drawGameBoard(g2);

    }


    private void drawGameBoard(Graphics2D g2){
        final short SIZE_OF_BOARD = 10;
        final short AMOUNT_OF_COLUMNS = 12; // 10 (size of board) + 1 from each side
        double part = this.getWidth() / (double) SIZE_OF_BOARD;
        double partHeight = this.getHeight() / (double) SIZE_OF_BOARD;

        for(int i = 0 ; i < SIZE_OF_BOARD ; i++){
            g2.draw(new Rectangle2D.Double(i*part, 0.0,(i+1)*part, this.getHeight()));
            g2.draw(new Line2D.Double(0.0, (i)*partHeight, this.getWidth(), (i)*partHeight));
        }
    }

}
