package ua.training.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.travel.command.utils.PathUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by yuuto on 6/6/17.
 */

@RunWith(Parameterized.class)
public class PathUtilsTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"http://localhost:8080/login", "/login"},
                {"http://localhost:8080/", "/"},
                {"http://localhost:8080/login?error=2", "/login?error=2"},
                {"http://localhost:8080/admin/test", "/admin/test"}
        });
    }

    private String fullUrl;
    private String path;

    public PathUtilsTest(String fullUrl, String path) {
        this.fullUrl = fullUrl;
        this.path = path;
    }

    @Test
    public void testPathUtils(){
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer(fullUrl));
        Assert.assertEquals(path, PathUtils.getContextPath(request));
    }
}
