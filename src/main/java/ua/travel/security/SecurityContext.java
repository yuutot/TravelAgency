package ua.travel.security;

import ua.travel.command.utils.PathUtils;
import ua.travel.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.logging.Logger;

/**
 * Implements security
 */
public class SecurityContext {

    private static final Logger LOGGER = Logger.getLogger(SecurityContext.class.getName());
    private static final String PAGE = "/execute";
    private static final String COMMAND = "command";
    private static final String IMG = "img/";
    private static final String CSS = "css/";
    private static final String STYLE = "style/";
    private static final String FONT = "font/";
    private static final String PHOTO = "photo/";

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

    /**
     * Add Necessary credentials for command/page
     *
     * @param command
     * @param userTypes
     * @return builder
     */
    public SecurityContext addCredentials(String command, String... userTypes) {
        List<String> credentials = rights.computeIfAbsent(command, k -> new ArrayList<>());
        credentials.addAll(Arrays.asList(userTypes));
        return this;
    }

    public boolean checkUserCredentials(HttpServletRequest request, User user) {
        String url = PathUtils.getContextPath(request);
        List<String> credentials;
        if (url.equals(PAGE)) {
            String command = request.getParameter(COMMAND);
            credentials = rights.get(command);
            LOGGER.info("User " + user + " try to get access to " + command + " credentials: " + credentials);
        } else if (url.contains(IMG) || url.contains(PHOTO) || url.contains(CSS) || url.contains(STYLE) || url.contains(FONT)) {
            return true;
        } else {
            credentials = rights.get(url);
            LOGGER.info("User " + user + " try to get access to " + url + " credentials: " + credentials);
        }
        return credentials == null || credentials.contains("all") || user != null && credentials.contains(user.getUserType().getType());
    }
}
