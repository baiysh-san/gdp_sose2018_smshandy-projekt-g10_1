package smsHandy.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;

/**
 * Controller of ChangeProvider.fxml.
  */
public class ChangeProviderController {
    @FXML
    private Text numberText;
    @FXML
    private Button okButton;
    @FXML
    private MenuButton providerMenu;
    private MainApp mainApp;
    private SmsHandy smsHandy;

    /**
     * This method fills numberText, providerMenu with actual data.
     * @param smsHandy
     */
    public void setSettings(SmsHandy smsHandy) {
        this.smsHandy = smsHandy;
        numberText.setText(smsHandy.getNumber());
        providerMenu.getItems().clear();
        providerMenu.setText(smsHandy.getProvider().getName());
        mainApp.getProviders().forEach(provider -> providerMenu.getItems().add(new MenuItem(provider.getName())));
        providerMenu.getItems().forEach(menuItem -> menuItem.setOnAction(event -> providerMenu.setText(menuItem.getText())));
    }

    /**
     * Reaction to an OK button.
     */
    @FXML
    private void handleOKButton() {
        Provider provider = null;
        for (Provider p : mainApp.getProviders()) {
            if (p.getName().equals(providerMenu.getText())) {
                provider = p;
            }
        }
        smsHandy.changeProvider(provider);
        closeWindow();
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

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
