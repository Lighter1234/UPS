package SP;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Panel extends JPanel {
    private final short SIZE_OF_BOARD = 10;
    private final short SIZE_OF_MESSAGE = 10;

    private Game game;

    private Rectangle2D[] rectangles = new Rectangle2D[SIZE_OF_BOARD];
    private short[] pointers = new short[SIZE_OF_BOARD];
    private short[][] cells = new short[SIZE_OF_BOARD][SIZE_OF_BOARD];

    final Color[] colors = {Color.WHITE, Color.BLUE, Color.RED}; // 0 = no player, 1 = first player, 2 = second player

    private boolean messageReady = false;

    private Player player;

    private boolean gameEnded = false;
    private boolean gameFound = false;

//    private Message message;

    private String message;
    private int id;
    private int gameId;

    private boolean disconnected = false;

    private MessageSender ms;


    public Panel(MessageSender ms){
        this.ms = ms;
        this.addMouseListener(new MouseHandler(this));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGameBoard(g2);
        System.out.println("Panel");
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
     * @return
     */
    public void addCircle(int x, int y){
//        for(short i = 0 ; i < this.rectangles.length ; i++){
////            Rectangle2D current = this.rectangles[i];
////            if(current.contains(x, y)){
////                short pointer = game.getPointer(i);
////                this.prepareMoveMessage(i, i);
////            }
////        }
        this.pointers[x] += 1;
        this.cells[x][y] = 1;
    }

    public void addCircleFromOpponent(int x, int y){
//        for(short i = 0 ; i < this.rectangles.length ; i++){
//            Rectangle2D current = this.rectangles[i];
//            if(current.contains(x, y)){
//                short pointer = game.getPointer(i);
//                this.prepareMoveMessage(i, i);
//            }
//        }
        this.pointers[x] += 1;
        this.cells[x][y] = 2;
    }

    public void makeMove(double x, double y){
        for(short i = 0 ; i < this.rectangles.length ; i++){
            Rectangle2D current = this.rectangles[i];
            if(current.contains(x, y)){
                this.prepareMoveMessage(i, this.getPointer(i));
                return;
            }
        }
    }

    private short getPointer(int x){
        return this.pointers[x];
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

    public synchronized boolean isMessageReady(){
        return this.messageReady;
    }

//    public synchronized Message getMessage(){ return this.message; }
    public synchronized String getMessage(){ return this.message; }

    public void prepareMoveMessage(int x, int y){
        if(!messageReady) {
            String s = "move|" + x + "," + y + "|" + /*this.getId()*/0;
//            this.message = new Message(s, s.length());
          /*  System.out.println("Message prepared!" + this.messageReady + " Message: " + this.message);
*/
            this.message = s;
            ms.sendMessage(s);
//            this.messageReady = true;
        }
    }

    public void messageSent(){
        this.messageReady = false;
    }

    public void messageReceived(){
        this.message = null;
    }

    public int getGameId(){
        return this.gameId;
    }

    public void setGameId(int id){
        this.gameId = id;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public synchronized void gameFound(){ this.gameFound = true;
        System.out.println("Game found!"); }

    public synchronized boolean hasGameBeenFound(){ return this.gameFound; }

    public synchronized boolean isDisconnected(){
        return this.disconnected;
    }

    public synchronized void setDisconnectedFlag() {
        this.disconnected = true;
    }


}
