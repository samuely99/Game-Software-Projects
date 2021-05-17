/**
* @author Samuel Yorke and Maddison Tetrault
* Game code for toppling dominoes
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

public class TopplingDominoes extends Application implements Serializable{
  private String stageTitle = "Domino Duel";
  private int turn = 0;
  private boolean gameOver = false;
  private Stage lightedStage;
  private String[] boxesOfTheBoard;
  private int direction = 2;

/**
* This method will create the spaces for buttons in the array of the board and popuklate it with toads, frogs, and blank strings
* @param primaryStage the Stage for the game
*/
  public void start(Stage primaryStage) {
    Random rand  = new Random();
    int color = rand.nextInt(2);
    String player1 = "";
    String player2 = "";

    if(color == 1){
      player1 = "B";
      player2 = "R";
    }
    else{
      player1 = "R";
      player2 = "B";
    }

    lightedStage = primaryStage;

    this.boxesOfTheBoard = new String[20];

    for (int i = 0; i < 20; i++){
      boxesOfTheBoard[i] = " ";
    }

    this.boxesOfTheBoard[1] = player1;
    this.boxesOfTheBoard[4] = player1;
    this.boxesOfTheBoard[9] = player1;
    this.boxesOfTheBoard[10] = player1;
    this.boxesOfTheBoard[13] = player1;
    this.boxesOfTheBoard[15] = player1;
    this.boxesOfTheBoard[18] = player1;

    this.boxesOfTheBoard[3] = player2;
    this.boxesOfTheBoard[6] = player2;
    this.boxesOfTheBoard[8] = player2;
    this.boxesOfTheBoard[12] = player2;
    this.boxesOfTheBoard[16] = player2;
    this.boxesOfTheBoard[17] = player2;
    this.boxesOfTheBoard[19] = player2;

    primaryStage.setTitle(this.stageTitle);
    stageSet(this.lightedStage);


  }
/**
* This method will change the player who is allowed to act
* @param stage the current instance of the game
*/
  public void turnChange(Stage stage) {
    direction = 2;
    this.turn = 1 - this.turn;
    this.stageSet(stage);
  }
/**
* This method will add a title for the stage, a message, as well as domino buttons with color, directional buttons,
* @param stage the stage for the buttons and text to be added to
*/
  public void stageSet(Stage stage){
    System.out.println(blueDominoesLeft());
    System.out.println(redDominoesLeft());
    String[] teams = {"Blue", "Red"};
    String message = "";
    lightedStage.setTitle("Domino Duel! " + teams[turn] + "'s Turn.");
    this.lightedStage = stage;
    Button leftB = new Button("left");
    Button rightB = new Button("right");
    Button save = new Button("Save");
    Button load = new Button("Load");

    HBox h1 = new HBox(10);
    VBox v = new VBox(10);

    for (int j = 0; j < 20; j++) {
      String boxValue = boxesOfTheBoard[j];
      System.out.print(boxValue);
      Button pane = new Button();
      h1.getChildren().add(pane);

      pane.setMinSize(20,50);
      if (boxValue.equals("B")) {
          pane.setStyle("-fx-base: #1120ee;");
          blueActivation(pane, j);
      }

      else if (boxValue.equals("R")) {
        pane.setStyle("-fx-base: #ee1111;");
        redActivation(pane, j);
      }

    }
    System.out.println();
    v.getChildren().add(h1);

    leftB.setOnAction( (event) ->  {
      direction = 0;
      stageSet(stage);
    });

    rightB.setOnAction( (event) -> {
      direction = 1;
      stageSet(stage);
    });

    save.setOnAction( (event) -> {
      saveGame();
    });

    load.setOnAction( (event) -> {
      loadGame();
    });

//setting the stage

    if (turn == 0){
      if(blueDominoesLeft() == 0){
        message = "Red is the Winner!";
      }
      else if(blueDominoesLeft() > 0){
        if(direction == 2){
          message = "Blue! Choose a Diection";
        }
        else{
          message = "Blue! Choose a Domino";
        }
      }
    }
    else if(turn == 1){
      if(redDominoesLeft() == 0){
        message = "Blue is the Winner!";
      }
      else if(redDominoesLeft() > 0){
        if(direction == 2){
          message = "Red! Choose a Diection";
        }
        else{
          message = "Red! Choose a Domino";
        }
      }
    }
    v.getChildren().add(new Label(message));
    v.getChildren().add(leftB);
    v.getChildren().add(rightB);
    v.getChildren().add(save);
    v.getChildren().add(load);
    ScrollPane gameWindow = new ScrollPane(v);
    stage.setScene(new Scene(gameWindow));
    stage.show();
    stage.sizeToScene();

  }
/**
* A method to initalize the blue domino buttons
* @param blueButton the button that has a "B" value
* @param buttonIndex the index of the blue domino in the array of button values
*/
  public void blueActivation(Button blueButton, int buttonIndex){;
    blueButton.setOnAction( (event) -> {
      if (this.turn == 1 || gameOver == true){
        return;
      }

      if(direction == 0){
        int i = buttonIndex;
        while(i >= 0){
          if(this.boxesOfTheBoard[i].equals(" ")){
            break;
          }
          this.boxesOfTheBoard[i] = " ";
          i--;
        }
        turnChange(lightedStage);
      }

      if(direction == 1){
        int i = buttonIndex;
        while(i < 20){
          if(this.boxesOfTheBoard[i].equals(" ")){
            break;
          }
          this.boxesOfTheBoard[i] = " ";
          i++;
        }
        turnChange(lightedStage);
      }

    });
  }
/**
* A method to initalize the red domino buttons
* @param redButton the button that has a "B" value
* @param buttonIndex the index of the red domino in the array of button values
*/
  public void redActivation(Button redButton, int buttonIndex){
    redButton.setOnAction( (event) -> {
      if (this.turn == 0 || gameOver == true){
        return;
      }

      if(direction == 0){
        int i = buttonIndex;
        while(i >= 0){
          if(this.boxesOfTheBoard[i].equals(" ")){
            break;
          }
          this.boxesOfTheBoard[i] = " ";
          i--;
        }
        turnChange(lightedStage);
      }

      if(direction == 1){
        int i = buttonIndex;
        while(i < 20){
          if(this.boxesOfTheBoard[i].equals(" ")){
            break;
          }
          this.boxesOfTheBoard[i] = " ";
          i++;
        }
        turnChange(lightedStage);
      }

    });
  }
  /**
   * A method to give the numeber of dominoes the blue player has left
   * @return the number of blue dominos left
   */
  public int blueDominoesLeft(){
    int reamaining = 0;
    for(int i = 0; i < 20; i++){
      if(this.boxesOfTheBoard[i].equals("B")){
        reamaining += 1;
      }
    }
    return reamaining;
  }
  /**
   * A method to give the numeber of dominoes the red player has left
   * @return the number of red dominos left
   */
  public int redDominoesLeft(){
    int reamaining = 0;
    for(int i = 0; i < 20; i++){
      if(this.boxesOfTheBoard[i].equals("R")){
        reamaining += 1;
      }
    }
    return reamaining;
  }
/**
* A method that will save a game by writing the current instance of the board array to a text file
*/
  public void saveGame(){
    try{
      FileWriter file = new FileWriter("tdSave.txt");
      FileWriter file2 = new FileWriter("tdPlayerSave.txt");
      BufferedWriter saveWrite = new BufferedWriter(file);
      BufferedWriter playerWrite = new BufferedWriter(file2);
      for(int i = 0; i < 20; i++){
        saveWrite.write(this.boxesOfTheBoard[i]);
      }
      playerWrite.write(Integer.toString(this.turn));
      saveWrite.close();
      playerWrite.close();

  }
    catch(IOException e){
      System.out.println("Oh no");
    }
  }

  /**
  * A method that will load a game by reading the current instance of the board array from a text file
  */
  public void loadGame(){
    try{
      BufferedReader loadRead = new BufferedReader(new FileReader("tdSave.txt"));
      BufferedReader playerRead = new BufferedReader(new FileReader("tdPlayerSave.txt"));
      String gameline = loadRead.readLine();
      String playerLine = playerRead.readLine();
      String test = "";
      loadRead.close();
      for(int i = 0; i < 20; i++){
        this.boxesOfTheBoard[i] = String.valueOf(gameline.charAt(i));
      }
      turn = (playerLine.charAt(0) - 48);
      stageSet(this.lightedStage);
    }
    catch(IOException e){
      System.out.println("Oh no");
    }
  }


}
