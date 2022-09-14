package model;

import java.util.*;

public class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Node getNode(String name){
        var nodes = getNodes();
        Node currentNode;
        for (Iterator<Node> it = nodes.iterator(); it.hasNext();){
            Node current = it.next();
            if(current.getName().equals(name)){
                return current;
            }
        }
        return null;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public void clearGraph(){
        var nodes = getNodes();

        for (Iterator<Node> it = nodes.iterator(); it.hasNext();){
            Node node = it.next();
            node.clearSP();
            node.setDistance(Integer.MAX_VALUE);
        }
    }

}
