package fx;

import bool.expression.Expression;
import bool.expression.TruthTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/boolzer.css").toString());

        Controller ctrl = loader.getController();
        initialize(ctrl.getTextField1(), ctrl.getTextArea1(), ctrl.getTextArea4(), ctrl.getGridPane1());
        initialize(ctrl.getTextField2(), ctrl.getTextArea2(), ctrl.getTextArea5(), ctrl.getGridPane2());
        compare(ctrl.getTextField1(), ctrl.getTextField2(), ctrl.getTextArea3());
        compare(ctrl.getTextField2(), ctrl.getTextField1(), ctrl.getTextArea3());

        stage.setTitle("Boolzer");
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setScene(scene);
        stage.show();
    }

    private void initialize(TextField textField, Label textArea1, Label textArea2, GridPane gridPane) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            gridPane.getChildren().clear();

            try {
                Expression expression = new Expression(textField.getText());
                textArea1.setText(expression.getExpression());
                textArea1.setStyle("-fx-text-fill: black;");

                if (expression.size() == 1) {
                    textArea2.setText(String.valueOf(expression.size()) + " logic gate");
                } else {
                    textArea2.setText(String.valueOf(expression.size()) + " logic gates");
                }

                TruthTable truthTable = expression.getTruthTable();
                for (int x = 0; x < truthTable.getVariables().size(); x++) {
                    Label label = new Label(truthTable.getVariables().get(x).getLabel());
                    label.setMinSize(25, 25);
                    label.setStyle("-fx-font-weight: bold;");
                    gridPane.add(label, x, 0);
                }
                Label label = new Label("Out");
                label.setMinSize(25, 25);
                label.setStyle("-fx-font-weight: bold;");
                gridPane.add(label, truthTable.getVariables().size(), 0);

                for (int y = 0; y < truthTable.getTable().size(); y++) {
                    for (int x = 0; x < truthTable.getTable().get(y).size(); x++) {
                        Label label2 = new Label(String.valueOf(truthTable.getTable().get(y).get(x)));
                        label2.setMinSize(25, 17);
                        label2.setMaxSize(25, 17);
                        if (truthTable.getTable().get(y).get(truthTable.getTable().get(y).size() - 1) == '0') {
                            label2.setStyle("-fx-text-fill: gray;");
                        } else {
                            label2.setStyle("-fx-text-fill: black;");
                        }
                        gridPane.add(label2, x, y + 1);
                    }
                }
            } catch (IllegalArgumentException e) {
                textArea1.setStyle("-fx-text-fill: red;");
                textArea1.setText(e.getMessage());
                textArea2.setText("");
            }
        });
    }

    private void compare(TextField textArea1, TextField textArea2, Label textArea3) {
        textArea1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(textArea1.getText().isEmpty() || textArea2.getText().isEmpty())) {
                try {
                    Expression expression1 = new Expression(textArea1.getText());
                    Expression expression2 = new Expression(textArea2.getText());

                    if (expression1.isEquivalentWith(expression2)) {
                        textArea3.setText("The two expressions are equivalent");
                        textArea3.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

                    } else {
                        textArea3.setText("The two expressions are not equivalent");
                        textArea3.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                textArea3.setText("");
            }
        });
    }

}
