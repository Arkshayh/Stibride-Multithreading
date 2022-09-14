package dao;

import dto.LinesDto;
import exception.RepositoryException;
import repository.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LinesDao implements Dao<Integer, LinesDto> {

    private Connection connexion;

    private LinesDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static LinesDao getInstance() throws RepositoryException{
        return LinesDaoHolder.getInstance();
    }

    @Override
    public List<LinesDto> selectAll() throws RepositoryException {
        String sql = "SELECT id FROM LINES";
        List<LinesDto> dtos = new ArrayList<>();

        try (Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LinesDto dto = new LinesDto(rs.getInt(1));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public LinesDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id,name FROM STATIONS WHERE  id = ?";
        LinesDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new LinesDto(rs.getInt(1));
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

    private static class LinesDaoHolder{
        private static LinesDao getInstance() throws RepositoryException{
            return new LinesDao();
        }
    }
}
