package bomberman.model;

import java.util.Objects;

public class Pos {
    private int i ;
    private int j ;


    public Pos(){
        i= 0;
        j=0;
    }
    public Pos(int x, int y){
        i = x;
        j = y;
    }

    public int getI() {
        return i;
    }

     void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

     void setJ(int j) {
        this.j = j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Pos){
            Pos p = (Pos) o;
            return p.i == i && p.j ==j;
        }
        return false;
    }
    @Override
    public String toString() {
        return "(" + i + ","+j +")";
    }
}
