/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuSolver;

/**
 *
 * @author Dwayne
 */
class BacktrackingSolver extends SudokuSolver{
    
    
    public boolean solve(int[][] grid) {
        // Start the backtracking algorithm at the first cell
        return backtrack(grid, 0, 0);
    }

    private boolean backtrack(int[][] grid, int row, int col) {
        // Check if we've reached the end of the grid
        if (row == 9) {
            return true;
        }

        // Check if the current cell is already filled
        if (grid[row][col] != 0) {
            // Move to the next cell
            if (col == 8) {
                return backtrack(grid, row + 1, 0);
            } else {
                return backtrack(grid, row, col + 1);
            }
        }

        // Try filling the current cell with a value between 1 and 9
        for (int val = 1; val <= 9; val++) {
            if (isValid(grid, row, col, val)) {
                grid[row][col] = val;

                // Move to the next cell
                if (col == 8) {
                    if (backtrack(grid, row + 1, 0)) {
                        return true;
                    }
                } else {
                    if (backtrack(grid, row, col + 1)) {
                        return true;
                    }
                }
            }
        }

        // No valid value found, backtrack
        grid[row][col] = 0;
        return false;
    }

    private boolean isValid(int[][] grid, int row, int col, int val) {
        // Check if the value is already in the same row
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == val) {
                return false;
            }
        }

        // Check if the value is already in the same column
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == val) {
                return false;
            }
        }

        // Check if the value is already in the same 3x3 subgrid
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startRow + i][startCol + j] == val) {
                    return false;
                }
            }
        }

        // The value is valid
        return true;
    }
    
}
