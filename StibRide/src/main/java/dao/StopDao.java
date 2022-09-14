package dao;
;
import dto.StopDto;
import exception.RepositoryException;
import javafx.util.Pair;
import repository.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * It's a class that implements the Dao interface and it's used to select, insert, update and delete stops from the
 * database
 */
public class StopDao implements Dao<Pair<Integer, Integer>, StopDto> {

    private Connection connexion;

    private StopDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static StopDao getInstance() throws RepositoryException {
        return StopDaoHolder.getInstance();
    }

    /**
     * It returns a list of all the stops in the database
     *
     * @return A list of StopDto objects
     */
    @Override
    public List<StopDto> selectAll() throws RepositoryException {
        String sql =  "SELECT * FROM STOPS LEFT JOIN STATIONS as sta on sta.id = STOPS.id_station LEFT JOIN " +
                "LINES as lin on lin.id = STOPS.id_line ORDER BY id_line, id_order";
        List<StopDto> dtos = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                StopDto dto = new StopDto(rs.getInt("id_line"), rs.getInt("id_station"),
                        rs.getInt("id_order"), rs.getString("name"));
                dtos.add(dto);
            }


        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    /**
     * It returns a StopDto object, which is a data transfer object, that contains the information of a stop
     *
     * @param key the key of the object to select
     * @return A StopDto object
     */
    @Override
    public StopDto select(Pair<Integer, Integer> key) throws RepositoryException {
        String sql =  "SELECT * FROM STOPS LEFT JOIN STATIONS as sta on sta.id = STOPS.id_station LEFT JOIN " +
                "LINES as lin on lin.id = STOPS.id_line ORDER BY id_line, id_order";
        StopDto dto = null;
        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()){

                dto = new StopDto(rs.getInt("id_line"), rs.getInt("id_station"),
                        rs.getInt("id_order"), rs.getString("name"));

            }


        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }


    /**
     * The StopDaoHolder class is a static class that has a static method called getInstance() that returns a new StopDao
     * object
     */
    private static class StopDaoHolder {

        private static StopDao getInstance() throws RepositoryException {
            return new StopDao();
        }
    }
}
