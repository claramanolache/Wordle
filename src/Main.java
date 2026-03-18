import src.Wordle;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    String answer = chooseRandomAnswer();
    if (answer == null) {
        System.out.println("Error loading data.");
        return;
    }
    Wordle game = new Wordle(answer);
    game.printBoard();
    Scanner scanInput = new Scanner(System.in);
    while (true) {
        System.out.println("Enter your guess:");
        String guess = scanInput.nextLine();
        if (!game.guess(guess)) {
            continue;
        }
        if (game.isWordGuessed()) {
            System.out.println("Congratulations! You guessed the word in " + game.getNumGuesses() + " guesses!");
            break;
        } else if (game.getNumGuesses() >= 6) {
            System.out.println("Game over! The secret word was: " + String.join("", game.getSecretWord()));
            break;
        }
        game.printBoard();

    }
}
    public static String chooseRandomAnswer(){
        Scanner scanFile;
        try {
            scanFile = new Scanner(new File("resources/possibleAnswers.txt"));
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return null;
        }
        ArrayList<String> possibleAnswers = new ArrayList<>();
        while (scanFile.hasNextLine()) {
            possibleAnswers.add(scanFile.nextLine().toLowerCase());
        }
        int randomIndex = (int) (Math.random() * possibleAnswers.size());
        return possibleAnswers.get(randomIndex);
    }

