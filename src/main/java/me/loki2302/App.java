package me.loki2302;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ClickCountView.fxml"));
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("The Ultimate JavaFX Click Counter");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(App.class.getResource("/Style.css").toExternalForm());
        primaryStage.show();
    }
}
