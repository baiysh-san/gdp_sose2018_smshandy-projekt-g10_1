package smsHandy.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.Message;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class InboxController {
    @FXML
    private TableView <Message> inboxTableView;
    @FXML
    private TableColumn<Message, String> contentColumn;
    @FXML
    private TableColumn<Message, String> fromColumn;
    @FXML
    private TableColumn<Message, String> dateColumn;
    @FXML
    private Button openButton;
    @FXML
    private Button cancelButton;
    
    
    
    private MainApp mainApp;
    private SmsHandy smsHandy;
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setSettings(SmsHandy smsHandy) {
        this.smsHandy = smsHandy;
        ObservableList<Message> messages = FXCollections.observableArrayList(smsHandy.getReceived());
        inboxTableView.setItems(messages);
        contentColumn.setCellValueFactory(cellValue -> cellValue.getValue().getContentProperty());
        fromColumn.setCellValueFactory(cellValue -> cellValue.getValue().getFromProperty());
        dateColumn.setCellValueFactory(cellValue -> cellValue.getValue().getDatePropery());
        
    }
    @FXML
    private void handleOpenButton() {
        int selectedIndex = inboxTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageOverview.fxml"));
            try {
                Parent root = fxmlLoader.load();
                MessageInfoController controller = fxmlLoader.getController();
                Message message =  inboxTableView.getSelectionModel().getSelectedItem();
                controller.setSettings(message);
                Stage stage = new Stage();
                stage.setTitle("Message");
                stage.getIcons().add(new Image("file:resources/images/phone.png"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainApp.showAlert("Message is not selected", "Please select Message");
        }
    }
    @FXML
    private void handleCacnelButton() {
        Stage stage = (Stage) openButton.getScene().getWindow();
        stage.close();
    }
    
    
}
