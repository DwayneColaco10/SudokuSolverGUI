/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokumain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Dwayne
 */
public class SudokuGenerator {

    private static final int SIZE = 9;
    private int numberOfClues;
    private int[][] grid;
    private Random random;


    public SudokuGenerator(int numberOfClues) {
        this.numberOfClues = numberOfClues;
        this.grid = new int[SIZE][SIZE];
        this.random = new Random();
    }

    public int[][] generate() {
        clearGrid();
        fillValues();
        removeValues(numberOfClues);
        return grid;
    }

    private void clearGrid() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                grid[row][col] = 0;
            }
        }
    }

    private void fillValues() {
        for (int i = 1; i <= SIZE; i++) {
            int row, col;
            do {
                row = random.nextInt(SIZE);
                col = random.nextInt(SIZE);
            } while (grid[row][col] != 0);

            grid[row][col] = i;
        }

        // Use a solver to complete the grid
        ConstraintPropagation solver = new ConstraintPropagation(grid);
        solver.solve();
    }

    private void removeValues(int numberOfClues) {
        List<Integer> positions = new ArrayList<>(SIZE * SIZE);
        for (int i = 0; i < SIZE * SIZE; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions, random);

        for (int i = 0; i < numberOfClues; i++) {
            int position = positions.get(i);
            int row = position / SIZE;
            int col = position % SIZE;
            grid[row][col] = 0;
        }
    }

}
