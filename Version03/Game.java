import javafx.stage.*;

public interface Game{
  boolean gameOver = false;
  public void start(Stage primaryStage);
  public void turnChange(Stage stage);
  public void saveGame();
  public void loadGame();
  public void stageSet(Stage stage);
}
