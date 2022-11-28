package bomberman.model;

import java.util.Objects;

public class Pillar {
    private final Pos posOfPillar;

    public Pillar(int x, int y){
        posOfPillar = new Pos(x,y);
    }

    public Pos getPosOfPillar() {
        return posOfPillar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posOfPillar);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Pillar){
            Pillar p = (Pillar) o;
            return posOfPillar.getI() == p.posOfPillar.getI() && posOfPillar.getJ() == p.posOfPillar.getJ();
        }
        return false;
    }

    @Override
    public String toString() {
        return  "["+posOfPillar.getI() + ":" + posOfPillar.getJ()+"]";
    }
}
