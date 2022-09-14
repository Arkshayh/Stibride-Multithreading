package view;

import dto.SimulationDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.SimulationRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * It's a controller for the logs view
 */
public class ControllerLogsView implements Initializable {

    @FXML
    private TableColumn<SimulationDto, Integer> sizeCol;

    @FXML
    private TableColumn<SimulationDto, String> sortCol;

    @FXML
    private TableView<SimulationDto> tableLogs;

    @FXML
    private TableColumn<SimulationDto, String> timeCol;


    /**
     * It initializes the table view with the data from the database
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle that was given to the FXMLLoader.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("MaxSize"));
        sortCol.setCellValueFactory(new PropertyValueFactory<>("SortType"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("Timestamp"));

        updateTable();

        System.out.println("Fin de l'initialisation des logs");
    }

    /**
     * It gets all the simulations from the database and adds them to the table
     */
    private void updateTable(){
        try {
            SimulationRepository simulationRepository = new SimulationRepository();
            List<SimulationDto> simulationDtoList = simulationRepository.getAll();
            for (int i = 0; i < simulationDtoList.size(); i++) {
                tableLogs.getItems().add(simulationDtoList.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //tableLogs.getItems().add();
    }
}

