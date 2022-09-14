package dao;

import dto.SimulationDto;
import exception.RepositoryException;
import repository.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * It's a singleton class that implements the Dao interface
 */
public class SimulationDao implements Dao<Integer, SimulationDto> {
    private Connection connection;

    private SimulationDao() throws RepositoryException{
        connection = DBManager.getInstance().getConnection();
    }

    /**
     * If the instance is null, create it.  If it's not null, return it.
     *
     * @return A singleton instance of the SimulationDao class.
     */
    public static SimulationDao getInstance() throws RepositoryException{
        return SimulationDaoHolder.getInstance();
    }


    @Override
    public SimulationDto select(Integer key) throws RepositoryException {
        //No need
        return null;
    }

    @Override
    public List<SimulationDto> selectAll() throws RepositoryException {
        String sql = "Select * from SIMULATION";
        List<SimulationDto> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SimulationDto dto = new SimulationDto(rs.getInt(1), Timestamp.valueOf(rs.getString(2)),
                        rs.getString(3), rs.getInt(4));

                list.add(dto);

            }
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
        return list;
    }

    /**
     * It inserts a new row into the database
     *
     * @param simulationDto the object that contains the data to be inserted into the database.
     */
    public void insert(SimulationDto simulationDto) throws RepositoryException{
        String sql = "INSERT INTO SIMULATION (timestamp, sort_type, max_size) VALUES (?,?,?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, simulationDto.getTimestamp().toString());
            stmt.setString(2, simulationDto.getSortType());
            stmt.setInt(3, simulationDto.getMaxSize());

            stmt.execute();
            System.out.println("insert OK");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    /**
     * > The SimulationDaoHolder class is a static class that has a static method that returns a new instance of the
     * SimulationDao class
     */
    private static class SimulationDaoHolder {
        private static SimulationDao getInstance() throws RepositoryException {
            return new SimulationDao();
        }
    }

}
