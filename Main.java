//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Wordle game = new Wordle("APPLE");
    Scanner scan = new Scanner(System.in);
    game.printBoard();
    while (true){
        System.out.println("Enter your guess:");
        String guess = scan.nextLine();
        if (guess.length() != 5) {
            System.out.println("Invalid guess. Please enter a 5 letter word.");
            continue;
        }
        boolean over = game.guess(guess);
        game.printBoard();
        if (over) {
            if (game.isWordGuessed()) {
                System.out.println("Congratulations! You guessed the word in " + game.getNumGuesses() + " guesses!");
            } else {
                System.out.println("Game over! The secret word was: " + String.join("", game.getSecretWord()));
            }
            break;
        }
    }
}
