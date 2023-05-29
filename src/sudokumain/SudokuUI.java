/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokumain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author Dwayne
 */
class SudokuUI {
    private Scene scene;
    //int size;
    int SIZE;

      // 2D array to represent the Sudoku grid
    private int[][] grid;
    private boolean isBacktrackingSelected = false;

    int val = 0;

    // Button array to hold the buttons that represent the cells of the grid
    private Button[][] buttons;
    
    private Stage primaryStage;

  public SudokuUI(int size, Stage primaryStage) {
      this.SIZE = size;
      grid = new int[SIZE][SIZE];
      buttons = new Button[SIZE][SIZE];
      this.primaryStage = primaryStage;
      createScene();
    
  }

  public Scene getScene() {
    return scene;
  }
  private void createScene() {
      System.out.println("SIZE"+SIZE);
      
      
       GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        Label title = new Label("Sudoku Solver");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#2F4F4F"));
        //root.add(title, 0, 0, 9, 1);
        
        
        //creating a label to display the benchmarking results
        Label resultsLabel = new Label("");
        resultsLabel.setMinWidth(100);
        resultsLabel.setMinHeight(50);
        resultsLabel.setFont(Font.font("Arial", 10));
        //root.add(resultsLabel, 0, 10, 9, 1);
        

// Creating the buttons for the sudoku grid
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                //Button button = new Button();
                Button button = new Button("");

                button.setPrefSize(40, 40);

                // Set the background color of the button
                if ((row / (int) Math.sqrt(SIZE) + col / (int) Math.sqrt(SIZE)) % 2 == 0) {
                    button.setStyle("-fx-background-color: GAINSBORO;");
                } else {
                    button.setStyle("-fx-background-color: DIMGRAY;");
                }

                final int currentRow = row;
                final int currentCol = col;
                button.setOnAction(event -> {
                    val = grid[currentRow][currentCol];
                    val = (val + 1) % 10;
                    if (val == 0) {
                        val = 1;
                    }
                    // Calling the checkInput method to check if the input is valid
                    while (!checkInput(currentRow, currentCol, val, grid)) {
                        //setting the next number to 1 higher
                        val = (val + 1) % 10;
                        if (val == 0) {
                            val = 1;
                        }
                    }
                    grid[currentRow][currentCol] = val;
                    button.setText("" + val);
                });
                root.add(button, col, row + 1);
                //setting the button size and font style
                buttons[row][col] = button;
                buttons[row][col].setPrefSize(40, 40);
                buttons[row][col].setFont(Font.font("Arial", FontWeight.BOLD, 15));
            }
        }

        //Create buttons for solving method
        Button backtrackingButton = new Button("Backtracking");
        backtrackingButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: #2F4F4F; -fx-font-size: 14px; -fx-border-color: #2F4F4F; -fx-border-width: 1px;");
        backtrackingButton.setOnAction(event -> {
            isBacktrackingSelected = true;
           
        });
        Button contproButton = new Button("Constraint Propagation");
        contproButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: #2F4F4F; -fx-font-size: 14px; -fx-border-color: #2F4F4F; -fx-border-width: 1px;");
        contproButton.setOnAction(event -> {
            isBacktrackingSelected = false;
            
            
        });
        Button solveButton = new Button("Solve");
        solveButton.setStyle("-fx-background-color: #90EE90; -fx-text-fill: #2F4F4F; -fx-font-size: 14px; -fx-border-color: #2F4F4F; -fx-border-width: 1px;");
        solveButton.setOnAction(event -> {

            if (isBacktrackingSelected) {
                 
                // Call the backtracking solver method
                BacktrackingSolver backtrackingSolver = new BacktrackingSolver(grid);
                long startTime = System.nanoTime();
                if (backtrackingSolver.solve()) {
                    updateButtons(buttons, grid);
                } else {
                    System.out.println("No solution found");
                }
                long endTime = System.nanoTime();
                long timeTaken = (endTime - startTime);
                resultsLabel.setText("Time taken to solve sudoku using backracking: " + timeTaken + " nanoseconds");
                resultsLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #000000;");

            } // Call the SAT solver method
            else {
                ConstraintPropagation constraintPropagation = new ConstraintPropagation(grid);
                long startTime = System.nanoTime();
                if (constraintPropagation.solve()) {
                    updateButtons(buttons, grid);
                } else {
                    System.out.println("No solution found");
                }
                long endTime = System.nanoTime();
                long timeTaken = (endTime - startTime);
                resultsLabel.setText("Time taken to solve sudoku using constarint propagation: " + timeTaken + " nanoseconds");
                resultsLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #000000;");
            }
        });

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Clear the error message and reset the sudoku
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        buttons[i][j].setText("");
                        grid[i][j] = 0;
                    }
                }
            }
        });
        
        
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            save();
        });
        Button loadButton = new Button("Load");
        loadButton.setOnAction(event -> {
            load();
        });

        // Create the HBox that will hold the solving buttons
        HBox hBox = new HBox(10, backtrackingButton, contproButton, solveButton, resetButton,saveButton,loadButton);

        
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(title,root,resultsLabel,hBox);

        // Create the scene and show the stage
        scene = new Scene(box);
  }
     
 // Update the buttons with the solution
    private void updateButtons(Button[][] buttons, int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = buttons[row][col];
                int value = grid[row][col];
                button.setText(Integer.toString(value));
            }
        }
    }

   private boolean checkInput(int row, int col, int num,int[][] grid) {
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
   
    /**
     * Saves the current Sudoku grid to a file chosen by the user.
     */
   private void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Sudoku Grid");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Grid Files", "*.sgrid"));
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(grid);
                showAlert(Alert.AlertType.INFORMATION, "Sudoku Grid Saved", "The Sudoku grid has been saved successfully.");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error Saving Grid", "An error occurred while saving the Sudoku grid.");
            }
        }
    }

    /**
     * Loads a saved Sudoku grid from a file chosen by the user and updates the UI.
     */
    private void load() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Sudoku Grid");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Grid Files", "*.sgrid"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                grid = (int[][]) ois.readObject();
                updateButtons(buttons, grid);
                showAlert(Alert.AlertType.INFORMATION, "Sudoku Grid Loaded", "The Sudoku grid has been loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Error Loading Grid", "An error occurred while loading the Sudoku grid.");
            }
        }
    }

    /**
     * Displays an alert with the specified type, title, and content.
     *
     * @param alertType the type of the alert
     * @param title     the title of the alert
     * @param content   the content of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
