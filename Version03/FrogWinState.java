public class FrogWinState implements ToadsAndFrogsState {

  /**
  * A method that will get the endgame message
  * @param context the context of the state
  * @return the endgame message
  */
  public String getMessage(){
    //context.setState(this);
    return "The Frogian Empire Always Provails!";
  }
  /**
  * A mthod that will give the current state
  * @return the current state
  */
  public String toString(){
    return "Frog Win State";
  }
}
