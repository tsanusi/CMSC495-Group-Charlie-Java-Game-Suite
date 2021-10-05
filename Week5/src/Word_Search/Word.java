/*
 * File: Word.java
 * Author: Sherry Funches
 * Revision Date: October 3, 2021
 * Purpose: Class creates a word object for the word search game. Words hold
 * the text of words hidden in the puzzle and the letters(JLabels) for each word.
 */


package Word_Search;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;


public class Word {
	private String text;
	private Set <JLabel>wordLetters;
	
	public Word(String text) {
		this.text = text;
		wordLetters = new HashSet<JLabel>();
	}
	
	public void addLetter(JLabel letter) {
		wordLetters.add(letter);
	}
	
	//Function used to check if the letters intersected by a highlight/line
	//are the same as those in a word
	public boolean isEqual (Set <JLabel> lineLetters) {
		return wordLetters.equals(lineLetters);
	}
	
	public String toString() {
		return  text;
	}
	//Used to change color of words that are not found by the user during scoring.
	public void changeAllLetterColor(Color color) {
		for (JLabel letter: wordLetters) {
			letter.setForeground(color);
		}
	}

}
