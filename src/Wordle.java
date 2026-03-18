package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordle {
    // instance variables
    private String secretWordStr;
    private String[] secretWord;
    private String[][] gameBoard;
    private ArrayList<String> possibleGuesses;
    private ArrayList<String> letterList;
    private int numGuesses;
    private boolean wordGuessed;
    // constants
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BG_YELLOW = "\u001B[43m";
    public static final String ANSI_BG_GREEN = "\u001B[42m";

    // constructors
    public Wordle(String secretWord) {
        secretWordStr = secretWord.toLowerCase();
        // convert secret word into array of letters
        this.secretWord = new String[5];
        for (int i = 0; i < secretWord.length(); i++) {
            this.secretWord[i] = secretWordStr.substring(i, i + 1);
        }
        // create empty game board, fill with underscores
        gameBoard = new String[6][5];
        for (String[] row : gameBoard) {
            for (int i = 0; i < row.length; i++) {
                row[i] = "_";
            }
        }
        // fill possible guesses arraylist with words from file
        possibleGuesses = new ArrayList<>();
        loadData();
        // create temp alphabet array, convert into arraylist
        letterList = new ArrayList<>();
        String[] letterBoard = "abcdefghijklmnopqrstuvwxyz".split("");
        for (String letter : letterBoard) {
            letterList.add(letter);
        }
        // initialize game logic variables
        numGuesses = 0;
        wordGuessed = false;
    }
    // methodes
    public boolean guess(String guess) {
        guess = guess.toLowerCase();
        // check if guess is valid
        if (guess.length() != 5 || !possibleGuesses.contains(guess.toLowerCase())) {
            System.out.println("Invalid guess. Please enter a 5 letter word.");
            return false;
        }
        // convert guess into array of letters, add to game board
        String[] guessArr = new String[5];
        for (int i = 0; i < guess.length(); i++) {
            guessArr[i] = guess.substring(i, i + 1);
        }
        gameBoard[numGuesses] = guessArr;
        // check if correct guess
        if (guess.equals(secretWordStr)) {
            wordGuessed = true;
        }
        //update game logic
        numGuesses ++;
        updateLetterBoard(guessArr);
        // check if game over
        return true;
    }
    private void updateLetterBoard(String[] guess) {
        for (int i = 0; i < guess.length; i++) {
            if (!secretWordStr.contains(guess[i])) {
                letterList.remove(guess[i]);
            }
        }
    }

    public void printBoard(){
        for (String[] row : gameBoard) {
            for (int i = 0; i < row.length; i++) {
                String letter = row[i];
                if (row[i].equals(secretWord[i])) {
                    letter = ANSI_BG_GREEN + row[i] + ANSI_RESET;
                } else if (secretWordStr.contains(row[i])) {
                    letter = ANSI_BG_YELLOW + row[i] + ANSI_RESET;
                }
                System.out.print(letter + " ");
            }
            System.out.println();
        }
        System.out.println("Guess " + (numGuesses + 1) + " of 6");
        System.out.println("Letters remaining: ");
        for (String letter : letterList) {
            System.out.print(letter + " ");
        }
        System.out.println();
    }

    public boolean loadData() {
        Scanner scanGuess;
        Scanner scanAnswer;
        try {
            scanGuess = new Scanner(new File("resources/possibleGuesses.txt"));
            scanAnswer = new Scanner(new File("resources/possibleAnswers.txt"));
        }
        catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
            return false;
        }
        while (scanGuess.hasNextLine()) {
            possibleGuesses.add(scanGuess.nextLine().toLowerCase());
        }
        while (scanAnswer.hasNextLine()) {
            possibleGuesses.add(scanAnswer.nextLine().toLowerCase());
        }
        if (possibleGuesses.size() == 0) {
            System.out.println("Error loading data: no guesses found in file");
            return false;
        }
        return true;
    }
    // getters
    public int getNumGuesses() {
        return numGuesses;
    }
    public boolean isWordGuessed() {
        return wordGuessed;
    }
    public String getSecretWord() {
        String result = "";
        for (String letter : secretWord) {
            result += letter;
        }
        return result;
    }
}
