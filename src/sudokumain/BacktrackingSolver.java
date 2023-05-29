/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokumain;

/**
 *
 * @author Dwayne
 */
public class BacktrackingSolver {
      
     private int SIZE; // size of the grid (e.g. 4 for a 4x4 grid)
    private int[][] grid;

    public BacktrackingSolver(int[][] grid) {
        this.grid = grid;
        this.SIZE = grid.length;
    }

    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(row, col, num)) {
                            grid[row][col] = num;
                            if (solve()) {
                                return true;
                            } else {
                                grid[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            // check row
            if (grid[row][i] == num) {
                return false;
            }
            // check column
            if (grid[i][col] == num) {
                return false;
            }
        }

        // check subgrid
        int subGridRow = (row / (int) Math.sqrt(SIZE)) * (int) Math.sqrt(SIZE);
        int subGridCol = (col / (int) Math.sqrt(SIZE)) * (int) Math.sqrt(SIZE);
        for (int i = subGridRow; i < subGridRow + Math.sqrt(SIZE); i++) {
            for (int j = subGridCol; j < subGridCol + Math.sqrt(SIZE); j++) {
                if (grid[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}
