package smsHandy.view;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import smsHandy.MainApp;
import smsHandy.model.Message;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class InboxController {
    @FXML
    private TableView <Message> inboxTableView;
    @FXML
    private TableColumn<Message, String> contentColumn;
    @FXML
    private TableColumn<Message, String> fromColumn;
    @FXML
    private TableColumn<Message, String> dateColumn;
    
    
    
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
    
    
}
