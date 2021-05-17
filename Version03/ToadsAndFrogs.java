
/**
 * This is a cleaned version of the terribly designed code, TadsAndFrogs.java, written by Kyle Burke for Software Project 0
 * @author Samuel Yorke and Maddison Tetrault
 *
 * includes endgame message feature
 * with fixed resizing issues
 * added images to the buttons
 * added save and load game features
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
 import java.util.concurrent.CountDownLatch;

public class ToadsAndFrogs extends Application implements Serializable, Game{

  private String stageTitle = "Amphibian Battle!  Toad's Turn.";
  public int turn = 0;
  static Stage lightedStage = new Stage();
  public boolean gameOver = false;
  public boolean turnChanging = false;
  public ArrayList<String> boxesOfTheBoard;
  private ToadsAndFrogsState state;

/**
 * This method will create the spaces for buttons in the array of the board and popuklate it with toads, frogs, and blank strings
 * @param primaryStage the Stage for the game
 */
  @Override
  public void start(Stage primaryStage) {

    lightedStage = primaryStage;

    this.boxesOfTheBoard = new ArrayList<String>(10);

    for (int m = 0; m < 10; m++) {
      boxesOfTheBoard.add(" ");
    }

//placing the toads and frogs
    this.boxesOfTheBoard.set(0,"T");
    this.boxesOfTheBoard.set(3,"T");
    this.boxesOfTheBoard.set(4,"T");
    this.boxesOfTheBoard.set(2,"F");
    this.boxesOfTheBoard.set(7,"F");
    this.boxesOfTheBoard.set(9,"F");

    primaryStage.setTitle(this.stageTitle);
    stageSet(this.lightedStage);
  }

/**
 * This method will change the player who is allowed to act
 * @param stage the current instance of the stage
 */
  @Override
  public void turnChange(Stage stage) {
    this.turn = 1 - this.turn;
    this.stageSet(stage);
  }
/**
 * This method will add a title for the stage, a message, as well as buttons with images
 * @param stage the stage for the buttons and text to be added to
 */
  @Override
  public void stageSet(Stage stage) {
    turnChanging = false;
    String[] teams = {"Toad", "Frog"};
    String message = "";
    lightedStage.setTitle("Amphibian Battle!  " + teams[turn] + "'s Turn.");
    this.lightedStage = stage;
    Button save = new Button("Save");
    Button load = new Button("Load");
    Button menu = new Button("Game Menu");

    HBox h1 = new HBox(10);
    HBox h2 = new HBox(10);
//addding text and images to the buttons
    for (int j = 0; j < 10; j++) {
      String boxValue = boxesOfTheBoard.get(j);
      Button pane = new Button();
      pane.setText(boxValue);
      h1.getChildren().add(pane);

      if (boxValue.equals("T")) {
          Image toadImg = new Image ("toad.png");
          ImageView view = new ImageView(toadImg);
          pane.setGraphic(view);
          toadActivation(pane, j);
      }

      else if (boxValue.equals(" ")) {
        pane.setMinSize(75,48);
        blankActivation(pane, j);
      }

      else if (boxValue.equals("F")) {
        Image frogImg = new Image ("frog.png");
        ImageView view = new ImageView(frogImg);
        pane.setGraphic(view);
        frogActivation(pane, j);
      }

    }

    save.setOnAction( (event) -> {
      saveGame();
    });

    load.setOnAction( (event) -> {
      loadGame();
    });

    menu.setOnAction(new EventHandler<ActionEvent>() {
      Driver back = new Driver();
      @Override
      public void handle(ActionEvent e){
        back.start(Driver.menu);
        stage.close();
      }
    });

//checking if any moves are available, if not, display the win message depending on whose turn it is
    if (availableFrogTurns() == 0 && turn == 1){
      this.state = new ToadWinState();
      gameOver = true;
    }
    else if (availableToadTurns() == 0 && turn == 0){
      this.state = new FrogWinState();
      gameOver = true;
    }
    else {
      this.state = new TFGameState();
    }
    message = this.state.getMessage();

//setting the stage
    VBox v = new VBox(10);
    v.getChildren().add(h1);
    v.getChildren().add(new Label(message));
    h2.getChildren().add(save);
    h2.getChildren().add(load);
    h2.getChildren().add(menu);
    v.getChildren().add(h2);
    ScrollPane gameWindow = new ScrollPane(v);
    stage.setScene(new Scene(gameWindow));
    stage.show();
    stage.sizeToScene();

  }

/**
 * A method to initalize the toad buttons
 * @param toadButton the button that has a "T" value
 * @param buttonIndex the index of the toad in the array of button values
 */
  public void toadActivation(Button toadButton, int buttonIndex) {
    toadButton.setOnAction( (event) -> {
      System.out.println("Croak!");
      if (this.turn == 1) {
        return;
      }

      if (buttonIndex <= 8 && this.boxesOfTheBoard.get(buttonIndex + 1).equals(" ")) {
        this.boxesOfTheBoard.set(buttonIndex," ");
        this.boxesOfTheBoard.set(buttonIndex + 1,"T");
        turnChange(lightedStage);
      }

      else if (buttonIndex <= 7 && this.boxesOfTheBoard.get(buttonIndex + 1).equals("F") && this.boxesOfTheBoard.get(buttonIndex+ 2).equals(" ")) {
                this.boxesOfTheBoard.set(buttonIndex," ");
                this.boxesOfTheBoard.set(buttonIndex + 2,"T");
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
        System.out.println("Empty!");
        stageSet(lightedStage);
    });
  }
/**
 * A method to initalize the frog buttons
 * @param frogButton the button that has a "F" value
 * @param buttonIndex the index of the frog in the array of buttons values
 */
  public void frogActivation(Button frogButton, int buttonIndex) {
    frogButton.setOnAction((event) -> {
      System.out.println("Ribbit!");

      if (this.turn < 1) {
        return;
      }
      turnChanging = true;
      if (buttonIndex > 0 && this.boxesOfTheBoard.get(buttonIndex-1).equals(" ")) {
          this.boxesOfTheBoard.set(buttonIndex," ");
          this.boxesOfTheBoard.set(buttonIndex-1,"F");
          turnChange(lightedStage);
      }
      else if (buttonIndex > 1 && this.boxesOfTheBoard.get(buttonIndex-2).equals(" ") && this.boxesOfTheBoard.get(buttonIndex-1).equals("T")) {
          this.boxesOfTheBoard.set(buttonIndex," ");
          this.boxesOfTheBoard.set(buttonIndex-2,"F");
          turnChange(this.lightedStage);
      }
    });
  }

/**
 * A method to give the numeber of available moves the Frog player has
 * @return the number of moves the Frog player can make
 */
  public int availableFrogTurns(){
    int fTurns = 0;
    for (int i = 0; i < 10; i++){
      if(this.boxesOfTheBoard.get(i).equals("F")){
        if (i == 1 && this.boxesOfTheBoard.get(0).equals(" ")){
          fTurns += 1;
        }
        else if(i > 1 && (this.boxesOfTheBoard.get(i-1).equals(" ") || (this.boxesOfTheBoard.get(i-2).equals(" ") && this.boxesOfTheBoard.get(i-1).equals("T")))){
          fTurns += 1;
        }
      }
    }
    return fTurns;
  }
/**
 * A method to give the numeber of available moves the Toad player has
 * @return the number of moves the Toad player can make
 */
  public int availableToadTurns(){
    int tTurns = 0;
    for (int j = 0; j < 10; j++){
      if(this.boxesOfTheBoard.get(j).equals("T")){
        if (j == 8 && this.boxesOfTheBoard.get(9).equals(" ")){
          tTurns += 1;
        }
        else if(j < 8 && (this.boxesOfTheBoard.get(j+1).equals(" ") || (this.boxesOfTheBoard.get(j+2).equals(" ") && this.boxesOfTheBoard.get(j+1).equals("F")))){
          tTurns += 1;
        }
      }
    }
    return tTurns;
  }
  /**
  * A method that will save a game by writing the current instance of the board array to a text file
  */
  @Override
  public void saveGame(){
    try{
      FileWriter file = new FileWriter("tfSave.txt");
      FileWriter file2 = new FileWriter("tfPlayerSave.txt");
      BufferedWriter saveWrite = new BufferedWriter(file);
      BufferedWriter playerWrite = new BufferedWriter(file2);
      for(int i = 0; i < 10; i++){
        saveWrite.write(this.boxesOfTheBoard.get(i));
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
  @Override
  public void loadGame(){
    try{
      BufferedReader loadRead = new BufferedReader(new FileReader("tfSave.txt"));
      BufferedReader playerRead = new BufferedReader(new FileReader("tfPlayerSave.txt"));
      String gameline = loadRead.readLine();
      String playerLine = playerRead.readLine();
      String test = "";
      loadRead.close();
      for(int i = 0; i < 10; i++){
        this.boxesOfTheBoard.set(i,String.valueOf(gameline.charAt(i)));
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



} //end of ToadsAndFrogs.java
