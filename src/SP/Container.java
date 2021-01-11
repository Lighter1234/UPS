package SP;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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

    private int amountOfVictories = 0;
    private int amountOfLoses = 0;

    public Container(JFrame frame, Menu menu, MessageReceiver mr, MessageSender ms,
                     JPanel buttonPanel, ConnectionChecker cc) {
        this.frame = frame;
        this.menu = menu;
        this.mr = mr;
        this.ms = ms;
        this.buttonPanel = buttonPanel;
        this.cc = cc;
        panel = new Panel(ms);
        this.mr.setPanel(panel);
        subPanel = new JPanel(); // For better look
        list = new JList();

        subPanel.add(list);
        frame.add(subPanel, BorderLayout.CENTER);
    }

    public void sendCreateLobbyMessage(String lobbyName) {
        System.out.println("Sending message to create");
        try {
            ms.sendMessage("create|" + lobbyName + "|" + username);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setUsername(String username){
        this.username = username;
    }

    public void sendJoinLobbyMessage(String lobbyName) {
        System.out.println("Sending message to join");
        try {
            ms.sendMessage("join|" + lobbyName + "|" + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshLobbies(String[] lobbies){
        if(lobbies[0].equals("(null)")){
            return;
        }
        list.setListData(lobbies);
        list.repaint();
    }

    public void sendRefreshRequest() {
        try {
            ms.sendMessage("refresh|"+username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void switchToGame(){
      this.frame.remove(buttonPanel);
      this.frame.remove(subPanel);
      panel = new Panel(this.ms);
      this.frame.add(panel);
      this.mr.setPanel(panel);
      this.frame.repaint();
      panel.repaint();
    }

    public String getUsername() {
        return this.username;
    }

    public void switchToLobby() {
        this.frame.remove(panel);

        this.frame.add(buttonPanel);
        this.frame.add(subPanel);

//        panel = null;
        this.frame.repaint();

    }

    public void setId(int id) {
        this.panel.setId(id);
    }

    public void dispose(){
        this.frame.dispose();
    }
}
