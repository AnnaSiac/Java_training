package small2048game;

import java.util.Random;

public class Game {
    Grid grid;
    int roundNb;

    Game() {
        this.grid = new Grid();
        this.roundNb = 0;
    }

    void displayRoundMsg() {
        System.out.println("Round "+this.roundNb);
    }

    void displayWinMsg() {
        System.out.println("Congratulations! You have won after "+this.roundNb+" rounds.");
    }

    void displayLostMsg() {
        System.out.println("Sorry! The grid is blocked, you lost.";)
    }

    void addNewCell() {
        // Check if there is a free (zero) cell
        boolean freeCell = false;
        for (int i=0; i<this.grid.h(); i++) {
            for (int j=0; j<this.grid.w(); j++) {
                if (this.grid.cells[i][j] == 0) {
                    freeCell = true;
                }
            }
        }
        if (!freeCell) { // return if there are no free cell
            return;
        }

        // Generate a 2 or a 4
        Random random = new Random();
        int newValue = random.nextInt(2);
        newValue = (newValue + 1) * 2;

        // Randomly find a free cell and assign the new value (2 or 4)
        int i, j;
        do {
            i = random.nextInt(this.grid.h());
            j = random.nextInt(this.grid.w());
        } while (this.grid.cells[i][j] != 0);

        this.grid.cells[i][j] = newValue;
    }

    static int numberLenght(int number) {
        int length = 1;
        while ((number / 10) > 0) {
            number = number / 10;
            length += 1;
        }
        return length;
    }

    void displayGrid() {
        // Prepare the interline
        String interline = " ----";
        interline = interline.repeat(this.grid.w());

        // Iterate over the grid to print each cell correctly
        for (int i=0; i<this.grid.h(); i++) {
            System.out.println(interline);

            for (int j=0; j<this.grid.w(); j++) {
                System.out.print("|");
                // Print each cell number centered
                int nbSize = numberLenght(this.grid.cells[i][j]);
                switch (nbSize) { // Spaces before and after depends on the number's length
                    case 1:
                        System.out.print(" "+this.grid.cells[i][j]+"  ");
                    case 2:
                        System.out.print(" "+this.grid.cells[i][j]+" ");
                    case 3:
                        System.out.print(this.grid.cells[i][j]+" ");
                    case 4:
                        System.out.print(this.grid.cells[i][j]);
                }
            }
            // Print the final "|"
            System.out.println("|");
        }
        // Print the final interline
        System.out.println(interline);

        // Example of displayed result:
        //  ---- ---- ---- ----
        // | 2  | 4  | 8  | 16 |
        //  ---- ---- ---- ----
        // | 32 | 64 |128 |256 |
        //  ---- ---- ---- ----
        // |512 |1024|2048|2048|
        //  ---- ---- ---- ----
    }
}
