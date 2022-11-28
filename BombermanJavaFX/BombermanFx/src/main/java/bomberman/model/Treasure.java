package bomberman.model;


import java.util.*;

public class Treasure implements Bonus{
    private final Pos posOfTreasure;
    private final List<Bonus> treasureList = new ArrayList<>();

    public Treasure(int x, int y){
        posOfTreasure = new Pos(x,y);
    }

    public void addTreasure(Bonus bonus){
            treasureList.add(bonus);
    }

    public int fillTreasureInside(int n, Treasure t,int randomSave){
        Random random = new Random();
        if (t.treasureList.size() == randomSave)
            return n;
        else {
            Bonus bonus = randomBonus(posOfTreasure.getI(),posOfTreasure.getJ());
            t.addTreasure(bonus);
            if (!(bonus instanceof Treasure)){
                return fillTreasureInside((n),t,randomSave);
            }else{
                int nbBonusInside =random.nextInt(3)+1;
                fillTreasureInside(nbBonusInside,(Treasure)bonus,nbBonusInside);
            }

            return fillTreasureInside((n-1),t,randomSave);
        }
    }

    private Bonus randomBonus(int x,int y){
        Random random = new Random();
        int bonusPercentage = random.nextInt(100)  ;
        if (bonusPercentage < 10)
            return new Treasure(x,y);
        else if (bonusPercentage < 40)
            return new LifeBonus(x,y);
        else if (bonusPercentage < 70)
            return new Mine(x,y);
        else
            return new BombBonus(x,y);

    }

    @Override
    public void showPos() {
        System.out.println("Treasure : ");
        for (Bonus bonus : treasureList){
            System.out.print("    ");
            if (bonus instanceof Treasure) {
                System.out.print("    " );
            }
            bonus.showPos();
        }
        System.out.println("    ---------------");
    }


    public Bonus getBonus(){
        List<Bonus> listB = new ArrayList<>();
        if (treasureList.get(0) instanceof Treasure)
            listB = ((Treasure) treasureList.get(0)).getTreasureList();
        if (!(treasureList.get(0) instanceof Treasure)){
            return treasureList.remove(0);
        }else {
            if (!listB.isEmpty() )
                return treasureList.remove(0);
            else {
                return treasureList.get(0);
            }
        }
    }

    public Pos getPosOfTreasure() {
        return posOfTreasure;
    }

    public List<Bonus> getTreasureList(){
        return treasureList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treasure treasure = (Treasure) o;
        return posOfTreasure.equals(treasure.posOfTreasure) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posOfTreasure);
    }

    @Override
    public String toString() {
        String s = "";
        s += posOfTreasure + "inside \n" ;
                for (Bonus b : treasureList)
                    s += b.toString() + "\n";
                return s;
    }
}
