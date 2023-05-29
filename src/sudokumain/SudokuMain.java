/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokumain;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Dwayne
 */
public class SudokuMain extends Application {

    Scene main, option, TableV;
    int size;
 
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Image image = new Image(getClass().getResourceAsStream("Sudoku.jpg"));
        ImageView view = new ImageView(image);
        view.setFitHeight(720);
        view.setFitWidth(600);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setColor(Color.color(0.8f, 0.1f, 0.9f));
        //title
        Text gameName = new Text("Sudoku Solver");
        gameName.setEffect(ds);
        gameName.setFill(Color.BLACK);
        gameName.setCache(true);
        gameName.setFont(Font.font(null, FontWeight.BOLD, 60));

        //play button on menu screen
        Button playbtn = new Button("Solve Sudoku");
        playbtn.setOnAction(e -> {

            primaryStage.setScene(option);

        });
        playbtn.setStyle("-fx-base: #E94B3CFF;");

        //play button on menu screen
        Button exitbtn = new Button("Exit");
        exitbtn.setOnAction(e -> {

            System.exit(0);

        });
        exitbtn.setStyle("-fx-base: #E94B3CFF;");

        TableV();
        Button benbtn = new Button();
        benbtn.setText("Run Benchmark");
        benbtn.setOnAction(event -> {
            primaryStage.setScene(TableV);
            primaryStage.setTitle("Benchmarking");
        });
        benbtn.setStyle("-fx-base: #E94B3CFF;");

        //display the buttons vertically
        StackPane mainStack = new StackPane();
        VBox mainScreen = new VBox(5);
        mainScreen.setSpacing(30);
        mainScreen.setAlignment(Pos.CENTER);
        mainScreen.getChildren().addAll(gameName, playbtn, benbtn, exitbtn);
        mainStack.getChildren().addAll(view, mainScreen);
        main = new Scene(mainStack, 580, 700);

        Text optionTitle = new Text("Select a sudoku size");
        optionTitle.setEffect(ds);
        optionTitle.setFill(Color.BLACK);
        optionTitle.setCache(true);
        optionTitle.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 50));

        Button fourbtn = new Button("4x4");
        fourbtn.setOnAction(e -> {
            size = 4;
            // Create an instance of the SudokuUI class
            SudokuUI sudokuUI = new SudokuUI(size, primaryStage);
            primaryStage.setScene(sudokuUI.getScene());
            primaryStage.setTitle("The Solver");
        });
        fourbtn.setStyle("-fx-base: #E94B3CFF;");

        //play button on menu screen
        Button ninebtn = new Button("9x9");
        ninebtn.setOnAction(e -> {
            size = 9;
            // Create an instance of the SudokuUI class
            SudokuUI sudokuUI = new SudokuUI(size, primaryStage);
            primaryStage.setScene(sudokuUI.getScene());
            primaryStage.setTitle("The Solver");

        });
        ninebtn.setStyle("-fx-base: #E94B3CFF;");

        Button sixbtn = new Button("16x16");
        sixbtn.setOnAction(e -> {
            size = 16;
            // Create an instance of the SudokuUI class
            SudokuUI sudokuUI = new SudokuUI(size, primaryStage);
            primaryStage.setScene(sudokuUI.getScene());
            primaryStage.setTitle("The Solver");

        });
        sixbtn.setStyle("-fx-base: #E94B3CFF;");

        ImageView view2 = new ImageView(image);
        view2.setFitHeight(720);
        view2.setFitWidth(600);

        //display the buttons vertically
        StackPane mStack = new StackPane();
        VBox mScreen = new VBox(5);
        mScreen.setSpacing(30);
        mScreen.setAlignment(Pos.CENTER);
        mScreen.getChildren().addAll(optionTitle, fourbtn, ninebtn, sixbtn);
        mStack.getChildren().addAll(view2, mScreen);
        mStack.setStyle("-fx-background-color: #f0f0f0;");
        option = new Scene(mStack, 580, 700);

        primaryStage.setScene(main);
        primaryStage.show();
    }

    public void TableV() {
        // Add the TableView for benchmark results
        TableView<Result> table = new TableView<>();

        TableColumn<Result, Number> puzzleNumberColumn = new TableColumn<>("Puzzle Number");
        puzzleNumberColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPuzzleNumber()));

        TableColumn<Result, Number> constraintPropagationTimeColumn = new TableColumn<>("Constraint Propagation Time (ms)");
        constraintPropagationTimeColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getConstraintPropagationTime()));

        TableColumn<Result, Number> backtrackingTimeColumn = new TableColumn<>("Backtracking Time (ms)");
        backtrackingTimeColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getBacktrackingTime()));

        table.getColumns().addAll(puzzleNumberColumn, constraintPropagationTimeColumn, backtrackingTimeColumn);
        table.setPrefHeight(300);

        Button Tbenbtn = new Button();
        Tbenbtn.setText("Run Benchmark");
        Tbenbtn.setOnAction(event -> {
            SudokuBenchmark benchmark = new SudokuBenchmark();
            table.setItems(FXCollections.observableArrayList(benchmark.runBenchmark()));
        });

        
        StackPane TmainStack = new StackPane();
        VBox Tvbox = new VBox(10);
        Tvbox.setPadding(new Insets(10));
        Tvbox.getChildren().addAll( table,Tbenbtn);
        TmainStack.getChildren().addAll(Tvbox);
        TableV = new Scene(TmainStack, 580, 700);

    }

}
