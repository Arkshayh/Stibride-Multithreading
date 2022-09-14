package dto;

import javafx.util.Pair;

/**
 * This class is used to represent a stop in a line
 */
public class StopDto extends Dto<Pair<Integer,Integer>>{
    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */

    private LinesDto lines;
    private StationsDto stations;
    private Integer id_order;

    public StopDto(Integer idLine, Integer idStation ,Integer idOrder, String stationName) {
        super(new Pair<>(idLine, idStation));
        id_order = idOrder;
        lines = new LinesDto(idLine);
        stations = new StationsDto(idStation, stationName);
    }

    public LinesDto getLines() {
        return lines;
    }

    public StationsDto getStations() {
        return stations;
    }

    public Integer getId_order() {
        return id_order;
    }
}
