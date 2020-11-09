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
        System.out.println("Starting input:");
        String sentence = "";
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            while((sentence = br.readLine()) != "\n"){
                String str = prepareChatHeader(sentence);
                System.out.println(str);
                    oos.write(str.getBytes("UTF-8"));
            }

            oos.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private String prepareChatHeader(String str){
        int length = str.length();
        String ln = "";
        String lengthOf;
        if(length < 10){
            lengthOf = "000" + (length + 2);
            ln = "0" + length;
        }else if(length < 100){
            lengthOf = "0" + (length+2);
        }else{
            lengthOf = (length + 3)+"";
        }

        return "KIVUPSnick" +  "" + "" + lengthOf  +""+ ln + str;
    }
}
