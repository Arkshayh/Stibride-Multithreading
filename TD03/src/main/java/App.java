import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;

/**
 * It creates a model and a controller, and then tells the controller to start the application
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        Controller controller = new Controller(model);
        controller.start(primaryStage);
    }
}
