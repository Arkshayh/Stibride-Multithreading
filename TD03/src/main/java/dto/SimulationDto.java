package dto;

import java.sql.Timestamp;

/**
 * It's a DTO that holds the data for a simulation
 */
public class SimulationDto extends Dto<Integer>{
    private Timestamp timestamp;
    private String sortType;
    private int maxSize;

    public SimulationDto(Integer key, Timestamp timestamp, String sortType, int maxSize) {
        super(key);
        this.timestamp = timestamp;
        this.sortType =sortType;
        this.maxSize = maxSize;
    }

    /**
     * > This function returns the timestamp of the current time
     *
     * @return The timestamp of the event.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * This function returns the sort type
     *
     * @return The sortType variable is being returned.
     */
    public String getSortType() {
        return sortType;
    }

    /**
     * This function returns the maximum size of the stack.
     *
     * @return The maxSize variable is being returned.
     */
    public int getMaxSize() {
        return maxSize;
    }
}
