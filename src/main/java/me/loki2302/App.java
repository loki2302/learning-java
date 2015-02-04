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
        /*primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        Scene s = new Scene(root, 300, 250);


        primaryStage.setScene(s);
        s.getStylesheets().add(App.class.getResource("/Style.css").toExternalForm());

        primaryStage.show();*/

        Parent root = FXMLLoader.load(getClass().getResource("/Hello.fxml"));
        Scene s = new Scene(root, 500, 300);
        primaryStage.setTitle("Hello");
        primaryStage.setScene(s);
        s.getStylesheets().add(App.class.getResource("/Style.css").toExternalForm());
        primaryStage.show();
    }

}