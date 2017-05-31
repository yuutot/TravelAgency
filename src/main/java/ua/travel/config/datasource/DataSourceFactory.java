package ua.travel.config.datasource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by yuuto on 5/20/17.
 */
public class DataSourceFactory {
    public static DataSource getDataSource(DataSourceType type) throws SQLException {
        if(type.equals(DataSourceType.MYSQL)){
            return MySQLDataSourceConfig.getDataSource();
        } else {
            return H2DataSourceConfig.getDataSource();
        }
    }
}
