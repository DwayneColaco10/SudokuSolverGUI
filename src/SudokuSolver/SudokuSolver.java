/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuSolver;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Dwayne
 */
public class SudokuSolver extends Application {

    // 2D array to represent the Sudoku grid
    private int[][] grid = new int[9][9];

    // Button array to hold the buttons that represent the cells of the grid
    private Button[][] buttons = new Button[9][9];

    public static void main(String[] args) {
        launch(args);
    }
    private HBox hBox;

    @Override
    public void start(Stage primaryStage) {
        // Create the GridPane that will hold the buttons
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10));

        // Create the array of buttons that will represent the cells of the grid
        Button[][] buttons = new Button[9][9];

        // Create a text field for user to input a number
        TextField textField = new TextField();
        root.add(textField, 9, 0);

      Label errorLabel = new Label();
root.add(errorLabel, 0, 9, 9, 1);

for (int row = 0; row < 9; row++) {
    for (int col = 0; col < 9; col++) {
        Button button = new Button();
        button.setPrefSize(40, 40);
        final int currentRow = row;
        final int currentCol = col;
        button.setOnAction(event -> {
            String text = textField.getText();
            if (!text.isEmpty() && isValidInput(text)) {
                int value = Integer.parseInt(text);
                grid[currentRow][currentCol] = value;
                button.setText(text);
                textField.clear();
                errorLabel.setText("");
            } else {
                errorLabel.setText("Invalid input, please enter a number between 1 and 9");
            }
        });
        root.add(button, col, row);
        buttons[row][col] = button;
    }
}

        // Create the buttons for solving the puzzle using backtracking or sat solver
        RadioButton backtrackingButton = new RadioButton("Backtracking");
        RadioButton satButton = new RadioButton("SAT Solver");
        ToggleGroup toggleGroup = new ToggleGroup();
        backtrackingButton.setToggleGroup(toggleGroup);
        satButton.setToggleGroup(toggleGroup);
        backtrackingButton.setSelected(true);
        Button solveButton = new Button("Solve");
        solveButton.setOnAction(event -> {
            boolean isBacktrackingSelected = backtrackingButton.isSelected();
            if (isBacktrackingSelected) {
                BacktrackingSolver backtrackingSolver = new BacktrackingSolver();
                if (backtrackingSolver.solve(grid)) {
                    updateButtons(buttons, grid);
                } else {
                    System.out.println("No solution found");
                }
//            } else {
//                SATSolver satSolver = new SATSolver();
//                if (satSolver.solve(grid)) {
//                    updateButtons(buttons, grid);
//                } else {
//                    System.out.println("No solution found");
//                }
            }
        });

        // Create the HBox that will hold the solving buttons
        HBox hBox = new HBox(10, backtrackingButton, satButton, solveButton);
        root.add(hBox, 0, 10, 9, 1);

        // Create the scene and show the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku Solver");
        primaryStage.show();
    }

    private void updateButtons(Button[][] buttons, int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Button button = buttons[row][col];
                int value = grid[row][col];
                button.setText(Integer.toString(value));
            }
        }
    }

    // Method to validate Input
    private boolean isValidInput(String input) {
        try {
            int value = Integer.parseInt(input);
            return value >= 1 && value <= 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method to solve the Sudoku
    private void solve() {
        // Read the values from the buttons into the grid array
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String text = buttons[row][col].getText();
                if (text.isEmpty()) {
                    grid[row][col] = 0;
                } else {
                    grid[row][col] = Integer.parseInt(text);
                }
            }
        }

        // Determine the selected solve method
        boolean isBacktrackingSelected = ((RadioButton) hBox.getChildren().get(0)).isSelected();

        // Solve the puzzle using the selected method
        boolean isSolved;
        if (isBacktrackingSelected) {
            BacktrackingSolver solver = new BacktrackingSolver();
            isSolved = solver.solve(grid);
//        } else {
//            SATsolver solver = new SATsolver();
//            isSolved = solver.solve(grid);
//        }

            // Update the buttons with the solution
            if (isSolved) {
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        buttons[row][col].setText(Integer.toString(grid[row][col]));
                    }
                }
            } else {
                // Show an error message
                // ...
            }
        }

    }
}
