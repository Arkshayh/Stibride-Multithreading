package dao;

import config.ConfigManager;
import exception.RepositoryException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * It's a singleton that manages the connection to the database
 */
public class DBManager {
    private Connection connection;

    private DBManager() {
        try {
            ConfigManager.getInstance().load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * > If the connection is null or closed, then create a new connection
     *
     * @return A connection to the database.
     */
    Connection getConnection() throws RepositoryException {

        String jdbcUrl = "jdbc:sqlite:" + ConfigManager.getInstance().getProperties("db.url");
        //|| connection.isClosed()
        if (connection == null ) {
            try {
                connection = DriverManager.getConnection(jdbcUrl);
            } catch (SQLException ex) {
                throw new RepositoryException("Connexion impossible: " + ex.getMessage());
            }
        }
        return connection;
    }

    /**
     * It starts a transaction
     */
    void startTransaction() throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de démarrer une transaction: " + ex.getMessage());
        }
    }

    /**
     * It starts a transaction
     *
     * @param isolationLevel 0 = read uncommitted, 1 = read committed, 2 = repeatable read, 3 = serializable
     */
    void startTransaction(int isolationLevel) throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);

            int isol = 0;
            switch (isolationLevel) {
                case 0:
                    isol = java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
                    break;
                case 1:
                    isol = java.sql.Connection.TRANSACTION_READ_COMMITTED;
                    break;
                case 2:
                    isol = java.sql.Connection.TRANSACTION_REPEATABLE_READ;
                    break;
                case 3:
                    isol = java.sql.Connection.TRANSACTION_SERIALIZABLE;
                    break;
                default:
                    throw new RepositoryException("Degré d'isolation inexistant!");
            }
            getConnection().setTransactionIsolation(isol);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de démarrer une transaction: " + ex.getMessage());
        }
    }

    /**
     * It commits the transaction and sets the auto-commit mode to true
     */
    void validateTransaction() throws RepositoryException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de valider la transaction: " + ex.getMessage());
        }
    }

    /**
     * It rolls back the current transaction and sets the auto-commit mode to true
     */
    void cancelTransaction() throws RepositoryException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible d'annuler la transaction: " + ex.getMessage());
        }
    }

    static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    /**
     * The DBManagerHolder class is loaded on the first execution of DBManager.getInstance() or the first access to
     * DBManagerHolder.INSTANCE, not before
     */
    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }
}
