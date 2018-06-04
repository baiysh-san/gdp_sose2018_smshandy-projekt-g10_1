package smsHandy.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.Provider;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import smsHandy.model.SmsHandy;

import javafx.scene.text.Text;
import smsHandy.model.TariffPlanSmsHandy;

import java.io.IOException;

/**
 * Controller of SMS_HandyOverviewController.fxml.
 */
public class SMS_HandyOverviewController {
    @FXML
    private TableView<Provider> providerTableView;
    @FXML
    private TableColumn<Provider, String> providerNameColumn;

    @FXML
    private TableView<SmsHandy> smsHandyTableView;
    @FXML
    private TableColumn<SmsHandy, String> handyNumberColumn;
    @FXML
    private TableColumn<SmsHandy, String> handyTypeColumn;
    @FXML
    private Text currentProviderText;
    @FXML
    private Text numberOfHandysText;
    @FXML
    private Text textNumber;
    @FXML
    private Text tariff;
    @FXML
    private Text textTarif;
    @FXML
    private Text textProvider;
    @FXML
    private Text typeOfBalance;
    @FXML
    private Text textBalance;


    private MainApp mainApp;

    /**
     * TODO
     */
    @FXML
    private void initialize() {
        providerNameColumn.setCellValueFactory(cellValue -> cellValue.getValue().getNameProperty());
        handyNumberColumn.setCellValueFactory(cellValue -> cellValue.getValue().getNumberProperty());
        handyTypeColumn.setCellValueFactory(cellValue -> cellValue.getValue().getTypeProperty());
        providerTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showHandysOfCurrentProvider(newValue);
                    }
                });
        smsHandyTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->{
                    if (newValue != null) {
                        showInfoOfCurrentHandy(newValue);
                    } else {
                        showEmptyInfo();
                    }
                });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Fills providerTableView, smsHandyTableView with initial data.
     */
    public void setSettings() {
        providerTableView.setItems(mainApp.getProviders());
        smsHandyTableView.setItems(mainApp.getHandies());
        numberOfHandysText.setText(getNumberOfAllHandys());
        showEmptyInfo();

    }

    /**
     * Reaction to a newProviderButton click.
     */
    @FXML
    private void handleNewProviderButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProvider.fxml"));
        try {
            Parent root = fxmlLoader.load();
            CreateProviderController controller = fxmlLoader.getController();
            controller.setMainApp(mainApp);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("file:resources/images/phone.png"));
            stage.setTitle("Create provider");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reaction to a newHandyButton click.
     */
    @FXML
    private void handleNewHandyButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateSMSHandy.fxml"));
        try {
            Parent root = fxmlLoader.load();
            CreateSMSHandyController controller = fxmlLoader.getController();
            controller.setMainApp(mainApp);
            controller.setSettings();
            Stage stage = new Stage();
            stage.setTitle("Create SMS-Handy");
            stage.getIcons().add(new Image("file:resources/images/phone.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Reaction to a changeProviderButton click.
     */
    @FXML
    private void handleChangeProviderButton() {
        int selectedIndex = smsHandyTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangeProvider.fxml"));
            try {
                Parent root = fxmlLoader.load();
                ChangeProviderController controller = fxmlLoader.getController();
                controller.setMainApp(mainApp);
                controller.setSettings(smsHandyTableView.getSelectionModel().getSelectedItem());
                Stage stage = new Stage();
                stage.setTitle("Change provider");
                stage.getIcons().add(new Image("file:resources/images/phone.png"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainApp.showAlert("SMS-Handy is not selected", "Please select SMS-Handy");
        }
    }
    /**
     * Reaction to a deleteProviderButton click.
     */
    @FXML
    private void handleDeleteProviderButton() {
        int selectedIndex = providerTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Provider provider = providerTableView.getSelectionModel().getSelectedItem();
            Provider.getProviderList().remove(provider);
            mainApp.getProviders().remove(provider);
            provider.removeAllHandys();
            mainApp.getHandies().removeIf(handy -> handy.getProvider() == provider);
        } else {
            mainApp.showAlert("Provider is not selected!", "Please select Provider!");
        }
    }
    /**
     * Reaction to a deleteHandyButton click.
     */
    @FXML
    private void handleDeleteHandyButton() {
        int selectedIndex = smsHandyTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            SmsHandy smsHandy = smsHandyTableView.getSelectionModel().getSelectedItem();
            mainApp.getProviders().forEach(provider -> {
                if (provider.hasHandy(smsHandy)) {
                    provider.removeHandy(smsHandy);
                }
            });
            mainApp.getHandies().remove(smsHandy);
        } else {
            mainApp.showAlert("SMS-Handy is not selected", "Please select SMS-Handy");
        }
    }
    /**
     * Reaction to a inboxButton click.
     */
    @FXML
    private void handleInboxButton() {
        int selectedIndex = smsHandyTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InboxOverview.fxml"));
            try {
                Parent root = fxmlLoader.load();
                InboxController controller = fxmlLoader.getController();
                SmsHandy handy =  smsHandyTableView.getSelectionModel().getSelectedItem();
                handy.listReceived();
                //controller.setMainApp(mainApp);
                controller.setSettings(handy);
                Stage stage = new Stage();
                stage.setTitle("Inbox");
                stage.getIcons().add(new Image("file:resources/images/phone.png"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainApp.showAlert("SMS-Handy is not selected", "Please select SMS-Handy");
        }
    }
    /**
     * Reaction to a sentButton click.
     */
    @FXML
    private void handleSentButton() {
        int selectedIndex = smsHandyTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SentOverview.fxml"));
            try {
                Parent root = fxmlLoader.load();
                SentController controller = fxmlLoader.getController();
                SmsHandy handy =  smsHandyTableView.getSelectionModel().getSelectedItem();
                handy.listReceived();
                //controller.setMainApp(mainApp);
                controller.setSettings(handy);
                Stage stage = new Stage();
                stage.setTitle("Sent");
                stage.getIcons().add(new Image("file:resources/images/phone.png"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainApp.showAlert("SMS-Handy is not selected", "Please select SMS-Handy");
        }
    }
    
    /**
     * Reaction to a SendSMSButton click.
     */
    @FXML
    private void handleSendSMSButton() {
        int selectedIndex = smsHandyTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SendMessage.fxml"));
            try {
                Parent root = fxmlLoader.load();
                SendMessageController controller = fxmlLoader.getController();
                SmsHandy handy =  smsHandyTableView.getSelectionModel().getSelectedItem();
                controller.setMainApp(mainApp);
                controller.setSettings(handy);
                Stage stage = new Stage();
                stage.setTitle("Send SMS");
                stage.getIcons().add(new Image("file:resources/images/phone.png"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainApp.showAlert("SMS-Handy is not selected", "Please select SMS-Handy");
        }
    }
    /**
     * Reaction to a DepositButton click.
     */
    @FXML
    private void handleDepositButton() {
        int selectedIndex = smsHandyTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if(!smsHandyTableView.getSelectionModel().getSelectedItem().getType().equals("Tariff plan")) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DepositOverview.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    DepositController controller = fxmlLoader.getController();
                    SmsHandy handy =  smsHandyTableView.getSelectionModel().getSelectedItem();
                    //controller.setMainApp(mainApp);
                    controller.setSettings((PrepaidSmsHandy)handy);
                    Stage stage = new Stage();
                    stage.setTitle("Deposit");
                    stage.getIcons().add(new Image("file:resources/images/phone.png"));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            } else {
                mainApp.showAlert("SMS-Handy is not prepaid!", "Please select only PrepraidSMSHandy to deposit!");
            }
            
        } else {
            mainApp.showAlert("SMS-Handy is not selected", "Please select SMS-Handy");
        }
    }

    /**
     * Returns number (Anzahl) of all SMS-Handys.
     * @return number (Anzahl) of all SMS-Handys.
     */
    private String getNumberOfAllHandys() {
        return String.valueOf(mainApp.getHandies().size());
    }

    /**
     *  Shows handys of the provider, we have given.
     * @param provider which list of handys we want to show.
     */
    private void showHandysOfCurrentProvider(Provider provider) {
        smsHandyTableView.setItems(mainApp.getHandysByProvider(provider));
        currentProviderText.setText(provider.getName());
        numberOfHandysText.setText(String.valueOf(mainApp.getHandysByProvider(provider).size()));
    }

    /**
     * Make all elements empty of AnchorPane that shows info about Sms-Handy.
     */
    private void showEmptyInfo() {
        textNumber.setText("");
        textTarif.setText("");
        textProvider.setText("");
        textBalance.setText("");
        typeOfBalance.setText("");
        tariff.setText("");
    }

    /**
     * Shows info of SMS-Handy, we have given.
     * @param smsHandy which info we want to show.
     */
    private void showInfoOfCurrentHandy(SmsHandy smsHandy) {
        tariff.setText("Tariff:");
        textNumber.setText(smsHandy.getNumber());
        textTarif.setText(smsHandy.getType());
        textProvider.setText(smsHandy.getProvider().getName());

        if (smsHandy.getType().equals("Tariff plan")) {
            TariffPlanSmsHandy tariffPlanSmsHandy = (TariffPlanSmsHandy) smsHandy;
            textBalance.setText(String.valueOf(tariffPlanSmsHandy.getRemainingFreeSms()));
            typeOfBalance.setText("Remaining free SMS:");
        }
        if (smsHandy.getType().equals("Prepaid")) {
            typeOfBalance.setText("Account balance:");
            textBalance.setText(String.valueOf(smsHandy.getProvider().getCreditForSmsHandy(smsHandy.getNumber())));
        }

    }

    /**
     * Shows all SMS-Handys.
     */
    @FXML
    private void showAllHandys() {
        smsHandyTableView.setItems(mainApp.getHandies());
        currentProviderText.setText("All");
        numberOfHandysText.setText(getNumberOfAllHandys());
    }

}
