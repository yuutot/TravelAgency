package ua.travel.config.datasource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Data source factory. Return data source by enum
 */
public class DataSourceFactory {

    private static DataSourceType dataSourceType;

    public static DataSource getDataSource() throws SQLException {
        if(dataSourceType.equals(DataSourceType.MYSQL)){
            return MySQLDataSourceConfig.getDataSource();
        } else {
            return H2DataSourceConfig.getDataSource();
        }
    }

    public static void setDataSourceType(DataSourceType dataSourceType) {
        DataSourceFactory.dataSourceType = dataSourceType;
    }

    public static DataSourceType getDataSourceType() {
        return dataSourceType;
    }
}
