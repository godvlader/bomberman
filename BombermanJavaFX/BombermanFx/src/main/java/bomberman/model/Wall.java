package bomberman.model;

import java.util.Objects;

public class Wall {
    private final Pos posOfWall ;

    public Wall(int x, int y){
        posOfWall = new Pos(x,y);
    }

    public Pos getPosOfWall() {
        return posOfWall;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posOfWall);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Wall){
            Wall w = (Wall) o;
            return posOfWall.getI() == w.posOfWall.getI() && posOfWall.getJ() == w.posOfWall.getJ();
        }
        return false;
    }

    @Override
    public String toString(){
        return posOfWall.toString();
    }
}
