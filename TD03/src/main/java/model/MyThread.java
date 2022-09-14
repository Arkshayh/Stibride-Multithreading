package model;

import util.Observable;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * It's a thread that implements the Observable interface
 */
public class MyThread extends Thread implements Observable {
    private List<Observer> list;
    private ListTabATrier listeTab;
    private String typeDeTri;
    private long nbOperation = 0;
    private double durée;
    private int taille;

    public MyThread(ListTabATrier tab, String typeDeTri) {
        this.typeDeTri = typeDeTri;
        list = new ArrayList<>();
        listeTab = tab;
    }

    @Override
    public void addObserver(Observer observer) {
        list.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        list.remove(observer);
    }

    @Override
    public void notifyObservers() {
        long duréeFin = System.currentTimeMillis();
        DetailsForTable details = new DetailsForTable(typeDeTri, taille, nbOperation,  (duréeFin - durée));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).update(details);
        }
    }

    /**
     * It takes the first element of the list of arrays, and if it's not null, it takes the array and the size of the
     * array, and then it sorts the array with the sorting method specified by the user
     */
    public void run(){
        Integer [] tab;
        while(listeTab.getElemByIndex(0) != null){
            tab = listeTab.pop();
            durée = System.currentTimeMillis();
            taille = tab.length;
            if(typeDeTri == "Tri Fusion"){
                triFusion(tab, tab.length);
            }
            else{
                triAbull(tab);
            }
            notifyObservers();

        }
    }

    /**
     * The function takes an array of integers as a parameter and sorts it using the bubble sort algorithm
     *
     * @param arr the array to sort
     */
    public void triAbull(Integer[] arr) {
        int i = 0, n = arr.length;
        boolean swapNeeded = true;
        nbOperation += 3;
        while (i < n - 1 && swapNeeded) {
            swapNeeded = false;
            nbOperation++;
            for (int j = 1; j < n - i; j++) {
                if (arr[j - 1] > arr[j]) {
                    nbOperation++;
                    int temp = arr[j - 1];
                    nbOperation++;
                    arr[j - 1] = arr[j];
                    nbOperation++;
                    arr[j] = temp;
                    nbOperation++;
                    swapNeeded = true;
                    nbOperation++;
                }
            }
            if(!swapNeeded) {
                break;
            }
            i++;
            nbOperation++;
        }
    }

    /**
     * If the array is of length 1 or less, return. Otherwise, split the array in half, sort the left half, sort the right
     * half, and merge the two halves
     *
     * @param a the array to be sorted
     * @param length the length of the array
     */
    public void triFusion(Integer [] a, int length) {
        if (length < 2) {
            return;
        }
        nbOperation++;
        int mid = length / 2;
        Integer[] l = new Integer[mid];
        Integer[] r = new Integer[length - mid];
        nbOperation+=3;
        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
            nbOperation++;
        }
        for (int i = mid; i < length; i++) {
            r[i - mid] = a[i];
            nbOperation++;
        }
        triFusion(l, mid);
        nbOperation++;
        triFusion(r, length - mid);
        nbOperation++;
        Fusion(a, l, r, mid, length - mid);
    }

    /**
     * It takes two sorted arrays and merges them into a single sorted array
     *
     * @param a the array to be sorted
     * @param l left array
     * @param r the array to be sorted
     * @param left the size of the left array
     * @param right the size of the right array
     */
    private void Fusion(Integer[] a, Integer[] l, Integer[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        nbOperation+=3;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
            nbOperation++;
        }
        while (i < left) {
            a[k++] = l[i++];
            nbOperation++;
        }
        while (j < right) {
            a[k++] = r[j++];
            nbOperation++;
        }
    }
}
