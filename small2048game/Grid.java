package small2048game;

// import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;

public class Grid {
    int[][] cells;

    Grid () {
        int height = 4;
        int width = 4;
        this.cells = new int[height][width];
    }

    int h() { // height of the grid
        if (this.cells.length > 0 && this.cells[0].length == 0) {
            return 0;
        } else {
            return this.cells.length;
        }
    }

    int w() { // width of the grid
        if (this.cells.length == 0) {
            return 0;
        } else {
            return this.cells[0].length;
        }
    }

    boolean isBlocked() { // checks if the grid is blocked (game over)
        for (int i=0; i<this.h(); i++) {
            for (int j=0; j<this.w(); j++) {
                if (this.cells[i][j] == 0) { // blocked if cells equal to 0
                    return false;
                } else if (i < this.h()-1 && this.cells[i][j]==this.cells[i+1][j]) {
                    return false; // or if there are 2 identical cells next to each other
                } else if (j < this.w()-1 && this.cells[i][j]==this.cells[i][j+1]) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean has2048() { // checks if there's a 2048 in the cells (game won)
        for (int i=0; i<this.h(); i++) {
            for (int j=0; j<this.w(); j++) {
                if (this.cells[i][j] >= 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    void turnGridAround(int angle) {
        if ( angle % 90 != 0) {
            throw new InputMismatchException("Invalid angle for turning grid around");
        }

        angle = angle / 90;
        angle = (angle + 4) % 4;
        int[][] pivotedCells;

        switch (angle) {
            case 0: // simply copy the array
                pivotedCells = new int[this.h()][this.w()];
                for (int i=0; i<this.h(); i++) {
                    for (int j=0; j<this.w(); j++) {
                        pivotedCells[i][j] = this.cells[i][j];
                    }
                }
                break;
            case 1: // copy the array but rotate it 90° counter-clockwise
                pivotedCells = new int[this.w()][this.h()];
                for (int i=0; i<this.h(); i++) {
                    for (int j=0; j<this.w(); j++) {
                        pivotedCells[this.w()-j-1][i] = this.cells[i][j];
                    }
                }
                break;
            case 2: // copy the array but rotate it 180° counter-clockwise
                pivotedCells = new int[this.h()][this.w()];
                for (int i=0; i<this.h(); i++) {
                    for (int j=0; j<this.w(); j++) {
                        pivotedCells[this.h()-i-1][this.w()-j-1] = this.cells[i][j];
                    }
                }
                break;
            case 3: // copy the array but rotate it 270° counter-clockwise
                pivotedCells = new int[this.w()][this.h()];
                for (int i=0; i<this.h(); i++) {
                    for (int j=0; j<this.w(); j++) {
                        pivotedCells[j][this.h()-i-1] = this.cells[i][j];
                    }
                }
                break;
            default:
                throw new InputMismatchException("Invalid angle for turning grid around");
        }

        this.cells = pivotedCells;
    }

    void slideNonZeroCellsToTheLeft () {
        // Recursion stopping condition (if it stays false)
        boolean movedACell = false; 

        // Switching all cells' couples like [O, non-zero] to [non-zero, 0]
        for (int i=0; i<this.h(); i++) {
            for (int j=0; j<this.w()-1; j++) {
                if (this.cells[i][j]==0 && this.cells[i][j+1]!=0) {
                    this.cells[i][j] = this.cells[i][j+1];
                    this.cells[i][j+1] = 0;
                    movedACell = true;
                }
            }
        }
        
        // Recursion to check if there are any more couples to switch
        if (movedACell) {
            this.slideNonZeroCellsToTheLeft();
        }
    }

    void moveCellsToTheLeft() {
        // Move the non-zero cells to the left of the zero ones
        this.slideNonZeroCellsToTheLeft();

        // If two side-by-side cells are equals, add the right one to
        // the left one and reset the right one to zero.
        for (int i=0; i<this.h(); i++) {
            for (int j=0; j<this.w()-1; j++) {
                if (this.cells[i][j]==this.cells[i][j+1]) {
                    this.cells[i][j] *= 2;
                    this.cells[i][j+1] = 0;
                }
            }
        }

        // And remove again any hole (zero) newly created
        this.slideNonZeroCellsToTheLeft();
    }

    void moveCells(String direction) {
        switch (direction) {
            case "left":
                this.moveCellsToTheLeft();
                break;
            case "right":
                this.turnGridAround(180);
                this.moveCellsToTheLeft();
                this.turnGridAround(-180);
                break;
            case "up":
                this.turnGridAround(90);
                this.moveCellsToTheLeft();
                this.turnGridAround(-90);
                break;
            case "down":
                this.turnGridAround(270);
                this.moveCellsToTheLeft();
                this.turnGridAround(-270);
                break;
            default:
                throw new InputMismatchException("Invalid direction for moving cells : "+direction);
        }
    }

    void addNewCell() {
        // Check if there is a free (zero) cell
        boolean freeCell = false;
        for (int i=0; i<this.h(); i++) {
            for (int j=0; j<this.w(); j++) {
                if (this.cells[i][j] == 0) {
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
            i = random.nextInt(this.h());
            j = random.nextInt(this.w());
        } while (this.cells[i][j] != 0);

        this.cells[i][j] = newValue;
    }

    // void testDisplayGrid () { // to delete after debug
    //     for (int[] line : this.cells) {
    //         System.out.println(Arrays.toString(line));
    //     }
    // }

    // public static void main(String[] args) {
    //     System.out.println("Grid just got executed!");
    // }
}
