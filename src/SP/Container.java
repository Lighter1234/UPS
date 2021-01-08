package SP;

import javax.swing.*;

public class Container {
    private JFrame frame;
    private Menu menu;
    private MessageReceiver mr;
    private MessageSender ms;
    private CreateLobbyButton clb;
    private JoinLobbyButton jlb;
    private ConnectionChecker cc;
    private Panel panel;

    private String username;

    public Container(JFrame frame, Menu menu, MessageReceiver mr, MessageSender ms,
                     CreateLobbyButton clb, JoinLobbyButton jlb, ConnectionChecker cc, Panel panel) {
        this.frame = frame;
        this.menu = menu;
        this.mr = mr;
        this.ms = ms;
        this.clb = clb;
        this.jlb = jlb;
        this.cc = cc;
        this.panel = panel;
    }

    public void sendCreateLobbyMessage(String lobbyName) {
        System.out.println("Sending message to create");
        ms.sendMessage("create|" + lobbyName + "|" + username);
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void sendJoinLobbyMessage(String lobbyName) {
        System.out.println("Sending message to join");
        ms.sendMessage("join|" + lobbyName + "|" + username);
    }
}
