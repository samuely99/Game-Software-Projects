public class FrogWinState implements State {

  /**
  * A method that will get the endgame message
  * @param context the context of the state
  * @return the endgame message
  */
  public String getEndMessage(){
    //context.setState(this);
    return "The Frogian Empire Always Provails!";
  }
  /**
  * A mthod that will give the current sate
  * @return the current state
  */
  public String toString(){
    return "Frog Win State";
  }
}
