package model;

import java.util.*;

/**
 * It represents a node in a graph
 */
public class Node {
    private int idStations;
    private String name;
    private List<Integer> listOfLine;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(String name, int id) {
        this.name = name;
        this.idStations = id;
        this.listOfLine = new ArrayList<>();
    }

    public void addLine(int line){
        if(!listOfLine.contains(line)){
            listOfLine.add(line);
        }
    }

    public List<Integer> getListOfLine(){
        return this.listOfLine;
    }

    public String getName() {
        return name;
    }

    public void clearSP(){
        shortestPath.clear();
    }


    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public boolean containsDestination(Node node){
        return getAdjacentNodes().containsKey(node);
    }

    public int getIdStations() {
        return idStations;
    }

    @Override
    public String toString() {
        return "id Stations : " + idStations + "\n name : " + name;
    }

    // getters and setters
}
