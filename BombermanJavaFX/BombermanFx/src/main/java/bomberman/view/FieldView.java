package bomberman.view;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import bomberman.vm.FieldViewModel;


import static bomberman.view.BombermanView.FIELD_WIDTH;
import static bomberman.view.BombermanView.PADDING;

public class FieldView extends GridPane {
    public FieldView(FieldViewModel fieldViewModel, DoubleProperty fieldWidthProperty){
        setGridLinesVisible(false);
        setPadding(new Insets(10));

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100.0/ FIELD_WIDTH);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100.00/ FIELD_WIDTH);
        DoubleBinding caseWidthProperty = fieldWidthProperty.subtract(PADDING*2).divide(FIELD_WIDTH);

        for (int i = 0; i < FIELD_WIDTH; ++i) {
            getColumnConstraints().add(columnConstraints);
            getRowConstraints().add(rowConstraints);
        }

        // Remplissage de la grille
        for (int i = 0; i < FIELD_WIDTH; ++i) {
            for (int j = 0; j < FIELD_WIDTH; ++j) {
                CaseView caseView = new CaseView(fieldViewModel.getCaseViewModel(i, j), caseWidthProperty);
                add(caseView, j, i); // lignes/colonnes inversées dans gridpane
            }
        }



    }
}
