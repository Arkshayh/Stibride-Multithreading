package repository;

import dto.Dto;
import exception.RepositoryException;

import java.util.List;

// A generic interface.
public interface Dao <K, T extends Dto<K>>{
    T select(K key) throws RepositoryException;

    /**
     * This function returns a list of all the objects in the repository.
     *
     * @return A list of all the objects in the repository.
     */
    List<T> selectAll() throws RepositoryException;
}
