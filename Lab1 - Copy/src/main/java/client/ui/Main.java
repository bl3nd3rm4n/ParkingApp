package client.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.services.Subject;

import java.net.URL;
import java.sql.Timestamp;` `

@SuppressWarnings("Duplicates")
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
        URL resource = getClass().getResource("/home/internship/Desktop/Lab1 - Copy/src/main/resources/form.fxml");
        System.out.println(resource);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(resource);
        Pane myPane = (Pane) loader.load();
        Scene myScene = new Scene(myPane, 1000, 400);
        primaryStage.setScene(myScene);
        primaryStage.setTitle("Log in");
        primaryStage.show();}
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
