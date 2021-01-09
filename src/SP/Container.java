package SP;

import javax.swing.*;
import java.awt.*;

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
    private JList list;
    private JPanel subPanel;
    private JPanel buttonPanel;

    public Container(JFrame frame, Menu menu, MessageReceiver mr, MessageSender ms,
                     JPanel buttonPanel, ConnectionChecker cc, Panel panel) {
        this.frame = frame;
        this.menu = menu;
        this.mr = mr;
        this.ms = ms;
        this.buttonPanel = buttonPanel;
        this.cc = cc;
        this.panel = panel;
        subPanel = new JPanel(); // For better look
        list = new JList();

        subPanel.add(list);
        frame.add(subPanel, BorderLayout.CENTER);
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

    public void refreshLobbies(String[] lobbies){
        if(lobbies[0].equals("(null)")){
            return;
        }
        list.setListData(lobbies);
        list.repaint();
    }

    public void sendRefreshRequest() {
        ms.sendMessage("refresh|"+username);
    }


    public void switchToGame(){
      this.frame.remove(buttonPanel);
      this.frame.remove(subPanel);
      this.frame.add(panel);
      panel.repaint();
    }
}
