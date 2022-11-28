package bomberman.vm;

import bomberman.model.GameStatus;
import javafx.beans.property.*;
import bomberman.model.GameFacade;

public class ButtonViewModel {
    private final GameFacade game;

    public ButtonViewModel(GameFacade game){
        this.game = game;
    }


    public void redirect(){
        if (game.getStatus() == GameStatus.GAME_NOT_LAUNCH)
            game.start();
        else
            game.reload();
    }


    public void reloadValues(){game.reloadValues();}
    public ReadOnlyStringProperty playLabelProperty(){return new SimpleStringProperty("Play");}

    public ReadOnlyStringProperty configGameLabelProperty(){return new SimpleStringProperty("Config game");}

    public BooleanProperty activeConfigProperty(){return game.activeConfigProperty();}

    public ReadOnlyBooleanProperty isGameInProgressProperty() {
        return game.isInProgressProperty();
    }

    public ReadOnlyBooleanProperty isStartableProperty(){ return game.isStartableProperty();}


}
