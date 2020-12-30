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

        mr = new MessageReceiver(socket, panel);
        mr.start();
        cc = new ConnectionChecker(socket, panel, ms);


    }


    @Override
    public void run() {
        ms.sendMessage("connect|0|" + name);
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
        frame.add(panel, BorderLayout.CENTER);

        menu.setGameStartedFlag();

    }
}
