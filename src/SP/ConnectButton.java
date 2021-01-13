package SP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectButton extends JButton implements ActionListener {
    private Menu m;

    public ConnectButton(Menu m){
        super("Connect");
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
        if(m.getUserName() != null){
            JFrame panel = new JFrame();
            Object[] options = {"OK"};

            JOptionPane.showOptionDialog(panel,
                    "You're already connected, exit the window and then connect again","Error",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            return;
        }
        m.createConnection();
    }
}
