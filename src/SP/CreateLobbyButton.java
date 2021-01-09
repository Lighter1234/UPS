package SP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateLobbyButton extends JButton implements ActionListener {

    private Menu m;

    private JTextField name;

    public CreateLobbyButton(Menu m, JTextField name){
        super("Create lobby");
        this.addActionListener(this);
        this.m = m;
        this.name = name;
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String lobbyName = name.getText();
        if(lobbyName.length() == 0){
            JFrame frame= new JFrame();
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(frame,
                    "You have to input name of the lobby","No lobby name",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
        }else{
            m.createLobby(name.getText());
        }
    }
}
