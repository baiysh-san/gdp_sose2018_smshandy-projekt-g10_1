package smsHandy.view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.Provider;

public class CreateProviderController {
    @FXML
    private TextField nameField;
    @FXML
    private Button okButton;
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private void handleOKButton() {
        String providerName = nameField.getText().trim();
        if (!providerName.equals("")) {
            for (Provider provider: mainApp.getProviders()) {
                if (provider.getName().equals(providerName)) {
                    showAlert("Provider with this name already exists!", "");
                    return;
                }
            }
            mainApp.getProviders().add(new Provider(providerName));
            closeWindow();
        } else {
            showAlert("No provider name given!", "Please give provider name.");
        }

    }
    @FXML
    private void handleCancelButton() {
        closeWindow();
    }
    private void closeWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getStage());
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
