package SP;

import java.net.Socket;

public class MessageSender extends Thread {

    private Socket socket;
    private Panel panel;
    private Game game;

    public MessageSender(Socket socket, Panel panel){
        this.socket = socket;
        this.panel = panel;
    }

    public void run(){


        while(socket.isConnected()){

            while(panel.hasMadeMove()){



            }

        }
    }

}
