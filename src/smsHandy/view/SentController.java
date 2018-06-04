package smsHandy.view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.Message;
import smsHandy.model.SmsHandy;
/**
 * Controller of SentOverview.fxml
 *
 */
public class SentController {
    @FXML
    private TableView <Message> sentTableView;
    @FXML
    private TableColumn<Message, String> contentColumn;
    @FXML
    private TableColumn<Message, String> toColumn;
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
    /**
     * Fills SentTableView with data from smsHandy
     * @param smsHandy
     */
    public void setSettings(SmsHandy smsHandy) {
        this.smsHandy = smsHandy;
        ObservableList<Message> messages = FXCollections.observableArrayList(smsHandy.getSent());
        sentTableView.setItems(messages);
        contentColumn.setCellValueFactory(cellValue -> cellValue.getValue().getContentProperty());
        toColumn.setCellValueFactory(cellValue -> cellValue.getValue().getToProperty());
        dateColumn.setCellValueFactory(cellValue -> cellValue.getValue().getDatePropery());
        
    }
    /**
     * Reaction to openButton
     */
    @FXML
    private void handleOpenButton() {
        int selectedIndex = sentTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageOverview.fxml"));
            try {
                Parent root = fxmlLoader.load();
                MessageInfoController controller = fxmlLoader.getController();
                Message message =  sentTableView.getSelectionModel().getSelectedItem();
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
    /**
     * Reaction to cancelButton
     */
    @FXML
    private void handleCancelButton() {
        Stage stage = (Stage) openButton.getScene().getWindow();
        stage.close();
    }
}
