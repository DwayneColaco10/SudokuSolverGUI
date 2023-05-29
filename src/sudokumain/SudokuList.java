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
public class SudokuList {
    
        public static List<int[][]> getPuzzles() {
        List<int[][]> sudokuPuzzles = new ArrayList<>();

        SudokuGenerator easyGenerator = new SudokuGenerator(20);
        SudokuGenerator mediumGenerator = new SudokuGenerator(30);
        SudokuGenerator hardGenerator = new SudokuGenerator(40);

        for (int i = 0; i < 5; i++) {
            sudokuPuzzles.add(easyGenerator.generate());
            sudokuPuzzles.add(mediumGenerator.generate());
            sudokuPuzzles.add(hardGenerator.generate());
        }

        return sudokuPuzzles;
    }
    
}
