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
    private JPanel headerPanel;

    private JLabel nameLabel;
    private JLabel scoreLabel;

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
        panel.setLayout(new BorderLayout());
        this.mr.setPanel(panel);
        subPanel = new JPanel(); // For better look
        list = new JList();

        subPanel.add(list);
        frame.add(subPanel, BorderLayout.CENTER);
        System.out.println("Test mr:" + mr + " panel: " + panel.getCounter());
        headerPanel = new JPanel();
        nameLabel = new JLabel();
        scoreLabel = new JLabel();
        this.headerPanel.add(scoreLabel);
        this.headerPanel.add(nameLabel);


        frame.add(headerPanel, BorderLayout.NORTH);

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
        this.menu.setUsername(username);
        this.username = username;

        this.nameLabel.setText("Name: " + username);

        this.scoreLabel.setText("Victories: " + amountOfVictories + " Loses: " + amountOfLoses);

        this.headerPanel.repaint();

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
            list.removeAll();
            list.repaint();

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


    public void switchToGame(Panel p){
      this.frame.remove(buttonPanel);
      this.frame.remove(subPanel);
      if(p == null){
          panel = new Panel(this.ms);
      }else{
          panel = p;
      }
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
//        this.setUsername(null);
//        this.menu.setUsername(null);
    }

    public void setId(int id) {
        this.panel.setId(id);
    }

    public void dispose(){

        this.frame.dispose();
    }

    public Panel getPanel(){ return this.panel;}

    public boolean isUsernameSet(){
        return this.username == null;
    }

    public void incrementVictoryCounter(){
        this.amountOfVictories++;
        System.out.println("Victories:" + amountOfVictories);
        this.headerPanel.repaint();
        this.frame.repaint();
    }

    public void incrementDefeatCounter(){
        this.amountOfLoses++;
        System.out.println("Victories:" + amountOfVictories);

        this.headerPanel.repaint();
        this.frame.repaint();

    }
}
