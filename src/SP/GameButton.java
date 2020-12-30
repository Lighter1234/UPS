package SP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButton extends JButton implements ActionListener {

    private Menu m;

    public GameButton(Menu m){
        super("New Game");
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
        m.switchToGameMenu();
    }
}
