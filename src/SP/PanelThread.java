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

    private Socket socket;
    private String name;

    private MessageSender ms;
    private MessageReceiver mr;
    private ConnectionChecker cc;
    private JFrame frame;

    private Container container;
    private Menu menu;

    private JPanel buttonPanel;

    public PanelThread(String address, int port, Menu menu, String name){
        this.address = address;
        this.port = port;
        this.menu = menu;
        this.name = name;


        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Cannot connect to server!");
            return;
        }


        ms = new MessageSender(socket);

        mr = new MessageReceiver(socket, menu);
        mr.start();
        cc = new ConnectionChecker(socket, menu, ms);

        JTextField clbt = new JTextField("", 2);
        JTextField jlbt = new JTextField("", 2);

        CreateLobbyButton clb = new CreateLobbyButton(menu, clbt);
        JoinLobbyButton jlb = new JoinLobbyButton(menu, jlbt);
        RefreshButton rb = new RefreshButton(menu);
        buttonPanel = new JPanel();


        buttonPanel.add(clb);
        buttonPanel.add(clbt);
        buttonPanel.add(jlb);
        buttonPanel.add(jlbt);
        buttonPanel.add(rb);

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
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    frame = null;
//                    mode = GAME_MODE;
                }
            }
        });
        frame.add(buttonPanel, BorderLayout.SOUTH);

        container = new Container(frame, menu, mr, ms, buttonPanel, cc);
        menu.setContainer(container);
        System.out.println("---------------New Container-------------");
    }


    @Override
    public void run() {
        try {
            ms.sendMessage("connect|0|" + name);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        menu.setContainer(container);

        System.out.println("Sent message");
        while(!menu.wasNameChecked()){
            if(menu.wasNameIncorrect()){
                System.out.println("NAme was incorrect");
                return;
            }
        }

        System.out.println("Setting up");
        container.setUsername(name);
        cc.start();

//        panel.setSize(640, 480);
//        frame.add(panel, BorderLayout.CENTER);
//        frame.repaint();

//        menu.setGameStartedFlag();
        frame.repaint();
//        panel.repaint();

    }
}
