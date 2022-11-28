package bomberman.model;

import java.util.Objects;

import static bomberman.model.Field.FIELD_WIDTH;

public class Bomb {

    private final Pos posOfBomb;
    private final int AREA_OF_BOMB = 1;

    public Bomb(int x, int y){
        posOfBomb = new Pos(x,y);
    }

    public Pos getPosOfBomb() {
        return posOfBomb;
    }

    private boolean isBombDropped = false;



    public boolean containInAreaExplosion(Pos pos){
        return isAtLeft(pos) || isAtRight(pos) || isDown(pos) || isUp(pos) || pos.equals(posOfBomb);
    }

    boolean isAtLeft(Pos pos){
        Pos posLeft = new Pos(pos.getI(),pos.getJ() - AREA_OF_BOMB);
        if (posLeft.getJ() >= 0)
            return posLeft.equals(posOfBomb);
        else
            return false;
    }

    boolean isAtRight(Pos pos){
        Pos posRight = new Pos(pos.getI(),pos.getJ()+AREA_OF_BOMB);
        if (posRight.getJ() < FIELD_WIDTH)
            return posRight.equals(posOfBomb);
        else
            return false;
    }

    boolean isUp(Pos pos){
        Pos posUp = new Pos(pos.getI()-AREA_OF_BOMB,pos.getJ());
        if (posUp.getI() >= 0)
            return posUp.equals(posOfBomb);
        else
            return false;
    }

    boolean isDown(Pos pos){
        Pos posDown = new Pos(pos.getI() + AREA_OF_BOMB,pos.getJ());
        if (posDown.getI() < FIELD_WIDTH)
            return posDown.equals(posOfBomb);
        else
            return false;
    }

    public boolean isBombDropped() {
        return isBombDropped;
    }

    public void setIsBombDropped(boolean val) {
        isBombDropped = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bomb bomb = (Bomb) o;
        return posOfBomb.equals(bomb.posOfBomb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posOfBomb);
    }
}
