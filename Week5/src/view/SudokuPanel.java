package sudoku.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import sudoku.controller.SudokuController;
import sudoku.model.Game;
import sudoku.model.UpdateAction;

public class SudokuPanel extends JPanel implements Observer {
    
    private static final Color COLOR_CANDIDATE = new Color(102, 153, 255);

    private Field[][] fields;       
    private JPanel[][] panels;      

    public SudokuPanel() {
        super(new GridLayout(3, 3));

        panels = new JPanel[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                panels[y][x] = new JPanel(new GridLayout(3, 3));
                panels[y][x].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                add(panels[y][x]);
            }
        }

        fields = new Field[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fields[y][x] = new Field(x, y);
                panels[y / 3][x / 3].add(fields[y][x]);
            }
        }
    }

    public void update(Observable o, Object arg) {
        switch ((UpdateAction)arg) {
            case NEW_GAME:
                setGame((Game)o);
                break;
            case CHECK:
                setGameCheck((Game)o);
                break;
            case SELECTED_NUMBER:
            case CANDIDATES:
            case HELP:
                setCandidates((Game)o);
                break;
        }
    }

    public void setGame(Game game) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fields[y][x].setBackground(Color.WHITE);
                fields[y][x].setNumber(game.getNumber(x, y), false);
            }
        }
    }

    private void setGameCheck(Game game) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fields[y][x].setBackground(Color.WHITE);
                if (fields[y][x].getForeground().equals(Color.BLUE))
                    fields[y][x].setBackground(game.isCheckValid(x, y) ? Color.GREEN : Color.RED);
            }
        }
    }

    private void setCandidates(Game game) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fields[y][x].setBackground(Color.WHITE);
                if (game.isHelp() && game.isSelectedNumberCandidate(x, y))
                    fields[y][x].setBackground(COLOR_CANDIDATE);
            }
        }
    }

    public void setController(SudokuController sudokuController) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++)
                panels[y][x].addMouseListener(sudokuController);
        }
    }
}