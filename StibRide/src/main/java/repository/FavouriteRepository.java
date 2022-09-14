package repository;

import dao.FavouriteDao;
import dto.FavouriteDto;
import exception.RepositoryException;
import java.util.List;

/**
 * This class is used to interact with the database
 */
public class FavouriteRepository implements Repository<Integer, FavouriteDto> {
    private final FavouriteDao dao;

    public FavouriteRepository() throws RepositoryException {
        this.dao = FavouriteDao.getInstance();
    }

    /**
     * > This function returns a list of all the favouriteDto objects in the database
     *
     * @return A list of FavouriteDto objects.
     */
    @Override
    public List<FavouriteDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    /**
     * > This function gets a favourite from the database
     *
     * @param key The primary key of the record to be retrieved.
     * @return A FavouriteDto object.
     */
    @Override
    public FavouriteDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    /**
     * This function returns false.
     *
     * @param key The key to search for
     * @return A boolean value.
     */
    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return false;
    }

    /**
     * It returns the origin and destination of a favorite
     *
     * @param nameFav the name of the favorite
     */
    public String [] getOrigineArriveName(String nameFav){
        return dao.selectByName(nameFav);
    }

    /**
     * This function adds a favourite to the database.
     *
     * @param favouriteDto The object that you want to insert into the database.
     */
    public void add(FavouriteDto favouriteDto) {
        dao.insert(favouriteDto);
    }

    /**
     * Update the name of the person with the current name to the new name.
     *
     * @param name the new name of the user
     * @param currentName The name of the current user.
     */
    public void update(String name, String currentName){
        dao.update(name, currentName);
    }

    /**
     * Delete a user from the database.
     *
     * @param name The name of the file to be deleted.
     */
    public void delete(String name){
        dao.delete(name);
    }

}
