import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Startup {
    private static CreateStartups anotherClass = new CreateStartups();
    private static GameHelper gameHelper = new GameHelper();
    private static PlayGame playGame = new PlayGame();
    public static void main(String[] args) {
        String userInput = gameHelper.getUserInput();
        String[][] assignStartupsValues = assignStartups(new String[]{"Startup1", "Startup2", "Startup3"});
        System.out.println("Welcome to the Startup Guessing Game!");
        System.out.println(gameHelper.printGameStatus(assignStartupsValues));
        while(!userInput.equalsIgnoreCase("exit")) {
            System.out.println("You guessed: " + userInput);
            String[][] feedback = playGame.checkGuess(userInput, assignStartupsValues);
            System.out.println(gameHelper.printGameStatus(feedback));
            String result = playGame.announceResult(feedback);
            System.out.println(result);
            userInput = gameHelper.getUserInput();
        }
    }

    public static String[][] assignStartups(String[] startups) {
        String[][] createdStartups = new String[startups.length][4];
        for (int i = 0; i < startups.length; i++) {
            Boolean[] direction = gameHelper.getDirection(7, 5);
            String[] st = anotherClass.createStartup(direction[0], direction[1]);
            createdStartups[i][0] = startups[i];
            for (int j = 1; j <= st.length; j++) {
                createdStartups[i][j] = st[j-1];
            }
        }
        return createdStartups;
    }
}

/*
 * This class is responsible to create three startups
 * Give each startup a different name
 * Put the startups on a grid
 */
class CreateStartups {
    private int STARTUP_SIZE = 3;
    private int BOARD_SIZE = 7;

    private String[] startupsLetters = {"a", "b", "c", "d", "e", "f", "g"};
    private String[] possibleNumbers = {"1", "2", "3", "4", "5"};
    
    private GameHelper gameHelper = new GameHelper();

    public String[] startUp = new String[STARTUP_SIZE];

    public String[] createStartup(Boolean growRight, Boolean growDown) {

        System.out.println("Grow right? =>" + growRight);
        System.out.println("Grow Down? =>" + growDown);

        while(!(growRight && growDown)) {
            System.out.println("Inside while loop ");

            if(growRight && !growDown) {
                System.out.println("Growing right");
                return growRight();
            }

            if(!growRight && growDown) {
                System.out.println("Growing Down");
                return growDown();
            }

            if(!growRight && !growDown) {
                continue;
            }
        }

        if(growRight && growDown) {
            System.out.println("Growing both ways");
             return growRight();
        }

        return startUp;  
    }

    private String[] growRight() {
        int randomLetterIndex = gameHelper.getRandomIndex(BOARD_SIZE);

        String firstLetter = startupsLetters[randomLetterIndex];
        
        String selectedNumber = possibleNumbers[gameHelper.getRandomIndex(5)];

        int firstNumber = Integer.parseInt(selectedNumber);
        
        int secondNumber = firstNumber+1;
        int thirdNumber = secondNumber+1;

        startUp[0] = firstLetter + firstNumber;
        startUp[1] = firstLetter + secondNumber;
        startUp[2] = firstLetter + thirdNumber;
        
        return startUp;
    }

    private String[] growDown() {
        int randomLetterIndex = gameHelper.getRandomIndex(BOARD_SIZE-2);

        System.out.println("Random letter index: " + randomLetterIndex);
        String firstLetter = startupsLetters[randomLetterIndex];

        String selectedNumber = possibleNumbers[gameHelper.getRandomIndex(5)];

        int firstNumber = Integer.parseInt(selectedNumber);
        
        String secondLetter = startupsLetters[1+randomLetterIndex];
        String thirdLetter = startupsLetters[2+randomLetterIndex];

        startUp[0] = firstLetter + firstNumber;
        startUp[1] = secondLetter + firstNumber;
        startUp[2] = thirdLetter + firstNumber;

        return startUp;
    }
}

/* 
 * Check user guess with all the three startups
 */

 class PlayGame {
    private String[][] startupNames = new String[3][4];
    public String[][] checkGuess(String userGuess, String[][] startups) {
        for (int i = 0; i < startups.length; i++) {
            startupNames[i][0] = startups[i][0];
            for (int j = 1; j < startups[i].length; j++) {
                if (startups[i][j].equalsIgnoreCase(userGuess)) {
                    startupNames[i][j] = "GUSSED"; // User guessed correctly
                }
            }
        }
        return startupNames; // User guessed incorrectly
    }

    public String announceResult(String[][] startups) {
        ArrayList<String> sunkStartup = new ArrayList<String>();
        String finalResult = "";
        Boolean isHit = false;
        int sunkStartupCount = 0;
        for (int i = 0; i < startups.length; i++) {
            startupNames[i][0] = startups[i][0];
            int gussedCount = 0;
            for (int j = 1; j < startups[i].length; j++) {
                if (startups[i][j] != null && startups[i][j].equalsIgnoreCase("GUSSED")) {
                    isHit = true;
                    gussedCount++;
                }
            }

            if(gussedCount == 3) {
                sunkStartup.add(startups[i][0]);
            } 
        }

        if(!isHit) {
            finalResult = "Miss :(";
        } else {
            finalResult = "Hit!";
            if(sunkStartup.size() > sunkStartupCount) {
                finalResult += " You sunk "+sunkStartup.getLast();
                sunkStartupCount++;
            } 

            if(sunkStartup.size() == 3) {
                finalResult += " You sunk all the startups now!";
            } 
        }
        return finalResult.toString();
    }
 }

 /*
 * get user input
 * Keep playing the game until the user guesses all three startups
 * Display the result of the game
 */

 class GameHelper {
     public String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your guess: ");
        String userGuess = scanner.nextLine();
        if(userGuess.equalsIgnoreCase("exit")) {
            scanner.close();
        }
        return userGuess;
     }

    public int getRandomIndex(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    public Boolean[] getDirection(int BOARD_SIZE, int INDEX_BOUNDRY) {
        int randomLetterIndex = getRandomIndex(BOARD_SIZE);
        int randomNumberIndex = getRandomIndex(BOARD_SIZE);
        Boolean growRight = false;
        Boolean growDown = false;
        if(randomLetterIndex < INDEX_BOUNDRY) {
            growRight = true;
        }
        
        if(randomNumberIndex < INDEX_BOUNDRY) {
            growDown = true;
        }

        return new Boolean[]{growRight, growDown};  
    }

    public String printGameStatus(String[][] startups) {
        StringBuilder sb = new StringBuilder();
        for (String[] startup : startups) {
            for (String s : startup) {
                sb.append(s).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
 }