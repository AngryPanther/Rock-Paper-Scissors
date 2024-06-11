package rockpaperscissors;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static String playerName = "";
    static int playerScore = 350;
    static String[] list;
    static String[] list2;

    /* Main method - Greets player -> accepts game rules (if player does not provide the rules, game will default to
    standard rock paper scissors) -> going forward the game will only accept an element of the provided list, !rating,
    and !exit */
    public static void main(String[] args) throws IOException {
        Scanner scInput = new Scanner(System.in);
        String playerInput = "";
        startingScore();
        String input = scInput.nextLine();
        if (input.isEmpty()) {
            input = "rock,paper,scissors";
        }

        list = input.split(",");
        final String arrayString = String.join(",", list);
        System.out.println("Okay, let's start");

        while (!"exit".equals(playerInput)) {
            playerInput = scInput.nextLine();
            if (arrayString.contains(playerInput)) {
                arrayInput(playerInput);
                System.out.println(playGame(playerInput));
            } else {
                switch (playerInput) {
                    case "!rating":
                        System.out.printf("Your rating: %s\n", playerScore);
                        break;
                    case "!exit":
                        System.out.println("Bye!");
                        scInput.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid input");
                }
            }
        }
    }

    //Method to determine starting score for player
    private static void startingScore() throws IOException {
        File file = new File(".");
        Scanner scInput = new Scanner(System.in);
        Scanner scFile = new Scanner(file.getCanonicalPath());
        System.out.print("Enter your name: ");
        playerName = scInput.nextLine();
        System.out.printf("Hello, %s\n", playerName);
        while (scFile.hasNextLine()) {
            if (playerName.equals(scFile.next())) {
                playerScore = Integer.parseInt(scFile.next());
            }
        }
}

    /*Helper method to determine whether player wins.  The player's selection becomes the starting element
    of a new list with the subsequent elements to follow. The list needs to loop to the beginning one time if the first
    element of the original list is not chosen.  I.e.: the "rock" of ["rock", "paper", "scissors"] */
    private static void arrayInput(String playerInput) {
        int n = java.util.Arrays.asList(list).indexOf(playerInput);
        list2 = Arrays.copyOf(list, list.length, String[].class);
        for (int i = 0; i < list2.length; i++) {
            list2[i] = list[n];
            if (n == list2.length-1) {
                n = 0;
            } else {
                n++;
            }
            if (list2[i].equals(list[n])) {
                break;
            }
        }

    }

    /*Play game method.  Computer selects a random element from the list against the player's selection.  The second
    list (created above) is split in half with a slight bias to the first half (The first half will have an additional
    element in the case of an odd number of total elements.)  Player's selections loses to any element in the first half
    of the revised list (here noted as firstHalf) and defeats any element in the second half of the revised list
    (secondHalf). Player is scored accordingly.*/

    private static String playGame(String playerInput) {
        Random rand = new Random();
        int randomizer = rand.nextInt(0, list.length);
        String computerSelection = list[randomizer];
        String result = "";
        String[] firstHalf = Arrays.copyOfRange(list2, 0, (list.length/2)+1);
        String[] secondHalf = Arrays.copyOfRange(list2, (list.length/2)+1, list.length);
        for (String element: firstHalf) {
            if (element.equals(computerSelection) && element.equals(playerInput)) {
                playerScore += 50;
                result = "There is a draw (" + element + ")";
            } else if (element.equals(computerSelection)) {
                result = "Sorry, but the computer chose " + computerSelection;
            }
        } for (String element: secondHalf) {
            if (element.equals(computerSelection) && element.equals(playerInput)) {
                playerScore += 50;
                result = "There is a draw (" + element + ")";
            } else if (element.equals(computerSelection)) {
                playerScore += 100;
                result = "Well done. The computer chose " + computerSelection + " and failed";
            }
        } return result;
    }
}

