package smsHandy.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.Provider;
import javafx.scene.control.TableView;

import java.io.IOException;

public class SMS_HandyOverviewController {
    @FXML
    private TableView<Provider> providerTableView;
    @FXML
    private TableColumn<Provider, String> providerNameColumn;

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

}
