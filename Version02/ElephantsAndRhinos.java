
/**
* @author Samuel Yorke and Maddison Tetrault
* Game code for elephants and Rhinos
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

public class ElephantsAndRhinos extends Application implements Serializable{

  private String stageTitle = "The age old battle of Elephants VS Rhinos!";
  private int turn = 0;
  private Stage lightedStage;
  private String[] boxesOfTheBoard;
//  private ElephantsAndRhinosState state;
  //Context context = new Context();

/**
 * This method will create the spaces for buttons in the array of the board and popuklate it with elephants, rhinos, and blank strings
 * @param primaryStage the Stage for the game
 */
  public void start(Stage primaryStage) {

    lightedStage = primaryStage;

    this.boxesOfTheBoard = new String[10];

    for (int m = 0; m < 10; m++) {
      boxesOfTheBoard[m] = " ";
    }

//placing the elephants and rhinos
    this.boxesOfTheBoard[0] = "E";
    boxesOfTheBoard[3] = "E";
    this.boxesOfTheBoard[4] = "E";
    boxesOfTheBoard[2] = "R";
    this.boxesOfTheBoard[7] = "R";
    boxesOfTheBoard[9] = "R";

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
 * This method will add a title for the stage, a message, as well as buttons with images and text
 * @param stage the stage for the buttons and text to be added to
 */
  public void stageSet(Stage stage) {
    String[] teams = {"Elephant", "Rhino"};
    String message = "";
    lightedStage.setTitle("Mammal Matchup!  " + teams[turn] + "'s Turn.");
    this.lightedStage = stage;
    Button save = new Button("Save");
    Button load = new Button("Load");

    HBox h1 = new HBox(10);

//addding text and images to the buttons
    for (int j = 0; j < 10; j++) {
      String boxValue = boxesOfTheBoard[j];
      Button pane = new Button();
      pane.setText(boxValue);
      h1.getChildren().add(pane);

      if (boxValue.equals("E")) {
          Image eleImg = new Image ("elephant.png");
          ImageView view = new ImageView(eleImg);
          pane.setGraphic(view);
          elephantActivation(pane, j);
      }

      else if (boxValue.equals(" ")) {
        pane.setMinSize(75,48);
        blankActivation(pane, j);
      }

      else if (boxValue.equals("R")) {
        Image rhinoImg = new Image ("rhino.png");
        ImageView view = new ImageView(rhinoImg);
        pane.setGraphic(view);
        rhinoActivation(pane, j);
      }

    }

    save.setOnAction( (event) -> {
      saveGame();
    });

    load.setOnAction( (event) -> {
      loadGame();
    });

//checking if any moves are available, if not, display the win message depending on whose turn it is
    if (availableRhinoTurns() == 0 && turn == 1){
      message = "Elephants Win";
      //this.state = new ElephantWinState();
    }
    else if (availableElephantTurns() == 0 && turn == 0){
      message = "Rhinos Win";
      //this.state = new RhinoWinState();
    }
    else {
      message = "If you can't move, then you lose the game.";
     //this.state = new TFGameState();
    }
//    message = this.state.getMessage();

//setting the stage
    VBox v = new VBox(10);
    v.getChildren().add(h1);
    v.getChildren().add(new Label(message));
    v.getChildren().add(save);
    v.getChildren().add(load);
    ScrollPane gameWindow = new ScrollPane(v);
    stage.setScene(new Scene(gameWindow));
    stage.show();
    stage.sizeToScene();

  }

/**
 * A method to initalize the elephant buttons
 * @param elephantButton the button that has a "E" value
 * @param buttonIndex the index of the elephant in the array of button values
 */
  public void elephantActivation(Button elephantButton, int buttonIndex) {
    elephantButton.setOnAction( (event) -> {
      if (this.turn == 1) {
        return;
      }

      if (buttonIndex <= 8 && this.boxesOfTheBoard[buttonIndex + 1].equals(" ")) {
        this.boxesOfTheBoard[buttonIndex] = " ";
        this.boxesOfTheBoard[buttonIndex + 1] = "E";
        turnChange(lightedStage);
      }

    });
  }

/**
 * A method to initalize the empty buttons
 * @param blankButton the button that has a "   " value
 * @param buttonIndex the index of the "    " in the array of buttons values
 */
  public void blankActivation(Button blankButton, int buttonIndex) {
    blankButton.setOnAction((event) -> {
        stageSet(lightedStage);
    });
  }
/**
 * A method to initalize the rhino buttons
 * @param rhinoButton the button that has a "R" value
 * @param buttonIndex the index of the rhino in the array of buttons values
 */
  public void rhinoActivation(Button rhinoButton, int buttonIndex) {
    rhinoButton.setOnAction((event) -> {

      if (this.turn < 1) {
        return;
      }

      if (buttonIndex > 0 && this.boxesOfTheBoard[buttonIndex-1].equals(" ")) {
          this.boxesOfTheBoard[buttonIndex] = " ";
          this.boxesOfTheBoard[buttonIndex-1] = "R";
          turnChange(lightedStage);
      }
    });
  }

/**
 * A method to give the numeber of available moves the Rhino player has
 * @return the number of moves the Rhino player can make
 */
  public int availableRhinoTurns(){
    int fTurns = 0;
    for (int i = 0; i < 10; i++){
      if(this.boxesOfTheBoard[i].equals("R")){
        if (i == 1 && this.boxesOfTheBoard[0].equals(" ")){
          fTurns += 1;
        }
        else if(i > 1 && (this.boxesOfTheBoard[i-1].equals(" "))){
          fTurns += 1;
        }
      }
    }
    return fTurns;
  }
/**
 * A method to give the numeber of available moves the Elephant player has
 * @return the number of moves the Elephant player can make
 */
  public int availableElephantTurns(){
    int tTurns = 0;
    for (int j = 0; j < 10; j++){
      if(this.boxesOfTheBoard[j].equals("E")){
        if (j == 8 && this.boxesOfTheBoard[9].equals(" ")){
          tTurns += 1;
        }
        else if(j < 8 && (this.boxesOfTheBoard[j+1].equals(" "))){
          tTurns += 1;
        }
      }
    }
    return tTurns;
  }
  /**
  * A method that will save a game by writing the current instance of the board array to a text file
  */
  public void saveGame(){
    try{
      FileWriter file = new FileWriter("tfSave.txt");
      FileWriter file2 = new FileWriter("tfPlayerSave.txt");
      BufferedWriter saveWrite = new BufferedWriter(file);
      BufferedWriter playerWrite = new BufferedWriter(file2);
      for(int i = 0; i < 10; i++){
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
      BufferedReader loadRead = new BufferedReader(new FileReader("tfSave.txt"));
      BufferedReader playerRead = new BufferedReader(new FileReader("tfPlayerSave.txt"));
      String gameline = loadRead.readLine();
      String playerLine = playerRead.readLine();
      String test = "";
      loadRead.close();
      for(int i = 0; i < 10; i++){
        this.boxesOfTheBoard[i] = String.valueOf(gameline.charAt(i));
        //System.out.println(this.boxesOfTheBoard[i]);
      }
      turn = (playerLine.charAt(0) - 48);
      //System.out.println(blueDominoesLeft());
      stageSet(this.lightedStage);
    }
    catch(IOException e){
      System.out.println("Oh no");
    }
  }



} //end of ElephantsAndRhinos.java
