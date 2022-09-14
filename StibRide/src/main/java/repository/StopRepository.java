package repository;

import dao.StopDao;
import dto.StopDto;
import exception.RepositoryException;
import javafx.util.Pair;

import java.util.List;

/**
 * It's a repository that uses a StopDao to get StopDto objects
 */
public class StopRepository implements Repository<Pair<Integer,Integer>, StopDto> {
    private final StopDao dao;

    public StopRepository() throws RepositoryException {
        this.dao = StopDao.getInstance();
    }


    /**
     * This function returns a list of all the stops in the database.
     *
     * @return A list of StopDto objects.
     */
    @Override
    public List<StopDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    /**
     * This function gets a stop from the database.
     *
     * @param key The key of the object to be retrieved.
     * @return A StopDto object.
     */
    @Override
    public StopDto get(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.select(key);
    }

    /**
     * If the key is in the database, return true, otherwise return false.
     *
     * @param key The key of the element to search for.
     * @return A boolean value.
     */
    @Override
    public boolean contains(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.select(key) != null;
    }
}
