package fx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private Label textArea1;

    @FXML
    private Label textArea2;

    @FXML
    private Label textArea3;

    @FXML
    private Label textArea4;

    @FXML
    private Label textArea5;

    @FXML
    private GridPane gridPane1;

    @FXML
    private GridPane gridPane2;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea1.setStyle(null);

        Platform.runLater(() -> vBox.requestFocus());
    }

    public TextField getTextField1() {
        return textField1;
    }

    public TextField getTextField2() {
        return textField2;
    }

    public Label getTextArea1() {
        return textArea1;
    }

    public Label getTextArea2() {
        return textArea2;
    }

    public Label getTextArea3() {
        return textArea3;
    }

    public Label getTextArea4() {
        return textArea4;
    }

    public Label getTextArea5() {
        return textArea5;
    }

    public GridPane getGridPane1() {
        return gridPane1;
    }

    public GridPane getGridPane2() {
        return gridPane2;
    }
}
