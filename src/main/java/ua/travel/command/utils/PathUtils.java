package ua.travel.command.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuuto on 5/30/17.
 */
public class PathUtils {
    public static String getContextPath(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        Matcher m = Pattern.compile("http://[^/]+").matcher(url);
        if (m.find()) {
            url = url.substring(m.end(), url.length());
        }
        return url;
    }
}
