package SP;

import javax.swing.*;
import java.awt.*;


public class MainClass {

    public static void main(String[] args){
        String ip;
        int port;
        switch(args.length){
            case 2:
                ip = args[0];
                port = Integer.parseInt(args[1]);
                break;
            case 1:
                ip = args[0];
                port = 10001;
                break;
            default:
                ip = "147.228.63.10";
                port = 10001;
                break;
        }

        JFrame frame = new JFrame();
        Menu menu = new Menu(ip, port);
        frame.setLayout(new BorderLayout());
        frame.setSize(600,480);
        frame.add(menu, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



    }
}
