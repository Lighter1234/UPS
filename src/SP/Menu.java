package SP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class Menu extends JPanel {

    private final int AMOUNT_OF_COLS = 10;
    private final int MAX_NAME_LENGTH = 12;

    private final int MENU_MODE = 0;
    private final int GAME_MODE = 1;
    private final int GAME_SEARCHING = 2;
    private final int GAME_RUNNING = 3;

    private int mode = MENU_MODE;

    private GameButton gb;
    private MenuButton mb;
    private ConnectButton cb;
    private JTextField tf;
    private JFrame frame;
    private Panel panel;

    private Socket socket;
    private String address;
    private int port;

    private boolean playerInitialized = false;
    private boolean nameIncorrect = false;
    private boolean nameChecked = false;

    public Menu(String address, int port){
        this.address = address;
        this.port = port;
        gb = new GameButton(this);
        mb = new MenuButton(this);
        cb = new ConnectButton(this);
        tf = new JTextField();
        tf.setColumns(AMOUNT_OF_COLS);
        this.add(gb);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        switch(mode){
            case MENU_MODE:
                drawMenu(g2);
                break;
            case GAME_MODE:
                drawGameMenu(g2);
                break;
        }


    }

    private void drawMenu(Graphics2D g2) {
        System.out.println("Menu");
//        this.repaint();
    }

    public void switchToGameMenu() {
        this.mode = GAME_MODE;
        this.remove(gb);
//        gb = null;

        this.add(mb);
        this.add(tf);
        this.add(cb);
        this.repaint();
    }

    private void drawGameMenu(Graphics2D g) {
        System.out.println("Clsoe enough");

    }

    public void switchToMainMenu(){
        this.mode = MENU_MODE;
        this.remove(tf);
        this.remove(mb);
        this.remove(cb);
//        mb = null;

//        gb = new GameButton(this);
        this.add(gb);
        this.repaint();

    }

    public void createConnection(){
        String name = tf.getText();

        if(name.contains("\\W") || name.length() > MAX_NAME_LENGTH){
            tf.setText("Incorrect name!");
            return;
        }
        this.resetNameChecking();

//        try {
//            socket = new Socket(this.address, this.port);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //TODO CONNECTION

        System.out.println("Name: "+ name);
        PanelThread pt = new PanelThread(address, port, frame, this, name);
        pt.start();

        // Game running
        this.mode = GAME_SEARCHING;


        this.repaint();
    }


    public void setGameStartedFlag(){
        this.mode = GAME_RUNNING;
    }

    public synchronized boolean isPlayerInitialized(){
        return this.playerInitialized;
    }

    public synchronized void setPlayerInitialized(){
        this.playerInitialized = true;
    }

    public synchronized boolean wasNameIncorrect(){
        return this.nameIncorrect;
    }

    public synchronized boolean wasNameChecked(){ return this.nameChecked; }

    public synchronized void setNameChecked(){
        this.nameChecked = true;
    }

    public synchronized void resetNameChecking(){
        this.nameChecked = false;
        this.nameIncorrect = false;
    }

    public synchronized void setNameIncorrect(){
        System.out.println("Name is incorrect!");
        this.nameIncorrect = true;

    }
}
