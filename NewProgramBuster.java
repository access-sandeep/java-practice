import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class NewProgramBuster {
    private static CreateNewPrograms anotherClass = new CreateNewPrograms();
    private static GameHelper gameHelper = new GameHelper();
    // private static PlayGame playGame = new PlayGame();
    public static void main(String[] args) {
        // String userInput = gameHelper.getUserInput();
        // System.out.println("Welcome to the NewProgram Guessing Game!");
        // System.out.println(gameHelper.printGameStatus(assignNewProgramsValues));
        ArrayList<NewProgram> NewPrograms = new ArrayList<NewProgram>();
        
        NewProgram one = new NewProgram();
        one.setName("Onion");

        NewProgram two = new NewProgram();
        two.setName("Tomato");

        NewProgram three = new NewProgram();
        three.setName("Potato");
        
        NewPrograms.add(one);
        NewPrograms.add(two);
        NewPrograms.add(three);

        System.out.println(NewPrograms.size() + " NewPrograms created.");
        ArrayList<String> s1t1 = new ArrayList<String>();
        
        for(int np = 0; np < NewPrograms.size(); np++) {
            NewProgram newProgram = NewPrograms.get(np);
            Boolean[] direction = gameHelper.getDirection(7, 5);
            s1t1.clear();
            s1t1 = anotherClass.createNewProgram(direction[0], direction[1]);

            System.out.println("NewProgram: " + newProgram.getName() + " created with locations: " + s1t1.get(0) + ", " + s1t1.get(1) + ", " + s1t1.get(2)  );

            ArrayList<String> st = new ArrayList<String>();
            st.add(s1t1.get(0));
            st.add(s1t1.get(1));
            st.add(s1t1.get(2));
            
            newProgram.setLocations(st);
        }
        

        System.out.println(one.getLocations());
        System.out.println(two.getLocations());
        System.out.println(three.getLocations());

        // int i = 1;
        // for(NewProgram NewProgram : NewPrograms) {
        //     System.out.println(i+++". " + NewProgram.getName() + " at locations: " + NewProgram.getLocations());
        // }

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
            ArrayList<String> st = anotherClass.createNewProgram(direction[0], direction[1]);
            for (int j = 0; j < st.size(); j++) {
                createdNewPrograms[i][j] = st.get(j);
            }
        }
        return createdNewPrograms;
    }
}

class NewProgram {
    private String name;
    private ArrayList<String> locations = new ArrayList<>();

    public void clearLocations() {
        this.locations.clear();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocations(ArrayList<String> getLocations) {
        this.clearLocations();
        // System.out.println("Setting locations for NewProgram: " + getLocations);
        int i = 0;
        for(String location : getLocations) {
            this.locations.add(i++, location );
            // System.out.println(i+" Setting location: " + location + " for NewProgram: " + this.name);

            if(i == 3) {
                break; // Only set three locations
            }
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getLocations() {
        return this.locations;
    }
}

/*
 * This class is responsible to create three NewPrograms
 * Give each NewProgram a different name
 * Put the NewPrograms on a grid
 */
class CreateNewPrograms {
    private int BOARD_SIZE = 7;

    private String[] NewProgramsLetters = {"a", "b", "c", "d", "e", "f", "g"};
    private String[] possibleNumbers = {"1", "2", "3", "4", "5"};
    
    private GameHelper gameHelper = new GameHelper();

    public ArrayList<String> NewProgram = new ArrayList<String>();

    public ArrayList<String> createNewProgram(Boolean growRight, Boolean growDown) {

        while(!(growRight && growDown)) {
            if(growRight && !growDown) {
                return growRight();
            }

            if(!growRight && growDown) {
                return growDown();
            }

            if(!growRight && !growDown) {
                continue;
            }
        }

        if(growRight && growDown) {
             return growRight();
        }

        return NewProgram;  
    }

    private ArrayList<String> growRight() {
        int randomLetterIndex = gameHelper.getRandomIndex(BOARD_SIZE);

        String firstLetter = NewProgramsLetters[randomLetterIndex];
        
        String selectedNumber = possibleNumbers[gameHelper.getRandomIndex(5)];

        int firstNumber = Integer.parseInt(selectedNumber);
        
        int secondNumber = firstNumber+1;
        int thirdNumber = secondNumber+1;

        NewProgram.add(firstLetter + firstNumber);
        NewProgram.add(firstLetter + secondNumber);
        NewProgram.add(firstLetter + thirdNumber);
        
        return NewProgram;
    }

    private ArrayList<String> growDown() {
        int randomLetterIndex = gameHelper.getRandomIndex(BOARD_SIZE-2);
        
        String firstLetter = NewProgramsLetters[randomLetterIndex];

        String selectedNumber = possibleNumbers[gameHelper.getRandomIndex(5)];

        int firstNumber = Integer.parseInt(selectedNumber);
        
        String secondLetter = NewProgramsLetters[1+randomLetterIndex];
        String thirdLetter = NewProgramsLetters[2+randomLetterIndex];

        NewProgram.add(firstLetter + firstNumber);
        NewProgram.add(secondLetter + firstNumber);
        NewProgram.add(thirdLetter + firstNumber);

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