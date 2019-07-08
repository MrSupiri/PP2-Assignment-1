import java.util.*;
import java.util.stream.Collectors;

/**
 * Name - Isala Piyarisi
 * IIT ID - 2018421
 * UOW ID - w1742118
 */
public class GolfClub {
    private static Map<String, Integer> scores = new HashMap<>();
    private static Map<String, Integer> scoresArchive = new HashMap<>();
    private static Map<String, Integer> lastScores = new HashMap<>();
    private static Map<String, Integer> lastScoresArchive = new HashMap<>();
    private static boolean restoredSnapshot = false;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Springfield Golf Club.");
        // Defined option here so it can be accessed by the while loop condition
        int option;
        do {
            showMenu();

            // Prevent User from entering a Letter
            System.out.print("> ");
            while (!sc.hasNextInt()) {
                System.out.println("\nERROR 406: Invalid Input\nSelect *Only* one of these Option:\n");
                showMenu();
                System.out.print("> ");
                // Take the next Input
                sc.nextLine();
            }
            option = sc.nextInt();
            sc.nextLine();
            // Switch case statement to map inputs to it's relevant methods
            switch (option) {
                case 1:
                    enterScore();
                    break;

                case 2:
                    findGolfer();
                    break;

                case 3:
                    displayScoreboard();
                    break;

                case 4:
                    removeGolfer();
                    break;

                case 5:
                    restoreGolfer();
                    break;

                case 6:
                    restoreLastState();
                    break;

                case 7:
                    // Exit will be handle by the while loop
                    break;

                // Entered Value didn't match with any of the outputs
                default:
                    System.out.println("\nERROR 406: Invalid Input");
                    showMenu();
            }

        } while (option != 7);
    }

    // First rule of programming - Don't repeat yourself
    private static void showMenu() {
        System.out.println("\nSelect one of these Option:");
        System.out.println("\t1) Enter Scores");
        System.out.println("\t2) Find Golfer");
        System.out.println("\t3) Display Scoreboard");
        System.out.println("\t4) Remove Golfer");
        System.out.println("\t5) Restore a removed golfer");
        if(isRestoredSnapshot()){
            System.out.println("\t6) Redo last action");
        }
        else{
            System.out.println("\t6) Undo last action");
        }
        System.out.println("\t7) Exit Program\n");
    }

    /**
     * Get Player's name and scores and store in a temporary  HashMap
     * and finally add all the collected data to the Main HashMap
     */
    private static void enterScore() {
        Map<String, Integer> newScores = new HashMap<>();
        System.out.print("How many golfers in the group: ");

        while (!sc.hasNextInt()) {
            System.out.println("\nERROR 406: Invalid Input");
            System.out.print("How many golfers in the group: ");
            sc.nextLine();
        }
        int n_golfers = sc.nextInt();

        for (int i = 0; i < n_golfers; i++) {
            System.out.println("\nGOLFER " + (i + 1));
            sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            int score = getScoreFromUser();

            if(scores.containsKey(name) || newScores.containsKey(name)){
                System.out.println(name + " is already existing in the data structure");
                System.out.println("Are you want to override the data (Y/n) ?\nEnter 'n' if you don't want to override the data");
                if(sc.next().charAt(0) == 'n'){
                    i--;
                    continue;
                }
            }
            newScores.put(name, score);

        }

        setMultipleScores(newScores);
    }

    /**
     * Recursive function to validate both inputted value is string and it's the defined range
     * @return - inputted score of the player
     */
    private static int getScoreFromUser(){
        System.out.print("Result: ");
        while (!sc.hasNextInt()) {
            System.out.println("\nERROR 400: Invalid Result,\n\tMake sure your result is between 18 and 108, Enter Again !\n");
            System.out.print("Result: ");
            sc.next();
        }
        int score = sc.nextInt();
        if(score < 18 || score > 108){
            System.out.println("\nERROR 400: Invalid Result,\n\tMake sure your result is between 18 and 108, Enter Again !\n");
            score = getScoreFromUser();
        }
        return score;
    }

    /**
     * Find player from the data structure
     */
    private static void findGolfer() {
        System.out.print("Name: ");
        String name = sc.nextLine();

        if (checkGolfer(name)) {
            System.out.println(name + "\t - " + getScores(name));
        } else {
            System.out.println("ERROR 404: Player not found");
        }
    }

    /**
     * Remove a player from data structure
     */
    private static void removeGolfer() {
        System.out.print("Name: ");
        String name = sc.nextLine();

        if (checkGolfer(name)) {
            setScoresArchive(name, getScores(name));
            scores.remove(name);
            System.out.println(name + " was removed from the data structure");
        } else {
            System.out.println("ERROR 404: Player not found");
        }
    }

    /**
     * Restore accidentally deleted player
     */
    private static void restoreGolfer() {
        System.out.print("Name: ");
        String name = sc.nextLine();

        if (checkGolferArchive(name)) {
            setScores(name, getScoresArchive(name));
            scoresArchive.remove(name);
            System.out.println(name + " was restored to the data structure");
        } else {
            System.out.println("ERROR 404: Player not found");
        }
    }

    /**
     * Sort the scores hash map in the ascending order and print it one by one
     */
    private static void displayScoreboard() {
        // Sorting Method was inspired from http://lewandowski.io/2014/04/java8-map/
        for (Map.Entry<String, Integer> score : scores.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList())) {
            System.out.println(score.getKey() + "\t - " + score.getValue());
        }
    }

    /**
     * Restore the last state to current state
     */
    private static void restoreLastState(){
        Map<String, Integer> currentScores= new HashMap<>(scores);
        Map<String, Integer> currentScoresArchive= new HashMap<>(scoresArchive);
        if(isRestoredSnapshot()) {
            System.out.println("Redoing the last action");
            restoredSnapshot = false;
        }
        else {
            System.out.println("Undoing the last action");
            restoredSnapshot = true;
        }
        scores= new HashMap<>(lastScores);
        scoresArchive= new HashMap<>(lastScoresArchive);
        lastScores= new HashMap<>(currentScores);
        lastScoresArchive= new HashMap<>(currentScoresArchive);

    }

    /**
     * Setter for scores
     * @param name - Name of the Player
     * @param score - Player's Score
     */
    private static void setScores(String name, int score) {
        snapshotCurrentState();
        scores.put(name, score);
    }

    /**
     * Enter multiple number of players to scores
     * @param newScores - Scores as HashMap
     */
    private static void setMultipleScores(Map<String, Integer> newScores) {
        snapshotCurrentState();
        scores.putAll(newScores);
    }

    /**
     * Getter for Score scoresArchive
     * @param name - name of the player
     * @return - score of the given player
     */
    private static Integer getScores(String name) {
        return scores.get(name);
    }

    /**
     * check if the player exists in the Scores
     * @param name - name of the player
     * @return - weather player is the Scores
     */
    private static boolean checkGolfer(String name) {
        return scores.containsKey(name);
    }

    /**
     * Setter for Score scoresArchive
     * @param name - Name of the Player
     * @param score - Score of the Player
     */
    private static void setScoresArchive(String name, int score) {
        snapshotCurrentState();
        scoresArchive.put(name, score);
    }

    /**
     * Getter for Score scoresArchive
     * @param name - Name of the Player
     * @return - Score of the given player
     */
    private static Integer getScoresArchive(String name) {
        return scoresArchive.get(name);
    }

    /**
     * check if the player exists in the scoresArchive
     * @param name - name of the player
     * @return - weather player is the scoresArchive
     */
    private static boolean checkGolferArchive(String name) {
        return scoresArchive.containsKey(name);
    }

    /**
     * Take the Snapshot of current state and save it
     */
    private static void snapshotCurrentState(){
        lastScores= new HashMap<>(scores);
        lastScoresArchive= new HashMap<>(scoresArchive);
    }

    /**
     * Check if the last action is undo
     * @return - last action is undo or not
     */
    private static boolean isRestoredSnapshot(){
        return restoredSnapshot;
    }

}
