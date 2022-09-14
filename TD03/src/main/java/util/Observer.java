package util;


import model.DetailsForTable;

public interface Observer {

    /**
     * This method is called whenever the observed object has changed.
     */
    void update(DetailsForTable object);
}
