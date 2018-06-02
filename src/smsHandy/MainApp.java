package smsHandy;

import java.io.IOException;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import smsHandy.model.Provider;
import smsHandy.view.RootController;

public class MainApp extends Application{
    private Stage stage; 
    private BorderPane rootPane;
    private ObservableList<Provider> providers = FXCollections.observableArrayList();
    public MainApp() {
        providers.add(new Provider("nettoCOM"));
        providers.add(new Provider("O2"));
        providers.add(new Provider("Beeline"));
    }
    @Override
    public void start(Stage stage) throws Exception {
        // TODO Auto-generated method stub
        this.stage = stage;
        showRootPanel();
        showMessageOverview();
        showProviderOverview();
        showHandyOverview();
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
    public void showProviderOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProviderOverview.fxml"));
            AnchorPane providerOverview = loader.load();
            RootController rootController = loader.getController();
            rootController.setMainApp(this);
            rootPane.setLeft(providerOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showHandyOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/HandyOverview.fxml"));
            AnchorPane handyOverview = loader.load();
            rootPane.setCenter(handyOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showMessageOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MessageOverview.fxml"));
            AnchorPane messageOverview = loader.load();
            rootPane.setRight(messageOverview);
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Provider> getProviders() {
        return providers;
    }

    public Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
