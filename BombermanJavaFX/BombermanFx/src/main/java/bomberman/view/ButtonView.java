package bomberman.view;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import bomberman.vm.ButtonViewModel;

public class ButtonView extends VBox {

    private final ButtonViewModel buttonViewModel ;

    private final Button playButton = new Button();
    private final Button configGameButton = new Button();


    public ButtonView(ButtonViewModel buttonViewModel){
        this.buttonViewModel = buttonViewModel;
        configBottomPart();
    }

    private void configBottomPart(){
        getChildren().add(playButton);
        setAlignment(Pos.TOP_CENTER);
        configBindingButton();
        managePlayButton();
    }


    private void managePlayButton(){
        playButton.setOnMouseClicked(e -> {
                buttonViewModel.redirect();
        });

    }

    private void configBindingButton(){
        playButton.textProperty().bind(buttonViewModel.playLabelProperty());
        playButton.disableProperty().bind(buttonViewModel.isGameInProgressProperty());
        playButton.disableProperty().bind(buttonViewModel.isStartableProperty().not());
        configGameButton.textProperty().bind(buttonViewModel.configGameLabelProperty());
        buttonViewModel.isGameInProgressProperty().addListener((obs,oldVal,newVal)->{
            if(!newVal)
                manageConfigGameButton();
        });
    }

    private void manageConfigGameButton(){
        getChildren().remove(playButton);
        getChildren().add(configGameButton);

        configGameButton.setOnAction(e -> {
            buttonViewModel.activeConfigProperty().setValue(true);
            getChildren().remove(configGameButton);
            getChildren().add(playButton);
            buttonViewModel.reloadValues();
        });
    }


}
