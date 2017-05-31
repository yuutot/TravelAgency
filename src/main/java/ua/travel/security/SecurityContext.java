package ua.travel.security;

import ua.travel.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuuto on 5/31/17.
 */
public class SecurityContext {

    private static SecurityContext securityContext;
    private Map<String, String> rights = new HashMap<>();

    private SecurityContext() {}

    public synchronized static SecurityContext newInstance(){
        if(securityContext == null){
            securityContext = new SecurityContext();
        }
        return securityContext;
    }

    public SecurityContext addCredentials(String url, String userType){
        rights.put(url,userType);
        return this;
    }

    public boolean checkUserCredentials(String url, User user) {
        String userType = rights.get(url);
        return userType != null && userType.equals(user.getUserType().getType());
    }
}
