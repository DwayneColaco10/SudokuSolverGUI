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
public class Result {
    private int puzzleNumber;
    private double constraintPropagationTime;
    private double backtrackingTime;

    public Result(int puzzleNumber, double constraintPropagationTime, double backtrackingTime) {
        this.puzzleNumber = puzzleNumber;
        this.constraintPropagationTime = constraintPropagationTime;
        this.backtrackingTime = backtrackingTime;
    }

    public int getPuzzleNumber() {
        return puzzleNumber;
    }

    public double getConstraintPropagationTime() {
        return constraintPropagationTime;
    }

    public double getBacktrackingTime() {
        return backtrackingTime;
    }
}
