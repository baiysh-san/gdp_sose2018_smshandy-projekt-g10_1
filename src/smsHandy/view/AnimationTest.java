package smsHandy.view;

import javafx.animation.FadeTransition;

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javafx.stage.Stage;


public class AnimationTest extends Application {

    @Override
    public void start(Stage primaryStage)
    {
       ImageView iv = new ImageView();
       Image image = new Image("file:resources/images/gal.png");
       iv.setImage(image);

       FadeTransition ft = new FadeTransition();
       ft.setNode(iv);
       ft.setDuration(new Duration(2000));
       ft.setFromValue(0.0);
       ft.setToValue(1.0);
       ft.setCycleCount(6);
       ft.setAutoReverse(true);

       iv.setOnMouseClicked(me -> ft.play());

       Group root = new Group();
       root.getChildren().add(iv);
       Scene scene = new Scene(root, image.getWidth(), image.getHeight());

       primaryStage.setTitle("FadeTransition Demo");
       primaryStage.setScene(scene);
       primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
