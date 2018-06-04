package smsHandy.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    
    public void setSettings(PrepaidSmsHandy handy) {
        this.handy = handy;
        depositField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    depositField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    
    @FXML
    private void handleUploadButton() {
        try {
            handy.deposit(Integer.parseInt(depositField.getText()));
            handleCancelButton();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCancelButton() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
}
