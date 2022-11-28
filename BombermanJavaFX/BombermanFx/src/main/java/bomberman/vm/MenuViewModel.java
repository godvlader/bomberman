package bomberman.vm;


import bomberman.model.GameStatus;
import javafx.beans.property.*;
import bomberman.model.GameFacade;

public class MenuViewModel {

    private final GameFacade game;

    private final IntegerProperty playerNbLifesText = new SimpleIntegerProperty();
    private final IntegerProperty playerNbBombsText = new SimpleIntegerProperty();
    private final StringProperty gameStatusText = new SimpleStringProperty();

    public MenuViewModel(GameFacade game){
        this.game = game;
        initLabelValues();
        configLogicStatus();
    }

    public void start(){
        game.start();
    }


    private void initLabelValues(){
        playerNbLifesText.setValue(nb_lifesProperty().getValue());
        playerNbBombsText.setValue(nb_bombsProperty().getValue());
        gameStatusText.setValue(game.getStatus().statusString);

    }

    private void configLogicStatus(){
        game.gameStatusProperty().addListener((obs,old,newval) -> refreshStatus(newval));
        game.nb_bombsProperty().addListener((obs,old,newVal) -> refreshNbBombs(newVal.intValue()));
        game.nb_lifesProperty().addListener((obs,old,newVal) -> refreshNbLifes(newVal.intValue()));
    }

    public void refreshNbLifes(int nbLifes){
        if (nbLifes >= 0)
            playerNbLifesText.setValue(nbLifes);
        if (nbLifes <= 0)
            game.lost();
    }

    public void refreshStatus(GameStatus status){
        switch (status){
            case GAME_NOT_LAUNCH:
                gameStatusText.setValue(GameStatus.GAME_NOT_LAUNCH.statusString);
                break;
            case GAME_IN_PROGRESS:
                gameStatusText.setValue(GameStatus.GAME_IN_PROGRESS.statusString);
                break;
            case PLAYER_WON:
                gameStatusText.setValue(GameStatus.PLAYER_WON.statusString);
                break;
            case PLAYER_LOSE:
                gameStatusText.setValue(GameStatus.PLAYER_LOSE.statusString);
                break;

        }
    }

    public void refreshNbBombs(int nbBomb){
        if (nbBomb >= 0) {
            playerNbBombsText.setValue(nbBomb);
        }
        else
            game.lost();
    }


    public ReadOnlyIntegerProperty nbLifesLabelProperty(){return playerNbLifesText;}

    public ReadOnlyIntegerProperty nbBombProperty(){return playerNbBombsText;}

    public ReadOnlyStringProperty gameStatusProperty(){return gameStatusText;}

    public ReadOnlyBooleanProperty isGameInProgressProperty() {
        return game.isInProgressProperty();
    }

    public IntegerProperty nb_bombsProperty(){return game.nb_bombsProperty();}

    public IntegerProperty nb_lifesProperty(){return game.nb_lifesProperty();}

    public IntegerProperty nb_keysProperty(){return game.nb_keysProperty();}

    public BooleanProperty activeConfigProperty(){return game.activeConfigProperty();}



}
