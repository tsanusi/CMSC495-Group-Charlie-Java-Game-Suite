// MAIN MENU PROGRAM

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {
    private static final String gameOne = "GAME ONE",
                                gameTwo = "GAME TWO",
                                gameThree = "GAME THREE",
                                gameFour = "GAME FOUR",
                                gameFive = "GAME FIVE";
    private static JFrame frame;
    private static JPanel panel;
    private static JButton game1Button , game2Button, game3Button, game4Button, game5Button, aboutButton;
    private static JLabel firstDialogueBox;
    private static JLabel secondDialogueBox;
    public static void main(String[] args) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,450);
        panel = new JPanel();
        panel.setBounds(0,0,300,300);
        panel.setLayout(null);
        frame.setTitle ("CMSC 495 Game Suite");
        firstDialogueBox = new JLabel("CMSC 495 - Game Suite");
        firstDialogueBox.setBounds (80,5,240,30);
        secondDialogueBox= new JLabel ("Please Select One:");
        secondDialogueBox.setBounds (80,45,240,30);
        game1Button = new JButton(gameOne);
        game2Button = new JButton(gameTwo);
        game3Button = new JButton(gameThree);
        game4Button = new JButton(gameFour);
        game5Button = new JButton(gameFive);
        aboutButton = new JButton("about:");
        game1Button.setBounds (75,90,150,40);
        game2Button.setBounds (75,150,150,40);
        game3Button.setBounds (75,210,150,40);
        game4Button.setBounds (75,270,150,40);
        game5Button.setBounds (75,330,150,40);
        aboutButton.setBounds (100,370,100,20);
        panel.add (game1Button);
        panel.add (game2Button);
        panel.add (game3Button);
        panel.add(game4Button);
        panel.add(game5Button);
        panel.add (aboutButton);
        panel.add (firstDialogueBox);
        panel.add (secondDialogueBox);
        frame.add (panel);
        frame.setVisible(true);
        game1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    //<NAMEOFGAMEGILE>.main(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        game2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    //<NAMEOFGAMEGILE>.main(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        game3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    //<NAMEOFGAMEGILE>.main(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        game4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    //<NAMEOFGAMEGILE>.main(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        game5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    //<NAMEOFGAMEGILE>.main(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //custom title, no icon
                JOptionPane.showMessageDialog(frame,
                        "CMSC 495 Game Suite.\nCMSC 495\nFall 2021\nMark Munoz: Professor",
                        "CMSC 495 Game Suite",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
}