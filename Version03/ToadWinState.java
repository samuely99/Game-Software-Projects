public class ToadWinState implements ToadsAndFrogsState {
/**
* A method that will get the endgame message
* @param context the context of the state
* @return the endgame message
*/
  public String getMessage(){
    //context.setState(this);
    return "Toad Wins!..nice try you frog scum";
  }

/**
* A mthod that will give the current state
* @return the current state
*/
  public String toString(){
    return "Toad Win State";
  }
}
