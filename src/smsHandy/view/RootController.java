package smsHandy.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import smsHandy.MainApp;
import smsHandy.model.Main;
import smsHandy.model.Provider;
import javafx.scene.control.TableView;

public class RootController {
    @FXML
    private TableView<Provider> providerTableView;
    @FXML
    private TableColumn<Provider, String> providerNameColumn;
    private MainApp mainApp;
    public RootController() {

    }
    @FXML
    private void initialize() {
        providerNameColumn.setCellValueFactory(cellValue -> cellValue.getValue().getNameProperty());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        providerTableView.setItems(mainApp.getProviders());
    }
}
