package bomberman.vm;

import bomberman.model.GameFacade;

public class FieldViewModel {

    private final GameFacade game;

    public FieldViewModel(GameFacade game) { this.game = game;}

    public CaseViewModel getCaseViewModel(int line, int col){
        return new CaseViewModel(line,col,game);
    }

}
