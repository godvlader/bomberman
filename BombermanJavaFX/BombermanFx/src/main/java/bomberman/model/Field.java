package bomberman.model;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;


class Field {

    static final int FIELD_WIDTH = 15;
    private final int DELAY_MINE = 1000;
    private Timeline tl1,tl2,tl3;
    private final Case[][] matrix ;
    private final BooleanProperty isMineActivated = new SimpleBooleanProperty(false);


    Field(){
        matrix = new Case[FIELD_WIDTH][];
        for(int i = 0; i < FIELD_WIDTH; ++i){
            matrix[i] = new Case[FIELD_WIDTH];
            for(int j = 0; j< FIELD_WIDTH;++j){
                matrix[i][j] = new Case();
                int finalI = i;
                int finalJ = j;
                matrix[i][j].valueProperty().addListener((o,old,newVal)->{
                    if (newVal.equals(CaseValue.MINE)){
                        isMineActivated.set(true);
                        matrix[finalI][finalJ].setValue(CaseValue.MINE_ONE);
                        checkMineStatus(new Pos(finalI, finalJ));
                        stopTimeLines();
                        playTimeLines();
                    }
                });
            }
        }

    }

    public void setValueOnField(int x, int y, CaseValue value){
        matrix[x][y].setValue(value);
    }

    public CaseValue getValueOfField(int x, int y){
        return matrix[x][y].getValue();
    }

    public void checkMineStatus(Pos pos){

        tl1 = new Timeline(new KeyFrame(Duration.millis(DELAY_MINE), e -> {
            setValueOnField(pos.getI(), pos.getJ(), CaseValue.MINE_TWO); }));
        tl2 = new Timeline(new KeyFrame(Duration.millis(DELAY_MINE*2), e -> {
            setValueOnField(pos.getI(), pos.getJ(), CaseValue.MINE_THREE); }));
        tl3= new Timeline(new KeyFrame(Duration.millis(DELAY_MINE*3), e -> {
            setValueOnField(pos.getI(), pos.getJ(), CaseValue.EMPTY); }));

    }

    private void stopTimeLines(){
        tl1.stop();
        tl2.stop();
        tl3.stop();
    }
    private void playTimeLines(){
        tl1.play();
        tl2.play();
        tl3.play();
    }

    public void renewField(){
        for(int i = 0; i < FIELD_WIDTH; ++i){
            for(int j = 0; j< FIELD_WIDTH;++j){
                matrix[i][j].setValue(CaseValue.EMPTY);
            }
        }
    }


    ReadOnlyObjectProperty<CaseValue> valueProperty(int line, int col) {
        return matrix[line][col].valueProperty();
    }

    BooleanProperty isMineActivated(){return isMineActivated;}

}
