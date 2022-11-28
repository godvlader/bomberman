package bomberman.vm;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import bomberman.model.CaseValue;
import bomberman.model.GameFacade;

public class CaseViewModel {
    private final GameFacade game;
    private final int line, col;


    public CaseViewModel(int line, int col, GameFacade game) {
        this.line = line;
        this.col = col;
        this.game = game;
    }



    public ReadOnlyBooleanProperty playerGetMineProperty(){return game.playerGetMineProperty();}

    public  ReadOnlyObjectProperty<CaseValue> valueProperty() {
        return game.valueProperty(line,col);
    }

    public BooleanProperty isMineTimesUpProperty(){return game.isMineTimesUpProperty();}
    public BooleanProperty isMineActivatedProperty(){return game.isMineActivatedProperty();}


}
