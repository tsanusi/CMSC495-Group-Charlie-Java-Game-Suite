package Slider_Puzzle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SliderPuzzle extends JFrame implements ActionListener {

    // Create JButtons that will display puzzle image tiles
    private JButton jButton1, jButton2, jButton3, jButton4, jButton5, jButton6, jButton7, jButton8, jButton9, jButton10,
            jButton11, jButton12, firstButton, secondButton;

    // Toggle buttons used to determine what puzzle is displayed
    private JToggleButton newHalloweenPuzzleButton, newChristmasPuzzleButton, newNewYearsPuzzleButton;

    // New Puzzle button group
    private ButtonGroup newPuzzlesButtonGroup;

    Icon halloweenIcon0 = new ImageIcon();
    Icon halloweenIcon1 = new ImageIcon();
    Icon halloweenIcon2 = new ImageIcon();
    Icon halloweenIcon3 = new ImageIcon();
    Icon halloweenIcon4 = new ImageIcon();
    Icon halloweenIcon5 = new ImageIcon();
    Icon halloweenIcon6 = new ImageIcon();
    Icon halloweenIcon7 = new ImageIcon();
    Icon halloweenIcon8 = new ImageIcon();
    Icon halloweenIcon9 = new ImageIcon();
    Icon halloweenIcon10 = new ImageIcon();
    Icon halloweenIcon11 = new ImageIcon();
    Icon finalPictureIcon0 = new ImageIcon();
    Icon finalPictureIcon1 = new ImageIcon();
    Icon finalPictureIcon2 = new ImageIcon();


    Icon christmasIcon0 = new ImageIcon();
    Icon christmasIcon1 = new ImageIcon();
    Icon christmasIcon2 = new ImageIcon();
    Icon christmasIcon3 = new ImageIcon();
    Icon christmasIcon4 = new ImageIcon();
    Icon christmasIcon5 = new ImageIcon();
    Icon christmasIcon6 = new ImageIcon();
    Icon christmasIcon7 = new ImageIcon();
    Icon christmasIcon8 = new ImageIcon();
    Icon christmasIcon9 = new ImageIcon();
    Icon christmasIcon10 = new ImageIcon();
    Icon christmasIcon11 = new ImageIcon();


    Icon newYears0 = new ImageIcon();
    Icon newYears1 = new ImageIcon();
    Icon newYears2 = new ImageIcon();
    Icon newYears3 = new ImageIcon();
    Icon newYears4 = new ImageIcon();
    Icon newYears5 = new ImageIcon();
    Icon newYears6 = new ImageIcon();
    Icon newYears7 = new ImageIcon();
    Icon newYears8 = new ImageIcon();
    Icon newYears9 = new ImageIcon();
    Icon newYears10 = new ImageIcon();
    Icon newYears11 = new ImageIcon();

    // Boolean flag to determine if button has been clicked more than once
    private boolean firstClick = false;



    /**
     * Constructor for ImagePuzzle
     */
    public SliderPuzzle() {

        super("Slider Puzzle");

        halloweenImageDivider(new File("src/Slider_Puzzle/resources/villains.jpg"));
        christmasImageDivider(new File("src/Slider_Puzzle/resources/Christmas.jpg"));
        newYearsImageDivider(new File("src/Slider_Puzzle/resources/NewYears.jpg"));

        halloweenIcon0 = new ImageIcon("Halloween0.jpg");
        halloweenIcon1 = new ImageIcon("Halloween1.jpg");
        halloweenIcon2 = new ImageIcon("Halloween2.jpg");
        halloweenIcon3 = new ImageIcon("Halloween3.jpg");
        halloweenIcon4 = new ImageIcon("Halloween4.jpg");
        halloweenIcon5 = new ImageIcon("Halloween5.jpg");
        halloweenIcon6 = new ImageIcon("Halloween6.jpg");
        halloweenIcon7 = new ImageIcon("Halloween7.jpg");
        halloweenIcon8 = new ImageIcon("Halloween8.jpg");
        halloweenIcon9 = new ImageIcon("Halloween9.jpg");
        halloweenIcon10 = new ImageIcon("Halloween10.jpg");
        halloweenIcon11 = new ImageIcon("Halloween11.jpg");
        finalPictureIcon0 = new ImageIcon("Villains.jpg");
        finalPictureIcon1 = new ImageIcon("Christmas.jpg");
        finalPictureIcon2 = new ImageIcon("NewYears.jpg");


        christmasIcon0 = new ImageIcon("Christmas0.jpg");
        christmasIcon1 = new ImageIcon("Christmas1.jpg");
        christmasIcon2 = new ImageIcon("Christmas2.jpg");
        christmasIcon3 = new ImageIcon("Christmas3.jpg");
        christmasIcon4 = new ImageIcon("Christmas4.jpg");
        christmasIcon5 = new ImageIcon("Christmas5.jpg");
        christmasIcon6 = new ImageIcon("Christmas6.jpg");
        christmasIcon7 = new ImageIcon("Christmas7.jpg");
        christmasIcon8 = new ImageIcon("Christmas8.jpg");
        christmasIcon9 = new ImageIcon("Christmas9.jpg");
        christmasIcon10 = new ImageIcon("Christmas10.jpg");
        christmasIcon11 = new ImageIcon("Christmas11.jpg");


        newYears0 = new ImageIcon("NewYears0.jpg");
        newYears1 = new ImageIcon("NewYears1.jpg");
        newYears2 = new ImageIcon("NewYears2.jpg");
        newYears3 = new ImageIcon("NewYears3.jpg");
        newYears4 = new ImageIcon("NewYears4.jpg");
        newYears5 = new ImageIcon("NewYears5.jpg");
        newYears6 = new ImageIcon("NewYears6.jpg");
        newYears7 = new ImageIcon("NewYears7.jpg");
        newYears8 = new ImageIcon("NewYears8.jpg");
        newYears9 = new ImageIcon("NewYears9.jpg");
        newYears10 = new ImageIcon("NewYears10.jpg");
        newYears11 = new ImageIcon("NewYears11.jpg");


        /**
         * Create the main panel and subpanels that will contain a new puzzle option and puzzle hints
         */
        JPanel backGroundPanel = new JPanel();
        JPanel subPanel = new JPanel();
        JPanel mainPanel = new JPanel();
        JPanel puzzleChooserPanel = new JPanel();
        JPanel finalImageSubPanel = new JPanel();

        /**
         * Create JButtons that will display the default image icons
         */
        jButton1 = new JButton();
        jButton1.addActionListener(this);
        jButton2 = new JButton();
        jButton2.addActionListener(this);
        jButton3 = new JButton();
        jButton3.addActionListener(this);
        jButton4 = new JButton();
        jButton4.addActionListener(this);
        jButton5 = new JButton();
        jButton5.addActionListener(this);
        jButton6 = new JButton();
        jButton6.addActionListener(this);
        jButton7 = new JButton();
        jButton7.addActionListener(this);
        jButton8 = new JButton();
        jButton8.addActionListener(this);
        jButton9 = new JButton();
        jButton9.addActionListener(this);
        jButton10 = new JButton();
        jButton10.addActionListener(this);
        jButton11 = new JButton();
        jButton11.addActionListener(this);
        jButton12 = new JButton();
        jButton12.addActionListener(this);

        jButton1.setIcon(halloweenIcon11);
        jButton2.setIcon(halloweenIcon1);
        jButton3.setIcon(halloweenIcon8);
        jButton4.setIcon(halloweenIcon7);
        jButton5.setIcon(halloweenIcon9);
        jButton6.setIcon(halloweenIcon5);
        jButton7.setIcon(halloweenIcon6);
        jButton8.setIcon(halloweenIcon3);
        jButton9.setIcon(halloweenIcon2);
        jButton10.setIcon(halloweenIcon4);
        jButton11.setIcon(halloweenIcon10);
        jButton12.setIcon(halloweenIcon0);


        JLabel jLabelPowerIcon = new JLabel(halloweenIcon11);

        JLabel newPuzzleJLabel = new JLabel("Choose a puzzle: ");
        JButton finalImageJButton = new JButton("Halloween");
        JButton finalImageJButton1 = new JButton("Christmas");
        JButton finalImageJButton2 = new JButton("New Years");



        newPuzzlesButtonGroup = new ButtonGroup();

        newHalloweenPuzzleButton = new JToggleButton("Halloween");
        newHalloweenPuzzleButton.setSelected(true);
        newChristmasPuzzleButton = new JToggleButton("Christmas");
        newNewYearsPuzzleButton = new JToggleButton("New Years");


        newPuzzlesButtonGroup.add(newHalloweenPuzzleButton);
        newPuzzlesButtonGroup.add(newChristmasPuzzleButton);
        newPuzzlesButtonGroup.add(newNewYearsPuzzleButton);



        JLabel powerIconDefinitionLabel = new JLabel("\nSelect two image tiles from above to swap until puzzle is" +
                " complete: \n");

        JLabel finalPictureLabel = new JLabel("Puzzle Hints:         ");


        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));

        backGroundPanel.setLayout(new BoxLayout(backGroundPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(524, 522));

        mainPanel.setLayout(new GridLayout(4, 3, 0, 0));
        setSize(new Dimension(524, 522));
        mainPanel.add(jButton1);
        mainPanel.add(jButton2);
        mainPanel.add(jButton3);
        mainPanel.add(jButton4);
        mainPanel.add(jButton5);
        mainPanel.add(jButton6);
        mainPanel.add(jButton7);
        mainPanel.add(jButton8);
        mainPanel.add(jButton9);
        mainPanel.add(jButton10);
        mainPanel.add(jButton11);
        mainPanel.add(jButton12);


        subPanel.add(powerIconDefinitionLabel);


        puzzleChooserPanel.add(newPuzzleJLabel);
        puzzleChooserPanel.add(newHalloweenPuzzleButton);
        puzzleChooserPanel.add(newChristmasPuzzleButton);
        puzzleChooserPanel.add(newNewYearsPuzzleButton);


        finalImageSubPanel.add(finalPictureLabel);
        finalImageSubPanel.add(finalImageJButton);

        finalImageSubPanel.add(finalImageJButton1);

        finalImageSubPanel.add(finalImageJButton2);

        backGroundPanel.add(mainPanel);
        backGroundPanel.add(subPanel);
        backGroundPanel.add(puzzleChooserPanel);
        backGroundPanel.add(finalImageSubPanel);
        add(backGroundPanel);


        // Add action listeners to final image hint JButtons
        finalImageJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "", "Hint", JOptionPane.INFORMATION_MESSAGE, finalPictureIcon0);


            }
        });

        finalImageJButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "", "Hint", JOptionPane.INFORMATION_MESSAGE, finalPictureIcon1);
            }
        });

        finalImageJButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "", "Hint", JOptionPane.INFORMATION_MESSAGE, finalPictureIcon2);
            }
        });

        newHalloweenPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1.setIcon(halloweenIcon11);
                jButton2.setIcon(halloweenIcon1);
                jButton3.setIcon(halloweenIcon8);
                jButton4.setIcon(halloweenIcon7);
                jButton5.setIcon(halloweenIcon9);
                jButton6.setIcon(halloweenIcon5);
                jButton7.setIcon(halloweenIcon6);
                jButton8.setIcon(halloweenIcon3);
                jButton9.setIcon(halloweenIcon2);
                jButton10.setIcon(halloweenIcon4);
                jButton11.setIcon(halloweenIcon10);
                jButton12.setIcon(halloweenIcon0);
                jLabelPowerIcon.setIcon(halloweenIcon11);

            }
        });

        newChristmasPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1.setIcon(christmasIcon11);
                jButton2.setIcon(christmasIcon1);
                jButton3.setIcon(christmasIcon2);
                jButton4.setIcon(christmasIcon10);
                jButton5.setIcon(christmasIcon3);
                jButton6.setIcon(christmasIcon8);
                jButton7.setIcon(christmasIcon5);
                jButton8.setIcon(christmasIcon7);
                jButton9.setIcon(christmasIcon6);
                jButton10.setIcon(christmasIcon9);
                jButton11.setIcon(christmasIcon4);
                jButton12.setIcon(christmasIcon0);
                jLabelPowerIcon.setIcon(christmasIcon11);

            }
        });


        newNewYearsPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1.setIcon(newYears6);
                jButton2.setIcon(newYears7);
                jButton3.setIcon(newYears2);
                jButton4.setIcon(newYears11);
                jButton5.setIcon(newYears4);
                jButton6.setIcon(newYears5);
                jButton7.setIcon(newYears0);
                jButton8.setIcon(newYears1);
                jButton9.setIcon(newYears8);
                jButton10.setIcon(newYears10);
                jButton11.setIcon(newYears9);
                jButton12.setIcon(newYears3);
                jLabelPowerIcon.setIcon(newYears11);

            }
        });




        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }


    /**
     * Splits the complete Halloween image into separate tiles
     *
     * @param file to split
     */
    public void halloweenImageDivider(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fis);

            int rows = 4;
            int cols = 3;
            int tiles = rows * cols;

            int tileWidth = image.getWidth() / cols;
            int tileHeight = image.getHeight() / rows;
            int count = 0;
            BufferedImage images[] = new BufferedImage[tiles];
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    images[count] = new BufferedImage(tileWidth, tileHeight, image.getType());
                    Graphics2D gr = images[count++].createGraphics();
                    gr.drawImage(image, 0, 0, tileWidth, tileHeight, tileWidth * y, tileHeight * x,
                            tileWidth * y + tileWidth, tileHeight * x + tileHeight, null);
                    gr.dispose();
                }
            }
            for (int i = 0; i < images.length; i++) {
                ImageIO.write(images[i], "jpg", new File("Halloween" + i + ".jpg"));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Splits the complete Christmas image into separate tiles
     *
     * @param file to split
     */
    public void christmasImageDivider(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fis);

            int rows = 4;
            int columns = 3;
            int tiles = rows * columns;

            int tileWidth = image.getWidth() / columns;
            int tileHeight = image.getHeight() / rows;
            int count = 0;
            BufferedImage images[] = new BufferedImage[tiles];
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < columns; y++) {
                    images[count] = new BufferedImage(tileWidth, tileHeight, image.getType());
                    Graphics2D gr = images[count++].createGraphics();
                    gr.drawImage(image, 0, 0, tileWidth, tileHeight, tileWidth * y, tileHeight * x,
                            tileWidth * y + tileWidth, tileHeight * x + tileHeight, null);
                    gr.dispose();
                }
            }
            for (int i = 0; i < images.length; i++) {
                ImageIO.write(images[i], "jpg", new File("Christmas" + i + ".jpg"));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Splits the complete New Years image into separate tiles
     *
     * @param file to split
     */
    public void newYearsImageDivider(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fis);

            int rows = 4;
            int columns = 3;
            int tiles = rows * columns;

            int tileWidth = image.getWidth() / columns;
            int tileHeight = image.getHeight() / rows;
            int count = 0;
            BufferedImage images[] = new BufferedImage[tiles];
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < columns; y++) {
                    images[count] = new BufferedImage(tileWidth, tileHeight, image.getType());
                    Graphics2D gr = images[count++].createGraphics();
                    gr.drawImage(image, 0, 0, tileWidth, tileHeight, tileWidth * y, tileHeight * x,
                            tileWidth * y + tileWidth, tileHeight * x + tileHeight, null);
                    gr.dispose();
                }
            }
            for (int i = 0; i < images.length; i++) {
                ImageIO.write(images[i], "jpg", new File("NewYears" + i + ".jpg"));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if (!firstClick) {
            firstClick = true;
            firstButton = (JButton) e.getSource();
        } else {
            firstClick = false;
            secondButton = (JButton) e.getSource();
            if (secondButton != firstButton) {
                swapButtons();
                isComplete();
            }
        }
    }


    /**
     * Swaps the two selected image tiles
     */
    public void swapButtons(){
        Icon icon1 = firstButton.getIcon();
        Icon icon2 = secondButton.getIcon();

        firstButton.setIcon(icon2);
        secondButton.setIcon(icon1);


    }

    /**
     * Displays winning message when puzzle is complete
     */
    public void isComplete(){
        if(newHalloweenPuzzleButton.isSelected()){
            if(jButton1.getIcon() == halloweenIcon0 && jButton2.getIcon() == halloweenIcon1 && jButton3.getIcon() ==
                    halloweenIcon2 && jButton4.getIcon() == halloweenIcon3 && jButton5.getIcon() == halloweenIcon4 &&
                    jButton6.getIcon() == halloweenIcon5 && jButton7.getIcon() == halloweenIcon6 && jButton8.getIcon() ==
                    halloweenIcon7 && jButton9.getIcon() == halloweenIcon8 && jButton10.getIcon() == halloweenIcon9 &&
                    jButton11.getIcon() == halloweenIcon10 && jButton12.getIcon() == halloweenIcon11){
                JOptionPane.showMessageDialog(null, "Puzzle Complete!");
            }
        }else if(newChristmasPuzzleButton.isSelected()){
            if(jButton1.getIcon() == christmasIcon0 && jButton2.getIcon() == christmasIcon1 && jButton3.getIcon() ==
                    christmasIcon2 && jButton4.getIcon() == christmasIcon3 && jButton5.getIcon() == christmasIcon4 &&
                    jButton6.getIcon() == christmasIcon5 && jButton7.getIcon() == christmasIcon6 && jButton8.getIcon() ==
                    christmasIcon7 && jButton9.getIcon() == christmasIcon8 && jButton10.getIcon() == christmasIcon9 &&
                    jButton11.getIcon() == christmasIcon10 && jButton12.getIcon() == christmasIcon11){
                JOptionPane.showMessageDialog(null, "Puzzle Complete!");
            }


        }else if(newNewYearsPuzzleButton.isSelected()){
            if(jButton1.getIcon() == newYears0 && jButton2.getIcon() == newYears1 && jButton3.getIcon() ==
                    newYears2 && jButton4.getIcon() == newYears3 && jButton5.getIcon() == newYears4 &&
                    jButton6.getIcon() == newYears5 && jButton7.getIcon() == newYears6 && jButton8.getIcon() ==
                    newYears7 && jButton9.getIcon() == newYears8 && jButton10.getIcon() == newYears9 &&
                    jButton11.getIcon() == newYears10 && jButton12.getIcon() == newYears11){
                JOptionPane.showMessageDialog(null, "Puzzle Complete!");
            }


        }


    }

    public static void main(String[] args) {
        SliderPuzzle sliderPuzzle = new SliderPuzzle();

    }

}



