package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import model.Game;

public class ButtonController implements ActionListener {
    private Game game;
    
    public ButtonController(Game game) {
        this.game = game;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New"))
            game.newGame();
        else if (e.getActionCommand().equals("Check"))
            game.checkGame();
        else if (e.getActionCommand().equals("Exit"))
            System.exit(0);
        else if (e.getActionCommand().equals("Help on"))
            game.setHelp(((JCheckBox)e.getSource()).isSelected());
        else
            game.setSelectedNumber(Integer.parseInt(e.getActionCommand()));
    }
}