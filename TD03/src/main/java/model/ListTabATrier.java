package model;

import java.util.ArrayList;
import java.util.List;

/**
 * It's a list of arrays of integers
 */
public class ListTabATrier {
    private List<Integer []> maListe;

    public ListTabATrier() {
        this.maListe = new ArrayList<>();
    }

    public void remove(int index){
        maListe.remove(index);
    }

    /**
     * If the list is empty, return null, otherwise return the element at the given index.
     *
     * @param index the index of the element to get
     * @return An array of integers.
     */
    public synchronized Integer[] getElemByIndex(int index){
        if(maListe.size() == 0){
            return null;
        }
        return maListe.get(index);
    }

    /**
     * > The function returns the first element of the list, and removes it from the list
     *
     * @return The first element of the list.
     */
    public synchronized Integer [] pop(){
        if(maListe.size() == 0){
            return null;
        }
        Integer [] tab = maListe.get(0);
        maListe.remove(0);
        return tab;
    }

    /**
     * It adds an array of integers to a list of arrays of integers
     *
     * @param tab the array of integers to add to the list
     */
    public void add(Integer [] tab){
        maListe.add(tab);
    }


}
