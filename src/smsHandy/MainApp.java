package smsHandy;

import java.io.IOException;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;
import smsHandy.model.TariffPlanSmsHandy;
import smsHandy.view.SMS_HandyOverviewController;

public class MainApp extends Application{
    private Stage stage; 
    private BorderPane rootPane;
    private ObservableList<Provider> providers = FXCollections.observableArrayList();
    private ObservableList<SmsHandy> handies = FXCollections.observableArrayList();
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

        handies.add(handy1);
        handies.add(handy2);
        handies.add(handy3);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // TODO Auto-generated method stub
        this.stage = stage;
        this.stage.setTitle("SMS-Handy-App");
        this.stage.setResizable(false);
        this.stage.getIcons().add(new Image("file:resources/images/phone.png"));
        showRootPanel();
        showSmsHandyOverview();
        //showMessageOverview();
        //showProviderOverview();
        //showHandyOverview();
    }
    
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

//    public void showProviderOverview() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("view/ProviderOverview.fxml"));
//            AnchorPane providerOverview = loader.load();
//            SMS_HandyOverviewController rootController = loader.getController();
//            rootController.setMainApp(this);
//            rootPane.setLeft(providerOverview);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void showHandyOverview() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("view/HandyOverview.fxml"));
//            AnchorPane handyOverview = loader.load();
//            rootPane.setCenter(handyOverview);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void showMessageOverview() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("view/MessageOverview.fxml"));
//            AnchorPane messageOverview = loader.load();
//            rootPane.setRight(messageOverview);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public ObservableList<Provider> getProviders() {
        return providers;
    }

    public ObservableList<SmsHandy> getHandies() {
        return handies;
    }

    public Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public ObservableList<SmsHandy> getHandysByProvider(Provider provider) {
        ObservableList<SmsHandy> providerHandys = FXCollections.observableArrayList();
        handies.forEach(smsHandy -> {
            if (smsHandy.getProvider() == provider)
                providerHandys.add(smsHandy);
        });
        return providerHandys;
    }

}
