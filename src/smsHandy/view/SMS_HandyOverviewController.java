package smsHandy.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import smsHandy.MainApp;
import smsHandy.model.Main;
import smsHandy.model.Provider;
import javafx.scene.control.TableView;
import smsHandy.model.SmsHandy;

import java.io.IOException;

public class SMS_HandyOverviewController {
    @FXML
    private TableView<Provider> providerTableView;
    @FXML
    private TableColumn<Provider, String> providerNameColumn;

//    @FXML
//    private TextField createProviderTextField;

    private MainApp mainApp;
    public SMS_HandyOverviewController() {

    }
    @FXML
    private void initialize() {
        providerNameColumn.setCellValueFactory(cellValue -> cellValue.getValue().getNameProperty());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        providerTableView.setItems(mainApp.getProviders());
    }
    @FXML
    private void handleNewProviderButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProvider.fxml"));
        try {
            Parent root = fxmlLoader.load();
            CreateProviderController controller = fxmlLoader.getController();
            controller.setMainApp(mainApp);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @FXML
//    private void handleCreateProviderOKButton() {
//        String providerName = createProviderTextField.getText();
//        mainApp.getProviders().add(new Provider(providerName));
//    }
}
