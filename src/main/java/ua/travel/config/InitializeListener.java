package ua.travel.config;

import ua.travel.config.datasource.DataSourceType;
import ua.travel.dao.creator.TableCreator;
import ua.travel.dao.creator.TableCreatorFactory;
import ua.travel.security.SecurityContext;

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

        TableCreatorFactory
                .getTableCreator(DataSourceType.MYSQL, getClass().getAnnotation(EntityScan.class).value())
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
                .addCredentials("/admin/city", "admin")
                .addCredentials("/admin/hotel", "admin")
                .addCredentials("/admin/editTour", "admin")
                .addCredentials("/admin/createCity", "admin")
                .addCredentials("/admin/createHotel", "admin")
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