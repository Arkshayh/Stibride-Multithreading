package vue;

import java.util.List;

/**
 * It's a class that holds a String and a List of Integers
 */
public class DetailsForTable {
    private String stations;
    private List<Integer>  lines;

    public DetailsForTable(String stations, List<Integer> lines) {
        this.stations = stations;
        this.lines = lines;
    }

    public String getStations() {
        return stations;
    }

    public void setStations(String stations) {
        this.stations = stations;
    }

    public List<Integer> getLines() {
        return lines;
    }

}
