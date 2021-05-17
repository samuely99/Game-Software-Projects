public class ToadWinState implements State {
/**
* A method that will get the endgame message
* @param context the context of the state
* @return the endgame message
*/
  public String getEndMessage(){
    //context.setState(this);
    return "Toad Wins!..nice try you frog scum";
  }

/**
* A mthod that will give the current sate
* @return the current state
*/
  public String toString(){
    return "Toad Win State";
  }
}
