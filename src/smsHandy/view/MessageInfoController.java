package smsHandy.view;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import smsHandy.model.Message;

public class MessageInfoController {
   @FXML
   private TextArea contentArea;
   
   @FXML
   private Text fromText;
   @FXML
   private Text toText;
   @FXML 
   private Text dateText;
   @FXML
   private Button cancelButton;
   
   
   public void setSettings(Message message) {
        contentArea.setText(message.getContent());
        fromText.setText(message.getFrom());
        toText.setText(message.getTo());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateString = dateFormat.format(message.getDate());
        dateText.setText(dateString);
   }
   @FXML
   private void handleCacnelButton() {
       Stage stage = (Stage) cancelButton.getScene().getWindow();
       stage.close();
   }
}
