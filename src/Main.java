import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static Map<String, Integer> scores = new HashMap<>();

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Welcome to Springfield Golf Club.");
        System.out.println("Select Option:");
        System.out.println("\t1) Enter Scores");
        System.out.println("\t2) Find Golfer");
        System.out.println("\t3) Display Scoreboard");
        System.out.println("\t4) Exit Program");

        for(;;) {
            System.out.print("> ");
            // Prevent User from entering a Letter
            if(!stdin.hasNextInt()){
                invalidInputError();
                // Reset the Standard In Reader
                stdin = new Scanner(System.in);
                continue;
            }
            int option = stdin.nextInt();
            if(option == 1){
                System.out.print("How many golfers in the group: ");
                int n_golfers;
                if(stdin.hasNextInt()) {
                    n_golfers = stdin.nextInt();
                }
                else{
                    invalidInputError();
                    stdin = new Scanner(System.in);
                    continue;
                }
                for (int i = 0; i < n_golfers; i++) {
                    System.out.print("Name: ");
                    String name = stdin.next();
                    // If the name is not in the HashMap this getter will return null
                    if (getScores(name) != null) {
                        System.out.println("\n" + name + " already exist, \n\tEnter -1 for score if you don't want to override the current value");
                    }
                    System.out.print("Result: ");
                    // Prevent User from entering a Letter
                    if (stdin.hasNextInt()) {
                        int score = stdin.nextInt();
                        if (score >= 18 && score <= 108) {
                            setScores(name, score);
                            continue;
                        } else if (score == -1) {
                            continue;
                        }
                        // This line only comes if the score is not in range
                        System.out.println("\nERROR 400: Invalid Result,\n\tMake sure your result is between 18 and 108\n");
                    } else {
                        stdin = new Scanner(System.in);
                    }
                }

            }
            else if(option == 2){
                System.out.print("Name: ");
                String name = stdin.next();
                if (getScores(name) != null) {
                    System.out.println(name + "\t - " + getScores(name));
                }
                else {
                    System.out.println("ERROR 404: Player not found");
                }

            }
            else if(option == 3){
                // Sorting Method Taken from http://lewandowski.io/2014/04/java8-map/
                for (Map.Entry<String,Integer> score : scores.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toList())) {
                    System.out.println(score.getKey() + "\t - " + score.getValue());
                }

            }
            else if(option == 4){
                // Exit the Infinite Loop
                break;
            }
            else{
                invalidInputError();
                // Reset the Standard In Reader
                stdin = new Scanner(System.in);
            }
        }
    }

    // Don't repeat yourself
    private static void invalidInputError(){
        System.out.println("\nERROR 406: Invalid Input");
        System.out.println("Select *Only* one of these Option:");
        System.out.println("\t1) Enter Scores");
        System.out.println("\t2) Find Golfer");
        System.out.println("\t3) Display Scoreboard");
        System.out.println("\t4) Exit Program");

    }

    private static void setScores(String name, int score){
        scores.put(name, score);
    }

    private static Integer getScores(String name){
        return scores.get(name);
    }
}
