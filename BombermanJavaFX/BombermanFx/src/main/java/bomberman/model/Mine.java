package bomberman.model;


import java.util.Objects;

import static bomberman.model.Field.FIELD_WIDTH;

public class Mine implements Bonus{
    private final Pos posOfMine;
    private final int AREA_OF_MINE = 1;


    public Mine(int x, int y){
        posOfMine = new Pos(x,y);

    }



    public boolean containInAreaExplosion(Pos pos){
        return isAtLeft(pos) || isAtRight(pos) || isDown(pos) || isUp(pos) || pos.equals(posOfMine);
    }

    boolean isAtLeft(Pos pos){
        Pos posLeft = new Pos(pos.getI(),pos.getJ() - AREA_OF_MINE);
        if (posLeft.getJ() >= 0)
            return posLeft.equals(posOfMine);
        else
            return false;
    }

    boolean isAtRight(Pos pos){
        Pos posRight = new Pos(pos.getI(),pos.getJ()+AREA_OF_MINE);
        if (posRight.getJ() < FIELD_WIDTH)
            return posRight.equals(posOfMine);
        else
            return false;
    }

    boolean isUp(Pos pos){
        Pos posUp = new Pos(pos.getI()-AREA_OF_MINE,pos.getJ());
        if (posUp.getI() >= 0)
            return posUp.equals(posOfMine);
        else
            return false;
    }

    boolean isDown(Pos pos){
        Pos posDown = new Pos(pos.getI() + AREA_OF_MINE,pos.getJ());
        if (posDown.getI() < FIELD_WIDTH)
            return posDown.equals(posOfMine);
        else
            return false;
    }

    @Override
    public void showPos() {
        System.out.println("Mine - Position : " + posOfMine );
    }


    public Pos getPosOfMine() {
        return posOfMine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mine mine = (Mine) o;
        return posOfMine.equals(mine.posOfMine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posOfMine);
    }

    @Override
    public String toString() {
        return posOfMine.toString();
    }

}
