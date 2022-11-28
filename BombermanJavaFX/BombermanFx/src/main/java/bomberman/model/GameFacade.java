package bomberman.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static bomberman.model.Field.FIELD_WIDTH;


public class GameFacade {
    private final Game game = new Game();
    private final Player player = new Player();


    private final int DELAY = 1000;

    // le jeu est démarré :
    private final BooleanProperty isStarted = new SimpleBooleanProperty(false);
    // le jeu est en cours
    private final BooleanProperty isInProgress = new SimpleBooleanProperty(false);
    // Le jeu respecte les règles métiers pour commencer une partie
    private final BooleanProperty isStartable = new SimpleBooleanProperty(false);
    // permet d'afficher ou non les inputs (Textfield) |Fait le lien entre Menuview et ButtonView|
    private final BooleanProperty activeConfig = new SimpleBooleanProperty(false);
    // permet de désactiver le minuteur de la Mine dans CaseView
    private final BooleanProperty playerGetMine = new SimpleBooleanProperty(false);

    private final BooleanProperty isMineTimesUp = new SimpleBooleanProperty(false);
    public GameFacade(){
        // le jeu est démarré s'il n'est pas dans le statut de configuration des joueurs
        isStarted.bind(gameStatusProperty().isNotEqualTo(GameStatus.GAME_NOT_LAUNCH));
        // Le jeu est démarré quand on appuie sur PLAY
        isInProgress.bind(game.gameStatusProperty().isEqualTo(GameStatus.GAME_IN_PROGRESS));

        isStartable.bind((game.gameStatusProperty().isEqualTo(GameStatus.GAME_IN_PROGRESS).not()).and(player.isValidConfig()));


        isMineTimesUpProperty().addListener((obs,oldVal,newVal) -> {
            if (newVal){
                for (Pos pos : getPosOfMineActivated()){
                    explodeMine(pos);
                    isMineTimesUp.set(false);
                }
            }
        });


    }

    private void putPlayerInField(){
        game.putPlayerInField(player);
    }



    public void init(){
        game.init();
    }

    public void start(){
        if(isStartable.get()){
            game.setListOfKeys(nb_keysProperty().get());
            game.start();
            putPlayerInField();
            player.loadValues();
         }
    }


    public void lost(){
        if (player.getNb_lifes() >= 1 && player.getNb_bombs() >= 0) {
            player.loseOneLife();
            game.getField().setValueOnField(player.getPosPlayer().getI(),player.getPosPlayer().getJ(),CaseValue.EMPTY);
            player.setPosPlayer(0,0);
            putPlayerInField();
        }else {
            game.lost();
        }
    }

    public void reload(){
        if (getStatus() == GameStatus.PLAYER_WON || getStatus() == GameStatus.PLAYER_LOSE){
            game.clearAllLists();
            game.setListOfKeys(nb_keysProperty().get());
            game.reload();
            player.setPosPlayer(0,0);
            putPlayerInField();
            player.loadValues();
        }
    }

    public void isWinGame(){
        game.won();
    }

    public void movePlayer(Controls controls) {
        switch (controls){
            case UP:
                goUp();
                break;
            case DOWN:
                goDown();
                break;
            case LEFT:
                goLeft();
                break;
            case RIGHT:
                goRight();
                break;
            case SPACE:
                dropBomb();
                break;
        }
    }

    private void goUp(){
        Pos up = new Pos(player.getPosPlayer().getI()-1,player.getPosPlayer().getJ());
        if (up.getI() >= 0 && !isWall(up) && !isPillar(up) && !isBomb(up)) {
            if(player.getIsBombDropped()) {
                game.getField().setValueOnField(player.getDroppedBomb().getPosOfBomb().getI(),player.getDroppedBomb().getPosOfBomb().getJ(),CaseValue.BOMB);
                player.setIsBombDropped(false);
            }else{
                displayBonus(player.getPosPlayer());
            }
            player.setPosPlayer(up.getI(), up.getJ());
            game.putPlayerInField(player);
        }
        getElementOfGame();
        isWinGame();
    }
    private void goDown(){
        Pos down = new Pos(player.getPosPlayer().getI()+1,player.getPosPlayer().getJ());
        if (down.getI() < FIELD_WIDTH && !isWall(down) && !isPillar(down) && !isBomb(down)) {
            if(player.getIsBombDropped()) {
                game.getField().setValueOnField(player.getDroppedBomb().getPosOfBomb().getI(),player.getDroppedBomb().getPosOfBomb().getJ(),CaseValue.BOMB);
                player.setIsBombDropped(false);
            }else{
                displayBonus(player.getPosPlayer());
            }
            player.setPosPlayer(down.getI(),down.getJ());
            game.putPlayerInField(player);
        }
        getElementOfGame();
        isWinGame();
    }
    private void goLeft(){
        Pos left = new Pos(player.getPosPlayer().getI(),player.getPosPlayer().getJ()-1);
        if (left.getJ() >= 0 && !isWall(left) && !isPillar(left) && !isBomb(left)) {
            if(player.getIsBombDropped()) {
                game.getField().setValueOnField(player.getDroppedBomb().getPosOfBomb().getI(),player.getDroppedBomb().getPosOfBomb().getJ(),CaseValue.BOMB);
                player.setIsBombDropped(false);
            }else{
                displayBonus(player.getPosPlayer());
            }
            player.setPosPlayer(left.getI(), left.getJ());
            game.putPlayerInField(player);
        }
        getElementOfGame();
        isWinGame();
    }
    private void goRight(){
        Pos right = new Pos(player.getPosPlayer().getI(),player.getPosPlayer().getJ()+1);
        if (right.getJ() < FIELD_WIDTH && !isWall(right) && !isPillar(right) && !isBomb(right)) {
            if(player.getIsBombDropped()) {
                game.getField().setValueOnField(player.getDroppedBomb().getPosOfBomb().getI(),player.getDroppedBomb().getPosOfBomb().getJ(),CaseValue.BOMB);
                player.setIsBombDropped(false);
            }else{
                displayBonus(player.getPosPlayer());
            }
            player.setPosPlayer(right.getI(),right.getJ());
            game.putPlayerInField(player);
        }
        getElementOfGame();
        isWinGame();
    }



    private void getLifeBonus(){
        LifeBonus lbDelete = null;
        for (LifeBonus lb :game.getListOfLifeBonus()){
            if (player.getPosPlayer().equals(lb.getPosOfLifeBonus())){
                lbDelete = lb;
                player.setNb_lifes(player.getNb_lifes()+1);
            }
        }
        game.removeElemListOfLifeBonus(lbDelete);
    }

    private void getBombBonus(){
        BombBonus bbDelete = null;
        for (BombBonus bb : game.getListOfBombBonus()){
            if (player.getPosPlayer().equals(bb.getPosOfBombBonus())){
                bbDelete = bb;
                player.setNb_bombs(player.getNb_bombs()+1);
            }
        }
        game.removeElemListOfBombBous(bbDelete);
    }

    private void getMine(){ // Si player prend la MINE (déclenchée par le mouvement Controls up,down,left,right)
        Mine mDelete = null;
        for (Mine m : game.getListOfMines()){
            if (player.getPosPlayer().equals(m.getPosOfMine()) ){
                mDelete = m;
                playerGetMine.setValue(true);
            }else{
                playerGetMine.setValue(false);
            }
        }
        game.removeElemListOfMine(mDelete);
    }


    private void getTreasure(){ 
        Bonus res;
        Treasure treasureDel = null;
        Treasure treasureInside = null;
        for (Treasure t : game.getListOfTreasure()){
            if (player.getPosPlayer().equals(t.getPosOfTreasure())){
                res = t.getTreasureList().isEmpty() ? null : t.getBonus();
                if (res instanceof LifeBonus){
                    game.addElemListOfLifeBonus((LifeBonus) res);
                }else if (res instanceof BombBonus){
                    game.addElemListOfBombBous((BombBonus) res);
                }else if (res instanceof Mine){
                    game.addElemListOfMine((Mine) res);
                }else if (res instanceof Treasure){
                    treasureInside = (Treasure) res;
                }
            }else if (t.getTreasureList().isEmpty()) {
                treasureDel = t;
            }
        }
        if (treasureInside != null)
            game.addElemListOfTreasure(treasureInside);
        game.removeElemListOfTreasure(treasureDel);
    }

    private void getElementOfGame(){
        getLifeBonus();
        getBombBonus();
        getMine();
        getTreasure();
        getWinningKeys();
    }

    public void explodeMine(Pos pos){
        checkElemArroundMine(pos);
        List<Mine> removeList = new ArrayList<>();
        for (Mine m : game.getListOfMines()){
            if (valueProperty(m.getPosOfMine().getI(),m.getPosOfMine().getJ()).get().equals(CaseValue.MINE_THREE)){
                removeList.add(m);
            }
        }
        for (Mine m : removeList){
            game.removeElemListOfMine(m);
            game.getField().setValueOnField(m.getPosOfMine().getI(),m.getPosOfMine().getJ(),CaseValue.EMPTY);
        }
    }


    private void checkElemArroundMine(Pos pos){
        List<Pos> mineToDel = new ArrayList<>();
        for (Mine m : game.getListOfMines()){
            if (m.containInAreaExplosion(pos)){
                mineToDel.add(pos);
            }
        }
        for (Pos p : mineToDel){
            isInAreaOfMine(p);
        }
    }

    private void isInAreaOfMine(Pos posOfMine){
        checkCenter(posOfMine);
        //check left case
        checkPosLeft(posOfMine);
        //check right case
        checkPosRight(posOfMine);
        // check up case
        checkPosUp(posOfMine);
        // check down case
        checkPosDown(posOfMine);
        //check if player near mine
        playerNearMine(new Mine(posOfMine.getI(), posOfMine.getJ()));
    }

    private void playerNearMine(Mine m){
        if(m.containInAreaExplosion(player.getPosPlayer()))
            lost();
    }

    private void checkCenter(Pos pos){
        game.removeElemListOfMine(new Mine(pos.getI(), pos.getJ()));
        removeBonusUnderMine(game.getAllBonusAtPos(pos),pos);
    }

    private void removeBonusUnderMine(CaseValue caseValue,Pos pos){
        switch (caseValue){
            case EXTRA_BOMB:
                game.removeElemListOfBombBous(new BombBonus(pos.getI(), pos.getJ()));
                break;
            case MINE:
                game.removeElemListOfMine(new Mine(pos.getI(), pos.getJ()));
                break;
            case LIFE:
                game.removeElemListOfLifeBonus(new LifeBonus(pos.getI(), pos.getJ()));
                break;
            case TREASURE:
                game.removeElemListOfTreasure(new Treasure(pos.getI(), pos.getJ()));
                break;
            default:
                game.getField().setValueOnField(pos.getI(), pos.getJ(), CaseValue.EMPTY);
        }
    }

    private List<Pos> getPosOfMineActivated(){
        List<Pos> mineActivatedList = new ArrayList<>();
        for (Mine m : game.getListOfMines()){
            if (isMineActivated(m.getPosOfMine()))
                mineActivatedList.add(m.getPosOfMine());
        }
        return mineActivatedList;
    }

    private boolean isMineActivated(Pos pos){
        return valueProperty(pos.getI(), pos.getJ()).get() == CaseValue.MINE_ONE
                ||valueProperty(pos.getI(), pos.getJ()).get() == CaseValue.MINE_TWO
                ||valueProperty(pos.getI(), pos.getJ()).get() == CaseValue.MINE_THREE
                ||valueProperty(pos.getI(), pos.getJ()).get() == CaseValue.MINE;
    }
    private void displayBonus(Pos pos){
        game.getField().setValueOnField(pos.getI(), pos.getJ(), game.getAllBonusAtPos(pos));
    }

    private boolean isWall(Pos pos){
        return game.getValueOfField(pos.getI(),pos.getJ()) == CaseValue.WALL;
    }
    private boolean isPillar(Pos pos){
        return game.getValueOfField(pos.getI(),pos.getJ()) == CaseValue.PILLAR;
    }
    private boolean isBomb(Pos pos){
        return game.getValueOfField(pos.getI(),pos.getJ()) == CaseValue.BOMB;
    }

    void dropBomb() {
        player.dropBomb();
        explode();
    }

    public void explode() {
        for (Bomb elem : player.getListOfDroppedBombs()) {
            new Timeline(new KeyFrame(Duration.millis(DELAY), e -> {
                if (player.playerInArea())
                    lost();

                isInAreaOfBomb(elem.getPosOfBomb());

                if (player.getPosPlayer().getI() == 0 && player.getPosPlayer().getJ() == 0) {
                    game.getField().setValueOnField(0, 0, CaseValue.PLAYER);
                }
                player.setIsBombDropped(false);

                if (getNbBombs() == 0 && !game.isKeyInArea(elem.getPosOfBomb()))
                    game.lost();
                player.clearListOfBomb();
            })).play();
            //pour éviter bug de la bomb + player en position (0,1) || (1,0)
            if (player.getPosPlayer().getI() + player.getPosPlayer().getJ() == 1) {
                game.getField().setValueOnField(0, 0, CaseValue.EMPTY);
            }
        }
    }

     void isInAreaOfBomb(Pos posOfBomb)   {

        checkPosCenter(posOfBomb);
        // check left case
        checkPosLeft(posOfBomb);
        //check right case
        checkPosRight(posOfBomb);
        // check up case
        checkPosUp(posOfBomb);
        // check down case
        checkPosDown(posOfBomb);

    }

    private void checkPosCenter(Pos pos){
        displayBonus(pos);
    }

    private void checkPosLeft(Pos pos){
        Pos posLeft = new Pos(pos.getI(),pos.getJ() - 1);
        if (posLeft.getJ() >= 0){
            actionOnExplosion(posLeft);
            if (game.getValueOfField(posLeft.getI(),posLeft.getJ()) != CaseValue.PILLAR) {
                isKey(posLeft);
            }
        }
    }

    private void checkPosRight(Pos pos){
        Pos posRight = new Pos(pos.getI(),pos.getJ()+1);
        if (posRight.getJ() < FIELD_WIDTH){
            actionOnExplosion(posRight);
             if (game.getValueOfField(posRight.getI(),posRight.getJ()) != CaseValue.PILLAR)
                isKey(posRight);

        }
    }

    private void checkPosUp(Pos pos){
        Pos posUp = new Pos(pos.getI()-1,pos.getJ());
        if (posUp.getI() >= 0){
            actionOnExplosion(posUp);
            if (posUp.getI() >= 0  && game.getValueOfField(posUp.getI(), posUp.getJ()) != CaseValue.PILLAR)
                isKey(posUp);

        }
    }

    private void checkPosDown(Pos pos){
        Pos posDown = new Pos(pos.getI() + 1,pos.getJ());
        if (posDown.getI() < FIELD_WIDTH){
            actionOnExplosion(posDown);
            if (posDown.getI() <FIELD_WIDTH && game.getValueOfField(posDown.getI(), posDown.getJ()) != CaseValue.PILLAR)
                isKey(posDown);

        }
    }

    private void isKey(Pos pos){
        Key key = new Key(pos.getI(), pos.getJ());
         player.setDiscoverKeys(game.getListOfKeys().isEmpty());

        if (game.getListOfKeys().contains(key) && !player.getDiscoverKeys()) {
            game.getField().setValueOnField(pos.getI(), pos.getJ(), CaseValue.KEY);
            player.setDiscoverKeys(false);
        }else{
            displayBonus(pos);
        }

    }

    private void actionOnExplosion(Pos pos){
        switch (game.getValueOfField(pos.getI(), pos.getJ())){
            case WALL:
                game.removeElemListOfWall(new Wall(pos.getI(), pos.getJ()));
                break;
            case KEY:
                game.lost();
                break;
            case TREASURE:
                game.removeElemListOfTreasure(new Treasure(pos.getI(), pos.getJ()));
                break;
            case LIFE:
                game.removeElemListOfLifeBonus(new LifeBonus(pos.getI(), pos.getJ()));
                player.setNb_lifes(player.getNb_lifes()-1);
                break;
            case EXTRA_BOMB:
                isInAreaOfBomb(pos);
                game.removeElemListOfBombBous(new BombBonus(pos.getI(), pos.getJ()));
                break;
            case MINE:
                game.removeElemListOfMine(new Mine(pos.getI(), pos.getJ()));
                break;
        }
    }

    private void getWinningKeys(){
        Key keyDelete = null;
        for (Key key : game.getListOfKeys()){
            if (player.getPosPlayer().equals(key.getPosOfKey())){
                keyDelete = key;
            }
        }
        game.removeElemListOfKeys(keyDelete);
    }


    public void reloadValues(){
        player.reloadValues();
    }

    public ReadOnlyBooleanProperty playerGetMineProperty(){return playerGetMine;}

    public ReadOnlyObjectProperty<CaseValue> valueProperty(int line, int col) {
        return game.valueProperty(line, col);
    }

    public ReadOnlyObjectProperty<GameStatus> gameStatusProperty() {
        return game.gameStatusProperty();
    }

    public GameStatus getStatus() {
        return gameStatusProperty().get();
    }

    public Integer getNbBombs(){return nb_bombsProperty().get();}


    public ReadOnlyBooleanProperty isStartedProperty() {
        return isStarted;
    }

    public ReadOnlyBooleanProperty isInProgressProperty() {
        return isInProgress;
    }

    public ReadOnlyBooleanProperty isStartableProperty(){
        return isStartable;
    }

    public BooleanProperty activeConfigProperty(){return activeConfig;}

    public IntegerProperty nb_bombsProperty(){return player.nb_bombsProperty();}

    public IntegerProperty nb_lifesProperty(){return player.nb_lifesProperty();}

    public IntegerProperty nb_keysProperty(){return player.nb_keysProperty();}

    public BooleanProperty isMineTimesUpProperty(){return isMineTimesUp;}


    public BooleanProperty isMineActivatedProperty(){return game.isMineActivatedProperty();}
}
