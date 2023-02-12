//PROG2 VT2020, Inlämmning 2
//Mohammed Tahmid Chowdhury moch8386

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class AlertDescribedPlace extends Alert {

    private TextField inputName = new TextField();
    private TextField inputDesrib = new TextField();

    public AlertDescribedPlace(){
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Name:"), inputName);
        grid.addRow(1, new Label("Description:"), inputDesrib);
        getDialogPane().setContent(grid);
        setHeaderText(null);
        setTitle("New Place");
    }

    public String getInputName(){
        return inputName.getText();
    }

    public String getInputDesrib(){
        return inputDesrib.getText();
    }
}
