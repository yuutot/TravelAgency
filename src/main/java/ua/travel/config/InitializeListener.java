package ua.travel.config;

import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.creator.TableCreator;
import ua.travel.dao.creator.TableCreatorFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by yuuto on 5/19/17.
 */
@WebListener
@EntityScan("ua.travel.entity.*")
public class InitializeListener implements ServletContextListener {

    @Override
    public final void contextInitialized(ServletContextEvent sce) {
        TableCreator tableCreator = TableCreatorFactory.getTableCreator(DataSourceType.MYSQL, getClass().getAnnotation(EntityScan.class).value());
        tableCreator.createTableForEntity();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}