


import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.UIManager;
import controller.ButtonController;
import controller.SudokuController;
import model.Game;
import view.SudokuPanel;

public class Sudoku extends JFrame {
    public Sudoku() {
        super("Sudoku");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        Game game = new Game();

        ButtonController buttonController = new ButtonController(game);
        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setController(buttonController);
        add(buttonPanel, BorderLayout.EAST);

        SudokuPanel sudokuPanel = new SudokuPanel();
        SudokuController sudokuController = new SudokuController(sudokuPanel, game);
        sudokuPanel.setGame(game);
        sudokuPanel.setController(sudokuController);
        add(sudokuPanel, BorderLayout.CENTER);

        game.addObserver(buttonPanel);
        game.addObserver(sudokuPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ex) { ex.printStackTrace(); }
        new Sudoku();
    }
}