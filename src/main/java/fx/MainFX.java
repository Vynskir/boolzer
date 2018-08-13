package fx;

import bool.expression.Expression;
import bool.expression.TruthTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/boolzer.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/boolzer.css").toString());

        initialize(controller.getTextField1(), controller.getTextArea1(), controller.getGridPane1());
        initialize(controller.getTextField2(), controller.getTextArea2(), controller.getGridPane2());
        initializeCompare(controller.getTextArea1(), controller.getTextArea2(), controller.getTextArea3());

        stage.setTitle("Boolzer");
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setScene(scene);
        stage.show();
    }

    private void initialize(TextField textField, TextField textArea, GridPane gridPane) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            gridPane.getChildren().clear();

            try {
                Expression expression = new Expression(textField.getText());
                textArea.setStyle("-fx-text-fill: black;");
                textArea.setText(expression.getExpression());

                TruthTable truthTable = expression.getTruthTable();
                for (int x = 0; x < truthTable.getVariables().size(); x++) {
                    gridPane.add(new Label(truthTable.getVariables().get(x).getLabel()), x, 0);
                }
                gridPane.add(new Label("O"), truthTable.getVariables().size(), 0);

                for (int x = 0; x < truthTable.getTable().size(); x++) {
                    for (int y = 0; y < truthTable.getTable().get(x).size(); y++) {
                        gridPane.add(new Label(String.valueOf(truthTable.getTable().get(x).get(y))), y, x + 1);
                    }
                }
            } catch (IllegalArgumentException e) {
                textArea.setStyle("-fx-text-fill: red;");
                textArea.setText(e.getMessage());
            }
        });
    }

    private void initializeCompare(TextField textArea1, TextField textArea2, TextField textArea3) {
        compare(textArea1, textArea2, textArea3);
        compare(textArea2, textArea1, textArea3);
    }

    private void compare(TextField textArea1, TextField textArea2, TextField textArea3) {
        textArea1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!textArea1.getText().isEmpty() && !textArea2.getText().isEmpty()) {
                try {
                    Expression expression1 = new Expression(textArea1.getText());
                    Expression expression2 = new Expression(textArea2.getText());

                    if (expression1.isEquivalentWith(expression2)) {
                        textArea3.setText("The two expressions are equivalent");
                        textArea3.setStyle("-fx-text-fill: green;");

                    } else {
                        textArea3.setText("The two expressions are not equivalent");
                        textArea3.setStyle("-fx-text-fill: red;");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

}
