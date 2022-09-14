package présentateur;

import dto.FavouriteDto;
import dto.StationsDto;
import javafx.stage.Stage;
import model.Model;
import model.Node;
import observer.Observable;
import observer.Observer;
import vue.ControllerView;
import vue.View;

import java.util.ArrayList;
import java.util.List;

/**
 * The Presenter class is the class that contains the code for the controller
 */
public class Presenter implements Observer {
    private ControllerView view;
    private Model model;

    public Presenter(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    /**
     * "The start function is called when the program is started. It creates a new View object and calls the start function
     * of that object."
     *
     * The View class is the class that contains the code for the GUI. The View class is a subclass of the
     * javafx.application.Application class. The start function is a function that is defined in the
     * javafx.application.Application class. The start function is called when the program is started
     *
     * @param stage The stage is the window that the application will be displayed in.
     */
    public void start(Stage stage) throws Exception {
        View view = new View(this);
        view.start(stage);
    }

    /**
     * This function sets the view of the controller to the view passed in.
     *
     * @param view The view that the controller is associated with.
     */
    public void setView(ControllerView view) {
        this.view = view;
    }

    /**
     * The function takes in two strings, the departure and arrival stations, and calls the model's getShortestPath
     * function to get the shortest path between the two stations
     *
     * @param depart The departure station
     * @param arrive the name of the destination station
     */
    public void searchOnClick(String depart, String arrive){
        Thread thread = new Thread(() -> model.getShortestPath(depart, arrive));
        thread.start();

    }

    /**
     * It adds a favorite to the database
     *
     * @param start the starting point of the route
     * @param end the end address
     * @param nomFavoris the name of the favorite
     */
    public void addFavOnClick(String start, String end, String nomFavoris){
        model.addToDBFav(start, end, nomFavoris);
    }

    /**
     * It returns a list of strings.
     *
     * @return A list of strings
     */
    public List<String> getFav(){
        List<FavouriteDto> favouriteDtos = model.generetateFavRepository();
        List<String> favString = new ArrayList<>();

        for (int i = 0; i < favouriteDtos.size(); i++) {
            favString.add(favouriteDtos.get(i).getName());
        }

        return favString;

    }

    /**
     * When the observable object changes, update the table columns with the new list of nodes.
     *
     * @param observable The Observable object.
     * @param arg The object that is passed to the update method.
     */
    @Override
    public void update(Observable observable, Object arg) {
        view.updateTableColumns((List<Node>) arg);
    }

    /**
     * It takes a list of stations, converts them to strings, and returns them
     *
     * @return A list of strings.
     */
    public List<String> getStations(){
        List<StationsDto> stations =  model.generateStationRepository();
        List<String> stationsToString = new ArrayList<>();
        for (int i = 0; i < stations.size(); i++) {
            stationsToString.add(stations.get(i).getName());
        }
        return stationsToString;
    }

    /**
     * This function takes in a string and returns an array of strings
     *
     * @param name The name of the station you want to get the information for.
     * @return An array of strings.
     */
    public String[] getStationName(String name){
        return model.getStationName(name);
    }

    /**
     * This function takes in a new name and the current name of a favorite and modifies the name of the favorite in the
     * database
     *
     * @param name the new name of the favorite
     * @param currentName The name of the favorite you want to modify.
     */
    public void modifyFavName(String name, String currentName){
        model.modifyFavName(name, currentName);
    }

    /**
     * This function deletes a favorite name from the database
     *
     * @param name The name of the favorite you want to delete.
     */
    public void deleteFavName(String name){
        model.deleteFav(name);
    }

}
