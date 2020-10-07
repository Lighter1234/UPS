package SP;

import javax.swing.*;
import java.util.Arrays;

public class MainClass {

    public static void main(String[] args){
        JFrame frame = new JFrame();
        Panel panel = new Panel();

        frame.setSize(600,480);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

//        int[][] table = {{1,4,5}, {2,6,7}, {3,8,9}};
//        for(int i = 0 ; i < table.length ; i++){
//            System.out.println(Arrays.toString(table[i]));
//        }
//        System.out.println();
    }
}
