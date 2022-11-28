package bomberman.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;

public class Case {
    private final ObjectProperty<CaseValue> value = new SimpleObjectProperty<>(CaseValue.EMPTY);

    CaseValue getValue(){ return value.getValue();}

     void setValue(CaseValue value){
        this.value.setValue(value);
    }

    ReadOnlyObjectProperty<CaseValue> valueProperty() { return value;}
}
