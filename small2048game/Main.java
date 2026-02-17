package small2048game;

import java.util.Scanner;

public class Main {
    static boolean stringIsInList(String word, String[] list) {
        boolean inList = false;
        for (String element: list) {
            if (element.equals(word)) {
                inList = true;
            }
        }
        return inList;
    }

    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        String direction;
        String[] acceptableInputs = {"z","q","s","d","Z","Q","S","D"};

        while (!(game.grid.has2048() || game.grid.isBlocked())) {
            // Display the current state of the grid
            game.displayRoundMsg();
            game.displayGrid();

            // Ask for input (next direction), check if valid and convert the value
            System.out.println("Please use Z,Q,S,D to move the grid.");
            System.out.println("(Z : up, S : down, Q : left, D : right)");
            direction = scanner.nextLine();
            while (!stringIsInList(direction, acceptableInputs)) {
                System.out.println("Sorry, invalid input.\nPlease enter Z, Q, S or D for the corresponding directions.");
                direction = scanner.nextLine();
            }
            switch (direction) {
                case "z":
                    direction = "up";
                    break;
                case "Z":
                    direction = "up";
                    break;
                case "s":
                    direction = "down";
                    break;
                case "S":
                    direction = "down";
                    break;
                case "q":
                    direction = "left";
                    break;
                case "Q":
                    direction = "left";
                    break;
                case "d":
                    direction = "right";
                    break;
                case "D":
                    direction = "right";
                    break;
            }

            // Update the grid according to user input
            game.grid.moveCells(direction);

            // Add a new number to the grid if possible
            game.grid.addNewCell();

            // Increment the round counter
            game.roundNb += 1;
        }

        // Display the appropriate message and the grid one last time
        game.displayRoundMsg();
        game.displayGrid();

        if (game.grid.has2048()) {
            game.displayWinMsg();
        } else if (game.grid.isBlocked()) {
            game.displayLostMsg();
        }

        // // Close the application properly
        scanner.close();
        System.exit(0);
    }
}
