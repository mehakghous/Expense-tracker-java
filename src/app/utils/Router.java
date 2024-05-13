package app.utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class Router {
    private Router(){}

    public static void navigate(Node node, URL path) throws IOException {
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(load(path));
        stage.setScene(scene);
    }

    public static <T> T  load(URL path) throws IOException {
        FXMLLoader loader = new FXMLLoader(path);
        return loader.load();
    }



}
