package ua.training.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.travel.entity.User;
import ua.travel.security.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by yuuto on 6/6/17.
 */

@RunWith(Parameterized.class)
public class SecurityTest {

    @Before
    public void createSecurityContext(){
        SecurityContext.getInstance()
                .addCredentials("/admin", "admin")
                .addCredentials("/user", "user", "admin");
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"/admin", null},
                {"/user", null}
        });
    }

    private String command;
    private User user;

    public SecurityTest(String command, User user) {
        this.command = command;
        this.user = user;
    }

    @Test
    public void testSecurity(){
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer(command));
        Assert.assertFalse(SecurityContext.getInstance().checkUserCredentials(request, user));
    }
}
