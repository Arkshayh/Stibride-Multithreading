package repository;

import dao.LinesDao;
import dto.LinesDto;
import exception.RepositoryException;

import java.util.List;

/**
 * This class is used to get a list of all the lines in the database
 */
public class LinesRepository implements Repository<Integer, LinesDto> {
    private final LinesDao dao;

    public LinesRepository() throws RepositoryException{
        dao = LinesDao.getInstance();
    }

    /**
     * > This function returns a list of all the lines in the database
     *
     * @return A list of all the lines in the database.
     */
    @Override
    public List<LinesDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    /**
     * > This function is used to get a LinesDto object from the database using the LinesDao object
     *
     * @param key The primary key of the record to be retrieved.
     * @return A LinesDto object
     */
    @Override
    public LinesDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    /**
     * > This function checks if the key exists in the database
     *
     * @param key the key of the item to be refreshed
     * @return A boolean value.
     */
    @Override
    public boolean contains(Integer key) throws RepositoryException {
        LinesDto refreshItem = dao.select(key);
        return dao.select(key) != null;
    }
}
