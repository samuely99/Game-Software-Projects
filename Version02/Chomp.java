/**
* @author Samuel Yorke and Maddison Tetrault
* Game code for Chomp
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

public class Chomp extends Application implements Serializable{
  private  String stageTitle = "Food Fight!";
  private int turn = 0;
  private boolean endGame = false;
  private Stage lightedStage;
  private ArrayList<ArrayList<String>> boxesOfTheBoard;

/**
* This method will create the spaces for buttons in the array of the board and popuklate it with toads, frogs, and blank strings
* @param primaryStage the Stage for the game
*/
  public void start(Stage primaryStage){
    lightedStage = primaryStage;

    this.boxesOfTheBoard = new ArrayList<ArrayList<String>>(10);

    for(int i = 0; i < 8; i++){
      ArrayList<String> row = new ArrayList<String>();
      for(int j = 0; j < 8; j++){
        if(i == 7 && j == 0){
          row.add(0,"X");
          //System.out.println(row.get(j));
        }
        else{
          row.add(j,"C");
          //System.out.println(row.get(j));
        }
      }
      this.boxesOfTheBoard.add(i,row);
    }
    primaryStage.setTitle(this.stageTitle);
    stageSet(this.lightedStage);
  }

/**
* This method will change the player who is allowed to act
* @param stage the current instance of the stage
*/
  public void turnChange(Stage stage) {
    this.turn = 1 - this.turn;
    this.stageSet(stage);
  }
/**
* This method will add a title for the stage, a message, as well as buttons with images
* @param stage the stage for the buttons and text to be added to
*/
  public void stageSet(Stage stage){
    String[] teams = {"Player 1", "Player 2"};
    String message = "Don't Eat the Cookie of Death!";
    lightedStage.setTitle("Cookie Combat! " + teams[turn] + "'s Turn");
    this.lightedStage = stage;
    Button save = new Button("Save");
    Button load = new Button("Load");

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(8);

    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        String boxValue = boxesOfTheBoard.get(i).get(j);
        Button pane = new Button();

        pane.setMinSize(50,50);

//addding text and images to the buttons
        if (boxValue.equals("C")){
          Image cookieImg = new Image("cookie.png");
          ImageView view = new ImageView(cookieImg);
          pane.setGraphic(view);
          cookieActivation(pane, j, i);
        }

        else if(boxValue.equals("X")){
          Image cookieImg = new Image("badcookie.png");
          ImageView view = new ImageView(cookieImg);
          pane.setGraphic(view);
          badCookieActivation(pane, j, i);
        }

        else{
          pane.setStyle("-fx-base: #FFFFFF;");
        }
        grid.add(pane, j, i);

      }
    }

    save.setOnAction( (event) -> {
      saveGame();
    });

    load.setOnAction( (event) -> {
      loadGame();
    });

    if(endGame == true){
      if(turn == 0){
        message = "Player 1 is the Winner!";
      }

      else {
        message = "Player 2 is the Winner";
      }
    }

    else{
      message = "Don't Eat the Cookie of Death!";
    }

    grid.add(save, 0, 9, 5, 10);
    grid.add(load, 1, 9, 5, 10);
    grid.add(new Label(message), 0, 8, 4, 4);
    stage.setScene(new Scene(grid));
    stage.show();
    stage.sizeToScene();

  }

/**
* A method to initalize the cookie buttons
* @param cookieButton the button that has a "C" value
* @param xIndex the x index of the cookie in the array of button values
* @param xIndex the y index of the cookie in the array of button values
*/
  public void cookieActivation(Button cookieButton, int xIndex, int yIndex){
    cookieButton.setOnAction( (event) -> {
      for (int i = yIndex; i >= 0; i--){
        for(int j = xIndex; j < 8; j++){
          this.boxesOfTheBoard.get(i).set(j, "0");
        }
      }
      turnChange(lightedStage);
    });
  }

/**
* A method to initalize the cookie buttons
* @param badCookieButton the button that has a "X" value
* @param xIndex the x index of the bad cookie in the array of button values
* @param xIndex the y index of the bad cookie in the array of button values
*/
  public void badCookieActivation(Button badCookieButton, int xIndex, int yIndex){
      badCookieButton.setOnAction( (event) -> {
        for (int i = yIndex; i >= 0; i--){
          for(int j = xIndex; j < 8; j++){
            this.boxesOfTheBoard.get(i).set(j, " ");
          }
        }
        endGame = true;
        turnChange(lightedStage);
      });
  }

/**
* A method that will save a game by writing the current instance of the board grid to a text file
*/
  public void saveGame(){
    try{
      BufferedWriter saveWrite = new BufferedWriter(new FileWriter("chSave.txt"));
      BufferedWriter playerWrite = new BufferedWriter(new FileWriter("chPlayerSave.txt"));
      for(int i = 0; i < 8; i++){
        for(int j = 0; j < 8; j++){
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
/**
* A method that will load a game by reading the current instance of the board grid from a file
*/
  public void loadGame() {
    try{
      BufferedReader loadRead = new BufferedReader(new FileReader("chSave.txt"));
      BufferedReader playerRead = new BufferedReader(new FileReader("chPlayerSave.txt"));
      String gameLine = "";
      String playerLine = playerRead.readLine();
      int i = 0;
      while((gameLine = loadRead.readLine()) != null){
        for(int j = 0; j < 8; j++){
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

}
