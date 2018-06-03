package smsHandy;

import java.io.IOException;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;
import smsHandy.model.TariffPlanSmsHandy;
import smsHandy.view.SMS_HandyOverviewController;

/**
 * Main class of program.
 */
public class MainApp extends Application{
    private Stage stage;
    private BorderPane rootPane;
    private ObservableList<Provider> providers = FXCollections.observableArrayList();
    private ObservableList<SmsHandy> handies = FXCollections.observableArrayList();

    /**
     * Constructor of MainApp. Here we create providers and handies.
     * Providers and handies must be added to relevant lists.
     * @throws Exception
     */
    public MainApp() throws Exception {
        Provider nettoCOM = new Provider("nettoCOM");
        Provider o2 = new Provider("O2");
        Provider beeline = new Provider("Beeline");

        providers.add(nettoCOM);
        providers.add(o2);
        providers.add(beeline);

        SmsHandy handy1 = new PrepaidSmsHandy("0312", nettoCOM);
        SmsHandy handy2 = new PrepaidSmsHandy("0313", nettoCOM);
        SmsHandy handy3 = new TariffPlanSmsHandy("0555", beeline);
        handy1.sendSms("0555", "LALALAdddddddddddddddddddddddddddddddddddddddddddddnsdfsdf");
        handy2.sendSms("0555", "LALALA2");

        handies.add(handy1);
        handies.add(handy2);
        handies.add(handy3);
    }

    /**
     * TODO
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("SMS-Handy-App");
        this.stage.setResizable(false);
        this.stage.getIcons().add(new Image("file:resources/images/phone.png"));
        showRootPanel();
        showSmsHandyOverview();
    }

    /**
     * This method shows our RootPanel - RootPanel.fxml.
     */
    public void showRootPanel() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootPanel.fxml"));
            rootPane = loader.load();
            Scene scene  = new Scene(rootPane, 650,400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method shows SMS-HandyOverview.fxml.
     * After we loaded fxml file, we added it to rootPanel;
     * Then we loaded controller of SMS-HandyOverview.
     * Also we set MainApp and 'Settings' to controller.
     * The method setSettings of SMS_HandyOverviewController sets initial data to tableViews.
     */
    public void showSmsHandyOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SMS-HandyOverview.fxml"));
            AnchorPane smsHandyOverview = (AnchorPane) loader.load();
            rootPane.setCenter(smsHandyOverview);

            SMS_HandyOverviewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method of the program.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns ObservableList of SmsHandys that relate to provider, we have given.
     * @param provider object of provider, which smsHandys we want to receive.
     * @return list of smsHandys that relate to provider, we have given.
     */
    public ObservableList<SmsHandy> getHandysByProvider(Provider provider) {
        ObservableList<SmsHandy> providerHandys = FXCollections.observableArrayList();
        handies.forEach(smsHandy -> {
            if (smsHandy.getProvider() == provider)
                providerHandys.add(smsHandy);
        });
        return providerHandys;
    }

    /**
     * Shows alert window.
     * @param headerText e.g. SMS-Handy is not selected
     * @param contentText e.g. Please select SMS-Handy
     */
    public void showAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    public ObservableList<Provider> getProviders() {
        return providers;
    }

    public ObservableList<SmsHandy> getHandies() {
        return handies;
    }

}
