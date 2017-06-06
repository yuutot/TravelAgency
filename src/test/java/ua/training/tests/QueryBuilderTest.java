package ua.training.tests;

import org.junit.Assert;
import org.junit.Test;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.builders.Condition;
import ua.travel.dao.builders.DeleteQueryBuilder;
import ua.travel.dao.builders.SelectQueryBuilder;
import ua.travel.entity.Tour;
import ua.travel.entity.User;
import ua.travel.entity.UserType;

import javax.jws.soap.SOAPBinding;

/**
 * Created by yuuto on 6/6/17.
 */
public class QueryBuilderTest {

    @Test
    public void deleteBuilder() {

        String expectedQuery = "delete from users where id = 2";
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
        String query = deleteQueryBuilder
                .addTable(User.class.getAnnotation(Table.class).value())
                .where()
                .addCondition("id", Condition.EVEN, 2)
                .build();
        Assert.assertEquals(expectedQuery, query.trim());
    }

    @Test
    public void selectBuilder() {

        String expectedQuery = "select * from users, user_type where users.type = user_type.id and users.id BETWEEN 1 and 2";
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder
                .addField("*")
                .from()
                .addTable(User.class.getAnnotation(Table.class).value())
                .addTable(UserType.class.getAnnotation(Table.class).value())
                .where()
                .addCondition("type", Condition.EVEN, "id", User.class, UserType.class)
                .and()
                .between("id", 1, 2, User.class)
                .build();
        Assert.assertEquals(expectedQuery, query.trim());
    }

    @Test
    public void joinSelectBuilderTest(){
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        selectQueryBuilder
                .addField("*")
                .from()
                .addTable(Tour.class.getAnnotation(Table.class).value())
                .createJoinForClass(Tour.class);
        System.out.println(selectQueryBuilder.build());
    }
}
