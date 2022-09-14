package repository;

import dao.SimulationDao;
import dto.SimulationDto;
import exception.RepositoryException;

import java.util.List;

/**
 * It's a repository that uses a DAO to get data from a database
 */
public class SimulationRepository implements Repository<Integer, SimulationDto>{
    private final SimulationDao dao;

    public SimulationRepository() throws RepositoryException {
        this.dao = SimulationDao.getInstance();
    }

    @Override
    public List<SimulationDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public SimulationDto get(Integer key) throws RepositoryException {
        return null;
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return false;
    }

    /**
     * This function adds a simulation to the database.
     *
     * @param simulationDto The object that will be inserted into the database.
     */
    public void add(SimulationDto simulationDto) throws RepositoryException {
        dao.insert(simulationDto);
    }

}
