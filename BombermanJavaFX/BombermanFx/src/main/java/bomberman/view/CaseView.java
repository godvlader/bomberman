package bomberman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.image.Image;
import bomberman.model.CaseValue;
import bomberman.vm.CaseViewModel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.util.Duration;

public class CaseView extends StackPane {

    private static final Image PLAYER = new Image("bombermaner.png");
    private static final Image WALL = new Image("mur.png");
    private static final Image PILLAR = new Image("pillar.png");
    private static final Image BOMB = new Image("bmb.gif");
    private static final Image KEY = new Image("key2.gif");
    private static final Image LIFE = new Image("lifesUp.png");
    private static final Image EXTRA_BOMB = new Image("bombBonus.png");
    private static final Image MINE = new Image("mine_three.png");
    private static final Image TREASURE = new Image("treasure.gif");
    private static final Image MINE_ONE = new Image("mine_one.png");
    private static final Image MINE_TWO = new Image("mine_two.png");
    private static final Image MINE_THREE = new Image("mine_three.png");


    private final ImageView imageView = new ImageView();


    private final Timeline tl1;

    private final int DELAY = 3000;



    public CaseView(CaseViewModel caseViewModel, DoubleBinding caseWidthProperty){
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(caseWidthProperty);

        imageView.setImage(setImg(caseViewModel.valueProperty().get()));
        getChildren().add(imageView);
        ReadOnlyObjectProperty<CaseValue> valueProp = caseViewModel.valueProperty();

        tl1 = new Timeline(new KeyFrame(Duration.millis(DELAY),e->{
            caseViewModel.isMineTimesUpProperty().setValue(true);
        }));

        valueProp.addListener((obs, old, newVal) -> {
                imageView.setImage(setImg(newVal));
        });
        caseViewModel.isMineActivatedProperty().addListener((o,old,newVal)->{
            if (newVal){
                caseViewModel.isMineActivatedProperty().set(false);
                tl1.play();
            }
        });
        caseViewModel.playerGetMineProperty().addListener((o,old,newVal)->{
            if (newVal){
                tl1.stop();
                caseViewModel.isMineTimesUpProperty().setValue(false);
            }
        });

    }

    private Image setImg(CaseValue caseValue){
        switch (caseValue){
            case PILLAR:
                return PILLAR;
            case PLAYER:
                return PLAYER;
            case WALL:
                return WALL;
            case BOMB:
                return BOMB;
            case KEY:
                return KEY;
            case LIFE:
                return LIFE;
            case EXTRA_BOMB:
                return EXTRA_BOMB;
            case MINE:
                return MINE;
            case MINE_ONE:
                return MINE_ONE;
            case MINE_TWO:
                return MINE_TWO;
            case MINE_THREE:
                return MINE_THREE;
            case TREASURE:
                return TREASURE;
            default:
                return null;
        }
    }
}
