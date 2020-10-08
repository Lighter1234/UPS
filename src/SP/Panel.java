package SP;

import org.w3c.dom.events.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Panel extends JPanel {
    final short SIZE_OF_BOARD = 10;

    private Game game;

    private Rectangle2D[] rectangles = new Rectangle2D[SIZE_OF_BOARD];

    private short[][] cells = new short[SIZE_OF_BOARD][SIZE_OF_BOARD];

    final Color[] colors = {Color.WHITE, Color.BLUE, Color.RED}; // 0 = no player, 1 = first player, 2 = second player

    private Player[] players;

    private Player player2;

    public Panel(){
        this.addMouseListener(new MouseHandler(this));
        this.players = new Player[] {new Player("", (short)1), new Player("", (short)2)};
        game = new Game();

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        drawGameBoard(g2);
    }


    private void drawGameBoard(Graphics2D g2){
        final short AMOUNT_OF_COLUMNS = 12; // 10 (size of board) + 1 from each side
        double part = this.getWidth() / (double) SIZE_OF_BOARD;
        double partHeight = this.getHeight() / (double) SIZE_OF_BOARD;

//        for(int i = 0 ; i < 1 ; i++){
//            g2.setColor(this.colors[(i+1)%3]);
//            Rectangle2D rect = new Rectangle2D.Double(i*part, 0.0,(i+1)*part, this.getHeight());
//            this.rectangles[i] = rect;
//            g2.fill(rect);
////            g2.draw(new Line2D.Double(0.0, (i)*partHeight, this.getWidth(), (i)*partHeight));
//        }

        this.prepareRectangles(g2, part);

        for(int i = 0 ; i < SIZE_OF_BOARD; i++){
            for(int j = 0 ; j < SIZE_OF_BOARD; j++ ){
                g2.setColor(
                        this.colors[
                        this.cells[i][j]
                        ]);

                Rectangle2D rect = new Rectangle2D.Double(
                        i*part, j*partHeight, (i+1) * part, (j+1) * partHeight
                                                            );
                g2.fill(rect);

                g2.setColor(Color.BLACK);
                g2.draw(rect);

            }
        }
    }

    public boolean addCircle(double x, double y, short player){
        for(short i = 0 ; i < this.rectangles.length ; i++){
            Rectangle2D current = this.rectangles[i];
            if(current.contains(x, y)){
                short pointer = game.getPointer(i);
                this.cells[i][pointer] = player;
                this.game.addCircle(i, players[player-1]);  // player == [1, 2]
                return true;
            }
        }

        return false;
    }

    private void prepareRectangles(Graphics2D g2, double part){
        for(int i = 0 ; i < SIZE_OF_BOARD ; i++){
            g2.setColor(this.colors[(i+1)%3]);
            double firstCoordinate = i*part;
            double difference = (i+1) * part - firstCoordinate;

            Rectangle2D rect = new Rectangle2D.Double(firstCoordinate, 0.0, difference, this.getHeight());
            this.rectangles[i] = rect;
            g2.fill(rect);
//            g2.draw(new Line2D.Double(0.0, (i)*partHeight, this.getWidth(), (i)*partHeight));
        }
    }

}
