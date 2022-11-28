package bomberman.model;

import java.util.Objects;

public class BombBonus implements Bonus{
    private final Pos posOfBombBonus;

    public BombBonus(int x,int y){
        posOfBombBonus = new Pos(x,y);
    }

    @Override
    public void showPos() {
        System.out.println("BombBonus - Position : " + posOfBombBonus );
    }


    public Pos getPosOfBombBonus() {
        return posOfBombBonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BombBonus bombBonus = (BombBonus) o;
        return posOfBombBonus.equals(bombBonus.posOfBombBonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posOfBombBonus);
    }

    @Override
    public String toString() {
        return posOfBombBonus.toString();
    }
}
