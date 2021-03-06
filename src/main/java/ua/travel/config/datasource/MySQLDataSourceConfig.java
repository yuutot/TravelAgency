package ua.travel.config.datasource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Config for h2 db
 */
public class MySQLDataSourceConfig {

    private static DataSource dataSource;

    static DataSource getDataSource() throws SQLException {
        if (dataSource == null) {
            try {
                InitialContext initContext = new InitialContext();
                dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/travel");
            } catch (NamingException e) {
                throw new SQLException("Cant create data source. " + e.getMessage());
            }
        }
        return dataSource;
    }
}