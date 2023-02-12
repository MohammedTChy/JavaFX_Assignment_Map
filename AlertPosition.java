//PROG2 VT2020, Inlämmning 2
//Mohammed Tahmid Chowdhury moch8386

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AlertPosition extends Alert {

    private TextField xinput = new TextField();
    private TextField yinput = new TextField();

    public AlertPosition(){
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("x: "), xinput);
        grid.addRow(1, new Label("y: "), yinput);
        getDialogPane().setContent(grid);
        setHeaderText(null);


    }

    public int getXAlert(){
        return Integer.parseInt(xinput.getText());
    }

    public int getYAlert(){
        return Integer.parseInt(yinput.getText());
    }
}
