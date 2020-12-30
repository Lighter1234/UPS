package SP;

import java.net.Socket;

public class ConnectionChecker extends Thread {

    private Socket socket;
    private Panel panel;

    public ConnectionChecker(Socket socket, Panel panel){
        this.socket = socket;
        this.panel = panel;
    }


    @Override
    public void run(){
        while(true){
            if(!socket.isConnected()){
                this.panel.setDisconnectedFlag();
                System.out.println("Server connection ran out!");
                break;
            }


        }
    }


}
