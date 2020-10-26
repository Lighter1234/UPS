package SP;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class MainClass {

    public static void main(String[] args){
        JFrame frame = new JFrame();
        Panel panel = new Panel();

        frame.setSize(600,480);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            Socket socket = new Socket("127.0.0.1", 10001);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
