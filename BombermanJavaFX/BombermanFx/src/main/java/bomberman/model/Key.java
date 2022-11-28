package bomberman.model;


public class Key {

    private final Pos posOfKey ;

    public Key(int x, int y){
        posOfKey = new Pos(x,y);
    }

    public Pos getPosOfKey() {
        return posOfKey;
    }

    public boolean checkKeyInArea(Pos pos){
        return   isKeyLeft(pos) || isKeyRight(pos) || isKeyUp(pos) || isKeyDown(pos);
    }
    private boolean isKeyLeft(Pos pos){
        Pos posLeft = new Pos(pos.getI(),pos.getJ() - 1);
        return posLeft.equals(posOfKey);
    }
    private boolean isKeyRight(Pos pos){
        Pos posRight = new Pos(pos.getI(),pos.getJ()+1);
        return posRight.equals(posOfKey);
    }
    private boolean isKeyUp(Pos pos){
        Pos posUp = new Pos(pos.getI()-1,pos.getJ());
        return posUp.equals(posOfKey);
    }
    private boolean isKeyDown(Pos pos){
        Pos posDown = new Pos(pos.getI() + 1,pos.getJ());
        return posDown.equals(posOfKey);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.posOfKey.getI();
        hash = 53 * hash +this.posOfKey.getJ();
        return hash;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Key){
            Key k = (Key) o;
            return k.getPosOfKey().getI() == posOfKey.getI() && k.getPosOfKey().getJ() == posOfKey.getJ();
        }
        return false;
    }
    @Override
    public String toString(){
        return "Key - Position : "+ posOfKey;
    }
}
