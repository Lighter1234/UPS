package SP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateLobbyButton extends JButton implements ActionListener {

    private Menu m;

    public CreateLobbyButton(Menu m){
        super("Create lobby");
        this.addActionListener(this);
        this.m = m;
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        m.createLobby();
    }
}
