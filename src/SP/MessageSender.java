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
            OutputStream oos = socket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error while initializing socket output stream!");
        }

    }

    public MessageSender(Socket socket, Panel panel){
        this.socket = socket;
        this.panel = panel;
        this.game = null;
        try {
            OutputStream oos = socket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error while initializing socket output stream!");
        }

    }

    public synchronized boolean checkMessage(){
        return this.panel.isMessageReady();
    }

    public synchronized boolean sendMessage(String message){
        if(this.panel.isDisconnected()){

            return false;
        }




        return true;
    }

}
