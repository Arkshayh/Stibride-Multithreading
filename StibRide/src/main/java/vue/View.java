package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import présentateur.Presenter;

/**
 * It's a JavaFX application that loads a FXML file and sets the controller of that FXML file to a new instance of the
 * ControllerView class
 */
public class View extends Application {
    private Presenter presenter;

    public View(Presenter presenter){
        this.presenter = presenter;
    }

    /**
     * The `start` function is called by the JavaFX runtime when the application is started. It loads the FXML file,
     * creates a new instance of the controller, and sets the controller's presenter to the presenter that was passed in to
     * the constructor
     *
     * @param stage The stage is the window that will be displayed.
     */
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/resources/fxml/view.fxml"));

        loader.setController(new ControllerView(presenter));
        Parent root = loader.load();
        ControllerView ctrl = loader.getController();

        presenter.setView(ctrl);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
