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

public class Driver extends Application{
  private Stage menu;


  public void start(Stage primaryStage){
    menu = primaryStage;
    primaryStage.setTitle("Game Menu");
    HBox h = new HBox(10);
    HBox h1 = new HBox(10);
    VBox v = new VBox(10);
    Button topplingDominoes = new Button("Toppling Dominoes");
    Button toadsAndFrogs = new Button("Toads And Frogs");
    Button elephantsAndRhinos = new Button("Elephants And Rhinos");
    Button chomp = new Button("Chomp");
    topplingDominoes.setMinSize(75,125);
    toadsAndFrogs.setMinSize(75,125);
    elephantsAndRhinos.setMinSize(75,125);
    chomp.setMinSize(75,125);
    h.getChildren().add(topplingDominoes);
    h.getChildren().add(toadsAndFrogs);
    h.getChildren().add(elephantsAndRhinos);
    h.getChildren().add(chomp);
    v.getChildren().add(h);
    ScrollPane menuWindow = new ScrollPane(v);
    menu.setScene(new Scene(menuWindow));
    menu.show();
    menu.sizeToScene();


    toadsAndFrogs.setOnAction( (event) -> {
      try{
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("java ToadsAndFrogs");
        menu.close();
      }
      catch(IOException e){
        System.out.println("something went wrong :(");
      }
    });

    topplingDominoes.setOnAction( (event) -> {
      try{
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("java TopplingDominoes");
        menu.close();
      }
      catch(IOException e){
        System.out.println("something went wrong :(");
      }
    });

    elephantsAndRhinos.setOnAction( (event) -> {
      try{
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("java ElephantsAndRhinos");
        menu.close();
      }
      catch(IOException e){
        System.out.println("something went wrong :(");
      }
    });

    chomp.setOnAction( (event) -> {
      try{
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("java Chomp");
        menu.close();
      }
      catch(IOException e){
        System.out.println("something went wrong :(");
      }
    });

  }
}
