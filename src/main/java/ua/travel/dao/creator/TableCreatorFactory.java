package ua.travel.dao.creator;

import ua.travel.config.datasource.DataSourceType;

/**
 * Created by yuuto on 5/20/17.
 */
public class TableCreatorFactory {
    public static TableCreator getTableCreator(DataSourceType type, String packageToScan){
        if(type.equals(DataSourceType.MYSQL)){
            return new MySQLTableCreator(packageToScan);
        } else {
            return new H2TableCreator(packageToScan);
        }
    }
}
