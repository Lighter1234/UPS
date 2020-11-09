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
        System.out.println("Testing output!");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            oos = socket.getOutputStream();


        while(socket.isConnected()){
            oos.write(br.readLine().getBytes());
            while(panel.messageReady()){
                oos.write(panel.getMessage().getBytes());
                panel.messageSent();
            }

        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
