import java.util.*;

public class BoardFactory{
  public ArrayList createBoard(String gameType, int size){
    if(gameType.equals("TF") || gameType.equals("ER") || gameType.equals("TD")){
      return new ArrayList<String>(size);
    }
    else if(gameType.equals("C") || gameType.equals("CC") || gameType.equals("M")){
      return new ArrayList<ArrayList<String>>(size);
    }
    return null;
  }
}
