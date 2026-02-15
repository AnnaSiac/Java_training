package small2048game;

import org.junit.Test;
import static org.junit.Assert.*;

public class GridTest {
    @Test
    public void constructorDefaultValues() {
        Grid testGrid = new Grid();
        assertTrue(testGrid.cells.length == 4);
        assertTrue(testGrid.cells[0].length == 4);
        
        int[][] testCells = new int[4][4];
        for (int i = 0; i < testCells.length; i++) {
            assertArrayEquals(testCells[i], testGrid.cells[i]);
        }
    }

    @Test
    public void heightAndWidth() {
        Grid testGrid = new Grid();
        Grid emptyGrid = new Grid();

        int[][] rectangle = {{654,64,646,987,879},
                            {879,87,879,987,87},
                            {5,844,-4,9,844}};
        testGrid.cells = rectangle;
        
        int[][] empty = {{}};
        emptyGrid.cells = empty;


        assertEquals(3, testGrid.h());
        assertEquals(5, testGrid.w());
        assertEquals(0, emptyGrid.h());
        assertEquals(0, emptyGrid.h());
    }

    @Test
    public void isBlockedTest() {
        Grid notBlockedGrid1 = new Grid();
        Grid notBlockedGrid2 = new Grid();
        Grid notBlockedGrid3 = new Grid();
        Grid blockedGrid = new Grid();

        int[][] verticalFives = {{12,5464,15,8},
                                {6,5,89,7},
                                {4,5,-5,78},
                                {884,8,-564,8}};
        notBlockedGrid1.cells = verticalFives;

        int[][] horizontalFives = {{12,5464,15,8},
                                    {6,45,89,7},
                                    {5,5,-5,78},
                                    {884,8,-564,8}};
        notBlockedGrid2.cells = horizontalFives;

        int[][] withZero = {{12,5464,15,8},
                            {6,4,89,7},
                            {4,5,-5,0},
                            {884,8,-564,8}};
        notBlockedGrid3.cells = withZero;

        int[][] fullyBlocked = {{12,5464,15,8},
                                {6,2,89,7},
                                {4,5,-5,78},
                                {884,8,-564,8}};
        blockedGrid.cells = fullyBlocked;

        assertFalse(notBlockedGrid1.isBlocked());
        assertFalse(notBlockedGrid2.isBlocked());
        assertFalse(notBlockedGrid3.isBlocked());
        assertTrue(blockedGrid.isBlocked());
    }

    @Test
    public void has2048Test() {
        Grid gridWith2048 = new Grid();
        Grid gridWithout2048 = new Grid();

        int[][] with2048 = {{12,2048,15,8},
                            {6,5,89,7},
                            {4,5,-5,78},
                            {884,8,-564,8}};
        gridWith2048.cells = with2048;

        int[][] without2048 = {{12,208,15,8},
                            {6,5,89,7},
                            {4,5,-5,78},
                            {884,8,-564,8}};
        gridWithout2048.cells = without2048;

        assertTrue(gridWith2048.has2048());
        assertFalse(gridWithout2048.has2048());
    }
}
