package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import présentateur.Presenter;

/**
 * It's the entry point of the application
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
        /*
        try{
            ConfigManager.getInstance().load();
            String dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);

            StationsRepository stationsRepository = new StationsRepository();
            List<StationsDto> listeDesStations =stationsRepository.getAll();

            for (int i = 0; i < listeDesStations.size(); i++) {
                System.out.print("\t" + listeDesStations.get(i).getKey());
                System.out.print("\t" + listeDesStations.get(i).getName());
                System.out.println("");
            }

            System.out.println("-----------------------");

            LinesRepository linesRepository = new LinesRepository();
            List<LinesDto> listeDesLignes = linesRepository.getAll();

            for (int i = 0; i < listeDesLignes.size(); i++) {
                System.out.print("\t" + listeDesLignes.get(i).getKey());
                System.out.println("");
            }
Q
        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        //launch(args);
         */
    }

    /**
     * The start function creates a model, creates a presenter, and tells the presenter to start the stage.
     *
     * @param stage The stage is the window that will be displayed.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        Presenter presenter = new Presenter(model);
        presenter.start(stage);
    }
}
