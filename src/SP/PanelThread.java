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

    private MessageSender ms;
    private MessageReceiver mr;
    private ConnectionChecker cc;

    private JFrame frame;
    private Menu menu;

    public PanelThread(String address, int port, JFrame frame, Menu menu){
        this.address = address;
        this.port = port;
        this.frame = frame;
        this.menu = menu;
    }


    @Override
    public void run() {
        JLabel jl = new JLabel("Game is searching");
        jl.setSize(new Dimension(640, 480));
        frame.add(jl, BorderLayout.CENTER);

        Panel panel = new Panel();
        System.out.println("Before");
        while(!panel.hasGameBeenFound()){
            //wait
        }
        System.out.println("After");
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mr = new MessageReceiver(socket, panel);
        ms = new MessageSender(socket, panel);
        cc = new ConnectionChecker(socket, panel);

        mr.start();
        cc.start();

        frame.add(panel, BorderLayout.CENTER);


        menu.setGameStartedFlag();

    }
}
