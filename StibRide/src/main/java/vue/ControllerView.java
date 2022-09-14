package vue;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import model.Node;
import org.controlsfx.control.SearchableComboBox;
import présentateur.Presenter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * It's the controller for the GUI
 */
public class ControllerView implements Initializable {
    private Presenter presenter;

    @FXML
    private Button displayFavButton;

    @FXML
    private Label labelNomDuFav;

    @FXML
    private TextField textFieldFav;

    @FXML
    private Label labelError;

    @FXML
    private Button addFavoris;

    @FXML
    private Button deleteFavButton;

    @FXML
    private SearchableComboBox<String> favMenuButton;

    @FXML
    private Button modifyFavBtn;

    @FXML
    private SearchableComboBox<String> endButton;

    @FXML
    private TableColumn<DetailsForTable, Integer> linesColumn;

    @FXML
    private Button searchButton;

    @FXML
    private SearchableComboBox<String> startButton;

    @FXML
    private TableColumn<DetailsForTable, String> stationsColumn;

    @FXML
    private TableView<DetailsForTable> tableView;

    public ControllerView(Presenter presenter){
        this.presenter = presenter;
    }

    /**
     * It initializes the GUI
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle that was specified in the FXMLLoader.load() method.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialisation start");

        stationsColumn.setCellValueFactory(new PropertyValueFactory<>("Stations"));
        linesColumn.setCellValueFactory(new PropertyValueFactory<>("Lines"));

        labelError.setTextFill(Color.web("#d60000"));
        labelError.setStyle("-fx-font-weight: bold");
        labelError.setText("Sélectionner une origine et une destination.");
        labelError.setVisible(false);

        setUpSearchButton();
        setUpMenuButtons();
        setFavButton();

        setUpComboFav();


        System.out.println("initialistion end");
    }

    /**
     * It adds a favorite to the favorites list
     */
    private void setFavButton(){
        addFavoris.setOnAction(actionEvent -> {
            if(startButton.getValue() == null || endButton.getValue() == null){
                labelError.setVisible(true);
            }
            else{
                if(textFieldFav.getText().equals("")){
                    labelNomDuFav.setTextFill(Color.web("#d60000"));
                    labelNomDuFav.setStyle("-fx-font-weight: bold");
                }
                else{
                    labelNomDuFav.setTextFill(Color.web("#000000"));
                    labelNomDuFav.setStyle("-fx-font-weight: regular");
                    labelError.setVisible(false);
                    presenter.addFavOnClick(startButton.getValue(), endButton.getValue(), textFieldFav.getText());
                }
            }
        });
    }

    /**
     * This function sets up the drop down menu for the start and end stations
     */
    private void setUpMenuButtons(){
        List<String> stations = presenter.getStations();

        startButton.setItems(FXCollections.observableArrayList(stations));
        endButton.setItems(FXCollections.observableArrayList(stations));
    }

    /**
     * This function sets up the search button, the display favorite button, the modify favorite button, and the delete
     * favorite button
     */
    private void setUpSearchButton(){
        searchButton.setOnAction(actionEvent -> {
            if(startButton.getValue() == null || endButton.getValue() == null){
                labelError.setVisible(true);
            }
            else{
                labelError.setVisible(false);
                tableView.getItems().clear();
                presenter.searchOnClick(startButton.getValue(), endButton.getValue());
            }
        });

        displayFavButton.setOnAction(actionEvent -> {
            if(favMenuButton.getValue() != null){
                String [] stationsName = presenter.getStationName(favMenuButton.getValue());
                labelError.setVisible(false);
                tableView.getItems().clear();
                presenter.searchOnClick(stationsName[0], stationsName[1]);
            }
        });


        modifyFavBtn.setOnAction(actionEvent -> {
            if(!textFieldFav.getText().equals("")){
                presenter.modifyFavName(textFieldFav.getText(),favMenuButton.getValue());
                setUpComboFav();
            }
        });

        deleteFavButton.setOnAction(actionEvent -> {
            presenter.deleteFavName(favMenuButton.getValue());
            setUpComboFav();
        });
    }

    /**
     * This function takes a list of nodes and adds them to the table view
     *
     * @param listOfNode a list of nodes that are to be added to the table
     */
    public void updateTableColumns(List<Node> listOfNode){
        Node current;
        for (int i = 0; i < listOfNode.size(); i++) {
            current = listOfNode.get(i);
            System.out.println(current);
            tableView.getItems().add(new DetailsForTable(current.getName(), current.getListOfLine()));
        }
        System.out.println("---------------------------------------");
    }

    /**
     * It takes the list of favorite locations from the presenter and sets the items of the favMenuButton to the list
     */
    private void setUpComboFav(){
        List<String> list = presenter.getFav();
        favMenuButton.setItems(FXCollections.observableList(list));
    }



}
