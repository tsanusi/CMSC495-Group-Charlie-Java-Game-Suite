/* File: WordSearchProgram.java
 * Author: Sherry Funches
 * Revision Date: October 4, 2021
 * Purpose: This class generates the GUI for the WordSearch Program. 
 * It allows the user to select a puzzle file and displays a word list and grid of letters
 * using data from that file. 
 * Users can highlight letters on the grid and cross off words on the list.
 * When complete, the user clicks submit and this class will score the number of
 * words correctly identified and display the locations of unidentified words in
 * red font on the grid. 
 */

package Word_Search;

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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileView;


@SuppressWarnings("serial")
public class WordSearchProgram  extends JFrame implements MouseMotionListener, MouseListener{
	
	private File selectedPuzzleFile;
	//Font of characters on word search puzzle board
	private Font letterFont = null;
	//Letters (JLabels) placed on the puzzle board
	private ArrayList<JLabel> letters;
	private PuzzleGenerator generator;
	//Words hidden in the puzzle
	private ArrayList<Word> words;
	//words on word list panel
	 private Set <JLabel> wordListLabels;
	//metrics for fonts in word list panel
	private FontMetrics wordListMetrics;
	
	//Is this the first puzzle upon opening programming
	private boolean firstPuzzle = true;
	
	//highlights on word search puzzle board
 	private ArrayList <Line2D.Double> lines = new ArrayList<Line2D.Double>();
 	//coordinates etc for drawing highlights on the board
    private double startx = 0.0;
    private double starty = 0.0;
    private boolean mouseDragged = false;
    private int lineCounter =0;
    // holds words and their strikethroughs for the word list panel
   	private HashMap<JComponent, Line2D.Double> strikeThroughs = new HashMap<JComponent,Line2D.Double>();
    //Highlight while cursor is dragged over the puzzle board
   	private Line2D.Double tempLine;
   	
   	//Has the puzzle been submitted for a solution
   	//Used to prevent drawing highlights after puzzle is solved 
   	private boolean isSolved = false;
    
    //GUI Components
   	private JButton selectButton;
    private JButton submitButton;
    private JButton undoButton;
    private JButton clearButton;
    private JButton loadButton;
    private JButton infoButton;
    private JTextArea selectedFileField;
    private JPanel gridPanel;
    private JPanel wordListPanel;
    private JScrollPane wordListScrollPane;
    
	//constructor
	public WordSearchProgram() {
		//set up GUI
		super("Word Search");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
		setResizable(true); 
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
		//Choose font size based on screen size
		int fontSize = 0;
		
		//max number of columns
		int maxColumns = 15;
		
		//max number of rows allowed for puzzles.
		int maxRows = 10;
		
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
		selectedFileField = new JTextArea(1,12);
		selectedFileField.setEditable(false);
		selectedFileField.setLineWrap(true);
		selectedFileField.setWrapStyleWord(true);
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
		infoButton = new JButton("How to Play");
		JPanel infoPanel = new JPanel(new GridLayout(1,5));
		infoPanel.add(infoButton);
		for (int i=0; i<4; i++) {
			infoPanel.add(new JLabel());
		}
		
		infoButton.addActionListener(e->displayGameInstructions());
		selectButton.addActionListener(e->selectPuzzleFile());
		submitButton.addActionListener(e->scorePuzzle());
		loadButton.addActionListener(e-> loadPuzzle());
		clearButton.addActionListener(e->checkClear());
		undoButton.addActionListener(e->undo());
		
		//Add components to JFrame
		JPanel allButtonsPanel = new JPanel();
		allButtonsPanel.add(selectPanel);
		allButtonsPanel.add(buttonPanel);
		add(allButtonsPanel, BorderLayout.PAGE_END);
		add(infoPanel, BorderLayout.PAGE_START);
		
		//display JFrame
		this.pack();
		//set location so width is centered and height is above center
		setLocation( screen.width/2 - getWidth()/2, (screen.height - getHeight()) / 4);			
	}
	
	//This class displays the grid of letters and user highlighting of words.
	 class GridPanel extends JPanel  {
 		
 		GridPanel () {
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

			 // Turn on antialiasing
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			//set background white
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
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startx = e.getX();
		starty= e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		//if mouse is dragged but the puzzle has not been 
		//submitted for scoring
		if (mouseDragged == true && isSolved == false) {
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
		//draw highlight and update while mouse is dragged.
		mouseDragged = true;
		double x = e.getX();
		double y = e.getY();
		if (isSolved == false) {
			tempLine = new Line2D.Double(startx, starty, x, y);	
		}
		repaint();				
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {	
	}
	
	// Method displays game instructions to user in dialog box.
	private void displayGameInstructions() {
		String instructions = "-Click select puzzle button to see puzzles.\n\n"
				+ "-Choose a puzzle file. Then click the load puzzle button.\n\n"
				+ "-Search for the words in the list on the right in the puzzle.\n\n"
				+ "-Highlight words by dragging the cursor over letters.\n\n"
				+ "-Only a continuous line across the center of all of a word's letters is valid.\n\n"
				+ "-Click word on list to strikethrough. Click again to undo.\n\n"
				+ "-Click the undo button to undo the last highlight.\n\n"
				+ "-Click the clear button to remove all highlights and strikethroughs.\n\n"
				+ "-Click the submit button to see your score and the puzzle's solution.\n\tUnfound words will appear in red.";
		
		JOptionPane.showMessageDialog(this,
			    instructions,
			    "How To Play",
			    JOptionPane.PLAIN_MESSAGE);
		
	}
	
	// method verifies that user wants to clear the board of highlights and strikethroughs
	private void checkClear() {
		Object[] options = {"Yes", "No"};
		int n = JOptionPane.showOptionDialog(this,
		    "Are you sure you want to clear the board?", "Confirm Clear Board",
		    JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[1]);	
		if(n==0) {
			clear();
		}
	}
	
	//clears highlights from word search puzzle board and strikethroughs from list
	private void clear() {
		lineCounter=0;
		lines.clear();
		strikeThroughs.clear();
		//Remove red letters after puzzle solved.
		if (words !=null) {
			for (Word word: words) {
				word.changeAllLetterColor(Color.BLACK);
			}
		}
		isSolved = false;
		repaint();
	}
	
	//removes last highlight from the word search puzzle board
	private void undo() {
		if(!lines.isEmpty() && isSolved ==false) {
			lines.remove(lineCounter-1);
			lineCounter--;	
			repaint();
		}
	}
	
	private void selectPuzzleFile() {
		File puzzleDirectory = new File ("./Word_Search/WordSearchPuzzles");
		JFileChooser fc = new JFileChooser(puzzleDirectory);
		//prevent user from opening files in other directories
		fc.setFileView(new FileView() {
			@Override
			public Boolean isTraversable(File f) {
				//return puzzleDirectory.equals(f);
				return (puzzleDirectory.getAbsolutePath().equals(f.getAbsolutePath()) || puzzleDirectory.equals(f));
			}
		} );
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
		gridPanel.addMouseListener(this);
		gridPanel.addMouseMotionListener(this);
		
		wordListScrollPane = new JScrollPane(wordListPanel);
		wordListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		wordListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		//add panels to grid
		add(gridPanel, BorderLayout.CENTER);
		add(wordListScrollPane, BorderLayout.LINE_END);
		
		//repaint
		revalidate();
		repaint();
		pack();	
	
	}
	
	//Creates panel to display list of words hidden in the puzzle
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
			}
			
			TitledBorder wordListPanelTitle = BorderFactory.createTitledBorder("Word List");
			Border wordListPanelPadding = BorderFactory.createEmptyBorder(10, 5,5, 10);
			Border wordListBorder = BorderFactory.createCompoundBorder(wordListPanelPadding,wordListPanelTitle);
			setBorder(wordListBorder);
			
			//make the word list panel big enough for the title
			Dimension wordListPanelDimension = getPreferredSize();
			int titleBorderWidth = (int) wordListPanelTitle.getMinimumSize(this).getWidth();
			Dimension newWordListPanelDimension = null;
			if (titleBorderWidth + 50 >wordListPanelDimension.getWidth()){
				newWordListPanelDimension = new Dimension(titleBorderWidth + 50, wordListPanelDimension.height);
			}else {
				newWordListPanelDimension = wordListPanelDimension;
			}
			setPreferredSize(newWordListPanelDimension);
			
		}//end constructor
		
		
		protected void paintComponent(Graphics g) {

			// Create a Graphics2D drawing context for drawing on the panel.
			Graphics2D g2 = (Graphics2D) g.create();
			wordListMetrics = g2.getFontMetrics();
		
			// Turn on antialiasing
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
			//get the clicked label
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
		
		ArrayList<Word> unfoundWords = new ArrayList<Word>(words); 
		//For every highlight create set of letters that the highlight intersects
		for (Line2D line: lines) {
			Set<JLabel> lineLetters = new HashSet<JLabel>();
			for (JLabel letter: letters) {
				Rectangle2D bounds = letter.getBounds();
				int centerSpan = letterFont.getSize();
				Rectangle2D labelCenter = new Rectangle2D.Double(bounds.getCenterX()-centerSpan/2, bounds.getCenterY()-centerSpan/2, centerSpan,centerSpan);
				if (line.intersects(labelCenter)) {
					lineLetters.add(letter);
				}
			}
			//Check if the set of letters intersected by a highlight belong to a word
			//If so, remove the word from the list
			ListIterator<Word> iterator = unfoundWords.listIterator();
			while(iterator.hasNext()){
				Word currWord = iterator.next();
			    if(currWord.isEqual(lineLetters)){
			        iterator.remove();
			    }
			}
		}
		//Change all unfound words to RED
		for (Word word: unfoundWords) {
			word.changeAllLetterColor(Color.RED);
			
		}
		repaint();
		JOptionPane.showMessageDialog(this,
			    "You found " + (words.size() - unfoundWords.size()) + " out of "+ words.size() + " words.",
			    "Final Score",
			    JOptionPane.PLAIN_MESSAGE);
		
		//prevent further drawing on board
		isSolved = true;
		
	} // end score puzzle
	


	public static void main(String[] args) {
		WordSearchProgram  gui = new WordSearchProgram();
		//gui.pack();
		//gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		

	}

}
