package bomberman.model;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private final IntegerProperty nb_bombs = new SimpleIntegerProperty(20);
    private final IntegerProperty nb_lifes = new SimpleIntegerProperty(3);
    private final IntegerProperty nb_keys = new SimpleIntegerProperty(1);
    private final BooleanProperty isValid = new SimpleBooleanProperty();

    private final Pos posPlayer ;
    private static int nb_bombs_save,nb_lifes_save;

    private static boolean  discoverKeys = false;
    private static Bomb droppedBomb;
    private final List<Bomb> listOfDroppedBombs = new ArrayList<>();



   public Player(){
       posPlayer = new Pos(0,0);
       isValid.bind(nb_lifes.greaterThanOrEqualTo(1).and(nb_keys.greaterThanOrEqualTo(1)
               .and(nb_keys.lessThanOrEqualTo(5))).and(nb_bombs.greaterThanOrEqualTo(nb_keys)));

   }

    public int getNb_lifes(){
        return nb_lifes.get();
    }
    public int getNb_bombs(){ return nb_bombs.get();}
    public Bomb getDroppedBomb() {
        return droppedBomb;
    }
    public List<Bomb> getListOfDroppedBombs() {
        return listOfDroppedBombs;
    }
    public boolean getIsBombDropped() {
        return droppedBomb != null && droppedBomb.isBombDropped();
    }
    public boolean getDiscoverKeys() {
        return discoverKeys;
    }

    public void setNb_lifes(int nb){nb_lifes.setValue(nb);}
    public void setNb_bombs(int nb){nb_bombs.setValue(nb);}
    public void setIsBombDropped(boolean val){ droppedBomb.setIsBombDropped(val);}
    public void setDiscoverKeys(boolean res) {
        discoverKeys = res;
    }

    void clearListOfBomb(){listOfDroppedBombs.clear();}

    public Pos getPosPlayer(){
        return posPlayer;
    }
    public void setPosPlayer(int x, int y){
       posPlayer.setI(x);
       posPlayer.setJ(y);
   }

    public int bombCounter(){
        if(getNb_bombs() >= 0)
            nb_bombs.setValue(getNb_bombs()-1);
        return getNb_bombs();
    }

    public int loseOneLife(){
        nb_lifes.setValue(nb_lifes.getValue()-1);
        return getNb_lifes();
    }

    public void loadValues(){
        nb_lifes_save = nb_lifes.get();
        nb_bombs_save = nb_bombs.get();
    }
    public void reloadValues(){
       nb_lifes.setValue(nb_lifes_save);
       nb_bombs.setValue(nb_bombs_save);
    }

     void dropBomb(){
       droppedBomb = new Bomb(posPlayer.getI(), posPlayer.getJ());
       listOfDroppedBombs.add(getDroppedBomb());
       setIsBombDropped(true);
       bombCounter();
    }

    boolean playerInArea(){
       for (Bomb bomb : listOfDroppedBombs){
           if (bomb.containInAreaExplosion(posPlayer))
              return true;
       }
       return false;
    }

    ReadOnlyBooleanProperty isValidConfig(){
       return isValid;
    }

    IntegerProperty nb_bombsProperty(){return nb_bombs;}

    IntegerProperty nb_lifesProperty(){return nb_lifes;}

    IntegerProperty nb_keysProperty(){return nb_keys;}
    

    @Override
    public String toString(){
       return posPlayer.toString();
    }
}
