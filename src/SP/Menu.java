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

//        try {
//            socket = new Socket(this.address, this.port);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //TODO CONNECTION
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(600,480);
//        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window and disconnect from the game?",
                        "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    panel = null; frame = null; socket = null;
                    mode = GAME_MODE;
                }
            }
        });
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
}
