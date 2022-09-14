package dto;

/**
 * > This class is a DTO for the Stations entity
 */
public class StationsDto extends Dto<Integer>{
    private String name;

    public StationsDto(Integer key,String name) {
        super(key);
        this.name = name;
    }

    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    protected StationsDto(Integer key) {
        super(key);
    }

    public String getName() {
        return name;
    }
}
