package ua.travel.dao.creator;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;

/**
 * Created by yuuto on 5/20/17.
 */
public class TableCreatorFactory {
    public static TableCreator getTableCreator(String packageToScan){
        if(DataSourceFactory.getDataSourceType().equals(DataSourceType.MYSQL)){
            return new MySQLTableCreator(packageToScan);
        } else {
            return new H2TableCreator(packageToScan);
        }
    }
}
