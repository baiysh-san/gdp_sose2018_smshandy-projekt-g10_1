package smsHandy.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;
import smsHandy.model.TariffPlanSmsHandy;

/**
 * Controller of CreateSMSHandy.fxml.
 */
public class CreateSMSHandyController {
    @FXML
    private Button okButton;
    @FXML
    private SplitMenuButton providerMenu;
    @FXML
    private TextField numberField;
    @FXML
    private RadioButton tariffRbutton;
    @FXML
    private RadioButton prepaidRbutton;
    @FXML
    private ToggleGroup planGroup;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * This method fills providerMenu with actual data.
     * Sets userData to radio buttons.
     */
    public void setSettings() {
        providerMenu.getItems().clear();
        providerMenu.setText("Choose provider");
        mainApp.getProviders().forEach(provider -> providerMenu.getItems().add(new MenuItem(provider.getName())));
        providerMenu.getItems().forEach(menuItem -> menuItem.setOnAction(event -> providerMenu.setText(menuItem.getText())));
        tariffRbutton.setUserData("Tariff");
        prepaidRbutton.setUserData("Prepaid");
    }

    /**
     * Reaction to OK button.
     */
    @FXML
    private void handleOKButton() {
        String number = numberField.getText().trim();
        Provider provider = null;
        if (!number.equals("")) {
            for (SmsHandy handy: mainApp.getHandies()) {
                if (handy.getNumber().equals(number)) {
                    mainApp.showAlert("SMS-Handy with this number already exists!", "");
                    return;
                }
            }
            if (!providerMenu.getText().equals("Choose provider")) {

                for (Provider p : mainApp.getProviders()) {
                    if (p.getName().equals(providerMenu.getText())) {
                        provider = p;
                    }
                }
                    try {
                        if (planGroup.getSelectedToggle().getUserData().equals("Tariff")) {
                            SmsHandy smsHandy = new TariffPlanSmsHandy(number, provider);
                            mainApp.getHandies().add(smsHandy);
                            closeWindow();
                        }
                        if (planGroup.getSelectedToggle().getUserData().equals("Prepaid")) {
                            SmsHandy smsHandy = new PrepaidSmsHandy(number, provider);
                            mainApp.getHandies().add(smsHandy);
                            closeWindow();
                        }

                    } catch (Exception e) {
                        mainApp.showAlert("Wrong number format!", "Please input correct number!");
                    }

            } else {
                mainApp.showAlert("Provider is not chosen!", "Please choose provider.");
            }

        } else {
            mainApp.showAlert("No number given!", "Please give number.");
        }

    }

    /**
     * Reaction to a cancel button.
     */
    @FXML
    private void handleCancelButton() {
        closeWindow();
    }
    /**
     * Closes window.
     */
    private void closeWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
