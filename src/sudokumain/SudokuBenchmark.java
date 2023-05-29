/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokumain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dwayne
 */
public class SudokuBenchmark {

public List<Result> runBenchmark() {
        List<int[][]> puzzles = SudokuList.getPuzzles();
        List<Result> results = new ArrayList<>();

        int puzzleNumber = 1;

        for (int[][] puzzle : puzzles) {
            int[][] puzzleCopy1 = copyGrid(puzzle);
            int[][] puzzleCopy2 = copyGrid(puzzle);

            long startTime = System.nanoTime();
            ConstraintPropagation constraintPropagation = new ConstraintPropagation(puzzleCopy1);
            constraintPropagation.solve();
            long endTime = System.nanoTime();
            double constraintPropagationTime = (endTime - startTime) / 1_000_000.0;

            startTime = System.nanoTime();
            BacktrackingSolver backtrackingSolver = new BacktrackingSolver(puzzleCopy2);
            backtrackingSolver.solve();
            endTime = System.nanoTime();
            double backtrackingTime = (endTime - startTime) / 1_000_000.0;

            results.add(new Result(puzzleNumber, constraintPropagationTime, backtrackingTime));
            puzzleNumber++;
        }

        return results;
    }

    private static int[][] copyGrid(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

}
