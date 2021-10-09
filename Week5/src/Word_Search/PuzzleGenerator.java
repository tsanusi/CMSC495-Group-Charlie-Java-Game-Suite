/*
 * File: PuzzleGenerator.java
 * Author: Sherry Funches
 * Revision Date: October 8, 2021
 * Purpose: This class reads in a formatted file that specifies the display of letters,
 * hidden words, and the location of hidden words for a word
 * search puzzle. The class creates JLabels representing the letters on the word search
 * puzzle grid and places them in the specified order to be added to the GUI.
 * It creates words and the letters that comprise them for the GUI display and scoring.
 */


package Word_Search;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class PuzzleGenerator {
	
	//matrix representing the puzzle grid
	private JLabel[][]letterMatrix;
	//list of words hidden in the puzzle
	private ArrayList<Word> words;
	//input data file
	private File puzzleDataFile;
	private Font letterFont;
	//number of rows in the puzzle grid
	private int numRows = 0;
	//number of columns in the puzzle grid
	private int numColumns =0;
	//number of words hidden in the puzzle
	private int numWords = 0;
	
	
	public PuzzleGenerator(File puzzleDataFile, Font letterFont) {
		this.puzzleDataFile = puzzleDataFile;
		words = new ArrayList<Word>();
		this.letterFont = letterFont;
		readFile();
	}
	
	//Returns an ordered ArrayList of JLabels. Each JLabel is a letter.
	//Used to fill out the GUI's puzzle grid
	public ArrayList<JLabel> getLetterLabels(){
		ArrayList<JLabel> letterList = new ArrayList<JLabel>();
		for(int i=0; i<numRows; i++) {
			for( int j=0; j<numColumns; j++) {
				letterList.add(letterMatrix[i][j]);
			}
		}
		return letterList;
	}
	
	//returns the number of rows in the word search grid
	public int getNumRows() {
		return numRows;
	}
	
	//returns the number of columns in the word search grid
	public int getNumColumns() {
		return numColumns;
	}
	
	
	//returns the words hidden in the puzzle
	public ArrayList<Word> getWords(){
		return words;
	}
	
	//processes puzzle data file
	private void readFile() {
		
		//Read In the file
		BufferedReader inputStream= null;
		String fileLine;
		int lineCounter = 0;
		
		try {
			inputStream= new BufferedReader(new FileReader(puzzleDataFile));
			
			while ((fileLine = inputStream.readLine()) != null) {
				
				//split line by by tabs
				String data[] =fileLine.trim().split("\t");
				
				//Read in first row, which has the puzzle's specifications
				if(lineCounter==0) {
					numRows = Integer.parseInt(data[0].trim());
					numColumns = Integer.parseInt(data[1].trim());
					numWords = Integer.parseInt(data[2].trim());
					
					//create matrix
					letterMatrix = new JLabel[numRows][numColumns];
					lineCounter++;
				}
				
				//then add letters to the matrix
				else if (lineCounter>0 && lineCounter<=numRows) {
					for(int i =0; i<=numColumns-1; i++) {
						JLabel letter = new JLabel(data[i]);
						
						//set font and center letters in label
						letter.setFont(letterFont);
						letter.setHorizontalAlignment(SwingConstants.CENTER);
		 				letter.setVerticalAlignment(SwingConstants.CENTER);
						letterMatrix[lineCounter-1][i] = letter;
					}
					lineCounter++;
				}
				//Read in the hidden words
				//Create word object and add text
				else if (lineCounter>numRows && lineCounter<=numRows + numWords) {
					words.add(new Word(data[0]));
					lineCounter++;
				}
				
				//Read in the matrix location of the hidden word's letters
				// and add those letters to the word
				else if (lineCounter>=numRows + numWords && lineCounter<=numRows + numWords+numWords) {
					for(int i =0; i<data.length-1;i+=2) {
						int row = Integer.parseInt(data[i]);
						int column = Integer.parseInt(data[i+1]);
						JLabel label = letterMatrix[row][column];
						words.get(lineCounter-numWords -numRows-1).addLetter(label);
					}
					lineCounter++;
				}
				
				
			}		
		}catch(FileNotFoundException e) {
			System.err.println(e.getMessage());
		}catch (IOException e) {
			System.err.println(e.getMessage());
			
		} finally {
			
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException io) {
				System.err.println("Issue closing the Files" + io.getMessage());
            }
		}//end finally
		
		
	}

}
