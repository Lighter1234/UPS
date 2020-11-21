package SP;

import java.io.*;
import java.net.Socket;

public class MessageSender extends Thread {

    private Socket socket;
    private Panel panel;
    private Game game;

    public MessageSender(Socket socket, Panel panel, Game game){
        this.socket = socket;
        this.panel = panel;
        this.game = game;
    }

    public void run(){
        OutputStream oos = null;
        String message;
        System.out.println("Select your name !");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            oos = socket.getOutputStream();
            message = "connect|0|" + br.readLine();
            oos.write(message.getBytes());


        while(socket.isConnected()){
//            if(checkMessage()){
//                System.out.println("Here");
//                    System.out.println("Sending message: " + panel.getMessage());
//                    oos.write(panel.getMessage().getBytes());
//                    panel.messageSent();
//            }
            message = "move|"+br.readLine()+ "|" +panel.getId();
            System.out.println("Sending: " + message);
            oos.write(message.getBytes());
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkMessage(){
        return this.panel.isMessageReady();
    }

}
