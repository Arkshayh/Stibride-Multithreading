package model;

import dto.FavouriteDto;
import dto.StationsDto;
import dto.StopDto;
import exception.RepositoryException;
import observer.Observable;
import observer.Observer;
import repository.FavouriteRepository;
import repository.StationsRepository;
import repository.StopRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model extends Observable {
    private Observable observable;
    private Graph graph;
    private Map<Integer, Node> stationsMap;

    public Model() {
        observable = new Observable();
        setUpGraph();
    }

    /**
     * Add an observer to the observable.
     *
     * @param observer The observer to be added.
     */
    public void addObserver(Observer observer){
        observable.addObserver(observer);
    }

    /**
     * > This function will return a list of all stations in the database
     *
     * @return A list of stations
     */
    public List<StationsDto> generateStationRepository(){
        try {
            return new StationsRepository().getAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * We create a graph, then we create a map of stations, then we get all the stops from the database, then we get all
     * the stations from the database, then we add all the stations to the map, then we get the id of the current station,
     * the id of the current stop, and the number of the line, then we loop through all the stops, and if the number of the
     * line is the same, and if the id of the stop is +1 or -1, and if the current station doesn't already have the station
     * as a destination, then we add the station as a destination, and we add the line to the station, then we loop through
     * all the stations in the map, and we add them to the graph
     */
    private void setUpGraph(){
        this.graph = new Graph();
        //Get destination int destination
        //get départ int start

        //La clé = id de la station
        this.stationsMap = new HashMap<>();
        try {
            StopRepository stopRepository = new StopRepository();
            var listeDeStop = stopRepository.getAll();

            StationsRepository stationsRepository = new StationsRepository();
            List<StationsDto> listOfStations = stationsRepository.getAll();

            for (int i = 0; i < listOfStations.size(); i++) {
                stationsMap.put(listOfStations.get(i).getKey(),
                        new Node(listOfStations.get(i).getName(), listOfStations.get(i).getKey()));
            }

            //si stop.getIdOrder +1/-1 && getLines.getKey identique alors on ajoute comme voisin
            //for (int i = 0; i < listeDeStop.size(); i++) {
              //  System.out.println(listeDeStop.get(i).getId_order() + " " + listeDeStop.get(i).getStations().getKey()+" "+ listeDeStop.get(i).getLines().getKey());
            //}

            int idCurrentStation;
            int stopIdOrder;
            int nbLine;

            for (int i = 0; i < listeDeStop.size(); i++) {
                idCurrentStation = listeDeStop.get(i).getStations().getKey();
                stopIdOrder = listeDeStop.get(i).getId_order();
                nbLine = listeDeStop.get(i).getLines().getKey();
                for (int j = 1; j < listeDeStop.size(); j++) {
                    if(listeDeStop.get(j).getLines().getKey() == nbLine){
                        if(listeDeStop.get(j).getId_order() == stopIdOrder - 1 || listeDeStop.get(j).getId_order() == stopIdOrder + 1 ){
                            if(!stationsMap.get(idCurrentStation).containsDestination(stationsMap.get(j))){
                                stationsMap.get(idCurrentStation).addDestination(stationsMap.get(listeDeStop.get(j).getStations().getKey()), 1);
                                stationsMap.get(idCurrentStation).addLine(nbLine);
                            }
                        }
                    }
                }
            }

            var keyset = stationsMap.keySet();
            for (int i = 0; i < keyset.size(); i++) {
                graph.addNode(stationsMap.get(keyset.toArray()[i]));
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        System.out.println("Graph created.");
    }

    /**
     * It takes two strings as parameters, and returns the shortest path between the two stations
     *
     * @param departure the departure station
     * @param arrive the name of the station where you want to go
     */
    public synchronized void getShortestPath(String departure, String arrive){
        Node nodeStart = null;
        Node nodeEnd = null;

        graph.clearGraph();

        try {
            StopRepository stopRepository = new StopRepository();
            var listeDeStop = stopRepository.getAll();
            for (int i = 0; i < listeDeStop.size(); i++) {
                if(listeDeStop.get(i).getStations().getName().equals(departure)){
                    nodeStart = stationsMap.get(listeDeStop.get(i).getStations().getKey());
                }
                if(listeDeStop.get(i).getStations().getName().equals(arrive)){
                    nodeEnd = stationsMap.get(listeDeStop.get(i).getStations().getKey());
                }
            }

            Graph tmp = Dijkstra.calculateShortestPathFromSource(graph, nodeStart);
            var listOfNodes = deepCopy(nodeEnd.getShortestPath());
            listOfNodes.add(nodeEnd);
            update(listOfNodes);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * When the list of nodes is updated, notify the observers that the list of nodes has been updated.
     *
     * @param listOfNodes The list of nodes that will be updated.
     */
    private void update(List<Node> listOfNodes){
        observable.notifyObservers(listOfNodes);
    }

    /**
     * It takes a list of nodes and returns a copy of that list
     *
     * @param liste The list of nodes that you want to copy.
     * @return A list of nodes.
     */
    private List<Node> deepCopy(List<Node> liste){
        List<Node> listOfNodesFullCopy = new ArrayList<>();

        for (int i = 0; i < liste.size(); i++) {
            listOfNodesFullCopy.add(liste.get(i));
        }
        return listOfNodesFullCopy;
    }

    /**
     * It adds a new favourite to the database
     *
     * @param start the start station
     * @param end the end station
     * @param name the name of the favorite
     */
    public void addToDBFav(String start, String end, String name){
        try {
            StationsDto depart = null;
            StationsDto arrive = null;

            StopRepository stopRepository = new StopRepository();
            List<StopDto> listeDeStop = stopRepository.getAll();

            for (int i = 0; i < listeDeStop.size(); i++) {
                if (listeDeStop.get(i).getStations().getName().equals(start)) {
                    depart = new StationsDto(listeDeStop.get(i).getStations().getKey(),start);
                }
                if (listeDeStop.get(i).getStations().getName().equals(end)) {
                    arrive = new StationsDto(listeDeStop.get(i).getStations().getKey(), end);
                }
            }

            FavouriteRepository favouriteRepository = new FavouriteRepository();
            favouriteRepository.add(new FavouriteDto(0, name, depart, arrive));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * It returns a list of FavouriteDto objects
     *
     * @return A list of FavouriteDto
     */
    public List<FavouriteDto> generetateFavRepository(){
        try {
            return new FavouriteRepository().getAll();
        }
        catch (Exception e){
            System.out.println(e);
        }
        System.out.println("ERREUUUUUUUUUUUUUUR");
        return null;
    }

    /**
     * This function is used to get the name of the station from the database
     *
     * @param name the name of the station
     * @return The name of the station
     */
    public String[] getStationName(String name){
        try {
            return new FavouriteRepository().getOrigineArriveName(name);
        }
        catch (Exception e){
            System.out.printf("e");
        }
        return null;
    }

    /**
     * It takes a new name and a current name as parameters, and updates the current name to the new name in the database
     *
     * @param newName The new name of the favourite
     * @param currentName The name of the favourite you want to modify.
     */
    public void modifyFavName(String newName, String currentName){
        try {
            new FavouriteRepository().update(newName, currentName);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * It deletes a favourite from the database
     *
     * @param name The name of the favourite to be deleted.
     */
    public void deleteFav(String name){
        try {
            new FavouriteRepository().delete(name);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
