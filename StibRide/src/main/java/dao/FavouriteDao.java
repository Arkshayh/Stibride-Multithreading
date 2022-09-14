package dao;

import dto.FavouriteDto;
import dto.StationsDto;
import exception.RepositoryException;
import repository.Dao;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * It's a singleton that implements the DAO interface
 */
public class FavouriteDao implements Dao<Integer, FavouriteDto> {
    private Connection connexion;

    private FavouriteDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static FavouriteDao getInstance() throws RepositoryException {
        return FavouriteDao.FavouriteDaoHolder.getInstance();
    }

    /**
     * It selects all the favorites from the database and returns them as a list of FavouriteDto objects
     *
     * @return A list of FavouriteDto
     */
    @Override
    public List<FavouriteDto> selectAll() throws RepositoryException {
        String sql =  "SELECT * FROM FAVORIS fav LEFT JOIN STATIONS sta on sta.id = fav.origine LEFT JOIN " +
                "STATIONS sta2 on sta2.id = fav.arrive ORDER BY id";
        List<FavouriteDto> dtos = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                FavouriteDto dto = new FavouriteDto(rs.getInt("id"), rs.getString("name"),
                         new StationsDto(rs.getInt("origine"), "null"), new StationsDto(rs.getInt("arrive"), "null"));
                dtos.add(dto);
            }


        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    /**
     * It selects a favourite from the database
     *
     * @param key the primary key of the record to be selected
     * @return A FavouriteDto object
     */
    @Override
    public FavouriteDto select(Integer key) throws RepositoryException {
        String sql =  "SELECT * FROM FAVORIS fav LEFT JOIN STATIONS sta on sta.id = fav.origine LEFT JOIN \" +\n" +
                "                \"STATIONS sta2 on sta2.id = fav.destination WHERE id = ?";
        FavouriteDto dto = null;
        try {
            PreparedStatement pstmt = connexion.prepareStatement(sql);
            pstmt.setInt(1,key);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){

                dto = new FavouriteDto(rs.getInt("id"), rs.getString("nom"),
                        (StationsDto) rs.getObject("origine"), (StationsDto) rs.getObject("arrive"));

            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    /**
     * It selects the name of the stations from the table FAVORIS and STATIONS, where the name of the favorite is the one
     * given in parameter
     *
     * @param nameFav the name of the favorite
     * @return A table with the name of the stations
     */
    public String [] selectByName(String nameFav){
        String origineName = "SELECT STATIONS.name from FAVORIS left join STATIONS on FAVORIS.origine = STATIONS.id WHERE FAVORIS.name = ?";
        String arriveName = "SELECT STATIONS.name from FAVORIS left join STATIONS on FAVORIS.arrive = STATIONS.id WHERE FAVORIS.name = ?";

        String [] tab = new String [2];

        try {
            PreparedStatement pstmt1 = connexion.prepareStatement(origineName);
            pstmt1.setString(1, nameFav);

            PreparedStatement pstmt2 = connexion.prepareStatement(arriveName);
            pstmt2.setString(1, nameFav);

            ResultSet rs;
            ResultSet rs2;

            rs = pstmt1.executeQuery();
            rs2 = pstmt2.executeQuery();

            tab[0] = rs.getString("name");
            tab[1] = rs2.getString("name");

        }
        catch (Exception e){
            System.out.println(e);
        }
        return tab;
    }

    /**
     * It inserts a new favourite in the database
     *
     * @param favouriteDto the object that contains the data to be inserted into the database.
     */
    public void insert(FavouriteDto favouriteDto){
        String sql = "INSERT INTO FAVORIS (name, origine, arrive) VALUES ('" + favouriteDto.getName() + "', " +
                favouriteDto.getDeparture().getKey() + ", " + favouriteDto.getArrive().getKey() + ")";
        /**
         * String sql = "INSERT INTO FAVORIS (name, origine, arrive) VALUES (?, ?, ?)";
         *
         *         try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
         *             stmt.setString(1, favouriteDto.getName());
         *             stmt.setInt(2, favouriteDto.getDeparture().getKey());
         *             stmt.setInt(3, favouriteDto.getArrive().getKey());
         *             stmt.executeUpdate(sql);
         */

        try {
            Statement stmt = connexion.createStatement();
            stmt.executeUpdate(sql); //TODO fixe erreur ici
            System.out.println("insert OK");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * It updates the name of a favorite in the database
     *
     * @param newName the new name of the favorite
     * @param currentName the name of the favorite you want to update
     */
    public void update(String newName, String currentName){
        String sql = "UPDATE FAVORIS SET name = ? WHERE name = ?";

        try {
            PreparedStatement pstm = connexion.prepareStatement(sql);
            pstm.setString(1, newName);
            pstm.setString(2, currentName);

            pstm.executeUpdate();

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * It deletes a row from the table FAVORIS where the name is equal to the name passed as a parameter
     *
     * @param name the name of the favorite to delete
     */
    public void delete(String name){
        String sql = "DELETE FROM FAVORIS WHERE FAVORIS.name = ?";

        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    /**
     * This class is a singleton that returns a single instance of the FavouriteDao class.
     */
    private static class FavouriteDaoHolder {

        private static FavouriteDao getInstance() throws RepositoryException {
            return new FavouriteDao();
        }
    }
}
