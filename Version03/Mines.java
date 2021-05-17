/**
* @author Samuel Yorke and Maddison Tetrault
* Game code for Mines
* with save and load game feature
*/
import javafx.application.Application;
import javafx.geometry.*;
import javafx.embed.swing.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import java.util.*;
import java.io.*;

public class Mines extends Application implements Serializable{
  private String stageTitle = "Walk Fight!";
  private int turn = 0;
  private int numberTurn = 0;
  private int size = 6;
  static Stage lightedStage = new Stage();
  public ArrayList<ArrayList<String>> boxesOfTheBoard;
  public boolean gameOver = false;


  public void start(Stage primaryStage){
    lightedStage = primaryStage;
    Random rand = new Random();

    this.boxesOfTheBoard = new ArrayList<ArrayList<String>>(size);

    for(int i = 0; i < size; i++){
      ArrayList<String> row = new ArrayList<String>();
      for(int j = 0; j < size; j++){
          row.add(j,"E");
      }
      this.boxesOfTheBoard.add(i,row);
    }
    for(int i = 0; i < 3; i++){
      int xValue = rand.nextInt(size);
      int yValue = rand.nextInt(size);
      this.boxesOfTheBoard.get(yValue).set(xValue, "X");
    }
    primaryStage.setTitle(this.stageTitle);
    stageSet(this.lightedStage);
  }

  public void turnChange(Stage stage) {
    this.turn = 1 - this.turn;
    numberTurn = 0;
    this.stageSet(stage);
  }

/**
* This method will add a title for the stage, a message, as well as buttons with images
* @param stage the stage for the buttons and text to be added to
*/
  public void stageSet(Stage stage){
    String[] teams = {"Blue", "Red"};
    String message = "Dont blow up!, Select Spots that Are not the Mine!";
    lightedStage.setTitle("Bomb Battle " + teams[turn] + "'s Turn");
    this.lightedStage = stage;
    Button save = new Button("Save");
    Button load = new Button("Load");
    Button menu = new Button("Game Menu");
    Button endTurn = new Button("End Turn");

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(8);

    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){
        String boxValue = boxesOfTheBoard.get(i).get(j);
        Button pane = new Button();

        pane.setMinSize(50,50);

//addding text and images to the buttons
        if (boxValue.equals("E")){
            emptyActivation(pane, j, i);
            pane.setStyle("-fx-base: #FFFFFF;");
        }

        else if(boxValue.equals("X")){
          pane.setStyle("-fx-base: #FFFF00;");
          mineActivation(pane, j, i);
        }

        else if(boxValue.equals("B")){
          pane.setStyle("-fx-base: #0000FF;");
//          badCookieActivation(pane, j, i);
        }

        else if(boxValue.equals("R")){
          pane.setStyle("-fx-base: #FF0000;");
//          badCookieActivation(pane, j, i);
        }

        else if(boxValue.equals("S")){
          pane.setStyle("-fx-base: #808000;");
//          badCookieActivation(pane, j, i);
        }

        else if(boxValue.equals("G")){
          pane.setStyle("-fx-base: #000000;");
//          badCookieActivation(pane, j, i);
        }

        grid.add(pane, j, i);

      }
    }

    if(gameOver == true){
      if (blueSpaces() > redSpaces()){
        message = "Blue is the Winner!";
      }
      else{
        message = "Red is the Winner!";
      }
    }
    save.setOnAction( (event) -> {
      saveGame();
    });

    load.setOnAction( (event) -> {
      loadGame();
    });

    endTurn.setOnAction( (event) -> {
      if(numberTurn == 0){
        return;
      }
      else{
        turnChange(lightedStage);
      }
    });

    menu.setOnAction(new EventHandler<ActionEvent>() {
      Driver back = new Driver();
      @Override
      public void handle(ActionEvent e){
        back.start(Driver.menu);
        stage.close();
      }
    });

    if(numberTurn == 1){
      message = "Choose Another Space or End Your Turn";
    }


    grid.add(endTurn, 0, 12, 5, 10);
    grid.add(save, 1, 12, 5, 10);
    grid.add(load, 2, 12, 5, 10);
    grid.add(menu, 3, 12, 5, 10);
    grid.add(new Label(message), 0, 10, 6, 6);
    stage.setScene(new Scene(grid));
    stage.show();
    stage.sizeToScene();

  }

  private void emptyActivation(Button emptyButton, int xIndex, int yIndex){
    emptyButton.setOnAction( (event) -> {
      if(this.turn == 0){
        this.boxesOfTheBoard.get(yIndex).set(xIndex, "B");
        numberTurn += 1;
        if(numberTurn == 2){
          turnChange(lightedStage);
        }
        else{
          this.stageSet(lightedStage);
        }
      }
      else if(this.turn == 1){
        this.boxesOfTheBoard.get(yIndex).set(xIndex, "R");
        numberTurn += 1;
        if(numberTurn == 2){
          turnChange(lightedStage);
        }
        else{
          this.stageSet(lightedStage);
        }
      }
    });
  }

  private void mineActivation(Button mineButton, int xIndex, int yIndex){
    mineButton.setOnAction( (event) -> {
      for (int i = 0; i < size; i++){
        for (int j = 0; j < size; j++){
          if(this.boxesOfTheBoard.get(i).get(j).equals("X")){
            this.boxesOfTheBoard.get(i).set(j,"G");
          }
          else if(this.boxesOfTheBoard.get(i).get(j).equals("B")){
            this.boxesOfTheBoard.get(i).set(j,"B");
          }
          else if(this.boxesOfTheBoard.get(i).get(j).equals("R")){
            this.boxesOfTheBoard.get(i).set(j,"R");
          }
          else{
            this.boxesOfTheBoard.get(i).set(j,"S");
          }
        }
      }
      gameOver = true;
      turnChange(lightedStage);
    });
  }

  public void saveGame(){
    try{
      BufferedWriter saveWrite = new BufferedWriter(new FileWriter("mSave.txt"));
      BufferedWriter playerWrite = new BufferedWriter(new FileWriter("mPlayerSave.txt"));
      for(int i = 0; i < size; i++){
        for(int j = 0; j < size; j++){
          saveWrite.write(this.boxesOfTheBoard.get(i).get(j));
        }
        saveWrite.write("\n");
      }
      playerWrite.write(Integer.toString(this.turn));
      saveWrite.close();
      playerWrite.close();
    }

    catch(IOException e){
      System.out.println("Oh no");
    }
  }

  public void loadGame() {
    try{
      BufferedReader loadRead = new BufferedReader(new FileReader("mSave.txt"));
      BufferedReader playerRead = new BufferedReader(new FileReader("mPlayerSave.txt"));
      String gameLine = "";
      String playerLine = playerRead.readLine();
      int i = 0;
      while((gameLine = loadRead.readLine()) != null){
        for(int j = 0; j < size; j++){
          this.boxesOfTheBoard.get(i).set(j, String.valueOf(gameLine.charAt(j)));
        }
        i += 1;
      }
      turn = (playerLine.charAt(0) - 48);
      stageSet(this.lightedStage);
  }
   catch(IOException e){
     System.out.println("Oh No");
    }
  }

  private int redSpaces(){
    int red = 0;
    for(int i = 0; i < size; i++){
      for (int j = 0; j < size; j++){
        if(this.boxesOfTheBoard.get(i).get(j).equals("R")){
          red += 1;
        }
      }
    }
    return red;
  }

  private int blueSpaces(){
    int blue = 0;
    for(int i = 0; i < size; i++){
      for (int j = 0; j < size; j++){
        if(this.boxesOfTheBoard.get(i).get(j).equals("B")){
          blue += 1;
        }
      }
    }
    return blue;
  }
}
