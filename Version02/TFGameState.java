public class TFGameState implements ToadsAndFrogsState {
/**
* A method that will get the game message
* @param context the context of the state
* @return the game message
*/
  public String getMessage(){
    //context.setState(this);
    return "If you can't move, then you lose the game.";
  }

/**
* A mthod that will give the current state
* @return the current state
*/
  public String toString(){
    return "Game State";
  }
}
