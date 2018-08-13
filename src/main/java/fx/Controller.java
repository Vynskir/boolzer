package fx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox vBox;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textArea1;

    @FXML
    private TextField textArea2;

    @FXML
    private TextField textArea3;


    @FXML
    private GridPane gridPane1;

    @FXML
    private GridPane gridPane2;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> vBox.requestFocus());
    }

    public TextField getTextField1() {
        return textField1;
    }

    public TextField getTextField2() {
        return textField2;
    }

    public TextField getTextArea1() {
        return textArea1;
    }

    public TextField getTextArea2() {
        return textArea2;
    }

    public TextField getTextArea3() {
        return textArea3;
    }

    public GridPane getGridPane1() {
        return gridPane1;
    }

    public GridPane getGridPane2() {
        return gridPane2;
    }
}
