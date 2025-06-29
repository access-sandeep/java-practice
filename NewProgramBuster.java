import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class NewProgramBuster {
    private static CreateNewPrograms anotherClass = new CreateNewPrograms();
    private static GameHelper gameHelper = new GameHelper();
    private static PlayGame playGame = new PlayGame();
    public static void main(String[] args) {
        // String userInput = gameHelper.getUserInput();
        String[][] assignNewProgramsValues = assignNewPrograms(new String[]{"NewProgram1", "NewProgram2", "NewProgram3"});
        // System.out.println("Welcome to the NewProgram Guessing Game!");
        // System.out.println(gameHelper.printGameStatus(assignNewProgramsValues));
        ArrayList<NewProgram> NewPrograms = new ArrayList<NewProgram>();
        
        NewProgram NewProgram1 = new NewProgram();
        NewProgram1.setName("Onion");

        NewProgram NewProgram2 = new NewProgram();
        NewProgram2.setName("Tomato");

        NewProgram NewProgram3 = new NewProgram();
        NewProgram3.setName("Potato");
        
        NewPrograms.add(NewProgram1);
        NewPrograms.add(NewProgram2);
        NewPrograms.add(NewProgram3);

        for(NewProgram NewProgram : NewPrograms) {
            String[] locations = anotherClass.createNewProgram(gameHelper.getDirection(7, 5)[0], gameHelper.getDirection(7, 5)[1]);
            NewProgram.setLocations(locations);
            System.out.println("##############################################################");
            System.out.println("######################### Start ##############################");
            System.out.println("##############################################################");
            System.out.println("NewProgram Name: " + NewProgram.getName());
            System.out.println("Locations: ");
            for (String location : locations) {
                System.out.print(location + " ");
            }
            System.out.println();
            System.out.println("##############################################################");
            System.out.println("########################## End ###############################");
            System.out.println("##############################################################");
            System.out.println();
        }
        // while(!userInput.equalsIgnoreCase("exit")) {
            // System.out.println("You guessed: " + userInput);
            // String[][] feedback = playGame.checkGuess(userInput, assignNewProgramsValues);
            // System.out.println(gameHelper.printGameStatus(feedback));
            // String result = playGame.announceResult(feedback);
            // System.out.println(result);
            // userInput = gameHelper.getUserInput();
        // }
    }

    public static String[][] assignNewPrograms(String[] NewPrograms) {
        String[][] createdNewPrograms = new String[NewPrograms.length][4];
        for (int i = 0; i < NewPrograms.length; i++) {
            Boolean[] direction = gameHelper.getDirection(7, 5);
            String[] st = anotherClass.createNewProgram(direction[0], direction[1]);
            createdNewPrograms[i][0] = NewPrograms[i];
            for (int j = 1; j <= st.length; j++) {
                createdNewPrograms[i][j] = st[j-1];
            }
        }
        return createdNewPrograms;
    }
}

class NewProgram {
    private String name;
    private String[] locations;

    public void setName(String name) {
        this.name = name;
    }

    public void setLocations(String[] locations) {
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public String[] getLocations() {
        return locations;
    }
}

/*
 * This class is responsible to create three NewPrograms
 * Give each NewProgram a different name
 * Put the NewPrograms on a grid
 */
class CreateNewPrograms {
    private int NewProgram_SIZE = 3;
    private int BOARD_SIZE = 7;

    private String[] NewProgramsLetters = {"a", "b", "c", "d", "e", "f", "g"};
    private String[] possibleNumbers = {"1", "2", "3", "4", "5"};
    
    private GameHelper gameHelper = new GameHelper();

    public String[] NewProgram = new String[NewProgram_SIZE];

    public String[] createNewProgram(Boolean growRight, Boolean growDown) {

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

        return NewProgram;  
    }

    private String[] growRight() {
        int randomLetterIndex = gameHelper.getRandomIndex(BOARD_SIZE);

        String firstLetter = NewProgramsLetters[randomLetterIndex];
        
        String selectedNumber = possibleNumbers[gameHelper.getRandomIndex(5)];

        int firstNumber = Integer.parseInt(selectedNumber);
        
        int secondNumber = firstNumber+1;
        int thirdNumber = secondNumber+1;

        NewProgram[0] = firstLetter + firstNumber;
        NewProgram[1] = firstLetter + secondNumber;
        NewProgram[2] = firstLetter + thirdNumber;
        
        return NewProgram;
    }

    private String[] growDown() {
        int randomLetterIndex = gameHelper.getRandomIndex(BOARD_SIZE-2);

        System.out.println("Random letter index: " + randomLetterIndex);
        String firstLetter = NewProgramsLetters[randomLetterIndex];

        String selectedNumber = possibleNumbers[gameHelper.getRandomIndex(5)];

        int firstNumber = Integer.parseInt(selectedNumber);
        
        String secondLetter = NewProgramsLetters[1+randomLetterIndex];
        String thirdLetter = NewProgramsLetters[2+randomLetterIndex];

        NewProgram[0] = firstLetter + firstNumber;
        NewProgram[1] = secondLetter + firstNumber;
        NewProgram[2] = thirdLetter + firstNumber;

        return NewProgram;
    }
}

/* 
 * Check user guess with all the three NewPrograms
 */

 class PlayGame {
    private String[][] NewProgramNames = new String[3][4];
    public String[][] checkGuess(String userGuess, String[][] NewPrograms) {
        for (int i = 0; i < NewPrograms.length; i++) {
            NewProgramNames[i][0] = NewPrograms[i][0];
            for (int j = 1; j < NewPrograms[i].length; j++) {
                if (NewPrograms[i][j].equalsIgnoreCase(userGuess)) {
                    NewProgramNames[i][j] = "GUSSED"; // User guessed correctly
                }
            }
        }
        return NewProgramNames; // User guessed incorrectly
    }

    public String announceResult(String[][] NewPrograms) {
        ArrayList<String> sunkNewProgram = new ArrayList<String>();
        String finalResult = "";
        Boolean isHit = false;
        int sunkNewProgramCount = 0;
        for (int i = 0; i < NewPrograms.length; i++) {
            NewProgramNames[i][0] = NewPrograms[i][0];
            int gussedCount = 0;
            for (int j = 1; j < NewPrograms[i].length; j++) {
                if (NewPrograms[i][j] != null && NewPrograms[i][j].equalsIgnoreCase("GUSSED")) {
                    isHit = true;
                    gussedCount++;
                }
            }

            if(gussedCount == 3) {
                sunkNewProgram.add(NewPrograms[i][0]);
            } 
        }

        if(!isHit) {
            finalResult = "Miss :(";
        } else {
            finalResult = "Hit!";
            if(sunkNewProgram.size() > sunkNewProgramCount) {
                finalResult += " You sunk "+sunkNewProgram.getLast();
                sunkNewProgramCount++;
            } 

            if(sunkNewProgram.size() == 3) {
                finalResult += " You sunk all the NewPrograms now!";
            } 
        }
        return finalResult.toString();
    }
 }

 /*
 * get user input
 * Keep playing the game until the user guesses all three NewPrograms
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

    public String printGameStatus(String[][] NewPrograms) {
        StringBuilder sb = new StringBuilder();
        for (String[] NewProgram : NewPrograms) {
            for (String s : NewProgram) {
                sb.append(s).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
 }