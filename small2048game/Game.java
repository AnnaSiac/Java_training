package small2048game;

public class Game {
    Grid grid;
    int roundNb;

    Game() {
        this.grid = new Grid();
        this.grid.addNewCell();
        this.grid.addNewCell();
        this.roundNb = 1;
    }

    void displayRoundMsg() {
        System.out.println("Round "+this.roundNb);
    }

    void displayWinMsg() {
        System.out.println("Congratulations! You have won after "+this.roundNb+" rounds.");
    }

    void displayLostMsg() {
        System.out.println("Sorry! The grid is blocked, you lost.");
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
                        break;
                    case 2:
                        System.out.print(" "+this.grid.cells[i][j]+" ");
                        break;
                    case 3:
                        System.out.print(this.grid.cells[i][j]+" ");
                        break;
                    case 4:
                        System.out.print(this.grid.cells[i][j]);
                        break;
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
