package SP;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class MainClass {

    public static void main(String[] args){
        JFrame frame = new JFrame();
      /*  Panel panel = new Panel();*/
        Menu menu = new Menu("147.228.63.10", 10000);
        frame.setLayout(new BorderLayout());
        frame.setSize(600,480);
        frame.add(menu, BorderLayout.CENTER);
//        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


      /*  try {
            Socket socket = new Socket("147.228.63.10", 10000);
            MessageSender ms = new MessageSender(socket, panel, new Game());
            MessageReceiver mr = new MessageReceiver(socket, panel, new Game());
            mr.start();
            mr.join();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/

    }
}
