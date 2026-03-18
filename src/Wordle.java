package src;

import java.util.ArrayList;

public class Wordle {
    // instance variables
    private String secretWordStr;
    private String[] secretWord;
    private String[][] gameBoard;
    private ArrayList<String> letterList;
    private int numGuesses;
    private boolean wordGuessed;
    // constants
    public static final String ANSI_RESET = "\u001B[0m";
    //public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BG_YELLOW = "\u001B[43m";
    //public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BG_GREEN = "\u001B[42m";

    // constructors
    public Wordle(String secretWord) {
        secretWordStr = secretWord.toLowerCase();
        this.secretWord = new String[5];
        for (int i = 0; i < secretWord.length(); i++) {
            this.secretWord[i] = secretWordStr.substring(i, i + 1);
        }
        gameBoard = new String[6][5];
        for (String[] row : gameBoard) {
            for (int i = 0; i < row.length; i++) {
                row[i] = "_";
            }
        }
        letterList = new ArrayList<>();
        // create temp alphabet array, convert into arraylist
        String[] letterBoard = "abcdefghijklmnopqrstuvwxyz".split("");
        for (String letter : letterBoard) {
            letterList.add(letter);
        }
        numGuesses = 0;
        wordGuessed = false;
    }
    // methodes
    /**
     * Updates the game board with the colored guess, removes used letters from letter list.
     * Precondition: guess is a 5 letter word in our allowed word list
     * @param guess
     * @return true if game over, false if game continues
     */
    public boolean guess(String guess) {
        guess = guess.toLowerCase();
        String[] guessArr = new String[5];
        for (int i = 0; i < guess.length(); i++) {
            guessArr[i] = guess.substring(i, i + 1);
        }
        String[] result = gameBoard[numGuesses];
        for (int i = 0; i < guessArr.length; i++) {
            if (guessArr[i].equals(secretWord[i])) {
                result[i] = ANSI_BG_GREEN + guessArr[i] + ANSI_RESET;
            } else if (secretWordStr.contains(guessArr[i])) {
                result[i] = ANSI_BG_YELLOW + guessArr[i] + ANSI_RESET;
            } else {
                result[i] = guessArr[i];
                letterList.remove(guessArr[i]);
                continue;
            }
            letterList.set(letterList.indexOf(guessArr[i]), result[i]);
        }
        numGuesses ++;
        if (guessArr.equals(secretWord)) {
            wordGuessed = true;
        }
        return wordGuessed || numGuesses >= 6;
    }
    public void printBoard(){
        for (String[] row : gameBoard) {
            for (String letter : row) {
                System.out.print(letter + " ");
            }
            System.out.println();
        }
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
