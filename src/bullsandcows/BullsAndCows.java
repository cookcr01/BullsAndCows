package bullsandcows;

/**
 * BullsAndCows.java by Cameron Cook Game called Bulls and Cows, the user and
 * the computer both have a secret number, and they have to guess each others
 * secret number.
 */
import java.util.*;

public class BullsAndCows {

    public static final int NUMBER_OF_ROUNDS = 10;

    public static void main(String[] args) {
        Combination.setAvailableNumbers();
        Guess.populateCowArray();
        Scanner input = new Scanner(System.in);
        boolean firstPlayer = findFirstPlayer();
        int turnOrder;
        if (firstPlayer) {
            System.out.println("You get to go first, your lucky.");
            turnOrder = 0;
        } else {
            System.out.println("The computer gets to goes first, prepared to get pwned noob.");
            turnOrder = 1;
        }

        Computer computer = new Computer();
        Player player = new Player();
        int[] guessAnswers;
        String currentPlayerTurn;
        for (int i = 0; i < NUMBER_OF_ROUNDS; i++) {
            System.out.println("");
            System.out.println("This is round " + (i + 1) + " out of 10");
            System.out.println("**************************");
            for (int currentPlayer = 0 + turnOrder; currentPlayer <= 1 + turnOrder; currentPlayer++) {
                if (currentPlayer % 2 == 0) {
                    currentPlayerTurn = "Player";
                    int[] playerGuess = Player.getGuess();
                    guessAnswers = Computer.checkGuess(playerGuess);
                    System.out.println(cowsAndBullsToString(guessAnswers, playerGuess));
                    System.out.println("");
                } else {
                    currentPlayerTurn = "Computer";
                    int[] cpuGuess = Computer.getGuess(player);
                    guessAnswers = Player.checkGuess(cpuGuess);
                    System.out.println(printComputerGuess(cpuGuess));
                    System.out.println(cowsAndBullsToString(guessAnswers, cpuGuess));
                    System.out.println("");
                }
                if (checkifAllBulls(guessAnswers)) {
                    System.out.println("The " + currentPlayerTurn + " has guessed the other's secret number.");
                    int winner = checkWhoWasFirstPlayer(turnOrder, currentPlayer);
                    if (winner != 0) {
                        if (getLastChanceOfPlayer(winner, player)) {
                            System.out.println("The game is a tie");
                            return;
                        }
                    }
                    System.out.println("           *");
                    System.out.println("          ***");
                    System.out.println("        *******");
                    System.out.println("      ************");
                    System.out.println("   *****************");
                    System.out.println(" *********************");
                    System.out.println("***********************");
                    System.out.println("***The " + currentPlayerTurn + " WINS!***");
                    System.out.println("***********************");
                    System.out.println(" *********************");
                    System.out.println("   *****************");
                    System.out.println("      ************");
                    System.out.println("        *******");
                    System.out.println("          ***");
                    System.out.println("           *");
                    return;

                }
            }

        }
        System.out.println("The game is a tie!");
    }
    /**
     * used to determine who the first player was, so that we know if they
     * another player gets to go again.
     *
     * @param turnOrder variable which represents who went first.
     * @param currentPlayer variable of who went last.
     * @return a integer which well tell me who went first.
     */
    public static int checkWhoWasFirstPlayer(int turnOrder, int currentPlayer) {
        if (currentPlayer % 2 == 0) {
            if (turnOrder == 0) {
                return 2;
            }
        } else {
            if (turnOrder == 1) {
                return 1;
            }
        }
        return 0;
    }
    
    /**
     * method is to check if the person that went first guesses the number, if
     * that is true it then lets the person that was second guess one more time.
     *
     * @param winner is variable of integer type that represents who won.
     * @param player is a object, which we use to check the guess of who every
     * gets to go again.
     * @return true to
     */
    public static Boolean getLastChanceOfPlayer(int winner, Player player) {
        int[] guessAnswers;
        switch (winner) {
            case 1:
                int[] playerGuess = Player.getGuess();
                guessAnswers = Computer.checkGuess(playerGuess);
                System.out.println(cowsAndBullsToString(guessAnswers, playerGuess));
                return checkifAllBulls(guessAnswers);
            case 2:
                int[] cpuGuess = Computer.getGuess(player);
                guessAnswers = Player.checkGuess(cpuGuess);
                System.out.println(printComputerGuess(cpuGuess));
                System.out.println(cowsAndBullsToString(guessAnswers, cpuGuess));
                return checkifAllBulls(guessAnswers);
            default:
                break;

        }
        return false;
    }

    /**
     * Checks if the 4 digit guesses is right.
     *
     * @param guessAnswers array which tells me if they are bulls, cows, or
     * nothing.
     * @return true if all bulls, false if anything else.
     */
    public static Boolean checkifAllBulls(int[] guessAnswers) {
        int check = 0;
        for (int ea : guessAnswers) {
            if (ea == 1) {
                check++;
            }
        }
        return (check == 4);

    }

    /**
     * determines a first player for me.
     *
     * @return true if greater then .5, false if less .5.
     */
    public static boolean findFirstPlayer() {
        return (Math.random() > .5);
    }
    
 /**
     * makes a string that will be printed of the cpu's guess.
     *
     * @param cpuGuess guess from the cpu.
     * @return returns a string to print out, that displays the cpu's guess.
     */
    public static String printComputerGuess(int[] cpuGuess) {
        String result = "The computer guesses: ";
        for (int ea : cpuGuess) {
            result += ea;
        }
        return result;
    }
    
    /**
     * concatinates a string that tells the user how many bulls and cows, and
     * what they are.
     *
     * @param filter array that of 0,1,2 that where and what the number was
     * bull, cow, or nothing.
     * @param guesses guess of the user or computer, so i can tell exactly what
     * number was a bull, cow, or nothing.
     * @return string to printed to tell what was in that combination they
     * guessed.
     */
    public static String cowsAndBullsToString(int[] filter, int[] guesses) {
        int cows = 0, bulls = 0;
        String bullString = "Bulls: ", cowString = "Cows: ";
        for (int i = 0; i < filter.length; i++) {
            if (filter[i] == 1) {
                bulls++;
                bullString += guesses[i] + " ";
            }
            if (filter[i] == 2) {
                cows++;
                cowString += guesses[i] + " ";
            }
        }

        return "There are " + bulls + " bulls and " + cows + " cows. (" + bullString + cowString + ")";
    }

}

class Player {

    private static Combination secretNumber;

    /**
     * gets input from user to create the users secret number.
     */
    public Player() {
        Scanner input = new Scanner(System.in);
        int[] playersNum;
        do {
            System.out.print("Enter your secret four digit number: ");
            String playerSecret = input.next();
            if (playerSecret.length() != 4) {
                continue;
            }
            playersNum = splitString(playerSecret);
            if (checkInput(playersNum)) {
                break;
            }
        } while (true);
        secretNumber = new Combination(playersNum);
    }
    
/**
     * makes sure the array does not have repeating numbers.
     *
     * @param array array to check if any numbers are repeating.
     * @return true if no numbers repeat, false if there are numbers repeating.
     */
    private static boolean checkInput(int[] array) {
        for (int x = 0; x < array.length; x++) {
            for (int i = x + 1; i < array.length; i++) {
                if (array[x] == array[i]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * takes the user inputed secret number, and splits it into a array, to make
     * it easier to work with.
     *
     * @param userInput what the user inputed to be his secret number.
     * @return array of the user secret number.
     */
    private static int[] splitString(String userInput) {
        int[] array = new int[4];
        for (int i = 0; i < 4; i++) {
            array[i] = Integer.parseInt(userInput.charAt(i) + "");
        }
        return array;
    }

    /**
     * checks the guess of the computer.
     *
     * @param guess computers guess.
     * @return array of 0,1,2 of which numbers are cows, bulls, and nothing.
     */
    public static int[] checkGuess(int[] guess) {
        return secretNumber.checkCowsandBulls(guess);
    }

    

    /**
     * get the guess from the user.
     *
     * @return array that is the guess of the user.
     */
    public static int[] getGuess() {
        Scanner input = new Scanner(System.in);
        int[] guess;
        do {
            System.out.print("Enter your guess: ");
            String playerGuess = input.next();
            if (playerGuess.length() != 4) {
                continue;
            }
            guess = splitString(playerGuess);
            if (checkInput(guess)) {
                break;
            }
        } while (true);

        return guess;
    }

}

class Computer {

    private static Combination secretNumber;

    /**
     * initializes the computer secretNumber.
     */
    public Computer() {
        this.secretNumber = new Combination();
    }

    /**
     * calls methods to check guess
     *
     * @param guess the guess to check with the secret number
     * @return array that contains 0,1,2 which tell what numbers where bulls,
     * cows, and nothing.
     */
    public static int[] checkGuess(int[] guess) {
        return secretNumber.checkCowsandBulls(guess);
    }

    /**
     * calls other methods, to create the computers guess.
     *
     * @param player object to compare the players secret number to the computer
     * guess.
     * @return array that will be the guess of the computer.
     */
    public static int[] getGuess(Player player) {
        Guess computerGuess = new Guess(player);
        return computerGuess.getGuess();
    }

}

class Combination {

    private int[] secretNumber = {-1, -1, -1, -1};
    private int[] guess = {-1, -1, -1, -1};
    private static ArrayList<Integer> availableNumbers = new ArrayList<>();

    /**
     * randomly generates a 4 digit number
     */
    public Combination() {
        for (int i = 0; i < 4; i++) {
            int number = checkNum(secretNumber);
            secretNumber[i] = number;
        }
    }

    /**
     * creates a secret number by being given a array.
     *
     * @param secretNumber the 4 digit number that needs to be a secret number.
     */
    public Combination(int[] secretNumber) {
        this.secretNumber = secretNumber;
    }

    /**
     * makes the guess of the computer smarter.
     *
     * @param pastGuess tells if this is the first time the computer is
     * guessing.
     * @param cpuBulls tells me the bulls in a array.
     * @param cpuCows tells me the cows in a array.
     */
    public Combination(Boolean pastGuess, int[] cpuBulls, int[][] cpuCows) {
        if (pastGuess) {
            for (int i = 0; i < 4; i++) {
                int number = checkNum(guess);
                guess[i] = number;
            }
        } else {
            for (int i = 0; i < guess.length; i++) {
                if (cpuBulls[i] != -1) {
                    guess[i] = cpuBulls[i];
                    continue;
                } else {
                    for (int x = 0; x < cpuCows.length; x++) {
                        if (cpuCows[x][1] == i) {
                            guess[i] = cpuCows[x][0];
                        }
                    }
                }
                if (guess[i] == -1) {
                    guess[i] = checkNum(guess);
                }
            }
        }
    }

    /**
     * adds available numbers to guess, to a array list.
     */
    public static void setAvailableNumbers() {
        for (int i = 0; i < 10; i++) {
            availableNumbers.add(i);
        }
    }

    /**
     * gets the guess array in this class.
     *
     * @return guess array.
     */
    public int[] getGuess() {
        return this.guess;
    }

    /**
     * generates a random number that is not in the array.
     *
     * @param array numbers you don't want to be in the array.
     * @return a random number.
     */
    private int checkNum(int[] array) {
        int ranNum = getRanNum();
        boolean isThere;
        do {
            isThere = false;
            for (int ea : array) {
                if (ranNum == ea) {
                    ranNum = getRanNum();
                    isThere = true;
                }
            }
        } while (isThere);
        return ranNum;
    }

    /**
     * gets a random number in the available numbers array list.
     *
     * @return a random number.
     */
    private static int getRanNum() {
        int ranNum = (int) (Math.random() * availableNumbers.size());

        return availableNumbers.get(ranNum);
    }

    /**
     * goes through the array and makes another array of 0,1,2 to tell which
     * numbers are cows and bulls.
     *
     * @param guess the guess of that needs checked against the secret number.
     * @return array of 0,1,2.
     */
    public int[] checkCowsandBulls(int[] guess) {
        int[] result = new int[4];
        for (int i = 0; i < guess.length; i++) {
            result[i] = 0;
            for (int y = 0; y < this.secretNumber.length; y++) {
                if (guess[i] == this.secretNumber[i]) {
                    result[i] = 1;
                } else if (guess[i] == this.secretNumber[y]) {
                    result[i] = 2;
                }
            }
        }
        return result;
    }

    /**
     * removes numbers from the available number array list.
     *
     * @param numsToRemove the numbers that need to be removed.
     * @param counter the position of the number that needs to be removed.
     */
    public void removeAvailableNumbers(int[][] numsToRemove, int counter) {
        for (int i = 0; i < numsToRemove[counter].length; i++) {
            for (int x = 0; x < availableNumbers.size(); x++) {
                if (guess[i] == availableNumbers.get(x)) {
                    availableNumbers.remove(x);
                    x--;
                }
            }
        }
    }

}

class Guess {

    private static Combination[] cpuGuesses = new Combination[10];
    private static int[][] pastCowsAndBulls = new int[10][];
    private static int turncounter = 0;
    private int guessNumber;
    private static int[] cpuBulls = {-1, -1, -1, -1};
    private static int[][] cpuCows = new int[4][2];
    private static int cowCounter = 0;

    /**
     * initializes the guess variables, and calls a whole bunch of methods.
     *
     * @param player
     */
    public Guess(Player player) {

        cpuGuesses[turncounter] = new Combination(checkPastGuess(), cpuBulls, cpuCows);
        pastCowsAndBulls[turncounter] = Player.checkGuess(cpuGuesses[turncounter].getGuess());
        addBulls();
        addCows();
        findCows();
        cpuGuesses[turncounter].removeAvailableNumbers(pastCowsAndBulls, turncounter);
        guessNumber = turncounter;
        turncounter++;

    }

    /**
     * adds bulls to cpu bulls array, so the computer knows not to move them.
     */
    private static void addBulls() {
        int[] array = cpuGuesses[turncounter].getGuess();
        for (int i = 0; i < pastCowsAndBulls[turncounter].length; i++) {
            if (pastCowsAndBulls[turncounter][i] == 1) {
                cpuBulls[i] = array[i];
                for (int x = 0; x < cpuCows.length; x++) {
                    if (cpuCows[x][0] == cpuBulls[i]) {
                        cpuCows[x][1] = -1;
                    }
                }
            }
        }

    }

    /**
     * adds the number and position of where the cow was found.
     */
    private static void addCows() {
        int[] array = cpuGuesses[turncounter].getGuess();
        if (cowCounter > 3) {
            return;
        }
        for (int i = 0; i < pastCowsAndBulls[turncounter].length; i++) {
            Boolean check = false;
            for (int x = 0; x < pastCowsAndBulls[turncounter].length; x++) {
                if (array[i] == cpuCows[x][0]) {
                    check = true;
                }
            }
            if (check) {
                continue;
            }
            if (pastCowsAndBulls[turncounter][i] == 2) {
                cpuCows[cowCounter][0] = array[i];
                cpuCows[cowCounter][1] = i;
                cowCounter++;
            }
        }
    }

    /**
     * finds the cows of the guess the computer made to make the guess smarter.
     */
    private static void findCows() {
        for (int i = 0; i < cpuCows.length; i++) {
            if (cpuCows[i][1] == -1) {
                continue;
            }

            if (cpuCows[i][0] != -1) {
                cpuCows[i][1]++;
                if (cpuCows[i][1] > 3) {
                    cpuCows[i][1] = 0;
                }
                for (int x = 0; x < cpuBulls.length - 1; x++) {
                    if (cpuBulls[cpuCows[i][1]] != -1) {
                        cpuCows[i][1]++;
                        if (cpuCows[i][1] > 3) {
                            cpuCows[i][1] = 0;

                        }
                    }
                }

            }
        }
    }

    /**
     * tells me if there are any pass guesses.
     *
     * @return true if there are, false if there aren't
     */
    private static Boolean checkPastGuess() {
        for (int x = 0; x < turncounter; x++) {
            for (int y = 0; y < pastCowsAndBulls[x].length; y++) {
                if (pastCowsAndBulls[x][y] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * populates cow array with -1 so we dont get confused with our 0,1,2.
     */
    public static void populateCowArray() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                cpuCows[i][j] = -1;
            }
        }
    }

    /**
     * gets the guess at a certain position in cpuGuesses
     *
     * @return the guess at a certain position.
     */
    public int[] getGuess() {
        return cpuGuesses[guessNumber].getGuess();
    }

}
