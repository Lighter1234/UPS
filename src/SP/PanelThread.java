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

    private Container container;
    private Menu menu;

    public PanelThread(String address, int port, Menu menu, String name){
        this.address = address;
        this.port = port;
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
                    panel = null;  socket = null; frame = null;
//                    mode = GAME_MODE;
                }
            }
        });
        JTextField clbt = new JTextField("", 2);
        JTextField jlbt = new JTextField("", 2);

        CreateLobbyButton clb = new CreateLobbyButton(menu, clbt);
        JoinLobbyButton jlb = new JoinLobbyButton(menu, jlbt);
        RefreshButton rb = new RefreshButton(menu);
        JPanel buttonPanel = new JPanel();


        buttonPanel.add(clb);
        buttonPanel.add(clbt);
        buttonPanel.add(jlb);
        buttonPanel.add(jlbt);
        buttonPanel.add(rb);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        cc.start();

//        panel.setSize(640, 480);
//        frame.add(panel, BorderLayout.CENTER);
//        frame.repaint();

//        menu.setGameStartedFlag();
        container = new Container(frame, menu, mr, ms, buttonPanel, cc, panel);
        menu.setContainer(container);
        container.setUsername(name);
        frame.repaint();
//        panel.repaint();

    }
}
