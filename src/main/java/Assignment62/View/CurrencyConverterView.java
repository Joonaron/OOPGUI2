package Assignment62.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CurrencyConverterView extends Application {

    private Button convertButton;
    private Button addCurrencyButton;
    private TextField resultField;
    private TextField amountField;
    private ChoiceBox<String> sourceCurrencyBox;
    private ChoiceBox<String> resultCurrencyBox;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label amountLabel = new Label("Amount to convert: ");
        amountField = new TextField();
        HBox amountInput = new HBox(10, amountLabel, amountField);

        Label sourceCurrencyLabel = new Label("Source currency: ");
        sourceCurrencyBox = new ChoiceBox<>();
        sourceCurrencyBox.getItems().addAll("USD", "EUR", "GBP");
        VBox sourceCurrencySection = new VBox(5, sourceCurrencyLabel, sourceCurrencyBox);

        Label resultCurrencyLabel = new Label("Result currency: ");
        resultCurrencyBox = new ChoiceBox<>();
        resultCurrencyBox.getItems().addAll("USD", "EUR", "GBP");
        VBox resultCurrencySection = new VBox(5, resultCurrencyLabel, resultCurrencyBox);

        Label resultLabel = new Label("Converted amount: ");
        resultField = new TextField();
        resultField.setEditable(false);

        convertButton = new Button("CONVERT");
        addCurrencyButton = new Button("ADD CURRENCY");
        addCurrencyButton.setOnAction(event -> openAddCurrencyWindow());

        HBox buttonSection = new HBox(10, convertButton, addCurrencyButton);
        buttonSection.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                amountInput,
                sourceCurrencySection,
                resultCurrencySection,
                resultLabel,
                buttonSection
        );

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CurrencyConverter");
        primaryStage.show();
    }

    private void openAddCurrencyWindow() {
        Stage newStage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label abbreviationLabel = new Label("Abbreviation: ");
        TextField abbreviationField = new TextField();
        HBox abbreviationInput = new HBox(10, abbreviationLabel, abbreviationField);

        Label nameLabel = new Label("Currency Name: ");
        TextField nameField = new TextField();
        HBox nameInput = new HBox(10, nameLabel, nameField);

        Label rateLabel = new Label("Conversion Rate: ");
        TextField rateField = new TextField();
        HBox rateInput = new HBox(10, rateLabel, rateField);

        Button saveButton = new Button("SAVE");
        saveButton.setOnAction(event -> {
            String abbreviation = abbreviationField.getText();
            String name = nameField.getText();
            double rate = Double.parseDouble(rateField.getText());
            CurrencyModel newCurrency = new CurrencyModel(abbreviation, name, rate);
            new DAO().persist(newCurrency);
            newStage.close();
            updateCurrencies();
        });

        root.getChildren().addAll(abbreviationInput, nameInput, rateInput, saveButton);

        Scene scene = new Scene(root, 300, 200);
        newStage.setScene(scene);
        newStage.setTitle("Add New Currency");
        newStage.showAndWait();
    }

    private void updateCurrencies() {
        sourceCurrencyBox.getItems().clear();
        resultCurrencyBox.getItems().clear();
        List<CurrencyModel> currencies = new DAO().getAllCurrencies();
        for (CurrencyModel currency : currencies) {
            sourceCurrencyBox.getItems().add(currency.getAbbreviation());
            resultCurrencyBox.getItems().add(currency.getAbbreviation());
        }
    }

    public Button getConvertButton() {
        return convertButton;
    }

    public TextField getResultField() {
        return resultField;
    }

    public TextField getAmountField() {
        return amountField;
    }

    public ChoiceBox<String> getSourceCurrencyBox() {
        return sourceCurrencyBox;
    }

    public ChoiceBox<String> getResultCurrencyBox() {
        return resultCurrencyBox;
    }

    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
