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
    VBox v = new VBox(10);
    Button topplingDominoes = new Button("Toppling Dominoes");
    Button toadsAndFrogs = new Button("Toads And Frogs");
    h.getChildren().add(topplingDominoes);
    h.getChildren().add(toadsAndFrogs);
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

  }
}
