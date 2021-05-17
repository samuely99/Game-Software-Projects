//This is a cleaned version of the terribly designed code, TadsAndFrogs.java, written by Kyle Burke for Software Project 0
// Authors: Samuel Yorke and Maddison Tetrault

/**
* @author Samuel Yorke and Maddison Tetrault
* includes endgame message feature
* with fixed resizing issues
* and added images to the buttons
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

public class ToadsAndFrogs extends Application {

  private String stageTitle = "Amphibian Battle!  Toad's Turn.";
  private int turn = 0;
  private Stage lightedStage;
  private String[] boxesOfTheBoard;

  public static void main(String[] args) {launch(args);}

//a method to initialize the board of the game
  public void start(Stage primaryStage) {

    lightedStage = primaryStage;

    this.boxesOfTheBoard = new String[10];

    for (int m = 0; m < 10; m++) {
      boxesOfTheBoard[m] = "    ";
    }

//placing the toads and frogs
    this.boxesOfTheBoard[0] = "Toad";
    boxesOfTheBoard[3] = "Toad";
    this.boxesOfTheBoard[4] = "Toad";
    boxesOfTheBoard[2] = "Frog";
    this.boxesOfTheBoard[7] = "Frog";
    boxesOfTheBoard[9] = "Frog";

    primaryStage.setTitle(this.stageTitle);
    stageSet(this.lightedStage);

  }

//will change which player can act
  public void turnChange(Stage stage) {
    this.turn = 1 - this.turn;
    this.stageSet(stage);
  }

//This method will create the stage and buttons for the game to be played
  public void stageSet(Stage stage) {
    String[] teams = {"Toad", "Frog"};
    String message = "";
    lightedStage.setTitle("Amphibian Battle!  " + teams[turn] + "'s Turn.");
    this.lightedStage = stage;

    HBox h1 = new HBox(10);

//addding text and images to the buttons
    for (int j = 0; j < 10; j++) {
      String boxValue = boxesOfTheBoard[j];
      Button pane = new Button();
      //pane.setText(boxValue);
      h1.getChildren().add(pane);

      if (boxValue.equals("Toad")) {
          Image toadImg = new Image ("toad.png");
          ImageView view = new ImageView(toadImg);
          pane.setGraphic(view);
          toadActivation(pane, j);
      }

      else if (boxValue.equals("    ")) {
        pane.setMinSize(75,48);
        blankActivation(pane, j);
      }

      else if (boxValue.equals("Frog")) {
        Image frogImg = new Image ("frog.png");
        ImageView view = new ImageView(frogImg);
        pane.setGraphic(view);
        frogActivation(pane, j);
      }

    }

//checking if any moves are available, if not, display the win message depending on whose turn it is
    if (availableFrogTurns() == 0 && turn == 1){
      message = "The Toads Are Victorious!...Nice Try You Frog Scum";
    }
    else if (availableToadTurns() == 0 && turn == 0){
      message = "The Frogian Empire Always Provails!";
    }
    else {
      message = "If you can't move, then you lose the game.";
    }

//setting the stage
    VBox v = new VBox(10);
    v.getChildren().add(h1);
    v.getChildren().add(new Label(message));

    ScrollPane gameWindow = new ScrollPane(v);
    stage.setScene(new Scene(gameWindow));
    stage.show();
    stage.sizeToScene();

  }

//A method to initialize the toad button
  public void toadActivation(Button toadButton, int buttonIndex) {
    toadButton.setOnAction( (event) -> {
      System.out.println("Croak!");
      if (this.turn == 1) {
        return;
      }

      if (buttonIndex <= 8 && this.boxesOfTheBoard[buttonIndex + 1] == "    ") {
        this.boxesOfTheBoard[buttonIndex] = "    ";
        this.boxesOfTheBoard[buttonIndex + 1] = "Toad";
        turnChange(lightedStage);
      }

      else if (buttonIndex <= 7 && this.boxesOfTheBoard[buttonIndex + 1] == "Frog" && this.boxesOfTheBoard[buttonIndex+ 2] == "    ") {
                this.boxesOfTheBoard[buttonIndex] = "    ";
                this.boxesOfTheBoard[buttonIndex + 2] = "Toad";
                turnChange(lightedStage);
      }
    });
  }

//A method to initialize the blank button
  public void blankActivation(Button blankButton, int buttonIndex) {
    blankButton.setOnAction((event) -> {
        System.out.println("Empty!");
        stageSet(lightedStage);
    });
  }

//A method to initialize the frog button
  public void frogActivation(Button frogButton, int buttonIndex) {
    frogButton.setOnAction((event) -> {
      System.out.println("Ribbit!");

      if (this.turn < 1) {
        return;
      }

      if (buttonIndex > 0 && this.boxesOfTheBoard[buttonIndex-1] == "    ") {
          this.boxesOfTheBoard[buttonIndex] = "    ";
          this.boxesOfTheBoard[buttonIndex-1] = "Frog";
          turnChange(lightedStage);
      }
      else if (buttonIndex > 1 && this.boxesOfTheBoard[buttonIndex-2] == "    " && this.boxesOfTheBoard[buttonIndex-1] == "Toad") {
          this.boxesOfTheBoard[buttonIndex] = "    ";
          this.boxesOfTheBoard[buttonIndex-2] = "Frog";
          turnChange(this.lightedStage);
      }
    });
  }

//this method will return the number of available turns for the frog to make
  public int availableFrogTurns(){
    int fTurns = 0;
    for (int i = 0; i < 10; i++){
      if(this.boxesOfTheBoard[i] == "Frog"){
        if (i == 1 && this.boxesOfTheBoard[0] == "    "){
          fTurns += 1;
        }
        else if(i > 1 && (this.boxesOfTheBoard[i-1] == "    " || (this.boxesOfTheBoard[i-2] == "    " && this.boxesOfTheBoard[i-1] == "Toad"))){
          fTurns += 1;
        }
      }
    }
    return fTurns;
  }

//this method will return the number of available turns for the toad to make
  public int availableToadTurns(){
    int tTurns = 0;
    for (int j = 0; j < 10; j++){
      if(this.boxesOfTheBoard[j] == "Toad"){
        if (j == 8 && this.boxesOfTheBoard[9] == "    "){
          tTurns += 1;
        }
        else if(j < 8 && (this.boxesOfTheBoard[j+1] == "    " || (this.boxesOfTheBoard[j+2] == "    " && this.boxesOfTheBoard[j+1] == "Frog"))){
          tTurns += 1;
        }
      }
    }
    return tTurns;
  }



} //end of ToadsAndFrogs.java
