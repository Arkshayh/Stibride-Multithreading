package view;

import controller.Controller;
import dto.SimulationDto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DetailsForTable;
import model.Model;
import repository.SimulationRepository;
import util.Observer;


import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * It's the controller of the view
 */
public class ControllerView implements Initializable, Observer {
    private Controller controller;
    private Model model;
    private int max_size;

    @FXML
    private MenuItem infoMenuItem;

    @FXML
    private Menu menuView;

    @FXML
    private MenuItem aboutItem;

    @FXML
    private LineChart<Integer, Long> chart;

    @FXML
    private ChoiceBox<String> configurationChoice;

    @FXML
    private TableColumn<DetailsForTable, Integer> durationCol;

    @FXML
    private Label leftStatus;

    @FXML
    private TableColumn<DetailsForTable, String> nameCol;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private MenuItem quitItem;

    @FXML
    private Label rightStatus;

    @FXML
    private TableColumn<DetailsForTable, Integer> sizeCol;

    @FXML
    private ChoiceBox<String> sortChoice;

    @FXML
    private Button start;

    @FXML
    private TableColumn<DetailsForTable, Integer> swapCol;

    @FXML
    private TableView<DetailsForTable> table;

    @FXML
    private Spinner<Integer> threadSpinner;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    public ControllerView(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        model.addObserver(this);
    }

    /**
     * It initializes the view
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle This is a ResourceBundle object that contains the resources for the application.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation");

        leftStatus.setTextFill(Color.web("#858585"));

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        threadSpinner.setValueFactory(valueFactory);

        sortChoice.getItems().add("Tri fusion");
        sortChoice.getItems().add("Tri à bulles");
        sortChoice.setValue("Tri fusion");

        configurationChoice.getItems().add("Very Easy : 1 - 100");
        configurationChoice.getItems().add("Easy : 1 - 1000");
        configurationChoice.getItems().add("Normal : 1 - 10000");
        configurationChoice.getItems().add("Hard : 1 - 100000");
        configurationChoice.setValue("Very Easy : 1 - 100");

        setUpMenuView();

        durationCol.setCellValueFactory(new PropertyValueFactory<>("Durée"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("TypeDeTri"));
        swapCol.setCellValueFactory(new PropertyValueFactory<>("Operations"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("Taille"));

        start.setOnAction(event -> {
            createSeries(sortChoice.getValue());
            switch (configurationChoice.getValue()) {
                case "Very Easy : 1 - 100" -> {
                    controller.startBtn(sortChoice.getValue(), 100, threadSpinner.getValue());
                    max_size = 100;
                }
                case "Easy : 1 - 1000" -> {
                    controller.startBtn(sortChoice.getValue(), 1000, threadSpinner.getValue());
                    max_size = 1000;
                }
                case "Normal : 1 - 10000" -> {
                    controller.startBtn(sortChoice.getValue(), 10000, threadSpinner.getValue());
                    max_size = 10000;
                }
                case "Hard : 1 - 100000" -> {
                    controller.startBtn(sortChoice.getValue(), 100000, threadSpinner.getValue());
                    max_size = 100000;
                }
                default -> throw new IllegalArgumentException("Error : ControllerView | ligne 110");
            }
        });

        System.out.println("Fin de l'initialisation");
    }

    /**
     * It creates a new window with a new controller and loads the fxml file
     */
    private void setUpMenuView(){
        infoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/logs.fxml"));
                    loader.setController(new ControllerLogsView());
                    Parent root1 = loader.load();

                    Stage stage = new Stage();
                    stage.setTitle("Simulations logs");
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void update(DetailsForTable detail) {
        if(detail != null){
            updateTable(detail);
            updateLineChart(detail);

            try{
                if(detail.getTaille() == max_size){
                    SimulationRepository simulationRepository = new SimulationRepository();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    SimulationDto simulationDto = new SimulationDto(1,timestamp, detail.getTypeDeTri(), detail.getTaille());
                    simulationRepository.add(simulationDto);
                }
            }
            catch (Exception e){
                System.out.println(e);
            }

        }
        updateLabelLeft();

    }

    /**
     * I want to add a new row to my tableView
     *
     * @param detail DetailsForTable
     */
    public void updateTable(DetailsForTable detail){
        System.out.println(detail.getTypeDeTri());
        table.getItems().add(detail);
    }


    /**
     * It updates the label on the left side of the GUI
     */
    private void updateLabelLeft(){
        Platform.runLater(() -> {
            if(Thread.activeCount() > 1 ){
                leftStatus.setText("Threads actifs : " + Thread.activeCount());
            }
            else{
                leftStatus.setText("Thread actif : " + Thread.activeCount());
            }
        });
    }

    /**
     * It creates a new series for the chart
     *
     * @param typeDeTri The name of the series.
     */
    private void createSeries(String typeDeTri){
        XYChart.Series series = new XYChart.Series();
        series.setName(typeDeTri);
        chart.getData().add(series);
    }

    /**
     * It updates the chart with the new data
     *
     * @param details the object that contains the data to be added to the chart
     */
    private void updateLineChart(DetailsForTable details){
        Platform.runLater(() -> {
            chart.getData().get(chart.getData().size() - 1).getData().add(new XYChart.Data(details.getTaille(),
                    details.getOperations()));
        });
    }

    /**
     * This function sets the leftStatus variable to the value of the leftStatus parameter.
     *
     * @param leftStatus The label that will display the status of the left player.
     */
    public void setLeftStatus(Label leftStatus) {
        this.leftStatus = leftStatus;
    }
}
