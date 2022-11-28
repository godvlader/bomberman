package bomberman.view;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import bomberman.vm.MenuViewModel;
import javafx.util.converter.NumberStringConverter;



import static bomberman.view.BombermanView.MENU_WIDTH;
import static bomberman.view.BombermanView.PADDING;

public class MenuView extends VBox {

    // Composants du "menu"
    private final Label playerNbLifesTxt = new Label("Nombre de vies : ");
    private final Label playerNbBombsTxt = new Label("Nombre de bombes : ");
    private final Label statusGameTxt = new Label("Statut de la partie : ");

    private final Label nbKeys = new Label("Nombre de clés : ");

    // Composants bindés du "menu"
    private final Label playerNbLifes = new Label();
    private final Label playerNbBombs = new Label();
    private final Label statusGame = new Label();

    private final TextField inputNbLifes = new TextField();
    private final TextField inputNbBombs = new TextField();
    private final TextField inputNbKeys = new TextField();


    HBox hBox1 = new HBox(playerNbLifesTxt,inputNbLifes);
    HBox hBox2 = new HBox(playerNbBombsTxt,inputNbBombs);
    HBox hBox3 = new HBox(nbKeys,inputNbKeys);
    private final MenuViewModel menuViewModel;

    public MenuView(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
        configMenu();
    }

    private void configMenu(){
        setPadding(new Insets(PADDING));
        setMinWidth(MENU_WIDTH);
        setAlignment(Pos.TOP_LEFT);
        setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        setSpacing(7);

        getChildren().addAll(hBox1,hBox2,hBox3);

        configLabels();
        configBindings();
    }

    private void configLabels(){
        playerNbLifes.textProperty().bind(menuViewModel.nbLifesLabelProperty().asString());
        playerNbBombs.textProperty().bind(menuViewModel.nbBombProperty().asString());
        statusGame.textProperty().bind(menuViewModel.gameStatusProperty());
    }
    private void resetInputs(){
        hBox1.getChildren().remove(playerNbLifes);
        hBox1.getChildren().add(inputNbLifes);
        hBox2.getChildren().remove(playerNbBombs);
        hBox2.getChildren().add(inputNbBombs);
        hBox3.getChildren().removeAll(statusGameTxt,statusGame);
        hBox3.getChildren().addAll(nbKeys,inputNbKeys);
    }

    private void configBindings(){
        inputNbLifes.textProperty().bindBidirectional(menuViewModel.nb_lifesProperty(),new NumberStringConverter());
        inputNbBombs.textProperty().bindBidirectional(menuViewModel.nb_bombsProperty(),new NumberStringConverter());
        inputNbKeys.textProperty().bindBidirectional(menuViewModel.nb_keysProperty(), new NumberStringConverter());
        menuViewModel.isGameInProgressProperty().addListener((obs, oldVal, newVal)->{
                if (newVal){
                    hBox1.getChildren().remove(inputNbLifes);
                    hBox1.getChildren().add(playerNbLifes);
                    hBox2.getChildren().remove(inputNbBombs);
                    hBox2.getChildren().add(playerNbBombs);
                    hBox3.getChildren().removeAll(inputNbKeys,nbKeys);
                    hBox3.getChildren().addAll(statusGameTxt,statusGame);
                }
        });
        menuViewModel.activeConfigProperty().addListener((obs,oldVal,newVal)->{
            if (newVal){
                resetInputs();
                menuViewModel.activeConfigProperty().setValue(false);
            }
        });
        onlyDigitsValue();
    }


    // Pour n'accepter que des CHIFFRES dans INPUTS
    private void onlyDigitsValue(){
        inputNbLifes.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    inputNbLifes.setText(newValue.replaceAll("[^\\d]",""));
                }
            }
        });
        inputNbBombs.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    inputNbBombs.setText(newValue.replaceAll("[^\\d]",""));
                }
            }
        });
        inputNbKeys.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    inputNbKeys.setText(newValue.replaceAll("[^\\d]",""));
                }
            }
        });
    }

}
