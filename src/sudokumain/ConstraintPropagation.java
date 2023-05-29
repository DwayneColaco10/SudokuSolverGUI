/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokumain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dwayne
 */
public class ConstraintPropagation {
 private List<Set<Integer>> rows;
    private List<Set<Integer>> cols;
    private List<Set<Integer>> boxes;
    private int boxSize;
    int[][] board;

    public ConstraintPropagation(int[][] board) {
        this.board = board;
        this.boxSize = (int) Math.sqrt(board.length);
        this.rows = new ArrayList<>();
        this.cols = new ArrayList<>();
        this.boxes = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            rows.add(new HashSet<Integer>());
            cols.add(new HashSet<Integer>());
            boxes.add(new HashSet<Integer>());
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int value = board[i][j];
                if (value != 0) {
                    rows.get(i).add(value);
                    cols.get(j).add(value);
                    boxes.get(getBoxIndex(i, j)).add(value);
                }
            }
        }
    }

    public boolean solve() {
        return solveHelper(0, 0);
    }

    private boolean solveHelper(int row, int col) {
        if (row == board.length) {
            return true;
        }

        int nextRow = col == board.length - 1 ? row + 1 : row;
        int nextCol = (col + 1) % board.length;

        if (board[row][col] != 0) {
            return solveHelper(nextRow, nextCol);
        }

        for (int i = 1; i <= board.length; i++) {
            if (isValid(row, col, i)) {
                board[row][col] = i;
                rows.get(row).add(i);
                cols.get(col).add(i);
                boxes.get(getBoxIndex(row, col)).add(i);

                if (solveHelper(nextRow, nextCol)) {
                    return true;
                }

                board[row][col] = 0;
                rows.get(row).remove(i);
                cols.get(col).remove(i);
                boxes.get(getBoxIndex(row, col)).remove(i);
            }
        }

        return false;
    }

    private boolean isValid(int row, int col, int value) {
        if (rows.get(row).contains(value)) {
            return false;
        }

        if (cols.get(col).contains(value)) {
            return false;
        }

        if (boxes.get(getBoxIndex(row, col)).contains(value)) {
            return false;
        }

        return true;
    }

    private int getBoxIndex(int row, int col) {
        int rowIndex = row / boxSize;
        int colIndex = col / boxSize;
        return rowIndex * boxSize + colIndex;
    }


}
