import java.util.Arrays;
import java.util.InputMismatchException;

public class Grid {
    int h;  // grid height
    int w;  // grid width
    int[][] cells;

    Grid () {
        this.h = 4;
        this.w = 4;
        this.cells = new int[this.h][this.w];
    }

    boolean isBlocked() { // checks if the grid is blocked (game over)
        for (int i=0; i<this.h; i++) {
            for (int j=0; j<this.w; j++) {
                if (this.cells[i][j] == 0) {
                    return false;
                } else if (i < this.h-1 && this.cells[i][j]==this.cells[i+1][j]) {
                    return false;
                } else if (j < this.w-1 && this.cells[i][j]==this.cells[i][j+1]) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean has2048() { // checks if there's a 2048 in the cells (game won)
        for (int i=0; i<this.h; i++) {
            for (int j=0; j<this.w; j++) {
                if (this.cells[i][j] >= 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    void turnGridAround(int angle) {
        if (angle % 90 != 0) {
            throw new InputMismatchException("Invalid angle for turning grid around");
        }

        angle = angle / 90;
        angle = angle % 4;
        int[][] pivotedCells;

        switch (angle) {
            case 0: // simply copy the array
                pivotedCells = new int[this.h][this.w];
                for (int i=0; i<this.h; i++) {
                    for (int j=0; j<this.w; j++) {
                        pivotedCells[i][j] = this.cells[i][j];
                    }
                }
                break;
            case 1: // copy the array but rotate it 90° counter-clockwise
                pivotedCells = new int[this.w][this.h];
                for (int i=0; i<this.h; i++) {
                    for (int j=0; j<this.w; j++) {
                        pivotedCells[this.w-j][i] = this.cells[i][j];
                    }
                }
                break;
            case 2: // copy the array but rotate it 180° counter-clockwise
                pivotedCells = new int[this.h][this.w];
                for (int i=0; i<this.h; i++) {
                    for (int j=0; j<this.w; j++) {
                        pivotedCells[this.h-i][this.w-j] = this.cells[i][j];
                    }
                }
                break;
            case 3: // copy the array but rotate it 270° counter-clockwise
                pivotedCells = new int[this.w][this.h];
                for (int i=0; i<this.h; i++) {
                    for (int j=0; j<this.w; j++) {
                        pivotedCells[j][this.h-i] = this.cells[i][j];
                    }
                }
                break;
            default:
                throw new InputMismatchException("Invalid angle for turning grid around");
        }

        this.cells = pivotedCells;
        this.h = pivotedCells.length;
        this.w = pivotedCells[0].length;
    }

    void slideNonZeroCellsToTheLeft () {
        // Recursion stopping condition (if it stays false)
        boolean movedACell = false; 

        // Switching all cells' couples like [O, non-zero] to [non-zero, 0]
        for (int i=0; i<this.h; i++) {
            for (int j=0; j<this.w-1; j++) {
                if (this.cells[i][j]==0 && this.cells[i][j+1]!=0) {
                    this.cells[i][j] = this.cells[i][j+1];
                    this.cells[i][j+1] = 0;
                    movedACell = true;
                }
            }
        }
        
        // Recursion to check if there are any more couples to switch
        if (movedACell) {
            System.out.println("starting a recursion iteration"); // to delete
            this.slideNonZeroCellsToTheLeft();
        }
    }

    void moveCellsToTheLeft() {
        // Move the non-zero cells to the left of the zero ones
        this.slideNonZeroCellsToTheLeft();

        // If two side-by-side cells are equals, add the right one to
        // the left one and reset the right one to zero.
        for (int i=0; i<this.h; i++) {
            for (int j=0; j<this.w-1; j++) {
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

            case "right":
                this.turnGridAround(180);
                this.moveCellsToTheLeft();
                this.turnGridAround(-180);

            case "up":
                this.turnGridAround(90);
                this.moveCellsToTheLeft();
                this.turnGridAround(-90);

            case "down":
                this.turnGridAround(270);
                this.moveCellsToTheLeft();
                this.turnGridAround(-270);
            
            default:
                throw new InputMismatchException("Invalid direction for moving cells : "+direction);
        }
    }

    void testDisplayGrid () { // to delete after debug
        for (int[] line : this.cells) {
            System.out.println(Arrays.toString(line));
        }
    }

    public static void main(String[] args) {
        System.out.println("Grid just got executed!");
    }
}

class Game {
    // todo
}