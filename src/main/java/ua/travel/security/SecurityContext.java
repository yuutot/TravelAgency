package ua.travel.security;

import ua.travel.command.utils.PathUtils;
import ua.travel.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by yuuto on 5/31/17.
 */
public class SecurityContext {

    private final Logger LOGGER = Logger.getLogger(SecurityContext.class.getName());

    private static SecurityContext securityContext;
    private Map<String, List<String>> rights = new HashMap<>();

    private SecurityContext() {
    }

    public static SecurityContext getInstance() {
        SecurityContext localInstance = securityContext;
        if (localInstance == null) {
            synchronized (SecurityContext.class) {
                localInstance = securityContext;
                if (localInstance == null) {
                    securityContext = localInstance = new SecurityContext();
                }
            }
        }
        return localInstance;
    }

    public SecurityContext addCredentials(String command, String... userTypes) {
        List<String> credentials = rights.computeIfAbsent(command, k -> new ArrayList<>());
        credentials.addAll(Arrays.asList(userTypes));
        return this;
    }

    public boolean checkUserCredentials(HttpServletRequest request, User user) {
        String url = PathUtils.getContextPath(request);
        List<String> credentials;
        if (url.equals("/execute")) {
            String command = request.getParameter("command");
            credentials = rights.get(command);
            LOGGER.info("User " + user + " try to get access to " + command + " credentials: " + credentials);
        } else if (url.contains("img/") || url.contains("css/") || url.contains("style/") || url.contains("font/")) {
            return true;
        } else {
            credentials = rights.get(url);
            LOGGER.info("User " + user + " try to get access to " + url + " credentials: " + credentials);
        }
        return credentials == null || credentials.contains("all") || user != null && credentials.contains(user.getUserType().getType());
    }
}
