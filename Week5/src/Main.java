//////////////////////////////////////////////
// CMSC 495 - Fall 2021                     //
// Professor Mark Munoz                     //
//------------------------------------------//
// Main Menu:                               //
// Written in Java:                         //
//------------------------------------------//
// Class: Main.java                         //
// This class is the class for the Main     //
// menu of the entire Java Game Suite       //
//------------------------------------------//
// Edit of code is Final                    //
//////////////////////////////////////////////
import Slider_Puzzle.SliderPuzzle;
import Sudoku.controller.Sudoku;
import Word_Search.WordSearchProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {
    private static final String gameOne = "Maze",
                                gameTwo = "Snake",
                                gameThree = "Sudoku",
                                gameFour = "Word Search",
                                gameFive = "Slider Puzzle";
    private static JFrame frame;
    private static JPanel panel;
    private static JButton game1Button , game2Button, game3Button, game4Button, game5Button, aboutButton;
    private static JLabel firstDialogueBox;
    private static JLabel secondDialogueBox;
    public static void main(String[] args) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,500);
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
        aboutButton.setBounds (100,395,100,40);
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
            public void actionPerformed(ActionEvent ae) {Maze.Maze.main(null); } });
        game2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) { Snake.Snake.main(null); } });
        game3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) { Sudoku.main(null); } });
        game4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) { Word_Search.WordSearchProgram.main(null); } });
        game5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) { SliderPuzzle.main(null);} });
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(frame,
                        "CMSC 495 Game Suite.\n" +
                                "CMSC 495\n" +
                                "Fall 2021\n" +
                                "Mark Munoz: Professor\n\n" +
                                "Credits:\n\n" +
                                "(Oyewole: Creation of Doc outline and Snake Game\n " +
                                "Sherry: Word_Search.WordSearchProgram\n" +
                                "Jeffrey: \n" +
                                "Janee: \n" +
                                "Wayne Mack: Main Menu design and Maze Game\n",
                        "CMSC 495 Game Suite",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
}
