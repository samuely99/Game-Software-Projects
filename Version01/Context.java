public class Context{
  private State state;

  public Context(){
    state = null;
  }

/**
* a method to set the state of the context
*/
  public void setState(State state){
    this.state = state;
  }

/**
* a method to get the current state
* @return the current state
*/
  public State getState(){
    return state;
  }
}
