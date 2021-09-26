package Word_Search;/*
 * File: Word_Search.WordSearchProgram.java
 * Author: Sherry Funches
 * Revision Date: September 9, 2021
 * Purpose: This file generates the GUI for a WordSearch puzzle. 
 * The user selects a file with the puzzle data. This class takes data
 * formatted by the Word_Search.PuzzleGenerator class and displays a word list and a grid of letters.
 * Users can highlight letters on the grid and cross off letters on the list.
 * When complete, the user clicks submit, and this class will score the number of
 * words correctly identified and display the locations of unidentified words in
 * red font on the grid. 
 * 
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class WordSearchProgram  extends JFrame implements MouseMotionListener, MouseListener{
	
	File selectedPuzzleFile;
	//Font of characters on word search Grid
	private Font letterFont = null;
	//Letters (JLabels) placed on the search grid
	private ArrayList<JLabel> letters;
	private PuzzleGenerator generator;
	//Words hidden in the puzzle
	private ArrayList<Word> words;
	//words on word list panel
	 private Set <JLabel> wordListLabels;
	//metrics for fonts in word list panel
	private FontMetrics wordListMetrics;
	
	//is this the first puzzle upon opening programming
	private boolean firstPuzzle = true;
	
	//highlights on word search grid
 	private ArrayList <Line2D.Double> lines = new ArrayList<Line2D.Double>();
 	//coordinates etc for drawing highlights on grid
    private double startx = 0.0;
    private double starty = 0.0;
    private boolean mouseDragged = false;
    private int lineCounter =0;
    //strike through for words on word list panel
   	private HashMap<JComponent, Line2D.Double> strikeThroughs = new HashMap<JComponent,Line2D.Double>();
    //Highlight while cursor is being dragged over the grid
   	private Line2D.Double tempLine;
    
    
    //GUI Components
   	private JButton selectButton;
    private JButton submitButton;
    private JButton undoButton;
    private JButton clearButton;
    private JButton loadButton;
    private JTextField selectedFileField;
    private JPanel gridPanel;
    private JPanel wordListPanel;
    private JScrollPane wordListScrollPane;
    
	
	//constructor
	public WordSearchProgram() {
		//set up GUI
		super("Word_Search.Word Search");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
		setResizable(true); // Don't let the user resize window.
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation( // Center window on screen.
					(screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
		
		//Choose font size based on screen size
		int fontSize = 0;
		//max number of rows allowed for puzzles.
		//max number of columns
		int maxColumns = 15;
		int maxRows = 10;
		//TODO font size calculations should include number of rows
		//DELETE: THe height - 200 so it fits in screen.  Then numRows *(40 +FontHeight);
		//That would be ((height -200)/numRows) -40 (padding) = FontHeight;
		//For custom fit ((height-200)/numRows)/3 = FontHeight and Padding are the same. Then feed
		//To constructors.
		//This would be for the max.. I wouldn't want the font being too big for min.
		/*
		if (screen.width < screen.height) {
			fontSize = screen.width/40;
		}else {
			fontSize = screen.height/40;
		}
		*/
		
		double smallestScreenDimension = Math.min(screen.height, screen.width);
		if (smallestScreenDimension>=800) {
			fontSize = (int) (smallestScreenDimension/40);
		}else {
			fontSize =(int) ((smallestScreenDimension-200)/maxRows)/3; 
		}
		
		
		letterFont = new Font("Monospaced", Font.PLAIN, fontSize);
		
		
		//Initialize and add components to GUI
		JPanel selectPanel = new JPanel();
		selectButton = new JButton("Select Puzzle");
		selectedFileField =  new JTextField(12);
		selectedFileField.setEditable(false);
		loadButton = new JButton("Load Puzzle");
		selectPanel.add(selectButton);
		selectPanel.add(selectedFileField);
		selectPanel.add(loadButton);
		JPanel buttonPanel = new JPanel();
		submitButton = new JButton("Submit");
		undoButton = new JButton("Undo");
		clearButton = new JButton("Clear");
		buttonPanel.add(submitButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(clearButton);
		
		selectButton.addActionListener(e->selectPuzzleFile());
		submitButton.addActionListener(e->scorePuzzle());
		loadButton.addActionListener(e-> loadPuzzle());
		clearButton.addActionListener(e->clear());
		undoButton.addActionListener(e->undo());
		
		//Add components to JFrame
		JPanel allButtonsPanel = new JPanel();
		allButtonsPanel.add(selectPanel);
		allButtonsPanel.add(buttonPanel);
		add(allButtonsPanel, BorderLayout.PAGE_END);
				
	}
	
	 class GridPanel extends JPanel  {
 		
	    	
 		GridPanel () {
 			//Changed Padding to size of font, not 20.
 			GridLayout layout = new GridLayout(generator.getNumRows(),generator.getNumColumns(), letterFont.getSize(), letterFont.getSize());
 			Border paddingBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
 			Border lineBorder  = BorderFactory.createLineBorder(Color.BLACK);
 			Border finalBorder = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);
 			setBorder(finalBorder);
 			
 			setLayout(layout);
 			
 			//add letters to panel
 			 for (JLabel letter: letters) {
 	        		 add(letter);
 	        }	
 		}
 		
 		protected void paintComponent(Graphics g) {

			 // Create a Graphics2D drawing context for drawing on the panel.
			Graphics2D g2 = (Graphics2D) g.create();

			/*
			 * Turn on antialiasing in this graphics context for better drawing.
			 */
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			/*
			 * Fill in the entire drawing area with white.
			 */
			g2.setPaint(Color.WHITE);
			g2.fillRect(0, 0, getWidth(), getHeight()); 
				
			AffineTransform savedTransform = g2.getTransform();
			
			
			//Highlight while mouse is dragged but before released
			if (tempLine!= null) {
				
				Color lineColor = new Color(0.96F, 0.93f, 0.26f, 0.3f);
				g2.setPaint(lineColor);
				g2.setStroke(new BasicStroke(letterFont.getSize() +1));
				g2.draw(tempLine);
				g2.setTransform(savedTransform);			
			}
			 
			//draw final user highlights onto the panel
			for (Line2D.Double line: lines) {
				Color lineColor = new Color(0.96F, 0.93f, 0.26f, 0.3f);
				g2.setPaint(lineColor);
				g2.setStroke(new BasicStroke(letterFont.getSize() +1));
				g2.draw(line);
				g2.setTransform(savedTransform);		
			}
 		}
	 }
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		//TODO 
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		startx = e.getX();
		starty= e.getY();
		System.out.println(startx + " " + starty);
		System.out.println("Grid Panel Height" + gridPanel.getHeight());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (mouseDragged == true) {
			double x = e.getX();
			double y = e.getY();
			
			Line2D.Double line = new Line2D.Double(startx, starty, x,y);
			lines.add(line);
			lineCounter++;
			startx = 0;
			starty= 0;
			x = 0;
			y = 0;
			mouseDragged= false;
			tempLine = null;
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		//draw highlight and update while mouse is dragged.
		mouseDragged = true;
		double x = e.getX();
		double y = e.getY();
		tempLine = new Line2D.Double(startx, starty, x, y);	
		repaint();		
				
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	//clears highlights from word search grid and strikethroughs from list
	private void clear() {
		lineCounter=0;
		lines.clear();
		strikeThroughs.clear();
		repaint();
	}
	
	//removes last highlight from the wordsearch grid
	private void undo() {
		if(!lines.isEmpty()) {
			lines.remove(lineCounter-1);
			lineCounter--;	
			repaint();
		}
	}
	
	private void selectPuzzleFile() {
		JFileChooser fc = new JFileChooser("Word_Search/puzzleFiles");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			selectedPuzzleFile = fc.getSelectedFile();
			selectedFileField.setText(selectedPuzzleFile.getName());
		}
	}
	

	
	private void loadPuzzle() {
		if(selectedPuzzleFile == null) {
			JOptionPane.showMessageDialog(this,
				    "Please Select File.",
				    "File Load Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//clear any existing marks:
		clear();
		generator = new PuzzleGenerator(selectedPuzzleFile, letterFont);
		
		letters = generator.getLetterLabels();
		words = generator.getWords();
		
	
		//create word search letter panel
		if (!firstPuzzle) {
			this.getContentPane().remove(gridPanel);
			wordListScrollPane.remove(wordListPanel);
			this.getContentPane().remove(wordListScrollPane);
			
		}else {
			firstPuzzle = false;
		}
		
		gridPanel = new GridPanel();
		wordListPanel = new WordListPanel();
		System.out.println(gridPanel.toString());
		gridPanel.addMouseListener(this);
		gridPanel.addMouseMotionListener(this);
		
		wordListScrollPane = new JScrollPane(wordListPanel);
		wordListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		wordListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		//add panels to grid
		add(gridPanel, BorderLayout.CENTER);
		//add(wordListPanel, BorderLayout.LINE_END);
		add(wordListScrollPane, BorderLayout.LINE_END);
		
		//repaint
		revalidate();
		repaint();
		
		//DELETE
		System.out.println("Font Size: " + letterFont.getSize());
		
	}
	
	
	class WordListPanel extends JPanel implements MouseListener{
		
		WordListPanel(){
			
			wordListLabels = new HashSet<JLabel>();
			setLayout( new GridLayout(words.size(),1, 5, 5));
			for (Word word: words) {
				JLabel wordLabel = new JLabel(word.toString());
				 wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
 				 wordLabel.setVerticalAlignment(SwingConstants.CENTER);
				add(wordLabel);
				wordListLabels.add(wordLabel);
				wordLabel.addMouseListener(this);
				//make sure this doesn't mess up the panel listen
			}
			
			TitledBorder wordListPanelTitle = BorderFactory.createTitledBorder("Word_Search.Word List");
			Border wordListPanelPadding = BorderFactory.createEmptyBorder(10, 5,5, 10);
			Border wordListBorder = BorderFactory.createCompoundBorder(wordListPanelPadding,wordListPanelTitle);
			setBorder(wordListBorder);
			
			//make the word list panel big enough for the title
			Dimension wordListPanelDimension = getPreferredSize();
			int titleBorderWidth = (int) wordListPanelTitle.getMinimumSize(this).getWidth();
			Dimension newWordListPanelDimension = null;
			System.out.println(titleBorderWidth);
			System.out.println(wordListPanelDimension.getWidth());
			if (titleBorderWidth + 50 >wordListPanelDimension.getWidth()){
				newWordListPanelDimension = new Dimension(titleBorderWidth + 50, wordListPanelDimension.height);
			}else {
				newWordListPanelDimension = wordListPanelDimension;
				System.out.println("took this");
			}
			setPreferredSize(newWordListPanelDimension);
			
		}//end constructor
		
		
		protected void paintComponent(Graphics g) {

			// Create a Graphics2D drawing context for drawing on the panel.
			Graphics2D g2 = (Graphics2D) g.create();
			wordListMetrics = g2.getFontMetrics();

			/*
			 * Turn on antialiasing in this graphics context for better drawing.
			 */
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			
			AffineTransform savedTransform = g2.getTransform();
			 
			for (Component wordLabel: strikeThroughs.keySet()) {
				Line2D.Double strikeThrough = strikeThroughs.get(wordLabel);
				Color lineColor = Color.BLACK;
				g2.setPaint(lineColor);
				g2.setStroke(new BasicStroke(2));
				g2.draw(strikeThrough);
				g2.setTransform(savedTransform);	
			}
		}


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			JLabel label = (JLabel)e.getComponent();
			
			if(wordListLabels.contains(label)){
				
				//get label center
				Rectangle2D bounds = label.getBounds();
				double labelCenterX = bounds.getCenterX();
				double labelCenterY = bounds.getCenterY();
				//draw line through the text close to the length of the actual string.
				int width = wordListMetrics.stringWidth(label.getText());
				double labelStartX = labelCenterX -(width/2) - 2;
				double labelEndX = labelCenterX + (width/2) + 2;
				Line2D.Double line = new Line2D.Double(labelStartX, labelCenterY,labelEndX, labelCenterY);
				
				//remove line if already present
				if (strikeThroughs.containsKey(label)) {
					strikeThroughs.remove(label);
				}
				else {
					strikeThroughs.put(label, line);
				}
				repaint();
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
		
	}
	
	
	private void scorePuzzle() {
		if(words == null) {
			return;
		}
		
		//creating two sets, of of all the potential words, and one of the actual words 
		//is the most efficient. However, the sets of letters in a word are embedded in a word, and no way 
		//to order the letters properly to recreate this for the found words.
		
		//Set <Set<JLabel>> potentialWords = new HashSet<Set<JLabel>>();
		ArrayList<Word> unfoundWords = new ArrayList<Word>(words); 
		for (Line2D line: lines) {
			Set<JLabel> lineLetters = new HashSet<JLabel>();
			for (JLabel letter: letters) {
				Rectangle2D bounds = letter.getBounds();
				int centerSpan = letterFont.getSize();
				Rectangle2D labelCenter = new Rectangle2D.Double(bounds.getCenterX()-centerSpan/2, bounds.getCenterY()-centerSpan/2, centerSpan,centerSpan);
				if (line.intersects(labelCenter)) {
					System.out.println(letter.getText() + " intersected");
					lineLetters.add(letter);
				}
			}
			ListIterator<Word> iterator = unfoundWords.listIterator();
			while(iterator.hasNext()){
				Word currWord = iterator.next();
				System.out.println(currWord.isEqual(lineLetters));
			    if(currWord.isEqual(lineLetters)){
			        iterator.remove();
			    }
			}
		}
		//Change all unfound words to RED
		for (Word word: unfoundWords) {
			word.changeAllLetterColor(Color.RED);
			repaint();
		}
		JOptionPane.showMessageDialog(this,
			    "You found " + (words.size() - unfoundWords.size()) + " out of "+ words.size() + " words.",
			    "Final Score",
			    JOptionPane.PLAIN_MESSAGE);
		
		
	} // end score puzzle
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordSearchProgram  gui = new WordSearchProgram();
		gui.pack();
		gui.setVisible(true);

	}

}
