package SP;

import java.net.Socket;

public class ConnectionChecker extends Thread {

    private Socket socket;
    private Panel panel;
    private MessageSender ms;

    private final long TEN_SECONDS = 10000;

    public ConnectionChecker(Socket socket, Panel panel, MessageSender ms){
        this.socket = socket;
        this.panel = panel;
        this.ms = ms;
    }


    @Override
    public void run(){
//        while(true){
//            if(!socket.isConnected()){
//                this.panel.setDisconnectedFlag();
//                System.out.println("Server connection ran out!");
//                return;
//            }
//
//            ms.sendMessage(panel.getId() + "|PING");
//            try {
//                this.sleep(TEN_SECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
    }


}
