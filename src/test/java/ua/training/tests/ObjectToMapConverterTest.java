package ua.training.tests;

import org.junit.Assert;
import org.junit.Test;
import ua.travel.dao.converters.ObjectToMapConverter;
import ua.travel.entity.User;
import ua.travel.entity.UserType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuuto on 6/6/17.
 */
public class ObjectToMapConverterTest {

    @Test
    public void convertUserToMap(){
        User user = new User();
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setType("user");
        user.setId(2L);
        user.setName("Name");
        user.setUserType(userType);
        Map<String, Object> parsedUser = ObjectToMapConverter.parse(user, User.class);
        Map<String, Object> exceptedResult = new HashMap<>();
        exceptedResult.put("name", "Name");
        exceptedResult.put("type", userType);
        exceptedResult.put("login", null);
        exceptedResult.put("password", null);
        exceptedResult.put("surname", null);
        exceptedResult.put("phone", null);
        exceptedResult.put("discount", null);
        Assert.assertEquals(exceptedResult, parsedUser);
    }
}
