package cv05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteThread extends Thread {

    private BufferedReader br;

    private Socket socket;

    public WriteThread(Socket socket){
        this.socket = socket;
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {

        String sentence;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());

            while((sentence = br.readLine()) != "\n"){
                    oos.writeObject(sentence);
            }

            oos.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
