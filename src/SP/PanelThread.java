package SP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class PanelThread extends Thread {

    private final String address;
    private final int port;

    private Panel panel;
    private Socket socket;
    private String name;

    private MessageSender ms;
    private MessageReceiver mr;
    private ConnectionChecker cc;

    private JFrame frame;
    private Menu menu;

    public PanelThread(String address, int port, JFrame frame, Menu menu, String name){
        this.address = address;
        this.port = port;
        this.frame = frame;
        this.menu = menu;
        this.name = name;


        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ms = new MessageSender(socket);
        this.panel = new Panel(ms);

        mr = new MessageReceiver(socket, panel, menu);
        mr.start();
        cc = new ConnectionChecker(socket, panel, ms);


    }


    @Override
    public void run() {
        ms.sendMessage("connect|0|" + name);
        //TODO same name
        System.out.println("Sent message");
        while(!menu.wasNameChecked()){
            if(menu.wasNameIncorrect()){
                System.out.println("NAme was incorrect");
                return;
            }
        }

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
//                    mode = GAME_MODE;
                }
            }
        });

        JLabel jl = new JLabel("Game is searching");
        jl.setSize(new Dimension(640, 480));
        frame.add(jl, BorderLayout.CENTER);

        System.out.println("Before");
        while(!panel.hasGameBeenFound()){
            //wait
        }
        System.out.println("After");



        cc.start();
        frame.remove(jl);
        panel.setSize(640, 480);
        frame.add(panel, BorderLayout.CENTER);
        frame.repaint();

        menu.setGameStartedFlag();

        frame.repaint();
        panel.repaint();

    }
}
