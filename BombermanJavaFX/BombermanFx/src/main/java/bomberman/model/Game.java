package bomberman.model;

import bomberman.sound.Sound;
import javafx.beans.property.*;

import java.util.*;

import static bomberman.model.Field.FIELD_WIDTH;

public class Game {
    private final Field field = new Field();
    private Key winningKey;
    private List<Wall> listOfWalls = new ArrayList<>();
    private List<Wall> listOfWallsSave = new ArrayList<>(listOfWalls);
    private final List<Pillar> listOfPillars = new ArrayList<>();
    private final Set<Key> listOfKeys = new HashSet<>();

    private final List<LifeBonus> listOfLifeBonus = new ArrayList<>();
    private final List<BombBonus> listOfBombBonus = new ArrayList<>();
    private final List<Mine> listOfMines = new ArrayList<>();
    private final List<Treasure> listOfTreasure = new ArrayList<>();
    private  Mine droppedMine;

    private final ObjectProperty<GameStatus> gameStatus = new SimpleObjectProperty<>(GameStatus.GAME_NOT_LAUNCH);

    public Game(){
        init();

    }

    void init(){
        generatePillars();
        generateWalls();
        setWallsOnField();
        setPillarsOnField();
        listOfWallsSave.addAll(listOfWalls);
    }

    private void reloadGame(){
        field.renewField();
        listOfWalls.addAll(listOfWallsSave);
        setWallsOnField();
        setPillarsOnField();
    }

    private void generateWalls(){
        Random random = new Random();
        int nbWalls = ((FIELD_WIDTH * FIELD_WIDTH)-listOfPillars.size()) *30 / 100 ;

        for (int i = 0; i < nbWalls; ++i){
            int x = random.nextInt(FIELD_WIDTH);
            int y = random.nextInt(FIELD_WIDTH);
            if (!containPillarAtPos(x,y) && x+y > 2) {
                Wall wall = new Wall(x, y);
                if (!listOfWalls.contains(wall))
                    listOfWalls.add(wall);
                else
                    --i;
            }else
                --i;
        }
    }

    private void generatePillars(){
        for (int i = 0; i<FIELD_WIDTH; ++i)
            for (int j = 0; j <FIELD_WIDTH; ++j)
                if (i %2 != 0 && j %2 != 0)
                    listOfPillars.add(new Pillar(i,j));
    }

    private Key generateKey(){
        Random random = new Random();
        int nbWall = random.nextInt(listOfWalls.size());
        winningKey = new Key(listOfWalls.get(nbWall).getPosOfWall().getI(),listOfWalls.get(nbWall).getPosOfWall().getJ());
        return winningKey;
    }

    private void generateLifeBonus(){
        Random random = new Random();
        int bound = ((FIELD_WIDTH * FIELD_WIDTH)-listOfPillars.size())*2/ 100;
        int nbLifes = random.nextInt(bound)+1 ;
        for (int i = 0; i < nbLifes; ++i){
            int x = random.nextInt(FIELD_WIDTH);
            int y = random.nextInt(FIELD_WIDTH);
            if (!containPillarAtPos(x,y) && x+y > 2) {
                LifeBonus LB = new LifeBonus(x,y);
                if (!listOfLifeBonus.contains(LB))
                    listOfLifeBonus.add(LB);
                else
                    --i;
            }else
                --i;
        }
        for (LifeBonus lb : listOfLifeBonus)
            lb.showPos();
    }
    private void generateBombBonus(){
        Random random = new Random();
        int bound = ((FIELD_WIDTH * FIELD_WIDTH)-listOfPillars.size())*2/ 100;
        int nbBombs = random.nextInt(bound)+1 ;
        for (int i = 0; i < nbBombs; ++i){
            int x = random.nextInt(FIELD_WIDTH);
            int y = random.nextInt(FIELD_WIDTH);
            if (!containPillarAtPos(x,y) && x+y > 2) {
                BombBonus BB = new BombBonus(x,y);
                if (!listOfBombBonus.contains(BB))
                    listOfBombBonus.add(BB);
                else
                    --i;
            }else
                --i;
        }
        for (BombBonus bb : listOfBombBonus)
            bb.showPos();
    }

    private void generateMine(){
        Random random = new Random();
        int bound = ((FIELD_WIDTH * FIELD_WIDTH)-listOfPillars.size())*2/ 100;
        int nbMines = random.nextInt(bound)  +1 ;
        for (int i = 0; i < nbMines; ++i){
            int x = random.nextInt(FIELD_WIDTH);
            int y = random.nextInt(FIELD_WIDTH);
            if (!containPillarAtPos(x,y) && x+y > 2) {
                droppedMine = new Mine(x,y);
                if (!listOfMines.contains(droppedMine))
                    listOfMines.add(droppedMine);
                else
                    --i;
            }else
                --i;
        }
        for (Mine m : listOfMines)
            m.showPos();
    }

    private int createTreasure(int n){
        Random random = new Random();
        if (n == 0)
            return n;
        else {
            int x,y;    //Création de la Pos Treasure
            do {
                x = random.nextInt(FIELD_WIDTH);
                y = random.nextInt(FIELD_WIDTH);
            } while (containPillarAtPos(x, y) || x + y < 2);
            Treasure treasure = new Treasure(x,y);
            int nb_bonus =  random.nextInt(3)+1;
            listOfTreasure.add(treasure);
            treasure.fillTreasureInside(nb_bonus,treasure,nb_bonus);

            return createTreasure(n-1);
        }
    }

    private void generateTreasure(){
        Random random = new Random();
        int bound = ((FIELD_WIDTH * FIELD_WIDTH)-listOfPillars.size())*5/ 100;
        int nbTreasure = random.nextInt(bound)+1 ;
        createTreasure(nbTreasure);
        for (Treasure t : listOfTreasure)
            t.showPos();
    }

    private boolean containPillarAtPos(int i, int j){
        Pillar pillar = new Pillar(i,j);
        return listOfPillars.contains(pillar);
    }

    private void setWallsOnField(){
        for (Wall wall : listOfWalls)
            field.setValueOnField(wall.getPosOfWall().getI(),wall.getPosOfWall().getJ(),CaseValue.WALL);
    }

    private void setPillarsOnField(){
        for(Pillar pillar : listOfPillars)
            field.setValueOnField(pillar.getPosOfPillar().getI(),pillar.getPosOfPillar().getJ(),CaseValue.PILLAR);
    }

    public void putPlayerInField(Player player){
        field.setValueOnField(player.getPosPlayer().getI(),player.getPosPlayer().getJ(),CaseValue.PLAYER);
    }

    public CaseValue getValueOfField(int x, int y){
        return field.getValueOfField(x,y);
    }


    private void putKeyInField(){
        for (Key key : listOfKeys){
            if (!listOfWalls.contains(new Wall(key.getPosOfKey().getI(),key.getPosOfKey().getJ())))
                field.setValueOnField(key.getPosOfKey().getI(),key.getPosOfKey().getJ(),CaseValue.KEY);
        }
        putBonusInField();
    }

    private void putBonusInField(){
            for (LifeBonus lb : listOfLifeBonus){
                if (!listOfWalls.contains(new Wall(lb.getPosOfLifeBonus().getI(),lb.getPosOfLifeBonus().getJ()))){
                    field.setValueOnField(lb.getPosOfLifeBonus().getI(),lb.getPosOfLifeBonus().getJ(),CaseValue.LIFE);
                }
            }
            for (BombBonus bb : listOfBombBonus){
                if (!listOfWalls.contains(new Wall(bb.getPosOfBombBonus().getI(),bb.getPosOfBombBonus().getJ()))){
                    field.setValueOnField(bb.getPosOfBombBonus().getI(),bb.getPosOfBombBonus().getJ(),CaseValue.EXTRA_BOMB);
                }
            }
            for (Mine m : listOfMines) {
                if (!listOfWalls.contains(new Wall(m.getPosOfMine().getI(),m.getPosOfMine().getJ()))){
                    field.setValueOnField(m.getPosOfMine().getI(),m.getPosOfMine().getJ(),CaseValue.MINE);
                }
            }
            for (Treasure t : listOfTreasure){
                if (!listOfWalls.contains(new Wall(t.getPosOfTreasure().getI(),t.getPosOfTreasure().getJ()))){
                    field.setValueOnField(t.getPosOfTreasure().getI(),t.getPosOfTreasure().getJ(),CaseValue.TREASURE);
                }

            }

    }

    public void setListOfKeys (int nbOfKeys){
        int i =0;
        while (i < nbOfKeys){
            Key key = generateKey();
            if (!listOfKeys.contains(key)){
                listOfKeys.add(key);
                i++;
            }
        }
    }

    void start(){
        generateLifeBonus();
        generateBombBonus();
        generateMine();
        generateTreasure();
        displayKey();
        putKeyInField();
        putBonusInField();
        newGame();
    }

    boolean isKeyInArea(Pos pos){
        return winningKey.checkKeyInArea(pos);
    }
    private void displayKey(){
        for (Key key : listOfKeys)
            System.out.println(key);
        System.out.println("===========================");
    }

    public void won(){
        //Sound.playBombWinLose("anc3_2122_a04\\src\\main\\resources\\win.wav");
        if(listOfKeys.isEmpty()){
            gameWin();
        }
    }

    List<LifeBonus> getListOfLifeBonus(){return listOfLifeBonus;}
    void removeElemListOfLifeBonus(LifeBonus lb){ listOfLifeBonus.remove(lb);}
    void addElemListOfLifeBonus(LifeBonus lb){listOfLifeBonus.add(lb);}
    List<BombBonus> getListOfBombBonus() {
        return listOfBombBonus;
    }
    void removeElemListOfBombBous(BombBonus bb){listOfBombBonus.remove(bb);}
    void addElemListOfBombBous(BombBonus bb){listOfBombBonus.add(bb);}
    List<Mine> getListOfMines() {
        return listOfMines;
    }
    void removeElemListOfMine(Mine m){listOfMines.remove(m);}
    void addElemListOfMine(Mine m){listOfMines.add(m);}
    List<Treasure> getListOfTreasure() {
        return listOfTreasure;
    }
    void removeElemListOfTreasure(Treasure t){listOfTreasure.remove(t);}
    void addElemListOfTreasure(Treasure t){listOfTreasure.add(t);}

    CaseValue getAllBonusAtPos(Pos pos){

        BombBonus bb = new BombBonus(pos.getI(),pos.getJ());
        Mine m = new Mine(pos.getI(), pos.getJ());
        LifeBonus lb = new LifeBonus(pos.getI(), pos.getJ());
        Treasure t = new Treasure(pos.getI(), pos.getJ());
        if (listOfBombBonus.contains(bb))
            return CaseValue.EXTRA_BOMB;
        else if (listOfMines.contains(m))
            return CaseValue.MINE;
        else if (listOfLifeBonus.contains(lb))
            return CaseValue.LIFE;
        else if (listOfTreasure.contains(t))
            return CaseValue.TREASURE;
        else
            return CaseValue.EMPTY;
    }


    Set<Key> getListOfKeys(){
        return listOfKeys;
    }

    void removeElemListOfKeys(Key key){
        listOfKeys.remove(key);
    }


    void removeElemListOfWall(Wall wall){
        listOfWalls.remove(wall);
    }

    void clearAllLists(){
        listOfKeys.clear();
        listOfLifeBonus.clear();
        listOfTreasure.clear();
        listOfMines.clear();
        listOfBombBonus.clear();
    }

    public void reload(){
        reloadGame();
        start();
    }

    public void lost(){
        //Sound.playBombWinLose("anc3_2122_a04\\src\\main\\resources\\lose.wav");
        gameLose();
    }

    ReadOnlyObjectProperty<GameStatus> gameStatusProperty() {
        return gameStatus;
    }

    ReadOnlyObjectProperty<CaseValue> valueProperty(int line, int col) {
        return field.valueProperty(line, col);
    }

    BooleanProperty isMineActivatedProperty(){return field.isMineActivated();}

    public void gameWin(){gameStatus.setValue(GameStatus.PLAYER_WON);}

    public void gameLose(){
        gameStatus.setValue(GameStatus.PLAYER_LOSE);
    }

    public void newGame() {
        gameStatus.setValue(GameStatus.GAME_IN_PROGRESS);
    }

    public Field getField (){return field;}
}
