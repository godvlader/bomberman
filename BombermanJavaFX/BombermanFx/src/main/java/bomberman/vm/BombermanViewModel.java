package bomberman.vm;

import bomberman.sound.Sound;
import bomberman.model.Controls;
import bomberman.model.GameFacade;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ReadOnlyStringProperty;



public class BombermanViewModel {

    private final MenuViewModel menuViewModel;
    private final FieldViewModel fieldViewModel;
    private final ButtonViewModel buttonViewModel;
    private final GameFacade game = new GameFacade();




    public MenuViewModel getMenuViewModel(){ return menuViewModel;}

    public FieldViewModel getFieldViewModel() {return fieldViewModel;}

    public ButtonViewModel getButtonViewModel(){return buttonViewModel;}

    public BombermanViewModel() {
        menuViewModel = new MenuViewModel(game);
        fieldViewModel = new FieldViewModel(game);
        buttonViewModel = new ButtonViewModel(game);
    }

    public void keyPressed(Controls controls) {
        game.movePlayer(controls);
    }

    public ReadOnlyStringProperty titleProperty(){return new SimpleStringProperty("Bomberman");}





}
