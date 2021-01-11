package SP;

import java.io.IOException;
import java.net.Socket;

public class ConnectionChecker extends Thread {

    private Socket socket;
    private Menu menu;
    private MessageSender ms;

    private final long TEN_SECONDS = 10000;

    public ConnectionChecker(Socket socket, Menu menu, MessageSender ms){
        this.socket = socket;
        this.menu = menu;
        this.ms = ms;
    }


    @Override
    public void run(){
        while(true){

            try {
                this.sleep(TEN_SECONDS);
                ms.sendMessage( "ping|" + menu.getUserName());
                System.out.println("Sending : " + "ping|" + menu.getUserName());
            } catch (InterruptedException | IOException e) {
//                e.printStackTrace();
                System.out.println("Server is not reachable!");
                this.menu.switchToMainMenu();
                return;
            }

        }
    }


}
