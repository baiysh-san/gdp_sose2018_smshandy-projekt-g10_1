package smsHandy.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.PrepaidSmsHandy;

/**
 * 
 * Controller of DepositOverview.fxml
 *
 */
public class DepositController {
    
    @FXML
    private TextField depositField; 
    @FXML
    private Button cancelButton;
    
    PrepaidSmsHandy handy;
    private MainApp mainApp;
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    /**
     * set Handy
     * @param handy
     */
    public void setSettings(PrepaidSmsHandy handy) {
        this.handy = handy;
        depositField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, //Typing only numbers
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    depositField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    /**
     * Reaction to uploadButton
     */
    @FXML
    private void handleUploadButton() {
        try {
            int amount = Integer.parseInt(depositField.getText());
            if (amount > 0) {
                handy.deposit(Integer.parseInt(depositField.getText()));
                handleCancelButton();
            } else {
                mainApp.showAlert("Amount must be greater than 0!", "Please give me you money!!1");
                //throw new Exception("Amount must be greater than 0");
            }
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Reaction to cancelButton
     */
    @FXML
    private void handleCancelButton() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
}
