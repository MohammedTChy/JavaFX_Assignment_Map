//PROG2 VT2020, Inlämmning 2
//Mohammed Tahmid Chowdhury moch8386

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class AlertNamedPlace extends Alert {

    private TextField inputName = new TextField();

    public AlertNamedPlace(){
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Name"), inputName);
        getDialogPane().setContent(grid);
        setHeaderText(null);
        setTitle("New Place");

    }

    public String getInputName(){
        return inputName.getText();
    }
}
