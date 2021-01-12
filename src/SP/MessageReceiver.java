package SP;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReceiver extends Thread{

    private Socket socket;
    private Panel panel;
    private Game game;
    private Menu menu;

    public MessageReceiver(Socket socket, Panel panel, Game game){
        this.socket = socket;
        this.panel = panel;
        this.game = game;
    }

    public MessageReceiver(Socket socket, Menu menu){
        this.socket = socket;
//        this.panel = panel;
        this.game = null;
        this.menu = menu;
    }

    public void run(){
        BufferedReader br = null;
        String message;
        String[] splitted;
        String move;
        Message mes = null;

        try{
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Testing input!");
            while(true){

                message = br.readLine().trim();

                if(message != null && message.length() != 0){
                    System.out.println(message.trim());
                    splitted = message.split("\\|");
//                    System.out.println(Arrays.toString(splitted[0]));
                    for(int i = 0 ; splitted.length > i  ; i++) {
                        System.out.println("Splitted" + i + " [" + splitted[i] + "]");
                    }

                    handleMessage(splitted);

                }
            }
        }catch(IOException | NullPointerException e){
            e.printStackTrace();
            return;
        }

    }

    public void handleMessage(String[] splitted){
        String[] mes;
        Object[] options = {"OK"};
//        JOptionPane.showOptionDialog(panel,
//                splitted[splitted.length - 1],"Message",
//                JOptionPane.PLAIN_MESSAGE,
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                options,
//                options[0]);
        if(splitted[0].equals("205")){
            System.out.println(Integer.parseInt(splitted[1]) + " int " + "String: "+ splitted[1]);
            menu.setId(Integer.parseInt(splitted[1]));
            menu.setPlayerInitialized();
            menu.setNameChecked();
        }
        if(splitted[0].contains("-411")){
            System.out.println("Name was incorrect!");
            menu.setNameIncorrect();
            menu.setNameChecked();
        }
        if(splitted[0].contains("200")){
            mes = panel.getMessage().split("\\|");
//            System.out.println("Accepted message:" + mes.getMessage());
            String s[] = mes[1].split(",");
            panel.addCircle(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
            panel.repaint();

            panel.messageReceived();
        }
        if(splitted[0].contains("305")){
            String s[] = splitted[1].split(",");
            panel.addCircleFromOpponent(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
            panel.repaint();
        }
        if(splitted[0].contains("300")){
            panel.setGameId(Integer.parseInt(splitted[2]));
            menu.switchToGame(null);
            panel.gameFound();
            return;
        }

        if(splitted[0].contains("201")){    //Victory
            menu.switchToLobby();

        }
        if(splitted[0].contains("350")){
            menu.refreshLobbies(splitted[1]);
        }
        if(splitted[0].contains("210")){
//            menu.switchToChosenLobby();
        }
        if(splitted[0].contains("211")){
//            menu.switchToChosenLobby();
        }
        if(splitted[0].contains("222")){
            System.out.println("panel: " + panel.getCounter());
            this.setPanel(menu.getPanel());
            System.out.println("panel2: " + panel.getCounter());
            int max = 0;
            int thisPlayer = Integer.parseInt(splitted[1]);
            String[] moves = splitted[2].split(" ");

            for(int i = 0 ; i < moves.length ; i++){
                String[] divided = moves[i].split("-");
                String[] temp = divided[1].split(",");

                int x = Integer.parseInt(temp[0]);
                int y = Integer.parseInt(temp[1]);
                int player = Integer.parseInt(divided[0]);
                if(y == 9){
                    panel.setPointer(x, (short)max);
                    max = 0;
                }
                if(player == 0){
                    continue;
                }
                if(max < y){
                    max = y;
                }
                if(player == thisPlayer){
                    System.out.println("Here x:"+  x + ", y:" + y );
                    this.panel.addCircle(x, y);
                }else{
                    System.out.println("Oponent x:"+ x + ", y:" +  y );
                    this.panel.addCircleFromOpponent(x, y);
                }
                panel.repaint();


            }
            panel.repaint();
            menu.switchToGame(panel);

//            menu.switchToChosenLobby();
        }
        if(splitted[0].contains("-201")){   // Defeat

            JOptionPane.showOptionDialog(panel,
                    splitted[1],"Defeat",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            menu.switchToLobby();
        }

    }


    public void setPanel(Panel panel){
        this.panel = panel;
    }

}
