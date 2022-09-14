package repository;

import dao.StationsDao;
import dto.StationsDto;
import exception.RepositoryException;

import java.util.List;

/**
 * This class is a repository for the stations table
 */
public class StationsRepository implements Repository<Integer, StationsDto>{
    private final StationsDao dao;

    public StationsRepository() throws RepositoryException{
        dao = StationsDao.getInstance();
    }

    /**
     * > This function returns a list of all stations in the database
     *
     * @return A list of all the stations in the database.
     */
    @Override
    public List<StationsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    /**
     * > This function gets a station by its id
     *
     * @param key The primary key of the record to be retrieved.
     * @return A StationsDto object.
     */
    @Override
    public StationsDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    /**
     * > This function checks if the key exists in the database
     *
     * @param key The key of the item to be retrieved.
     * @return The method returns a boolean value.
     */
    @Override
    public boolean contains(Integer key) throws RepositoryException {
        StationsDto refreshItem =dao.select(key);
        return dao.select(key) != null;
    }
}
