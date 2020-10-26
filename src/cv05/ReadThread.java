package cv05;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadThread extends Thread {

    private Socket socket;

    public ReadThread(Socket socket){
        this.socket = socket;
    }


    public void run(){
        ObjectInputStream ois = null;
        String message = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            while((message =  (String) ois.readObject()) != "\n"){

                System.out.println("Message Received: " + message);
            }
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
