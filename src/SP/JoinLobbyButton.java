package SP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinLobbyButton extends JButton implements ActionListener {

    private Menu m;

    public JoinLobbyButton(Menu m){
        super("Join lobby");
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
        m.joinLobby();
    }

    //TODO join
}
