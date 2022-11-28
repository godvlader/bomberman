package bomberman.view;

import bomberman.sound.Sound;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import bomberman.model.Controls;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.BorderPane;
import bomberman.vm.BombermanViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class BombermanView extends BorderPane {

    // ViewModel
    private final BombermanViewModel bombermanViewModel = new BombermanViewModel();

    // Constantes de mise en page
    static final int PADDING = 20;
    static final int FIELD_WIDTH = 15;
    static final int MENU_WIDTH = 160;
    private static final int SCENE_MIN_WIDTH = 650;
    private static final int SCENE_MIN_HEIGHT = 800;

    //Contrainte de mise en page
    private final DoubleProperty fieldWidthProperty = new SimpleDoubleProperty(250);

    // Composants principaux
    private MenuView menuView;
    private FieldView fieldView;
    private ButtonView buttonView;


    public BombermanView(Stage primaryStage) { start(primaryStage);}

    public void start(Stage stage) {
        // Mise en place des composants principaux
        configMainComponents(stage);

        // Mise en place de la scène et affichage de la fenêtre
        Scene scene = new Scene(this,SCENE_MIN_WIDTH,SCENE_MIN_HEIGHT);

        //background music
        //Sound.play("anc3_2122_a04\\src\\main\\resources\\menu.wav");

        configKeyPressed(scene);
        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    private void configMainComponents(Stage stage){
        stage.titleProperty().bind(bombermanViewModel.titleProperty());
        setPadding(new Insets(PADDING));
        // Mise en place des composants du menu
        configMenu();

        //Mise en place du Field du jeu
        configFieldView();

        //Mise en place du bouton play
        configPlayButton();
    }

    private void configMenu(){
        menuView = new MenuView(bombermanViewModel.getMenuViewModel());
        setTop(menuView);
    }

    private void configFieldView (){
        configFieldPane();
    }

    private void configFieldPane(){
        createField();
    }

    private void createField(){
        fieldView = new FieldView(bombermanViewModel.getFieldViewModel(), fieldWidthProperty);
        fieldView.setStyle("-fx-background-color: #2e8b00;");
        fieldView.minHeightProperty().bind(fieldWidthProperty);
        fieldView.minWidthProperty().bind(fieldWidthProperty);
        fieldView.maxHeightProperty().bind(fieldWidthProperty);
        fieldView.maxWidthProperty().bind(fieldWidthProperty);
        fieldWidthProperty.bind(Bindings.min(widthProperty().subtract(2 * PADDING), heightProperty()));


        setCenter(fieldView);
    }

    private void configPlayButton(){
        buttonView = new ButtonView(bombermanViewModel.getButtonViewModel());
        setBottom(buttonView);
    }



    public void configKeyPressed(Scene scene){
        scene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode()) {
                case RIGHT:
                        bombermanViewModel.keyPressed(Controls.RIGHT);
                    break;
                case LEFT:
                        bombermanViewModel.keyPressed(Controls.LEFT);
                    break;
                case UP:
                        bombermanViewModel.keyPressed(Controls.UP);
                    break;
                case DOWN:
                        bombermanViewModel.keyPressed(Controls.DOWN);
                    break;
                case SPACE:
                        bombermanViewModel.keyPressed(Controls.SPACE);
                    break;
            }
        });
    }


}
