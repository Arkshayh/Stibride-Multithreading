package dao;

import dto.StationsDto;
import exception.RepositoryException;
import repository.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * It's a singleton that implements the Dao interface
 */
public class StationsDao implements Dao<Integer, StationsDto> {

    private Connection connexion;

    private StationsDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static StationsDao getInstance() throws RepositoryException {
        return StationsDaoHolder.getInstance();
    }

    /**
     * It returns a list of all the stations in the database
     *
     * @return A list of StationsDto objects
     */
    @Override
    public List<StationsDto> selectAll() throws RepositoryException {
        String sql = "SELECT id,name FROM STATIONS";
        List<StationsDto> dtos = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                StationsDto dto = new StationsDto(rs.getInt(1), rs.getString(2));
                dtos.add(dto);
            }


        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    /**
     * It returns a StationsDto object, which is a data transfer object, that contains the id and name of a station
     *
     * @param key The primary key of the record to be retrieved.
     * @return A StationsDto object
     */
    @Override
    public StationsDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id,name FROM STATIONS WHERE  id = ?";
        StationsDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new StationsDto(rs.getInt(1), rs.getString(2));
                count++;
            }
            if (count > 1) {
                throw new RepositoryException("Record pas unique " + key);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    /**
     * The StationsDaoHolder class is a private static class that has a private static method that returns a new instance
     * of the StationsDao class
     */
    private static class StationsDaoHolder {

        private static StationsDao getInstance() throws RepositoryException {
            return new StationsDao();
        }
    }
}
