package SP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReceiver extends Thread{

    private Socket socket;
    private Panel panel;
    private Game game;

    public MessageReceiver(Socket socket, Panel panel, Game game){
        this.socket = socket;
        this.panel = panel;
        this.game = game;
    }

    public MessageReceiver(Socket socket, Panel panel){
        this.socket = socket;
        this.panel = panel;
        this.game = null;
    }

    public void run(){
        BufferedReader br = null;
        String message;
        String[] splitted;
        String move;
        Message mes = null;

        try{
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            System.out.println("Testing input!");
            while(true){
                message = br.readLine().trim();

                if(message != null){
                    System.out.println(message.trim());
                    splitted = message.split("\\|");
//                    System.out.println(Arrays.toString(splitted[0]));
                    for(int i = 0 ; splitted.length > i  ; i++) {
                        System.out.println("Splitted" + i + " [" + splitted[i] + "]");
                    }

                    handleMessage(splitted);

                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void handleMessage(String[] splitted){
        String[] mes;
        if(splitted[0].equals("205")){
            System.out.println(Integer.parseInt(splitted[1]) + " int " + "String: "+ splitted[1]);
            panel.setId(Integer.parseInt(splitted[1]));
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
            panel.gameFound();
            panel.repaint();
        }
    }

}
