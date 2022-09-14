package model;

import util.Observable;
import util.Observer;

import java.util.ArrayList;
import java.util.List;
import util.State;

/**
 * The Model class is an Observable class that holds a State object and a list of Observers
 */
public class Model implements Observable {
    private List<Observer> listObserver;
    private State state;

    public Model() {
        state = State.VOID;
        listObserver = new ArrayList<>();
    }

    /**
     * When the state changes, notify all observers.
     *
     * @param state The current state of the game.
     */
    private void setState(State state) {
        this.state = state;
        notifyObservers();
    }

    /**
     * When the state of the subject changes, notify all observers.
     */
    public void updateState(){
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        listObserver.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        listObserver.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < listObserver.size(); i++) {
            listObserver.get(i).update(null);
        }
    }
}
