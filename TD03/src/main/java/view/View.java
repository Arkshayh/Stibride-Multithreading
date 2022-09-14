package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;

/**
 * The View class is the JavaFX application class that loads the FXML file and sets the controller.
 */
public class View extends Application {
    private Controller controller;
    private Model model;

    public View(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
    }

    /**
     * The controllerView is set in the controller, and the controller is set in the controllerView
     *
     * @param stage The stage that the scene is set on.
     */
    public void start(Stage stage) throws Exception {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/fxml/TEST.fxml"));

        loader.setController(new ControllerView(controller, model));
        Parent root = loader.load();
        ControllerView ctrl = loader.getController();

        controller.setControllerView(ctrl);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
