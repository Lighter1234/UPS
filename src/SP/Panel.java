package SP;

import org.w3c.dom.events.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Panel extends JPanel {
    private final short SIZE_OF_BOARD = 10;
    private final short SIZE_OF_MESSAGE = 10;

    private Game game;

    private Rectangle2D[] rectangles = new Rectangle2D[SIZE_OF_BOARD];

    private short[][] cells = new short[SIZE_OF_BOARD][SIZE_OF_BOARD];

    final Color[] colors = {Color.WHITE, Color.BLUE, Color.RED}; // 0 = no player, 1 = first player, 2 = second player

    private boolean messageReady = false;

    private Player[] players;

    private Player player;

    private boolean gameEnded = false;

    private String message;

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

    /**
     * Adds a point
     *
     * @param x
     * @param y
     * @param player
     * @return
     */
    public void addCircle(double x, double y, short player){
        for(short i = 0 ; i < this.rectangles.length ; i++){
            Rectangle2D current = this.rectangles[i];
            if(current.contains(x, y)){
                short pointer = game.getPointer(i);
//                this.cells[i][pointer] = player;
                this.prepareMoveMessage(i);
      //          this.message = "<" +i +">" + "p" +player;

//                boolean victory = this.game.addCircle(i, players[player-1]);  // player == [1, 2]
//                if(victory){
//                    this.gameEnded = true;
//                }
            }
        }

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

    /**
     * Returns if the game has ended
     *
     * @return true if the game has ended, false if the game is on-going
     */
    public boolean hasGameEnded(){
        return this.gameEnded;
    }

    public boolean messageReady(){ return this.messageReady; }

    public String getMessage(){ return this.message; }

    public void prepareMoveMessage(int i){
        this.message = "plid" + this.player.getNumber() + "move" + i + SIZE_OF_MESSAGE ;

        this.messageReady = true;
    }

    public void messageSent(){
        this.messageReady = false;
    }
}
