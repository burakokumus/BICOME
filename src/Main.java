
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Resources/Views/StartStage.fxml"));
        primaryStage.setTitle("B.I.C.O.M.E");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}
