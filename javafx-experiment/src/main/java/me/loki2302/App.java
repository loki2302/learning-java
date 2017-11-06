package me.loki2302;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL viewResourceUrl = getClass().getResource("/ClickCountView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(viewResourceUrl);
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                System.out.println("ControllerFactory called!");
                return new ClickCountController(123);
            }
        });

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("The Ultimate JavaFX Click Counter");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(App.class.getResource("/Style.css").toExternalForm());
        primaryStage.show();
    }
}
