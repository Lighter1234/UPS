package SP;

import java.io.*;
import java.net.Socket;

public class MessageSender{

    private Socket socket;
    private Panel panel;
    private Game game;
    private OutputStream oos;

    public MessageSender(Socket socket, Panel panel, Game game){
        this.socket = socket;
        this.panel = panel;
        this.game = game;
        try {
            this.oos = socket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error while initializing socket output stream!");
        }

    }

    public MessageSender(Socket socket){
        this.socket = socket;
//        this.panel = panel;
        this.game = null;
        try {
            this.oos = socket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error while initializing socket output stream!");
        }

    }

    public synchronized boolean checkMessage(){
        return this.panel.isMessageReady();
    }

    public synchronized boolean sendMessage(String message) throws IOException{
//        if(this.panel.isDisconnected()){
//            return false;
//        }
        System.out.println("Sending: " + message);
         oos.write(message.getBytes());


        return true;
    }

}
