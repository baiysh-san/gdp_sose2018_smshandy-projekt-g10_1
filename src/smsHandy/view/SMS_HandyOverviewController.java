package smsHandy.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.Provider;
import javafx.scene.control.TableView;
import smsHandy.model.SmsHandy;

import javafx.scene.text.Text;
import smsHandy.model.TariffPlanSmsHandy;

import java.io.IOException;

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
    public SMS_HandyOverviewController() {

    }
    
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
    public void setSettings() {
        providerTableView.setItems(mainApp.getProviders());
        smsHandyTableView.setItems(mainApp.getHandies());
        numberOfHandysText.setText(getNumberOfAllHandys());
        showEmptyInfo();

    }
    @FXML
    private void handleNewProviderButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProvider.fxml"));
        try {
            Parent root = fxmlLoader.load();
            CreateProviderController controller = fxmlLoader.getController();
            controller.setMainApp(mainApp);
            Stage stage = new Stage();
            stage.setTitle("Create provider");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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

    private String getNumberOfAllHandys() {
        return String.valueOf(mainApp.getHandies().size());
    }
    private void showHandysOfCurrentProvider(Provider provider) {
        smsHandyTableView.setItems(mainApp.getHandysByProvider(provider));
        currentProviderText.setText(provider.getName());
        numberOfHandysText.setText(String.valueOf(mainApp.getHandysByProvider(provider).size()));
    }
    private void showEmptyInfo() {
        textNumber.setText("");
        textTarif.setText("");
        textProvider.setText("");
        textBalance.setText("");
        typeOfBalance.setText("");
        tariff.setText("");
    }
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
    @FXML
    private void showAllHandys() {
        smsHandyTableView.setItems(mainApp.getHandies());
        currentProviderText.setText("All");
        numberOfHandysText.setText(getNumberOfAllHandys());
    }

}
