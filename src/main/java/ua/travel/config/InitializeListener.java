package ua.travel.config;

import ua.travel.config.datasource.DataSourceFactory;
import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.creator.TableCreator;
import ua.travel.dao.creator.TableCreatorFactory;
import ua.travel.security.SecurityContext;
import ua.travel.service.OrderService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Created by yuuto on 5/19/17.
 */
@WebListener
@EntityScan("ua.travel.entity.*")
public class InitializeListener implements ServletContextListener {

    @Override
    public final void contextInitialized(ServletContextEvent sce) {

        try {
            LogManager.getLogManager().readConfiguration(getClass().getResourceAsStream("/log.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataSourceFactory
                .setDataSourceType(DataSourceType.MYSQL);

        TableCreatorFactory
                .getTableCreator(getClass().getAnnotation(EntityScan.class).value())
                .createTableForEntity();

        SecurityContext.getInstance()
                .addCredentials("/login", "all")
                .addCredentials("/register", "all")
                .addCredentials("/", "all")
                .addCredentials("/error", "all")
                .addCredentials("/tours", "all")
                .addCredentials("/order", "user", "admin")
                .addCredentials("/admin", "admin")
                .addCredentials("/admin/user", "admin")
                .addCredentials("/admin/order", "admin")
                .addCredentials("/admin/tour", "admin")
                .addCredentials("/admin/tours", "admin")
                .addCredentials("/admin/hotels", "admin")
                .addCredentials("/admin/city", "admin")
                .addCredentials("/admin/editTour", "admin")
                .addCredentials("/admin/createTour", "admin")
                .addCredentials("login", "all")
                .addCredentials("register", "all")
                .addCredentials("edit_order_status", "admin")
                .addCredentials("create_city", "admin")
                .addCredentials("create_hotel", "admin")
                .addCredentials("create_tour", "admin");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}