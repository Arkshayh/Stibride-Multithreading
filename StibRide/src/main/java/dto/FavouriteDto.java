package dto;

/**
 * It's a DTO that contains the key, name, departure and arrive of a favourite
 */
public class FavouriteDto extends Dto<Integer>{
    private String name;
    private StationsDto departure;
    private StationsDto arrive;

    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    public FavouriteDto(Integer key, String name, StationsDto departure, StationsDto arrive) {
        super(key);
        this.name = name;
        this.departure = departure;
        this.arrive = arrive;
    }

    public String getName() {
        return name;
    }

    public StationsDto getDeparture() {
        return departure;
    }

    public StationsDto getArrive() {
        return arrive;
    }
}
