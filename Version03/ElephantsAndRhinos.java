
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
  static Stage lightedStage = new Stage();
  public ArrayList<String> boxesOfTheBoard;
  public boolean gameOver = false;
//  private ElephantsAndRhinosState state;
  //Context context = new Context();

/**
 * This method will create the spaces for buttons in the array of the board and popuklate it with elephants, rhinos, and blank strings
 * @param primaryStage the Stage for the game
 */
  public void start(Stage primaryStage) {

    lightedStage = primaryStage;

    this.boxesOfTheBoard = new ArrayList<String>(10);

    for (int m = 0; m < 10; m++) {
      this.boxesOfTheBoard.add(" ");
    }

//placing the elephants and rhinos
    this.boxesOfTheBoard.set(0,"E");
    this.boxesOfTheBoard.set(3,"E");
    this.boxesOfTheBoard.set(4,"E");
    this.boxesOfTheBoard.set(2,"R");
    this.boxesOfTheBoard.set(7,"R");
    this.boxesOfTheBoard.set(9,"R");

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
    Button menu = new Button("Game Menu");

    HBox h1 = new HBox(10);
    HBox h2 = new HBox(10);
//addding text and images to the buttons
    for (int j = 0; j < 10; j++) {
      String boxValue = boxesOfTheBoard.get(j);
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

    menu.setOnAction(new EventHandler<ActionEvent>() {
      Driver back = new Driver();
      @Override
      public void handle(ActionEvent e){
        back.start(Driver.menu);
        stage.close();
      }
    });

//checking if any moves are available, if not, display the win message depending on whose turn it is
    if (availableRhinoTurns() == 0 && turn == 1){
      message = "Elephants Win";
      gameOver = true;
      //this.state = new ElephantWinState();
    }
    else if (availableElephantTurns() == 0 && turn == 0){
      message = "Rhinos Win";
      gameOver = true;
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
 * A method to initalize the elephant buttons
 * @param elephantButton the button that has a "E" value
 * @param buttonIndex the index of the elephant in the array of button values
 */
  public void elephantActivation(Button elephantButton, int buttonIndex) {
    elephantButton.setOnAction( (event) -> {
      if (this.turn == 1) {
        return;
      }

      if (buttonIndex <= 8 && this.boxesOfTheBoard.get(buttonIndex + 1).equals(" ")) {
        this.boxesOfTheBoard.set(buttonIndex," ");
        this.boxesOfTheBoard.set(buttonIndex + 1,"E");
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

      if (buttonIndex > 0 && this.boxesOfTheBoard.get(buttonIndex-1).equals(" ")) {
          this.boxesOfTheBoard.set(buttonIndex," ");
          this.boxesOfTheBoard.set(buttonIndex-1,"R");
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
      if(this.boxesOfTheBoard.get(i).equals("R")){
        if (i == 1 && this.boxesOfTheBoard.get(0).equals(" ")){
          fTurns += 1;
        }
        else if(i > 1 && (this.boxesOfTheBoard.get(i-1).equals(" "))){
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
      if(this.boxesOfTheBoard.get(j).equals("E")){
        if (j == 8 && this.boxesOfTheBoard.get(9).equals(" ")){
          tTurns += 1;
        }
        else if(j < 8 && (this.boxesOfTheBoard.get(j+1).equals(" "))){
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



} //end of ElephantsAndRhinos.java
