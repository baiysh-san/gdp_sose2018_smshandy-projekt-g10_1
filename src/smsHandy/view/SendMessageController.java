package smsHandy.view;



import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import smsHandy.MainApp;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.SmsHandy;
import smsHandy.model.TariffPlanSmsHandy;

/**
 * 
 * Controller of SendMessage.fxml
 */
public class SendMessageController {
    
    @FXML
    private TextField toField;
    @FXML
    private TextArea messageArea;
    @FXML
    private Button cancelButton;
    @FXML
    private ImageView imageV;
    
    private MainApp mainApp;
    private SmsHandy currentHandy;
    private String to;
    private String content;
    PrepaidSmsHandy prepaid;
    TariffPlanSmsHandy tariff;
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    /**
     * Check is handy prepaid or tariff
     * @param handy -> currentHandy
     */
    public void setSettings(SmsHandy handy) {
        currentHandy = handy;
        if(currentHandy.getType() == "Prepaid") {
            prepaid = (PrepaidSmsHandy) handy;
        } else {
            tariff = (TariffPlanSmsHandy) handy;
        }
    }
    /**
     * Reaction to sendButton
     */
    @FXML
    private void handleSendButton() {
        to = toField.getText();
        /*if(messageArea.getText() == null || messageArea.getText() =="" || messageArea.getText() ==" ") {
            runAnimation("no.png");
            mainApp.showAlert("Message must be not empty", "Please wright at least 1 character!");
        }*/
        content = messageArea.getText();
        try {
            currentHandy.sendSms(to, content);
            if(tariff != null) {
                if(!tariff.canSendSms()) {
                    runAnimation("no.png");
                    mainApp.showAlert("The limit has expired", "Please upload money!");
                    return;
                }
            } else {
                if (!prepaid.canSendSms()) {
                    runAnimation("no.png");
                    mainApp.showAlert("The limit has expired", "Please upload money!");
                    return;
                }
            }
            runAnimation("gal.png");
        } catch (NumberFormatException e) {
            runAnimation("no.png");
            mainApp.showAlert("This number doesn't exist", "Please choose an existing number!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Reaction to cancelButton
     */
    @FXML
    private void handleCacnelButton() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    /*
     * Runs animation 
     */
    public void runAnimation(String imageName) {
    Image image = new Image("file:resources/images/"+imageName);
    imageV.setImage(image);
    FadeTransition ft = new FadeTransition();
    ft.setNode(imageV);
    ft.setDuration(new Duration(2000));
    ft.setFromValue(0.0);
    ft.setToValue(1.0);
    ft.setCycleCount(6);
    ft.setAutoReverse(true);
    ft.play();
    //iv.setOnMouseClicked(me -> ft.play());
    }
}
