package bomberman.model;

import java.util.Objects;

public class LifeBonus implements Bonus{
    private final Pos posOfLifeBonus;

    public LifeBonus(int x,int y){
        posOfLifeBonus = new Pos(x,y);
    }


    public Pos getPosOfLifeBonus() {
        return posOfLifeBonus;
    }

    @Override
    public void showPos() {
        System.out.println("LifeBonus - Position : " + posOfLifeBonus );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LifeBonus lifeBonus = (LifeBonus) o;
        return posOfLifeBonus.equals(lifeBonus.posOfLifeBonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posOfLifeBonus);
    }

    @Override
    public String toString() {
        return posOfLifeBonus.toString();
    }
}
