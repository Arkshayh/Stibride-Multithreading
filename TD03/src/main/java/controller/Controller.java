package controller;

import javafx.stage.Stage;
import model.ListTabATrier;
import model.Model;
import model.MyThread;
import view.ControllerView;
import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * It's a controller that manages the view and the model
 */
public class Controller {
    private Model model;
    private View view;
    private ControllerView controllerView;
    private ListTabATrier listeDeTab;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this, model);
    }

    public void setControllerView(ControllerView controllerView) {
        this.controllerView = controllerView;
    }

    /**
     * The start function is called when the program is started. It creates a new stage (window) and sets the scene to be
     * the main view
     *
     * @param stage The stage is the window that will be displayed.
     */
    public void start(Stage stage) throws Exception {
        view.start(stage);
    }

    /**
     * It starts the sorting process
     *
     * @param typeDeTri the type of sorting algorithm to use.
     * @param nbEntier the number of integers to be sorted
     * @param nbThreadAccordé the number of threads that the user has chosen to use.
     */
    public void startBtn(String typeDeTri, int nbEntier, int nbThreadAccordé) {
        model.updateState();
        sortBy(nbEntier, nbThreadAccordé, typeDeTri);
    }

    /**
     * It generates 10 arrays of increasing size, and then calls the startThread function
     *
     * @param nbEntier the number of integers to sort
     * @param nbThreadAccordé the number of threads that will be used to sort the list of arrays.
     * @param typeDeTri the type of sorting algorithm to use.
     */
    private void sortBy(int nbEntier, int nbThreadAccordé, String typeDeTri){
        listeDeTab = new ListTabATrier();
        Integer [] tab;
        for (int i = 0; i <= nbEntier; i+=nbEntier/10) {
            tab = new Integer[i];
            for (int j = 0; j < i; j++) {
                tab[j] = genererInt(0,1000000000);
            }
            listeDeTab.add(tab);
        }
        startThread(nbThreadAccordé, typeDeTri);
    }

    /**
     * It generates a random integer between two given integers
     *
     * @param borneInf the lower bound of the random number
     * @param borneSup the maximum number that can be generated
     * @return A random number between the two parameters.
     */
    int genererInt(int borneInf, int borneSup){
        Random random = new Random();
        int nb;
        nb = borneInf+random.nextInt(borneSup-borneInf);
        return nb;
    }

    /**
     * It creates a thread for each array to sort
     *
     * @param nbThread the number of threads to be created
     * @param typetri the type of sorting algorithm to use
     */
    private void startThread(int nbThread, String typetri){
        for (int i = 0; i < nbThread; i++) {
            MyThread thread = new MyThread(listeDeTab,typetri);
            thread.addObserver(controllerView);
            thread.start();
        }
    }

}
